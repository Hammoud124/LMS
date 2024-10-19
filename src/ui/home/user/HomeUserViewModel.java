package ui.home.user;

import data.models.Book;
import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;
import org.jetbrains.annotations.Nullable;
import ui.home.user.state.FilterState;
import ui.home.user.state.SearchState;
import util.TextUtil;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Objects;

public class HomeUserViewModel {
    private final BookRepository bookRepo;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final List<Book> books;

    HomeUserViewModel() {
        bookRepo = BookRepositoryImpl.getInstance();
        books = bookRepo.getAllBooks();
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void observerUiState(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removeObserverUiState(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void searchBooks(@Nullable String searchText) {
        if (TextUtil.isEmpty(searchText)) {
            support.firePropertyChange(SearchState.STATE_NAME, null, new SearchState(books));
            return;
        }

        List<Book> bookList = books.stream().filter(book -> book.getTitle().contains(searchText)).toList();
        support.firePropertyChange(SearchState.STATE_NAME, books, new SearchState(bookList));
    }

    public void filterBooksByGenre(@Nullable String genre) {
        if (Objects.equals(genre, "none")) {
            support.firePropertyChange(FilterState.STATE_NAME, null, new FilterState(books));
            return;
        }
        List<Book> bookList = books.stream().filter(book -> book.getGenre().getName().equals(genre)).toList();
        support.firePropertyChange(FilterState.STATE_NAME, books, new FilterState(bookList));
    }

    public List<String> getAllCategories() {
        return bookRepo.getAllCategories();
    }

}

