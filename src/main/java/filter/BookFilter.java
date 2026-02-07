package filter;

import domain.Book;
import java.util.List;
import java.util.stream.Collectors;

public class BookFilter {

    public List<Book> filterByMinRating(List<Book> books, double minRating) {
        return books.stream()
                .filter(book -> book.getRating() >= minRating)
                .collect(Collectors.toList());
    }
}
