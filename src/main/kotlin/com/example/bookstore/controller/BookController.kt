package com.example.bookstore.controller

import com.example.bookstore.model.Book
import com.example.bookstore.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/books")
@CrossOrigin(
    origins = ["http://localhost:3000", "https://rgt-robotics-integration.vercel.app"],
    allowedHeaders = ["*"],
    methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH],
    allowCredentials = "true"
)
class BookController(private val bookService: BookService) {
    
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
        
        // Add debug logging for pagination issues
        println("DEBUG [$requestId]: getAllBooks called with page=$page, limit=$limit, search=$search, genre=$genre")
        
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
            "page" to validatedPage,
            "limit" to validatedLimit,
            "totalPages" to booksPage.totalPages,
            "hasNext" to booksPage.hasNext(),
            "hasPrevious" to booksPage.hasPrevious(),
            "isFirst" to booksPage.isFirst(),
            "isLast" to booksPage.isLast(),
            "requestId" to requestId
        )
        
        println("DEBUG [$requestId]: Returning response with page=$validatedPage, totalPages=${booksPage.totalPages}, totalElements=${booksPage.totalElements}")
        
        return ResponseEntity.ok()
            .header("Cache-Control", "no-cache, no-store, must-revalidate")
            .header("Pragma", "no-cache")
            .header("Expires", "0")
            .header("X-Request-ID", requestId)
            .body(response)
    }
    
    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Long): ResponseEntity<Book> {
        val book = bookService.getBookById(id)
        return ResponseEntity.ok(book)
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
        val newStock = request["stock"] ?: throw RuntimeException("Stock value is required")
        val updatedBook = bookService.updateStock(id, newStock)
        return ResponseEntity.ok(updatedBook)
    }
    
    @PostMapping("/{id}/sell")
    fun sellBook(
        @PathVariable id: Long,
        @RequestBody request: Map<String, Int>
    ): ResponseEntity<Book> {
        val quantity = request["quantity"] ?: 1
        val updatedBook = bookService.sellBook(id, quantity)
        return ResponseEntity.ok(updatedBook)
    }
} 