package bookstore.dao;

import bookstore.exception.AuthorNotFoundException;
import bookstore.exception.InvalidInputException;
import bookstore.model.Author;
import bookstore.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Endrit Brahimi
 */
public class AuthorDAO {

    private static ConcurrentHashMap<String, Author> authors = new ConcurrentHashMap<>();

    static {
//        Author author = new Author(UUID.randomUUID().toString(), "Endrit");
//        authors.put(author.getId(), author);
    }

    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    public Author getAuthorById(String id) {
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }
        return author;
    }

    public Author createAuthor(Author author) {
        if (author.getName() == null) {
            throw new InvalidInputException("Author name cannot be null or empty.");
        }

        author.setId(UUID.randomUUID().toString());
        authors.put(author.getId(), author);
        return author;
    }

    public Author updateAuthor(String id, Author updatedAuthor) {
        if (updatedAuthor.getName() == null) {
            throw new InvalidInputException("Author name cannot be null or empty.");
        }

        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        if (updatedAuthor.getName() != null) {
            author.setName(updatedAuthor.getName());
        }

        authors.put(author.getId(), author);
        return author;
    }

    public void deleteAuthor(String id) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        authors.remove(id);
    }

    public List<Book> getBooksByAuthor(String id) {
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        BookDAO bookDAO = new BookDAO();
        List<Book> booksByAuthor = bookDAO.getBooksByAuthor(id);

        return booksByAuthor;
    }

    public boolean validAuthor(String id) {
        return authors.containsKey(id);
    }
}
