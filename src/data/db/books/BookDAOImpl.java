

package data.db.books;

import config.DBConfig;
import data.models.Book;
import data.models.BookCopy;
import data.models.BorrowingRecord;
import data.models.Genre;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class BookDAOImpl implements BookDAO {

    private static final BookDAOImpl instance = new BookDAOImpl();

    private final SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");

    public static BookDAO getInstance() {
        return instance;
    }

    private BookDAOImpl() {

    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USER, DBConfig.PASSWORD);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT\n" +
                    "    b.*,\n" +
                    "    CASE\n" +
                    "        WHEN SUM(CASE WHEN bc.AvailabilityStatus = 'available' THEN 1 ELSE 0 END) > 0\n" +
                    "            THEN 1\n" +
                    "        ELSE 2\n" +
                    "        END AS availability_status\n" +
                    "FROM book b\n" +
                    "         LEFT JOIN bookcopies bc ON b.book_id = bc.BookID\n" +
                    "where b.is_deleted = false\n" +
                    "GROUP BY b.book_id;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(Book.fromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public boolean deleteBookByID(int bookId) {

        try (Connection conn = getConnection()) {
            String sql = "update book\n" +
                    "set is_deleted = true\n" +
                    "where book_id = ?\n";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addNewBook(Book book) {
        try (Connection conn = getConnection()) {
            String sql = "insert into book (title,author,isbn,publication_date,genre_id,pages,additional_details,image_path) values (?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);


            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getISBN());
            stmt.setString(4, formatter.format(book.getPublicationDate()));
            stmt.setInt(5, book.getGenre_id());
            stmt.setInt(6, book.getPages());
            stmt.setString(7, book.getAdditionalDetails());
            stmt.setString(8, book.getImagePath());

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean UpdateBookByID(int bookId, Book book) {
        try (Connection conn = getConnection()) {
            String sql = "update book "
                    + "set title = ?,"
                    + "author = ?,"
                    + "isbn = ?,"
                    + "publication_date = ?,"
                    + "genre_id = ?,"
                    + "pages = ?,"
                    + "additional_details = ?,"
                    + "image_path = ?"
                    + " where book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getISBN());
            stmt.setString(4, book.getFormattedPublicationDate());
            stmt.setInt(5, book.getGenre_id());
            stmt.setInt(6, book.getPages());
            stmt.setString(7, book.getAdditionalDetails());
            stmt.setString(8, book.getImagePath());
            stmt.setInt(9, bookId);

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Book getBookInfoByID(int bookId) {
        Book book = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM book where book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return book = Book.fromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    @Nullable
    @Override
    public BookCopy.AvailabilityStatus getAvailabilityStatus(int copyId) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT  AvailabilityStatus+0 as value from bookcopies where CopyID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, copyId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return BookCopy.AvailabilityStatus.fromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean setAvailabilityStatus(int copyId, BookCopy.AvailabilityStatus availabilityStatus) {
        try (Connection conn = getConnection()) {
            String sql = "update bookcopies\n" +
                    "set AvailabilityStatus = ?\n" +
                    "where CopyID = ?";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, availabilityStatus.id());
            stmt.setInt(2, copyId);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public BookCopy getBookCopyInfoByID(int copyId) {
        BookCopy bookCopy = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM bookcopies where copyID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, copyId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return bookCopy = BookCopy.fromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookCopy;
    }

    @Override
    public List<BorrowingRecord> getAllBorrowingRecords() {

        List<BorrowingRecord> borrowingRecords = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM borrowingrecords";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                borrowingRecords.add(BorrowingRecord.fromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return borrowingRecords;
    }

    @Override
    public void addNewBorrowingRecord(BorrowingRecord borrowingRecord) {

        try (Connection conn = getConnection()) {
            String sql = "insert into borrowingrecords (UserID,BookID,BorrowingDate,DueDate) values (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);


            stmt.setInt(1, borrowingRecord.getUserID());
            stmt.setInt(2, borrowingRecord.getBookID());
            stmt.setString(3, formatter.format(borrowingRecord.getBorrowDate()));
            if (borrowingRecord.getReturnDate() != null) {
                stmt.setString(4, formatter.format(borrowingRecord.getReturnDate()));
            }
            stmt.setString(4, null);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean borrowCopy(int bookId) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE bookcopies bc\n" +
                    "    JOIN (\n" +
                    "        SELECT CopyID\n" +
                    "        FROM bookcopies\n" +
                    "        WHERE BookID = ? AND AvailabilityStatus = 'AVAILABLE'\n" +
                    "        LIMIT 1\n" +
                    "    ) AS random_copy ON bc.CopyID = random_copy.CopyID\n" +
                    "SET bc.AvailabilityStatus = 'Borrowed'\n" +
                    "WHERE bc.CopyID = random_copy.CopyID;";


            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public int getCountOfCopiesAvailable(int bookId) {
        try (Connection conn = getConnection()) {
            String sql = "select count(CopyID) as numOfCopies\n" +
                    "from bookcopies\n" +
                    "where AvailabilityStatus = 'AVAILABLE' and BookID = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            return rs.getInt("numOfCopies");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<BookCopy> getAllBookCopies(int bookId) {
        List<BookCopy> copies = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT CopyID,BookID,AvailabilityStatus+0 FROM bookcopies\n" +
                    "where BookID = ? and is_deleted = false";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                copies.add(BookCopy.fromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return copies;
    }

    @Override
    public void deleteCopyByID(int copyId) {

        try (Connection conn = getConnection()) {
            String sql = "update bookcopies\n" +
                    "set is_deleted = true\n" +
                    "where CopyID = ?\n";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, copyId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Book> getMyBooks(int userId) {
        List<Book> books = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "select *\n" +
                    "from book\n" +
                    "where book.book_id in (select BookID\n" +
                    "                 from borrowingrecords\n" +
                    "                 where UserID = ? and is_active = true)\n" +
                    "and is_deleted = false";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(Book.fromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "select name from genre";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public void returnMyBook(int userId, int bookId) {
        try (Connection conn = getConnection()) {
            String sql = "update borrowingrecords\n" +
                    "set is_active = false\n" +
                    "where BookID = ? and UserID = ?\n";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Genre getGenreByID(int genreId) {
        Genre genre = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM genre where genre_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, genreId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return genre = Genre.fromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return genre;
    }

    @Override
    public Genre getGenreByName(String genreName) {
        Genre genre = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM genre where name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, genreName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return genre = Genre.fromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return genre;
    }
}