package com.example.bookstore.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
data class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    val book: Book,
    
    @Column(nullable = false)
    val quantity: Int,
    
    @Column(nullable = false)
    val totalPrice: Double,
    
    @Column(name = "sale_date", nullable = false)
    val saleDate: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) 