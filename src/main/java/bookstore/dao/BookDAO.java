package bookstore.dao;

import bookstore.exception.AuthorNotFoundException;
import bookstore.exception.BookNotFoundException;
import bookstore.exception.InvalidInputException;
import bookstore.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class BookDAO {

    // Concurrent HashMap that holds all the books in memory
    // Key: Book ID, Value: Book Object
    private static ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();

    // Just some test data to add at the start
    static {
//        Book book = new Book(UUID.randomUUID().toString(), "Endrit", new Author(), 10, 3);
//        books.put(book.getId(), book);
    }

    // Returns all Books in a ArrayList
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    // Finds Book by ID
    // Returns found Book Object
    public Book getBookById(String id) {
        // Tries to get book from HashMap
        Book book = books.get(id);

        // Validate if Book with ID exists
        // Throws BookNotFoundException if book doesn't exist
        if (book == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        return book;
    }

    // Creates a new Book and stores it in the HashMap
    public Book createBook(Book book) {
        // Validates all required fields are provided
        // Throws InvalidInputException if any field is missing
        if (book.getTitle() == null || book.getAuthor() == null || book.getAuthor().getId() == null
                || book.getPrice() == null || book.getStock() == null || book.getPublicationYear() == null) {
            throw new InvalidInputException("Title, author, author ID, price, stock and publication year must all be provided.");
        }

        // Validate if Author with ID exists
        // Throws AuthorNotFoundException if author doesn't exist
        AuthorDAO authorDAO = new AuthorDAO();
        if (!authorDAO.validAuthor(book.getAuthor().getId())) {
            throw new AuthorNotFoundException("Author not found with ID: " + book.getAuthor().getId());
        }

        // Generate a unigue ID
        // Set Author by getting the object by ID
        book.setId(UUID.randomUUID().toString());
        book.setAuthor(authorDAO.getAuthorById(book.getAuthor().getId()));

        // Stores new Book in the Map
        books.put(book.getId(), book);

        return book;
    }

    // Updates an existing Book's information
    // Returns updated Book Object
    public Book updateBook(String id, Book updatedBook) {
        // Validates at least one field is provided
        // Throws InvalidInputException if all fields are missing
        if (updatedBook.getTitle() == null && updatedBook.getAuthor() == null
                && updatedBook.getPrice() == null && updatedBook.getStock() == null) {
            throw new InvalidInputException("At least one field (title, author, price, or stock) must be provided.");
        }

        // Validate if Book with ID exists
        // Throws BookNotFoundException if book doesn't exist
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        // Updated each field if provided
        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            // Validate if Author with ID exists
            // Throws AuthorNotFoundException if author doesn't exist
            AuthorDAO authorDAO = new AuthorDAO();
            if (!authorDAO.validAuthor(updatedBook.getAuthor().getId())) {
                throw new AuthorNotFoundException("Author not found with ID: " + updatedBook.getAuthor().getId());
            }

            book.setAuthor(authorDAO.getAuthorById(updatedBook.getAuthor().getId()));
        }
        if (updatedBook.getPrice() != null) {
            // Validate if prices isn't smaller than than 0
            // Throws InvalidInputException if true
            if (updatedBook.getPrice() < 0) {
                throw new InvalidInputException("Price cannot be negative.");
            }

            book.setPrice(updatedBook.getPrice());
        }
        if (updatedBook.getStock() != null) {
            // Validate if stock isn't smaller than than 0
            // Throws InvalidInputException if true
            if (updatedBook.getStock() < 0) {
                throw new InvalidInputException("Stock cannot be negative.");
            }

            book.setStock(updatedBook.getStock());
        }
        if (updatedBook.getPublicationYear() != null) {
            book.setPublicationYear(updatedBook.getPublicationYear());
        }

        // Saves updated Book Object
        books.put(book.getId(), book);

        return book;
    }

    // Deletes Book object from the Map
    public void deleteBook(String id) {
        // Validate if Book with ID exists
        // Throws BookNotFoundException if book doesnt exist
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        // Removes Book from the Map
        books.remove(id);
    }

    // Checks if an Author ID exists in the Map, used for other DAOs
    // Returns boolean value
    public boolean validBook(String id) {
        return books.containsKey(id);
    }

    // Gets all Books by a Author
    // Returns ArrayList of Book objects
    public List<Book> getBooksByAuthor(String id) {
        // Makes a ArrayList to store the books
        List<Book> booksByAuthor = new ArrayList<>();

        // Loops through the books in the HashMap
        for (Book book : books.values()) {
            // Checks if the books author ID is the same as the provided ID
            // Adds the book into the ArrayList
            if (id.equals(book.getAuthor().getId())) {
                booksByAuthor.add(book);
            }
        }

        // Returns ArrayList
        return booksByAuthor;
    }
}
