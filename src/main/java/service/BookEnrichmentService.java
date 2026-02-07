package service;

import domain.Book;
import google.GoogleBookRating;
import java.util.List;

public class BookEnrichmentService {

    private final GoogleBookRating googleBookRating = new GoogleBookRating();

    public void enrich(List<Book> books) {
        googleBookRating.enrichBooksWithRatings(books);
        // Здесь можно добавить другие источники данных, например локальные csv или DB
    }
}
