package com.example.bookstore.controller

import com.example.bookstore.model.Book
import com.example.bookstore.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = ["http://localhost:3000"])
class BookController(private val bookService: BookService) {
    
    @GetMapping
    fun getAllBooks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") limit: Int,
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) genre: String?,
        @RequestParam(defaultValue = "title") sortBy: String,
        @RequestParam(defaultValue = "asc") sortOrder: String
    ): ResponseEntity<Map<String, Any>> {
        val booksPage = bookService.getAllBooks(page, limit, search, genre, sortBy, sortOrder)
        
        val response = mapOf(
            "books" to booksPage.content,
            "total" to booksPage.totalElements,
            "page" to page,
            "limit" to limit,
            "totalPages" to booksPage.totalPages
        )
        
        return ResponseEntity.ok(response)
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