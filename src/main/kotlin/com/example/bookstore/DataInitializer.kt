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
                ),
                Book(
                    title = "Brave New World",
                    author = "Aldous Huxley",
                    isbn = "9780060850524",
                    price = 13.50,
                    stock = 10,
                    description = "A futuristic society driven by technology, consumerism, and genetic engineering.",
                    genre = "Science Fiction",
                    imageUrl = "https://example.com/bravenewworld.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Jane Eyre",
                    author = "Charlotte Brontë",
                    isbn = "9780141441146",
                    price = 10.99,
                    stock = 14,
                    description = "The emotional and moral development of a young orphaned girl.",
                    genre = "Romance",
                    imageUrl = "https://example.com/janeeyre.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Catcher in the Rye",
                    author = "J.D. Salinger",
                    isbn = "9780316769488",
                    price = 12.50,
                    stock = 13,
                    description = "A teenage boy's journey through alienation and rebellion in 1950s America.",
                    genre = "Fiction",
                    imageUrl = "https://example.com/catcher.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Moby-Dick",
                    author = "Herman Melville",
                    isbn = "9780142437247",
                    price = 15.00,
                    stock = 9,
                    description = "Captain Ahab's obsessive quest to kill the white whale, Moby-Dick.",
                    genre = "Adventure",
                    imageUrl = "https://example.com/mobydick.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Frankenstein",
                    author = "Mary Shelley",
                    isbn = "9780486282114",
                    price = 8.99,
                    stock = 16,
                    description = "The haunting story of a scientist who creates a sentient creature.",
                    genre = "Horror",
                    imageUrl = "https://example.com/frankenstein.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Crime and Punishment",
                    author = "Fyodor Dostoevsky",
                    isbn = "9780140449136",
                    price = 13.99,
                    stock = 11,
                    description = "A psychological drama of a man who commits a murder and struggles with guilt.",
                    genre = "Classic",
                    imageUrl = "https://example.com/crime.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Alchemist",
                    author = "Paulo Coelho",
                    isbn = "9780061122415",
                    price = 11.99,
                    stock = 20,
                    description = "A young shepherd's journey to find his personal legend and destiny.",
                    genre = "Adventure",
                    imageUrl = "https://example.com/alchemist.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Wuthering Heights",
                    author = "Emily Brontë",
                    isbn = "9780141439556",
                    price = 10.50,
                    stock = 12,
                    description = "A tale of love and revenge on the Yorkshire moors.",
                    genre = "Romance",
                    imageUrl = "https://example.com/wuthering.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Lord of the Rings",
                    author = "J.R.R. Tolkien",
                    isbn = "9780618640157",
                    price = 22.99,
                    stock = 18,
                    description = "The epic struggle of good vs. evil in Middle-earth.",
                    genre = "Fantasy",
                    imageUrl = "https://example.com/lotr.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Picture of Dorian Gray",
                    author = "Oscar Wilde",
                    isbn = "9780141439570",
                    price = 9.50,
                    stock = 15,
                    description = "A young man's quest for eternal youth at the cost of his soul.",
                    genre = "Gothic",
                    imageUrl = "https://example.com/doriangray.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Dracula",
                    author = "Bram Stoker",
                    isbn = "9780141439846",
                    price = 9.99,
                    stock = 17,
                    description = "The classic vampire novel that introduced Count Dracula.",
                    genre = "Horror",
                    imageUrl = "https://example.com/dracula.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Fahrenheit 451",
                    author = "Ray Bradbury",
                    isbn = "9781451673319",
                    price = 12.00,
                    stock = 13,
                    description = "A dystopian world where books are banned and burned.",
                    genre = "Science Fiction",
                    imageUrl = "https://example.com/fahrenheit.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Brothers Karamazov",
                    author = "Fyodor Dostoevsky",
                    isbn = "9780374528379",
                    price = 14.50,
                    stock = 10,
                    description = "A philosophical novel about morality, faith, and family dynamics.",
                    genre = "Classic",
                    imageUrl = "https://example.com/karamazov.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "Les Misérables",
                    author = "Victor Hugo",
                    isbn = "9780451419439",
                    price = 18.00,
                    stock = 8,
                    description = "The journey of ex-convict Jean Valjean during post-revolutionary France.",
                    genre = "Historical",
                    imageUrl = "https://example.com/lesmiserables.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                ),
                Book(
                    title = "The Odyssey",
                    author = "Homer",
                    isbn = "9780140268867",
                    price = 10.00,
                    stock = 22,
                    description = "The epic journey of Odysseus as he returns home from the Trojan War.",
                    genre = "Epic",
                    imageUrl = "https://example.com/odyssey.jpg",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
            
            bookRepository.saveAll(books)
            println("Database initialized with ${books.size} books")
        }
    }
} 