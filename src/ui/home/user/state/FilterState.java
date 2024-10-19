package ui.home.user.state;

import data.models.Book;

import java.util.List;

public record FilterState(List<Book> books) implements HomeUserState {
    public static final String STATE_NAME = FilterState.class.getSimpleName();
}
