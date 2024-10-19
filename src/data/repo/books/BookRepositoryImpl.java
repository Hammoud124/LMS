/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data.repo.books;

import data.db.books.BookDAO;
import data.db.books.BookDAOImpl;
import data.models.Book;
import data.models.BookCopy;
import data.models.BorrowingRecord;
import data.models.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author GIGABIT
 */
public class BookRepositoryImpl implements BookRepository {

    private final Set<BookChangeListener> bookListeners = new HashSet<>();
    private static final BookRepositoryImpl instance = new BookRepositoryImpl();

    public static BookRepository getInstance() {
        return instance;
    }

    private final BookDAO bookDAO;

    private BookRepositoryImpl() {
        bookDAO = BookDAOImpl.getInstance();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public boolean deleteBookByID(int bookId) {
        return bookDAO.deleteBookByID(bookId);
    }

    @Override
    public boolean addNewBook(Book book) {
        boolean isAdded = bookDAO.addNewBook(book);
        if (isAdded) {
            notifyBookAddedListeners(book);
        }
        return isAdded;
    }

    @Override
    public boolean UpdateBookByID(int bookId, Book book) {
        return bookDAO.UpdateBookByID(bookId, book);
    }

    @Override
    public Book getBookInfoByID(int bookId) {
        return bookDAO.getBookInfoByID(bookId);
    }

    @Override
    public BookCopy.AvailabilityStatus getAvailabilityStatus(int copyId) {
        return bookDAO.getAvailabilityStatus(copyId);
    }

    @Override
    public boolean setAvailabilityStatus(int bookId, BookCopy.AvailabilityStatus availabilityStatus) {
        return bookDAO.setAvailabilityStatus(bookId, availabilityStatus);
    }

    @Override
    public BookCopy getBookCopyInfoByID(int copyId) {
        return bookDAO.getBookCopyInfoByID(copyId);
    }

    @Override
    public void addBookChangeListener(BookChangeListener listener) {
        bookListeners.add(listener);
    }

    @Override
    public void removeBookChangeListener(BookChangeListener listener) {
        bookListeners.remove(listener);
    }

    @Override
    public void addNewBorrowingRecord(BorrowingRecord borrowingRecord) {
        bookDAO.addNewBorrowingRecord(borrowingRecord);
    }

    @Override
    public List<BorrowingRecord> getBorrowingRecords() {
        return bookDAO.getAllBorrowingRecords();
    }

    @Override
    public boolean borrowCopy(int bookId) {
        return bookDAO.borrowCopy(bookId);
    }

    @Override
    public int getCountOfCopiesAvailable(int bookId) {
        return bookDAO.getCountOfCopiesAvailable(bookId);
    }

    @Override
    public List<BookCopy> getAllBookCopies(int bookId) {
        return bookDAO.getAllBookCopies(bookId);
    }

    @Override
    public void deleteCopyByID(int copyId) {
        bookDAO.deleteCopyByID(copyId);
    }

    @Override
    public List<Book> getMyBooks(int userId) {
        return bookDAO.getMyBooks(userId);
    }

    @Override
    public boolean isBookBorrowed(int bookID, int userId) {
        return getMyBooks(userId).stream().anyMatch(book -> book.getBookId() == bookID);
    }

    @Override
    public List<String> getAllCategories() {
        return bookDAO.getAllCategories();
    }

    @Override
    public void returnMyBook(int userId, int bookId) {
        bookDAO.returnMyBook(userId, bookId);
    }

    @Override
    public Genre getGenreByID(int genreId) {
        return bookDAO.getGenreByID(genreId);
    }

    @Override
    public Genre getGenreByName(String genreName) {
        return bookDAO.getGenreByName(genreName);
    }

    private void notifyBookAddedListeners(Book book) {
        bookListeners.forEach(l -> l.onBookAdded(book));
    }
}
