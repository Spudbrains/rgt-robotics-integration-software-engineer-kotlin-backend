package com.example.bookstore

import com.example.bookstore.model.Book
import com.example.bookstore.repository.BookRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataInitializer(private val bookRepository: BookRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Only initialize if no books exist
        if (bookRepository.count() == 0L) {
            val books = listOf(
                Book(
                    title = "The Great Gatsby",
                    author = "F. Scott Fitzgerald",
                    isbn = "9780743273565",
                    price = 12.99,
                    stock = 15,
                    description = "A story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.",
                    genre = "Fiction",
                    imageUrl = "https://example.com/gatsby.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "To Kill a Mockingbird",
                    author = "Harper Lee",
                    isbn = "9780446310789",
                    price = 14.99,
                    stock = 20,
                    description = "The story of young Scout Finch and her father Atticus in a racially divided Alabama town.",
                    genre = "Fiction",
                    imageUrl = "https://example.com/mockingbird.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "1984",
                    author = "George Orwell",
                    isbn = "9780451524935",
                    price = 11.99,
                    stock = 12,
                    description = "A dystopian novel about totalitarianism and surveillance society.",
                    genre = "Fiction",
                    imageUrl = "https://example.com/1984.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Pride and Prejudice",
                    author = "Jane Austen",
                    isbn = "9780141439518",
                    price = 9.99,
                    stock = 18,
                    description = "The story of Elizabeth Bennet and Mr. Darcy in Georgian-era England.",
                    genre = "Romance",
                    imageUrl = "https://example.com/pride.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Hobbit",
                    author = "J.R.R. Tolkien",
                    isbn = "9780547928241",
                    price = 16.99,
                    stock = 25,
                    description = "The adventure of Bilbo Baggins, a hobbit who embarks on a quest with thirteen dwarves.",
                    genre = "Fantasy",
                    imageUrl = "https://example.com/hobbit.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
            
            bookRepository.saveAll(books)
            println("Database initialized with ${books.size} books")
        }
    }
} 