package ui.home.admin.panels.copy;

import data.models.BookCopy;
import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class BookCopiesViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final BookRepository bookRepository;
    private List<BookCopy> copies;
    BookCopiesViewModel() {
        bookRepository = BookRepositoryImpl.getInstance();

    }

    void init(int bookId) {

       copies = getAllBookCopies(bookId);
        notifyCopiesChanged(copies);
    }

    private void notifyCopiesChanged(List<BookCopy> copies) {
        support.firePropertyChange("copies", null, copies);
    }

    public void addOnCopiesChangedListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removeOnCopyDeletedListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public List<BookCopy> getAllBookCopies(int bookId) {
        return bookRepository.getAllBookCopies(bookId);
    }

    public void deleteCopyByID(int copyId){
        bookRepository.deleteCopyByID(copyId);
        copies.removeIf(bookCopy -> bookCopy.getCopyID() == copyId);
        notifyCopiesChanged(copies);
    }
}
