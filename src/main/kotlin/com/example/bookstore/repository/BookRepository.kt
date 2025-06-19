package com.example.bookstore.repository

import com.example.bookstore.model.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    
    @Query("""
        SELECT b FROM Book b 
        WHERE (:search IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')) 
        OR LOWER(b.author) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')))
        AND (:genre IS NULL OR LOWER(b.genre) = LOWER(CAST(:genre AS text)))
    """)
    fun findBySearchAndGenre(
        @Param("search") search: String?,
        @Param("genre") genre: String?,
        pageable: Pageable
    ): Page<Book>
    
    fun findByIsbn(isbn: String): Book?
} 