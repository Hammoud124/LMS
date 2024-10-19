/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data.repo.books;

import data.models.Book;
import data.models.BookCopy;
import data.models.BorrowingRecord;
import data.models.Genre;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface BookRepository {
    List<Book> getAllBooks();

    boolean deleteBookByID(int bookId);

    boolean addNewBook(Book book);

    boolean UpdateBookByID(int bookId, Book book);

    Book getBookInfoByID(int bookId);

    @Nullable
    BookCopy.AvailabilityStatus getAvailabilityStatus(int copyId);

    boolean setAvailabilityStatus(int bookId, BookCopy.AvailabilityStatus availabilityStatus);

    BookCopy getBookCopyInfoByID(int copyId);

    void addBookChangeListener(BookChangeListener listener);

    void removeBookChangeListener(BookChangeListener listener);

    void addNewBorrowingRecord(BorrowingRecord borrowingRecord);

    List<BorrowingRecord> getBorrowingRecords();

    boolean borrowCopy(int bookId);

    int getCountOfCopiesAvailable(int bookId);

    List<BookCopy> getAllBookCopies(int bookId);

    void deleteCopyByID(int copyId);

    List<Book> getMyBooks(int userId);

    boolean isBookBorrowed(int bookID, int userId);

    List<String> getAllCategories();

    void returnMyBook(int userId, int bookId);

    Genre getGenreByID(int genreId);

    Genre getGenreByName(String genreName);
}
