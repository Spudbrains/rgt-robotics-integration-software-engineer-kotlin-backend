package com.example.bookstore.controller

import com.example.bookstore.model.Book
import com.example.bookstore.service.BookService
import com.example.bookstore.service.SaleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService,
    private val saleService: SaleService
) {
    
    // Track recent requests to identify duplicates
    private val recentRequests = ConcurrentHashMap<String, Long>()
    
    // Simple cache for recent responses (5 second TTL)
    private val responseCache = ConcurrentHashMap<String, Pair<Map<String, Any>, Long>>()
    
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "status" to "healthy",
            "timestamp" to System.currentTimeMillis(),
            "service" to "bookstore-api"
        ))
    }
    
    @GetMapping
    fun getAllBooks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") limit: Int,
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) genre: String?,
        @RequestParam(defaultValue = "title") sortBy: String,
        @RequestParam(defaultValue = "asc") sortOrder: String
    ): ResponseEntity<Map<String, Any>> {
        val requestId = UUID.randomUUID().toString().substring(0, 8)
        val requestKey = "page=$page&limit=$limit&search=$search&genre=$genre&sortBy=$sortBy&sortOrder=$sortOrder"
        val currentTime = System.currentTimeMillis()
        
        // Check cache first
        val cachedResponse = responseCache[requestKey]
        if (cachedResponse != null && (currentTime - cachedResponse.second) < 5000) {
            println("DEBUG [$requestId]: Returning cached response for page=$page")
            return ResponseEntity.ok()
                .header("Cache-Control", "public, max-age=5")
                .header("X-Request-ID", requestId)
                .header("X-Cached", "true")
                .body(cachedResponse.first)
        }
        
        // Check for duplicate requests within 1 second
        val lastRequestTime = recentRequests[requestKey]
        val isDuplicate = lastRequestTime != null && (currentTime - lastRequestTime) < 1000
        
        // Add debug logging for pagination issues
        println("DEBUG [$requestId]: getAllBooks called with page=$page, limit=$limit, search=$search, genre=$genre")
        if (isDuplicate) {
            println("WARNING [$requestId]: DUPLICATE REQUEST detected! Last request was ${currentTime - lastRequestTime!!}ms ago")
        }
        
        // Update recent requests
        recentRequests[requestKey] = currentTime
        
        // Validate pagination parameters
        val validatedPage = if (page < 0) 0 else page
        val validatedLimit = when {
            limit <= 0 -> 10
            limit > 100 -> 100
            else -> limit
        }
        
        val booksPage = bookService.getAllBooks(validatedPage, validatedLimit, search, genre, sortBy, sortOrder)
        
        val response = mapOf(
            "books" to booksPage.content,
            "total" to booksPage.totalElements,
            "page" to booksPage.number,
            "limit" to booksPage.size,
            "totalPages" to booksPage.totalPages,
            "hasNext" to booksPage.hasNext(),
            "hasPrevious" to booksPage.hasPrevious(),
            "requestId" to requestId,
            "isDuplicate" to isDuplicate,
            "timestamp" to currentTime
        )
        
        // Cache the response
        responseCache[requestKey] = response to currentTime
        
        println("DEBUG [$requestId]: Returning response with page=$validatedPage, totalPages=${booksPage.totalPages}, totalElements=${booksPage.totalElements}, isDuplicate=$isDuplicate")
        
        return ResponseEntity.ok()
            .header("Cache-Control", "no-cache, no-store, must-revalidate")
            .header("Pragma", "no-cache")
            .header("Expires", "0")
            .header("X-Request-ID", requestId)
            .header("X-Is-Duplicate", isDuplicate.toString())
            .body(response)
    }
    
    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): ResponseEntity<Book> {
        val book = bookService.getBookById(id)
        return ResponseEntity.ok(book)
    }
    
    // New endpoint to get book details with sales statistics
    @GetMapping("/{id}/details")
    fun getBookDetailsWithSales(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        val book = bookService.getBookById(id)
        val salesStats = saleService.getSalesStatisticsForBook(id)
        
        val response = mapOf(
            "book" to book,
            "salesStatistics" to salesStats
        )
        
        return ResponseEntity.ok(response)
    }
    
    @PostMapping
    fun createBook(@RequestBody book: Book): ResponseEntity<Book> {
        val createdBook = bookService.createBook(book)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook)
    }
    
    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Long, @RequestBody book: Book): ResponseEntity<Book> {
        val updatedBook = bookService.updateBook(id, book)
        return ResponseEntity.ok(updatedBook)
    }
    
    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): ResponseEntity<Void> {
        bookService.deleteBook(id)
        return ResponseEntity.noContent().build()
    }
    
    @PatchMapping("/{id}/stock")
    fun updateStock(
        @PathVariable id: Long,
        @RequestBody request: Map<String, Int>
    ): ResponseEntity<Book> {
        val newStock = request["stock"] ?: throw IllegalArgumentException("Stock value is required")
        val updatedBook = bookService.updateStock(id, newStock)
        return ResponseEntity.ok(updatedBook)
    }
    
    @PostMapping("/{id}/sell")
    fun sellBook(
        @PathVariable id: Long,
        @RequestBody request: Map<String, Int>
    ): ResponseEntity<Any> {
        val quantity = request["quantity"] ?: 1
        return try {
            val updatedBook = bookService.sellBook(id, quantity)
            ResponseEntity.ok(updatedBook)
        } catch (e: Exception) {
            val errorMsg: Any = e.message ?: "An unknown error occurred."
            ResponseEntity.badRequest().body(mapOf("error" to errorMsg))
        }
    }
} 
