package com.vaadin.crm.ui.views.user;

import com.vaadin.crm.backend.entity.User;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user", layout = MainLayout.class)
@PageTitle("Users | Vaadin CRM")
public class UserView extends VerticalLayout {

    private Button addButton;
    private HorizontalLayout horizontalLayoutOfBUttonAndTextField;
    private Grid<User> grid = new Grid<>(User.class);

    public UserView() {
        setSizeFull();
        configureGrid();

        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();

        add(initHeaderTop(), content);
    }

    private HorizontalLayout initHeaderTop() {
        horizontalLayoutOfBUttonAndTextField = new HorizontalLayout();
        addButton = new Button("Add new User");

        horizontalLayoutOfBUttonAndTextField.add(addButton);
        return horizontalLayoutOfBUttonAndTextField;
    }

    private void configureGrid() {
        grid.setClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("username", "roles", "password");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
