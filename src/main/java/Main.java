import csv.CsvBookReader;
import domain.Book;
import service.BookEnrichmentService;
import filter.BookFilter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CsvBookReader reader = new CsvBookReader();
        List<Book> books = reader.readBooksFromCsv("books.csv");

        BookEnrichmentService enrichmentService = new BookEnrichmentService();
        enrichmentService.enrich(books);

        BookFilter filter = new BookFilter();
        List<Book> topBooks = filter.filterByMinRating(books, 4.0);

        topBooks.forEach(System.out::println);
    }
}
