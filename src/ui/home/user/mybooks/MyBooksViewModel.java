package ui.home.user.mybooks;

import data.managers.auth.AuthManager;
import data.managers.auth.AuthManagerImpl;
import data.models.Book;
import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class MyBooksViewModel {
    private final BookRepository bookRepository;
    private final AuthManager authManager;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final List<Book> books;

    MyBooksViewModel() {
        bookRepository = BookRepositoryImpl.getInstance();
        authManager = AuthManagerImpl.getInstance();
        books = bookRepository.getMyBooks(authManager.getLoggedInUser().getId());
    }

    public void observeUiState(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public List<Book> getMyBooks() {
        return books;
    }

    public void returnBook(int bookId) {
        if (authManager.getLoggedInUser() == null) {
            return;
        }
        books.removeIf(book -> book.getBookId() == bookId);
        int userId = authManager.getLoggedInUser().getId();
        bookRepository.returnMyBook(userId, bookId);
        support.firePropertyChange("books", null, books);
    }
}
