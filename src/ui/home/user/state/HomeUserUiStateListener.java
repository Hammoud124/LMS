package ui.home.user.state;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class HomeUserUiStateListener implements PropertyChangeListener {
    Consumer<HomeUserState> userStateConsumer;

    public HomeUserUiStateListener(Consumer<HomeUserState> userStateConsumer) {
        this.userStateConsumer = userStateConsumer;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals(SearchState.STATE_NAME)) {
            SearchState searchState = (SearchState) evt.getNewValue();
            userStateConsumer.accept(searchState);
        } else if (propertyName.equals(FilterState.STATE_NAME)) {
            FilterState filterState = (FilterState) evt.getNewValue();
            userStateConsumer.accept(filterState);
        }
    }
}
