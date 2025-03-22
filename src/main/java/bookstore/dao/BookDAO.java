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

    private static ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();

    static {
//        Book book = new Book(UUID.randomUUID().toString(), "Endrit", new Author(), 10, 3);
//        books.put(book.getId(), book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book getBookById(String id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        return book;
    }

    public Book createBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null
                || book.getPrice() == null || book.getStock() == null) {
            throw new InvalidInputException("Title, author, price, and stock must all be provided.");
        }

        AuthorDAO authorDAO = new AuthorDAO();
        if (!authorDAO.validAuthor(book.getAuthor().getId())) {
            throw new AuthorNotFoundException("Author not found with ID: " + book.getAuthor().getId());
        }

        book.setId(UUID.randomUUID().toString());
        book.setAuthor(authorDAO.getAuthorById(book.getAuthor().getId()));
        books.put(book.getId(), book);
        return book;
    }

    public Book updateBook(String id, Book updatedBook) {
        if (updatedBook.getTitle() == null && updatedBook.getAuthor() == null
                && updatedBook.getPrice() == null && updatedBook.getStock() == null) {
            throw new InvalidInputException("At least one field (title, author, price, or stock) must be provided.");
        }

        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        if (updatedBook.getTitle() != null) {
            book.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            AuthorDAO authorDAO = new AuthorDAO();
            if (!authorDAO.validAuthor(updatedBook.getAuthor().getId())) {
                throw new AuthorNotFoundException("Author not found with ID: " + updatedBook.getAuthor().getId());
            }
            book.setAuthor(authorDAO.getAuthorById(updatedBook.getAuthor().getId()));
        }
        if (updatedBook.getPrice() != null) {
            if (updatedBook.getPrice() < 0) {
                throw new InvalidInputException("Price cannot be negative.");
            }

            book.setPrice(updatedBook.getPrice());
        }
        if (updatedBook.getStock() != null) {
            if (updatedBook.getStock() < 0) {
                throw new InvalidInputException("Stock cannot be negative.");
            }

            book.setStock(updatedBook.getStock());
        }

        books.put(book.getId(), book);
        return book;
    }

    public void deleteBook(String id) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }

        books.remove(id);
    }

    public boolean validBook(String id) {
        return books.containsKey(id);
    }

    public List<Book> getBooksByAuthor(String id) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : books.values()) {
            if (id.equals(book.getAuthor().getId())) {
                booksByAuthor.add(book);
            }
        }

        return booksByAuthor;
    }
}
