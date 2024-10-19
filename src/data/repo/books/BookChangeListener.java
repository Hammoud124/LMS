package data.repo.books;

import data.models.Book;

public interface BookChangeListener {
    void onBookAdded(Book book);
    //void onBookDeleted();
    //void onBookEdited(Book book);
}
