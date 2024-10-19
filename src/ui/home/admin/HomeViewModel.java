package ui.home.admin;

import data.models.Book;
import data.models.BookCopy;
import data.repo.books.BookChangeListener;
import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;
import org.jetbrains.annotations.Nullable;
import ui.home.admin.state.AddNewBookState;
import ui.home.admin.state.DeleteBookState;
import ui.home.admin.state.SearchState;
import util.TextUtil;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class HomeViewModel implements BookChangeListener {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final BookRepository bookRepository;
    private final List<Book> books;
    private boolean isAddScreenVisible;

    public boolean isAddScreenVisible() {
        return isAddScreenVisible;
    }

    public void setAddScreenVisible(boolean isAddScreenVisible) {
        this.isAddScreenVisible = isAddScreenVisible;
    }

    protected HomeViewModel() {
        bookRepository = BookRepositoryImpl.getInstance();
        // add book change listener
        bookRepository.addBookChangeListener(this);
        books = bookRepository.getAllBooks();
    }

    public void observeUiState(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void onBookAdded(Book book) {
        books.add(book);
        notifyBooksChanged(book);
    }

    private void notifyBooksChanged(Book book) {
        support.firePropertyChange(AddNewBookState.STATE_NAME, null, new AddNewBookState(book));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void deleteBookByID(int bookId) {
        bookRepository.deleteBookByID(bookId);
        books.removeIf(book -> book.getBookId() == bookId);
        support.firePropertyChange(DeleteBookState.STATE_NAME, null, new DeleteBookState(books  ));

    }

    public void updateBook(Book book) {
        bookRepository.UpdateBookByID(book.getBookId(), book);
    }

    public void searchBooks(@Nullable String searchText) {
        if (TextUtil.isEmpty(searchText)) {
            support.firePropertyChange(SearchState.STATE_NAME, null, new SearchState(books));
            return;
        }

        List<Book> bookList = books.stream().filter(book -> book.getTitle().contains(searchText)).toList();
        support.firePropertyChange(SearchState.STATE_NAME, books, new SearchState(bookList));
    }

    public List<BookCopy> getAllBookCopies(int bookID) {
        return bookRepository.getAllBookCopies(bookID);
    }

    public boolean hasCopies(int bookId) {
        return !getAllBookCopies(bookId).isEmpty();
    }
}
