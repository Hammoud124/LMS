
package data.models;

import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;
import org.jetbrains.annotations.Nullable;
import util.ResultSetUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Book {

    private int bookId;
    private String title;
    private String author;
    private String ISBN;
    private Date publicationDate;
    private int genre_id;
    private Genre genre;
    private int pages;
    private String additionalDetails;
    private String imagePath;
    private BookCopy.AvailabilityStatus availabilityStatus;
    public final static SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
    private static BookRepository bookRepository;

    private static final String COLUMN_BOOK_ID = "book_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_ISBN = "ISBN";
    private static final String COLUMN_GENRE = "genre_id";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_ADDITIONAL_DETAILS = "additional_details";
    private static final String COLUMN_IMAGE_PATH = "image_Path";
    private static final String COLUMN_AVAILABILITY_STATUS = "availability_status";
    private static final String COLUMN_DATE = "publication_date";


    public Book(int bookId, String title, String author,
                String ISBN, Date publicationDate, int genreId,
                int pages, String additionalDetails, String imagePath) {
        bookRepository = BookRepositoryImpl.getInstance();
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationDate = publicationDate;
        this.genre_id = genreId;
        this.genre = bookRepository.getGenreByID(genreId);
        this.pages = pages;
        this.additionalDetails = additionalDetails;
        this.imagePath = imagePath;
    }

    public static Book fromResultSet(ResultSet result) throws SQLException, ParseException {
        BookCopy.AvailabilityStatus availabilityStatus = ResultSetUtil.hasColumn(result, COLUMN_AVAILABILITY_STATUS)
                ? BookCopy.AvailabilityStatus.values()[result.getInt(COLUMN_AVAILABILITY_STATUS) - 1]
                : null;
        bookRepository = BookRepositoryImpl.getInstance();
        return new Book()
                .setBookId(result.getInt(COLUMN_BOOK_ID))
                .setTitle(result.getString(COLUMN_TITLE))
                .setAuthor(result.getString(COLUMN_AUTHOR))
                .setISBN(result.getString(COLUMN_ISBN))
                .setGenre_id(result.getInt(COLUMN_GENRE))
                .setPublicationDate(formatter.parse(result.getString(COLUMN_DATE)))
                .setAdditionalDetails(result.getString(COLUMN_ADDITIONAL_DETAILS))
                .setPages(result.getInt(COLUMN_PAGES))
                .setImagePath(result.getString(COLUMN_IMAGE_PATH))
                .setAvailabilityStatus(availabilityStatus)
                .setGenre(bookRepository.getGenreByID(result.getInt(COLUMN_GENRE)));
    }

    public Book() {
    }

    public BookCopy.AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public Book setAvailabilityStatus(BookCopy.AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
        return this;
    }

    public int getBookId() {
        return bookId;
    }

    public Book setBookId(int bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getISBN() {
        return ISBN;
    }

    public Book setISBN(String ISBN) {
        this.ISBN = ISBN;
        return this;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    @Nullable
    public String getFormattedPublicationDate() {
        if (publicationDate == null) {
            return null;
        }
        return formatter.format(publicationDate);
    }

    public Book setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
        return this;

    }

    public int getGenre_id() {
        return genre_id;
    }

    public Book setGenre_id(String genre) {
        this.genre_id = setGenre(bookRepository.getGenreByName(genre)).genre_id;
        return this;
    }

    public Book setGenre_id(int genre_id) {
        this.genre_id = genre_id;
        return this;
    }

    public int getPages() {
        return pages;
    }

    public Book setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public Book setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
        return this;
    }

    @Nullable
    public String getImagePath() {
        return imagePath;
    }

    public String getImagePathOrDefault() {
        if (imagePath == null) {
            return "C:\\Users\\GIGABIT\\Downloads\\book (1).png";
        }
        return imagePath;
    }

    public Book setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public Genre getGenre() {
        return genre;
    }

    public Book setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }
}
