package ui.home.admin.state;

import data.models.Book;

import java.util.List;

public record DeleteBookState(List<Book> books) implements HomeAdminState {
   public static final String STATE_NAME = DeleteBookState.class.getSimpleName();
}
