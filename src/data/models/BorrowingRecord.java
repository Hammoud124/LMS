package data.models;

import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;
import data.repo.users.UserRepository;
import data.repo.users.UserRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BorrowingRecord {
    private int borrowingID;
    private int userID;
    private User user;
    private int bookID;
    private Book book;
    private Date borrowDate;
    private Date returnDate;
    private BookRepository bookRepo;
    private UserRepository userRepo;

    public BorrowingRecord(int borrowingID, int userID, int bookID, Date borrowDate, Date returnDate) {
        bookRepo = BookRepositoryImpl.getInstance();
        userRepo = UserRepositoryImpl.getInstance();
        this.borrowingID = borrowingID;
        this.user = userRepo.getUserInfoById(userID);
        this.userID = userID;
        this.bookID = bookID;
        this.book = bookRepo.getBookInfoByID(bookID);
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public BorrowingRecord() {

    }

    public static BorrowingRecord fromResultSet(ResultSet result) throws SQLException {
        if (!result.next()) {
            return null;
        }
        return new BorrowingRecord()
                .setBorrowingID(result.getInt("BorrowingRecordID"))
                .setUserID(result.getInt("userID"))
                .setBookID(result.getInt("BookID"))
                .setBorrowDate(result.getDate("borrowingDate"))
                .setReturnDate(result.getDate("dueDate"));


    }

    public int getBorrowingID() {
        return borrowingID;
    }

    public BorrowingRecord setBorrowingID(int borrowingID) {
        this.borrowingID = borrowingID;
        return this;
    }

    public int getUserID() {
        return userID;
    }

    public BorrowingRecord setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BorrowingRecord setUser(User user) {
        this.user = user;
        return this;
    }

    public int getBookID() {
        return bookID;
    }

    public BorrowingRecord setBookID(int bookID) {
        this.bookID = bookID;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public BorrowingRecord setBook(Book book) {
        this.book = book;
        return this;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public BorrowingRecord setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public BorrowingRecord setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        return this;
    }
}
