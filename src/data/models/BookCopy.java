package data.models;

import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCopy {

    private int copyID;
    private int bookID;
    private Book book;
    private AvailabilityStatus availabilityStatus;
    private final BookRepository bookRepo;

    public BookCopy(int bookID, int copyID, AvailabilityStatus availabilityStatus) {
        bookRepo = BookRepositoryImpl.getInstance();
        this.bookID = bookID;
        this.copyID = copyID;
        this.book = bookRepo.getBookInfoByID(bookID);
        this.availabilityStatus = availabilityStatus;
    }

    public BookCopy() {
        bookRepo = BookRepositoryImpl.getInstance();

    }

    public static BookCopy fromResultSet(ResultSet result) throws SQLException {
        return new BookCopy()
                .setBookID(result.getInt("bookID"))
                .setCopyID(result.getInt("copyID"))
                .setAvailabilityStatus(result.getInt("AvailabilityStatus+0"))
                .setBook(new BookCopy().bookRepo.getBookInfoByID(result.getInt("bookID")));


    }

    public enum AvailabilityStatus {
        AVAILABLE,
        UNAVAILABLE,
        BORROWED;

        public int id() {
            return ordinal() + 1;
        }

        public static AvailabilityStatus fromResultSet(ResultSet result) throws SQLException {
            final int value = result.getInt("value") - 1;
            return AvailabilityStatus.values()[value];
        }
    }

    public int getCopyID() {
        return copyID;
    }

    public BookCopy setCopyID(int copyID) {
        this.copyID = copyID;
        return this;
    }

    public int getBookID() {
        return bookID;
    }

    public BookCopy setBookID(int bookID) {
        this.bookID = bookID;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public BookCopy setBook(Book book) {
        this.book = book;
        return this;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public BookCopy setAvailabilityStatus(int availabilityStatus) {
        this.availabilityStatus = AvailabilityStatus.values()[availabilityStatus - 1];
        return this;
    }
}
