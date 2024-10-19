package ui.home.admin.state;

import data.models.Book;

public record AddNewBookState(Book book) implements HomeAdminState {
   public static final String STATE_NAME = AddNewBookState.class.getSimpleName();
}
