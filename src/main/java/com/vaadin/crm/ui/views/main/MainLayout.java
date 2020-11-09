package com.vaadin.crm.ui.views.main;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        addHeader();
    }

    private void addHeader() {

        Div div = new Div();

        H1 logo = new H1("M.E.N.Z. System");
        logo.addClassName("logo");
        logo.getStyle().set("padding", "20px 32px");
        logo.getStyle().set("color", "#fff");

        Anchor aboutUs = new Anchor("about", "About Us");
        aboutUs.getStyle().set("padding", "12px 20px");
        aboutUs.getStyle().set("color", "#fff");
        Anchor service = new Anchor("service", "Services");
        service.getStyle().set("padding", "12px 20px");
        service.getStyle().set("color", "#fff");
        Anchor login = new Anchor("login", "Login");
        login.getStyle().set("padding", "12px 20px");
        login.getStyle().set("color", "#fff");
        div.add(aboutUs, service, login);

        HorizontalLayout header = new HorizontalLayout(logo, div);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.getElement().getStyle().set("background", "#000");
        header.addClassName("header");

        addToNavbar(header);
    }

}
