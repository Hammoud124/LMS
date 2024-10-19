package ui.home.user.bookdetails;

import data.managers.auth.AuthManager;
import data.managers.auth.AuthManagerImpl;
import data.models.BorrowingRecord;
import data.models.User;
import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;

import java.util.Date;

public class BookDetailsViewModel {
    private final BookRepository bookRepo;
    private final AuthManager authManager;

    BookDetailsViewModel() {
        bookRepo = BookRepositoryImpl.getInstance();
        authManager = AuthManagerImpl.getInstance();
    }

    public boolean borrowCopy(int bookId) {
        return bookRepo.borrowCopy(bookId);
    }

    public void addNewBorrowingRecord(int bookId) {
        BorrowingRecord borrowingRecord = new BorrowingRecord()
                .setBookID(bookId)
                .setUserID(getUser().getId())
                .setBorrowDate(new Date());

        bookRepo.addNewBorrowingRecord(borrowingRecord);
    }

    public boolean isBookBorrowed(int bookId, int userId) {
        return bookRepo.isBookBorrowed(bookId, userId);
    }

    public User getUser() {
        return authManager.getLoggedInUser();
    }
}
