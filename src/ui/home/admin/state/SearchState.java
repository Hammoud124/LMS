package ui.home.admin.state;

import data.models.Book;

import java.util.List;

public record SearchState(List<Book> books) implements HomeAdminState {
   public static final String STATE_NAME = SearchState.class.getSimpleName();
}
