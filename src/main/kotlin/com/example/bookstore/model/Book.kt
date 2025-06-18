package com.example.bookstore.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    var title: String,
    
    @Column(nullable = false)
    var author: String,
    
    @Column(nullable = false, unique = true)
    var isbn: String,
    
    @Column(nullable = false)
    var price: Double,
    
    @Column(nullable = false)
    var stock: Int,
    
    @Column(columnDefinition = "TEXT")
    var description: String? = null,
    
    @Column(name = "published_date")
    var publishedDate: String? = null,
    
    var genre: String? = null,
    
    @Column(name = "image_url")
    var imageUrl: String? = null,
    
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
} 