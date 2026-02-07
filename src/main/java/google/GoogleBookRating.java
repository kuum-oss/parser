package google;

import domain.Book;
import java.util.List;

public class GoogleBookRating {

    private final GoogleBooksClient client = new GoogleBooksClient();

    public void enrichBooksWithRatings(List<Book> books) {
        for (Book book : books) {
            double rating = client.fetchBookRating(book);
            book.setRating(rating);
        }
    }
}
