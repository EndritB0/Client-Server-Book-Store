package bookstore.dao;

import bookstore.model.Author;
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
    
    static{
        Author author = new Author(UUID.randomUUID().toString(), "Endrit");
        authors.put(author.getId(), author);
    }
    
    public List<Author> getAllAuthors(){
        return new ArrayList<>(authors.values());
    }
    
}
