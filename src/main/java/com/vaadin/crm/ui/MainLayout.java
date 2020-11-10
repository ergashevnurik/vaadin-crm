package com.vaadin.crm.ui;

import com.vaadin.crm.ui.views.dashboard.DashboardView;
import com.vaadin.crm.ui.views.lead.OrderView;
import com.vaadin.crm.ui.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/views/helloworld/hello-world-view.css")
public class MainLayout extends AppLayout {

    private RouterLink listView;
    private RouterLink orderManagement;
    private RouterLink dashboard;

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        listView = new RouterLink("List", ListView.class);
        listView.setHighlightCondition(HighlightConditions.sameLocation());

        orderManagement = new RouterLink("Order Management", OrderView.class);
        orderManagement.setHighlightCondition(HighlightConditions.sameLocation());

        dashboard = new RouterLink("Dashboard", DashboardView.class);
        dashboard.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                dashboard,
                listView,
                orderManagement
        ));
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassName("logo");

        Div logoutDiv = new Div();
        Anchor logout = new Anchor("logout", "Log out");
        logoutDiv.addClassName("logoutDiv");
        logoutDiv.add(logout);

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logoutDiv);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
    }



}
































