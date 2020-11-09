package com.vaadin.crm.ui.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "main", layout = MainLayout.class)
@PageTitle("Main View | Vaadin CRM")
public class MainView extends VerticalLayout {

    public MainView() {

        H3 title = new H3("Welcome To MENZ");

        add(
                createHero(),
                createCard()
        );

    }

    private VerticalLayout createHero() {

        VerticalLayout verticalLayout = new VerticalLayout();
        H1 heroText = new H1("M.E.N.Z.");
        Span heroDescription = new Span();
        heroDescription.setText("Our Company Presents Something New and Modern that is something which is constructed with High-Level Languages");

        Icon icon = new Icon(VaadinIcon.VAADIN_V);
        icon.setSize("100%");
        icon.setColor("green");

        Div containerButton = new Div();
        Button startButton = new Button("Let's Start");
        startButton.getStyle().set("background", "green");
        startButton.getStyle().set("color", "white");
        startButton.getStyle().set("margin-top", "25px");
        containerButton.add(startButton);

        Div container = new Div();
        container.setWidth("50%");
        container.getStyle().set("margin", "auto");
        container.add(heroText, heroDescription, containerButton);

        Div containerIcon = new Div();
        containerIcon.getStyle().set("margin-top", "60px");
        containerIcon.add(icon);

        HorizontalLayout horizontalLayout = new HorizontalLayout(container, containerIcon);

        verticalLayout.add(horizontalLayout);

        return verticalLayout;
    }

    private VerticalLayout createCard() {

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.add(horizontalLayout);

        return verticalLayout;
    }

}
