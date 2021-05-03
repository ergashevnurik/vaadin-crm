package com.vaadin.crm.ui.views.user;

import com.vaadin.crm.backend.Users.entity.Role;
import com.vaadin.crm.backend.Users.entity.User;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class UserForm extends FormLayout {

    private User user;

    private TextField usernameField = new TextField();
    private TextField passwordField = new TextField();
    private ComboBox<Role> userRoles = new ComboBox<>();

    private Button addButton;
    private Button deleteButton;
    private Button cancelButton;

    private HorizontalLayout horizontalLayoutForButtons;
    private VerticalLayout verticalLayoutForTextFieldAndComboBoxes;

    private Binder<User> bind = new Binder<>(User.class);

    public UserForm(List<User> users) {
        addClassName("contact-form");
        // bind.bindInstanceFields(this);
        userRoles.setItems(Role.values());

        add(initFieldAndComboBox(), initButtons());
    }

    private HorizontalLayout initButtons() {
        bind.addStatusChangeListener(evt -> addButton.setEnabled(bind.isValid()));
        horizontalLayoutForButtons = new HorizontalLayout();

        addButton = new Button();
        addButton.setText("Add");
        addButton.addClickListener(click -> validateAndSave());
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        addButton.addClickShortcut(Key.ENTER);


        deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.addClickListener(click -> fireEvent(new UserForm.DeleteEvent(this, user)));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickShortcut(Key.DELETE);

        cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.addClickListener(click -> fireEvent(new UserForm.CancelEvent(this)));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickShortcut(Key.ESCAPE);

        horizontalLayoutForButtons.add(addButton, deleteButton, cancelButton);

        return horizontalLayoutForButtons;
    }

    private VerticalLayout initFieldAndComboBox() {
        verticalLayoutForTextFieldAndComboBoxes = new VerticalLayout();

        usernameField.setPlaceholder("Enter Username");
        passwordField.setPlaceholder("Enter Password");

        verticalLayoutForTextFieldAndComboBoxes.add(usernameField, passwordField, userRoles);
        return verticalLayoutForTextFieldAndComboBoxes;
    }

    private void validateAndSave() {
        try {
            bind.writeBean(user);
            fireEvent(new UserForm.SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

    public void setOrder(User user) {
        this.user = user;
        bind.readBean(user);
    }

    public static abstract class UserFormEvent extends ComponentEvent<UserForm> {
        private User user;

        public UserFormEvent(UserForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserFormEvent {
        public SaveEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UserFormEvent {
        public DeleteEvent(UserForm source, User user) {
            super(source, user);
        }
    }

    public static class CancelEvent extends UserFormEvent {
        public CancelEvent(UserForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
