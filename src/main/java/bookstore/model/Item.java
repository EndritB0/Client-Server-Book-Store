package bookstore.model;

/**
 *
 * @author Endrit Brahimi
 */
public class Item {

        private Book book;
        private Integer quantity;

        public Item() {
        }

        public Item(Book book, Integer quantity) {
            this.book = book;
            this.quantity = quantity;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
