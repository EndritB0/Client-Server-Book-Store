package bookstore.dao;

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
public class BookDAO {
    
    private static ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();
    
    static{
        Book book = new Book(UUID.randomUUID().toString(), "Endrit", new Author(), 10, 3);
        books.put(book.getId(), book);
    }
    
    public List<Book> getAllBooks(){
        return new ArrayList<>(books.values());
    }
}
