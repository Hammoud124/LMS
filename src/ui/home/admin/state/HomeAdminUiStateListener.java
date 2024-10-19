package ui.home.admin.state;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class HomeAdminUiStateListener implements PropertyChangeListener {
    Consumer<HomeAdminState> onHomeAdminStateChange;

    public HomeAdminUiStateListener(Consumer<HomeAdminState> onChanged) {
        this.onHomeAdminStateChange = onChanged;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals(SearchState.STATE_NAME)) {
            SearchState searchBooksState = (SearchState) evt.getNewValue();
            onHomeAdminStateChange.accept(searchBooksState);
        } else if (propertyName.equals(AddNewBookState.STATE_NAME)) {
            AddNewBookState addNewBookState = (AddNewBookState) evt.getNewValue();
            onHomeAdminStateChange.accept(addNewBookState);
        } else {
            DeleteBookState deleteBookState = (DeleteBookState) evt.getNewValue();
            onHomeAdminStateChange.accept(deleteBookState);
        }
    }
}
