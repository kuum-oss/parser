package domain;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;
    private double rating; // рейтинг книги, по умолчанию 0

    public Book(String title, String author, String isbn, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.rating = 0.0;
    }

    // --- Геттеры и сеттеры ---
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public LocalDate getPublishedDate() { return publishedDate; }
    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return String.format("%s by %s [%s] Rating: %.2f", title, author, isbn, rating);
    }
}
