package ui.home.user.state;

import data.models.Book;

import java.util.List;

public record SearchState(List<Book> books) implements HomeUserState {
    public static final String STATE_NAME = SearchState.class.getSimpleName();
}
