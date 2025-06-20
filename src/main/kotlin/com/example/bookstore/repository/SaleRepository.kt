package com.example.bookstore.repository

import com.example.bookstore.model.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SaleRepository : JpaRepository<Sale, Long> {
    
    // Get all sales for a specific book
    fun findByBookId(bookId: Long): List<Sale>
    
    // Get total quantity sold for a specific book
    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM Sale s WHERE s.book.id = :bookId")
    fun getTotalQuantitySoldByBookId(@Param("bookId") bookId: Long): Int
    
    // Get total revenue for a specific book
    @Query("SELECT COALESCE(SUM(s.totalPrice), 0.0) FROM Sale s WHERE s.book.id = :bookId")
    fun getTotalRevenueByBookId(@Param("bookId") bookId: Long): Double
    
    // Get sales statistics for all books
    @Query("""
        SELECT 
            b.id as bookId,
            b.title as bookTitle,
            b.author as bookAuthor,
            COALESCE(SUM(s.quantity), 0) as totalQuantitySold,
            COALESCE(SUM(s.totalPrice), 0.0) as totalRevenue,
            COUNT(s.id) as numberOfSales
        FROM Book b
        LEFT JOIN Sale s ON b.id = s.book.id
        GROUP BY b.id, b.title, b.author
        ORDER BY totalQuantitySold DESC
    """)
    fun getSalesStatisticsForAllBooks(): List<Map<String, Any>>
    
    // Get sales statistics for a specific book
    @Query("""
        SELECT 
            b.id as bookId,
            b.title as bookTitle,
            b.author as bookAuthor,
            COALESCE(SUM(s.quantity), 0) as totalQuantitySold,
            COALESCE(SUM(s.totalPrice), 0.0) as totalRevenue,
            COUNT(s.id) as numberOfSales,
            b.stock as currentStock
        FROM Book b
        LEFT JOIN Sale s ON b.id = s.book.id
        WHERE b.id = :bookId
        GROUP BY b.id, b.title, b.author, b.stock
    """)
    fun getSalesStatisticsForBook(@Param("bookId") bookId: Long): Map<String, Any>?
    
    // Get recent sales (last N days)
    @Query("""
        SELECT s FROM Sale s 
        WHERE s.saleDate >= :startDate 
        ORDER BY s.saleDate DESC
    """)
    fun findRecentSales(@Param("startDate") startDate: java.time.LocalDateTime): List<Sale>
    
    // Get top selling books
    @Query("""
        SELECT 
            b.id as bookId,
            b.title as bookTitle,
            b.author as bookAuthor,
            COALESCE(SUM(s.quantity), 0) as totalQuantitySold
        FROM Book b
        LEFT JOIN Sale s ON b.id = s.book.id
        GROUP BY b.id, b.title, b.author
        ORDER BY totalQuantitySold DESC
        LIMIT :limit
    """)
    fun getTopSellingBooks(@Param("limit") limit: Int): List<Map<String, Any>>
} 