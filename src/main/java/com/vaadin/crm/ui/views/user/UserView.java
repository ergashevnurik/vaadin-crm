package com.vaadin.crm.ui.views.user;

import com.vaadin.crm.backend.entity.User;
import com.vaadin.crm.backend.service.UserServices;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user", layout = MainLayout.class)
@PageTitle("Users | Vaadin CRM")
public class UserView extends VerticalLayout {

    private Button addButton;
    private HorizontalLayout horizontalLayoutOfButtonAndTextField;
    private Grid<User> grid = new Grid<>(User.class);
    private TextField filter = new TextField();

    private final UserForm userForm;
    private final UserServices userServices;

    public UserView(UserServices userServices) {
        this.userServices = userServices;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        userForm = new UserForm(userServices.findAll());
        userForm.addListener(UserForm.SaveEvent.class, this::save);
        userForm.addListener(UserForm.DeleteEvent.class, this::delete);
        userForm.addListener(UserForm.CancelEvent.class, e -> clearForm());

        Div content = new Div(grid, userForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        updateList();
        clearForm();
    }

    private void configureGrid() {
        grid.setClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("username", "roles", "password");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> edit(evt.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filter.setPlaceholder("Filter by Username");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> updateList());

        addButton = new Button("", click -> addNewUser());
        Icon addIcon = new Icon(VaadinIcon.PLUS);
        addIcon.getStyle().set("color", "green");
        addButton.setIcon(addIcon);

        horizontalLayoutOfButtonAndTextField = new HorizontalLayout(addButton, filter);
        horizontalLayoutOfButtonAndTextField.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        return horizontalLayoutOfButtonAndTextField;
    }

    private void addNewUser() {
        grid.asSingleSelect().clear();
        edit(new User());
    }

    private void updateList() {
        grid.setItems(userServices.findAll(filter.getValue()));
    }

    private void clearForm() {
        userForm.setOrder(null);
        userForm.setVisible(false);
        removeClassName("editing");
    }

    private void delete(UserForm.DeleteEvent deleteEvent) {
        userServices.deleteUserById(deleteEvent.getUser().getId());
        updateList();
        clearForm();
    }

    private void save(UserForm.SaveEvent saveEvent) {
        userServices.saveUser(saveEvent.getUser());
        updateList();
        clearForm();
    }

    private void edit(User user) {
        if (user == null) {
            clearForm();
        } else {
            userForm.setOrder(user);
            userForm.setVisible(true);
            addClassName("editing");
        }
    }

}
