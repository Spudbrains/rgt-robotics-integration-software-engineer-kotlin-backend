package com.example.bookstore.service

import com.example.bookstore.model.Book
import com.example.bookstore.model.Sale
import com.example.bookstore.repository.BookRepository
import com.example.bookstore.repository.SaleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SaleService(
    private val saleRepository: SaleRepository,
    private val bookRepository: BookRepository
) {
    
    @Transactional
    fun recordSale(bookId: Long, quantity: Int): Sale {
        val book = bookRepository.findById(bookId)
            .orElseThrow { RuntimeException("Book not found with id: $bookId") }
        
        if (book.stock < quantity) {
            throw RuntimeException("Insufficient stock. Available: ${book.stock}, Requested: $quantity")
        }
        
        val sale = Sale(
            book = book,
            quantity = quantity,
            totalPrice = book.price * quantity
        )
        
        book.stock -= quantity
        bookRepository.save(book)
        
        return saleRepository.save(sale)
    }
    
    fun getSalesStatisticsForBook(bookId: Long): Map<String, Any>? {
        return saleRepository.getSalesStatisticsForBook(bookId)
    }
    
    fun getSalesStatisticsForAllBooks(): List<Map<String, Any>> {
        return saleRepository.getSalesStatisticsForAllBooks()
    }
    
    fun getTopSellingBooks(limit: Int = 10): List<Map<String, Any>> {
        return saleRepository.findTopSellingBooks(limit)
    }
    
    fun getRecentSales(days: Int = 30): List<Sale> {
        val startDate = LocalDateTime.now().minusDays(days.toLong())
        return saleRepository.findRecentSales(startDate)
    }
    
    fun getTotalQuantitySoldForBook(bookId: Long): Int {
        return saleRepository.getTotalQuantitySoldByBookId(bookId)
    }
    
    fun getTotalRevenueForBook(bookId: Long): Double {
        return saleRepository.getTotalRevenueByBookId(bookId)
    }
    
    fun getAllSalesForBook(bookId: Long): List<Sale> {
        return saleRepository.findByBookId(bookId)
    }
    
    fun getOverallSalesSummary(): Map<String, Any> {
        val allStats = getSalesStatisticsForAllBooks()
        val totalBooksSold = allStats.sumOf { (it["totalQuantitySold"] as? Number ?: 0).toLong() }
        val totalRevenue = allStats.sumOf { (it["totalRevenue"] as? Number ?: 0).toDouble() }
        val totalSales = allStats.sumOf { (it["numberOfSales"] as? Number ?: 0).toLong() }
        
        return mapOf(
            "totalBooksSold" to totalBooksSold,
            "totalRevenue" to totalRevenue,
            "totalSales" to totalSales,
            "averageRevenuePerSale" to if (totalSales > 0) totalRevenue / totalSales else 0.0,
            "topSellingBooks" to getTopSellingBooks(5)
        )
    }
    
    fun getSales(limit: Int): List<Sale> {
        return saleRepository.findTopSales(org.springframework.data.domain.PageRequest.of(0, limit))
    }
} 