package util;

import org.jetbrains.annotations.Nullable;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class DefaultDocumentListener implements DocumentListener {

    @Nullable
    private final Consumer<DocumentEvent> onChanged;

    public DefaultDocumentListener(@Nullable Consumer<DocumentEvent> onChanged) {
        this.onChanged = onChanged;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (onChanged != null) {
            onChanged.accept(e);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (onChanged != null) {
            onChanged.accept(e);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (onChanged != null) {
            onChanged.accept(e);
        }
    }
}
