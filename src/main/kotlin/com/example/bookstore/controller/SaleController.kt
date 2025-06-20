package com.example.bookstore.controller

import com.example.bookstore.service.SaleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sales")
class SaleController(private val saleService: SaleService) {
    
    // Get sales statistics for all books
    @GetMapping("/statistics")
    fun getSalesStatistics(): ResponseEntity<Map<String, Any>> {
        val stats = saleService.getSalesStatisticsForAllBooks()
        return ResponseEntity.ok(mapOf(
            "books" to stats,
            "totalBooks" to stats.size
        ))
    }
    
    // Get sales statistics for a specific book
    @GetMapping("/statistics/{bookId}")
    fun getSalesStatisticsForBook(@PathVariable bookId: Long): ResponseEntity<Map<String, Any>> {
        val stats = saleService.getSalesStatisticsForBook(bookId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(stats)
    }
    
    // Get top selling books
    @GetMapping("/top-selling")
    fun getTopSellingBooks(
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<Map<String, Any>> {
        val topBooks = saleService.getTopSellingBooks(limit)
        return ResponseEntity.ok(mapOf(
            "topSellingBooks" to topBooks,
            "limit" to limit
        ))
    }
    
    // Get overall sales summary
    @GetMapping("/summary")
    fun getSalesSummary(): ResponseEntity<Map<String, Any>> {
        val summary = saleService.getOverallSalesSummary()
        return ResponseEntity.ok(summary)
    }
    
    // Get recent sales
    @GetMapping("/recent")
    fun getRecentSales(
        @RequestParam(defaultValue = "30") days: Int
    ): ResponseEntity<Map<String, Any>> {
        val recentSales = saleService.getRecentSales(days)
        val salesData = recentSales.map { sale ->
            mapOf(
                "id" to sale.id,
                "bookId" to sale.book.id,
                "bookTitle" to sale.book.title,
                "quantity" to sale.quantity,
                "totalPrice" to sale.totalPrice,
                "saleDate" to sale.saleDate
            )
        }
        
        return ResponseEntity.ok(mapOf(
            "recentSales" to salesData,
            "days" to days,
            "totalSales" to salesData.size
        ))
    }
    
    // Get total quantity sold for a specific book
    @GetMapping("/{bookId}/quantity")
    fun getTotalQuantitySold(@PathVariable bookId: Long): ResponseEntity<Map<String, Any>> {
        val quantity = saleService.getTotalQuantitySoldForBook(bookId)
        return ResponseEntity.ok(mapOf(
            "bookId" to bookId,
            "totalQuantitySold" to quantity
        ))
    }
    
    // Get total revenue for a specific book
    @GetMapping("/{bookId}/revenue")
    fun getTotalRevenue(@PathVariable bookId: Long): ResponseEntity<Map<String, Any>> {
        val revenue = saleService.getTotalRevenueForBook(bookId)
        return ResponseEntity.ok(mapOf(
            "bookId" to bookId,
            "totalRevenue" to revenue
        ))
    }
    
    // Get all sales for a specific book
    @GetMapping("/{bookId}/history")
    fun getSalesHistory(@PathVariable bookId: Long): ResponseEntity<Map<String, Any>> {
        val sales = saleService.getAllSalesForBook(bookId)
        val salesData = sales.map { sale ->
            mapOf(
                "id" to (sale.id ?: 0L),
                "quantity" to sale.quantity,
                "totalPrice" to sale.totalPrice,
                "saleDate" to sale.saleDate,
                "createdAt" to sale.createdAt
            )
        }
        
        return ResponseEntity.ok(mapOf(
            "bookId" to bookId,
            "salesHistory" to salesData,
            "totalSales" to salesData.size
        ))
    }
    
    // Record a new sale (alternative to the existing sell endpoint)
    @PostMapping("/record")
    fun recordSale(@RequestBody request: Map<String, Any>): ResponseEntity<Map<String, Any?>> {
        val bookId = (request["bookId"] as? Number)?.toLong()
            ?: return ResponseEntity.badRequest().body(mapOf("error" to "Book ID is required and must be a number."))
        val quantity = (request["quantity"] as? Number)?.toInt() ?: 1
        
        try {
            val sale = saleService.recordSale(bookId, quantity)
            val response: Map<String, Any?> = mapOf(
                "message" to "Sale recorded successfully",
                "saleId" to sale.id,
                "bookId" to sale.book.id,
                "remainingStock" to sale.book.stock
            )
            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }
} 