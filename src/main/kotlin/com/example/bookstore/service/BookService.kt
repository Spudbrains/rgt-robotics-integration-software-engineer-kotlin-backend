package com.example.bookstore.service

import com.example.bookstore.model.Book
import com.example.bookstore.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(private val bookRepository: BookRepository) {
    
    fun getAllBooks(
        page: Int = 0,
        size: Int = 10,
        search: String? = null,
        genre: String? = null,
        sortBy: String = "title",
        sortOrder: String = "asc"
    ): Page<Book> {
        println("DEBUG: search=$search ({search?.javaClass?.name}), genre=$genre ({genre?.javaClass?.name}), sortBy=$sortBy ({sortBy.javaClass.name}), sortOrder=$sortOrder ({sortOrder.javaClass.name})")
        val sort = if (sortOrder.equals("desc", ignoreCase = true)) {
            Sort.by(Sort.Direction.DESC, sortBy)
        } else {
            Sort.by(Sort.Direction.ASC, sortBy)
        }
        
        val pageable = PageRequest.of(page, size, sort)
        return bookRepository.findBySearchAndGenre(search, genre, pageable)
    }
    
    fun getBookById(id: Long): Book {
        return bookRepository.findById(id)
            .orElseThrow { RuntimeException("Book not found with id: $id") }
    }
    
    @Transactional
    fun createBook(book: Book): Book {
        bookRepository.findByIsbn(book.isbn)?.let {
            throw RuntimeException("Book with ISBN ${book.isbn} already exists")
        }
        return bookRepository.save(book)
    }
    
    @Transactional
    fun updateBook(id: Long, bookUpdate: Book): Book {
        val existingBook = getBookById(id)
        
        existingBook.apply {
            title = bookUpdate.title
            author = bookUpdate.author
            isbn = bookUpdate.isbn
            price = bookUpdate.price
            stock = bookUpdate.stock
            description = bookUpdate.description
            publishedDate = bookUpdate.publishedDate
            genre = bookUpdate.genre
            imageUrl = bookUpdate.imageUrl
        }
        
        return bookRepository.save(existingBook)
    }
    
    @Transactional
    fun deleteBook(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw RuntimeException("Book not found with id: $id")
        }
        bookRepository.deleteById(id)
    }
    
    @Transactional
    fun updateStock(id: Long, newStock: Int): Book {
        val book = getBookById(id)
        book.stock = newStock
        return bookRepository.save(book)
    }
    
    @Transactional
    fun sellBook(id: Long, quantity: Int = 1): Book {
        val book = getBookById(id)
        
        if (book.stock < quantity) {
            throw RuntimeException("Insufficient stock. Available: ${book.stock}, Requested: $quantity")
        }
        
        book.stock -= quantity
        return bookRepository.save(book)
    }
} 