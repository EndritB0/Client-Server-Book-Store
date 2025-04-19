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

    // Concurrent HashMap that holds all the authors in memory
    // Key: Author ID, Value: Author Object
    private static ConcurrentHashMap<String, Author> authors = new ConcurrentHashMap<>();

    // Just some test data to add at the start
    static {
//        Author author = new Author(UUID.randomUUID().toString(), "Endrit");
//        authors.put(author.getId(), author);
    }

    // Returns all Authors in a ArrayList
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }

    // Finds Author by ID
    // Returns found Author Object
    public Author getAuthorById(String id) {
        // Tries get author from HashMap
        Author author = authors.get(id);

        // Validate if Author with ID exists
        // Throws AuthorNotFoundException if author doesn't exist
        if (author == null) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        return author;
    }

    // Creates a new Author and store it in the HashMap
    public Author createAuthor(Author author) {
        // Checks if Author name is provided
        // Throws InvalidInputException if nothing is provided
        if (author.getName() == null) {
            throw new InvalidInputException("Author name cannot be null or empty.");
        }

        // Generate a unigue ID
        author.setId(UUID.randomUUID().toString());

        // Stores new Author in the Map
        authors.put(author.getId(), author);
        return author;
    }

    // Update an existing Author information
    // Returning Updated Author
    public Author updateAuthor(String id, Author updatedAuthor) {
        // Validate if new Author's inforamtion is valid
        // Throws InvalidInputException if nothing is provided
        if (updatedAuthor.getName() == null) {
            throw new InvalidInputException("Author name cannot be null or empty.");
        }

        // Validate if Author with ID exists
        // Throws AuthorNotFoundException if author doesn't exist
        Author author = authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        // Update Author's Information
        if (updatedAuthor.getName() != null) {
            author.setName(updatedAuthor.getName());
        }

        // Saves updated Author Object
        authors.put(author.getId(), author);
        return author;
    }

    // Delete Author object from the Map
    public void deleteAuthor(String id) {
        // Validate if Author with ID exists
        // Throws AuthorNotFoundException if author doesnt exist
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        // Removes Author from the Map
        authors.remove(id);
    }

    // Retreives all Books written by Author
    // Returns ArrayList of Book objects
    public List<Book> getBooksByAuthor(String id) {
        // Validate if Author with ID exists
        // Throws AuthorNotFoundException if author doesn't exist
        if (!authors.containsKey(id)) {
            throw new AuthorNotFoundException("Author not found with ID: " + id);
        }

        // Uses BookDAO to get ArrayList of Books by Author ID
        BookDAO bookDAO = new BookDAO();
        List<Book> booksByAuthor = bookDAO.getBooksByAuthor(id);

        return booksByAuthor;
    }

    // Checks if an Author ID exists in the Map, used for other DAOs
    // Returns a boolean value
    public boolean validAuthor(String id) {
        return authors.containsKey(id);
    }
}
