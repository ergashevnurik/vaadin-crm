package com.vaadin.crm.ui.views.user;

import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user", layout = MainLayout.class)
@PageTitle("Users | Vaadin CRM")
public class UserView extends VerticalLayout {

    private Button addButton;
    private Div mainContainer;

    public UserView() {
        setSizeFull();
        add(initButtons());
    }

    private Component initButtons() {
        addButton = new Button("Add new User");
        mainContainer = new Div();
        mainContainer.add(addButton);
        return mainContainer;
    }

}
