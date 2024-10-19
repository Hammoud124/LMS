
package data.db.books;

import data.models.Book;
import data.models.BookCopy;
import data.models.BorrowingRecord;
import data.models.Genre;

import java.util.List;


public interface BookDAO {
    List<Book> getAllBooks();

    boolean deleteBookByID(int bookId);

    boolean addNewBook(Book book);

    boolean UpdateBookByID(int bookId, Book book);

    Book getBookInfoByID(int bookId);

    BookCopy.AvailabilityStatus getAvailabilityStatus(int copyId);

    boolean setAvailabilityStatus(int copyId, BookCopy.AvailabilityStatus availabilityStatus);

    BookCopy getBookCopyInfoByID(int copyId);

    void addNewBorrowingRecord(BorrowingRecord borrowingRecord);

    List<BorrowingRecord> getAllBorrowingRecords();

    boolean borrowCopy(int bookId);

    int getCountOfCopiesAvailable(int bookId);

    List<BookCopy> getAllBookCopies(int bookId);

    void deleteCopyByID(int copyId);

    List<Book> getMyBooks(int userId);

    List<String> getAllCategories();

    void returnMyBook(int userId, int bookId);

    Genre getGenreByID(int genreId);

    Genre getGenreByName(String genreName);
}

