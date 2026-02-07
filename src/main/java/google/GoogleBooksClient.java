package google;

import domain.Book;

public class GoogleBooksClient {

    // Заглушка для обращения к Google Books API
    public double fetchBookRating(Book book) {
        // Логика получения рейтинга через API
        // Сейчас возвращаем рандомный рейтинг для примера
        return Math.round(Math.random() * 50) / 10.0;
    }
}
