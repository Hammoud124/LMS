package ui.home.admin.addnewbook;

import data.models.Book;
import data.repo.books.BookRepository;
import data.repo.books.BookRepositoryImpl;
import org.jetbrains.annotations.Nullable;
import util.TextUtil;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBookViewModel {
    private final BookRepository bookRepository;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String title;
    private String author;
    private String ISBN;
    private Date publicationDate;
    private String genre;
    private int pages;
    private String additionalDetails;
    private String imagePath;
    private final SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");

    private boolean isEditMode;

    AddBookViewModel() {
        bookRepository = BookRepositoryImpl.getInstance();
    }

    void init(@Nullable Book book) {
        isEditMode = book != null;
        if (isEditMode) {
            populateBookDetails(book);
        }
    }

    boolean isEditMode() {
        return isEditMode;
    }

    public void setTitle(String title) {
        this.title = title;
        validate();
    }

    public void setAuthor(String author) {
        this.author = author;
        validate();
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
        validate();
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
        validate();
    }

    public void setPublicationDate(String publicationDate) {
        try {
            setPublicationDate(formatter.parse(publicationDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPages(int pages) {
        this.pages = pages;
        validate();
    }

    public void setGenre(String genre) {
        this.genre = genre;
        validate();
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
        validate();
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        validate();
    }

    public void validate() {
        boolean isValid = !TextUtil.isEmpty(title) && !TextUtil.isEmpty(author)
                && !TextUtil.isEmpty(ISBN) && publicationDate != null && !TextUtil.isEmpty(formatter.format(publicationDate))
                && !TextUtil.isEmpty(genre) && !TextUtil.isEmpty(Integer.toString(pages))
                && !TextUtil.isEmpty(additionalDetails);

        notifyAddEditButtonEnabled(isValid);
    }

    public void addBook() {
        Book book = new Book()
                .setTitle(title)
                .setAuthor(author)
                .setISBN(ISBN)
                .setPublicationDate(publicationDate)
                .setGenre_id(genre)
                .setPages(pages)
                .setAdditionalDetails(additionalDetails);
        bookRepository.addNewBook(book);
    }

    public void addOnAddEditButtonEnabledListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    private void notifyAddEditButtonEnabled(boolean enabled) {
        support.firePropertyChange("isAddEditButtonEnabled", null, enabled);
    }

    public void editBook(Book book) {
        bookRepository.UpdateBookByID(book.getBookId(), book);

    }

    private void populateBookDetails(Book book) {
        setTitle(book.getTitle());
        setAuthor(book.getAuthor());
        setISBN(book.getISBN());
        setPages(book.getPages());
        setPublicationDate((book.getPublicationDate()));
        setGenre(book.getGenre().getName());
        setAdditionalDetails(book.getAdditionalDetails());
        setPages(book.getPages());
    }
}
