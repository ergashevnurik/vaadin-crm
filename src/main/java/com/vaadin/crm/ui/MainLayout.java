package com.vaadin.crm.ui;

import com.vaadin.crm.ui.views.dashboard.DashboardView;
import com.vaadin.crm.ui.views.lead.LeadView;
import com.vaadin.crm.ui.views.list.ListView;
import com.vaadin.crm.ui.views.orders.OrdersView;
import com.vaadin.crm.ui.views.products.ProductsView;
import com.vaadin.crm.ui.views.user.UserView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

@PWA(
        name = "Vaadin CRM",
        shortName = "CRM",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png"
        }
)
@CssImport("./styles/views/helloworld/hello-world-view.css")
public class MainLayout extends AppLayout {

    private RouterLink listView;
    private Div listViewContainer;

    private RouterLink leadManagement;
    private Div leadManagementContainer;

    private RouterLink dashboard;
    private Div dashboardContainer;

    private RouterLink users;
    private Div usersContainer;

    private RouterLink orders;
    private Div ordersContainer;

    private RouterLink products;
    private Div productContainer;

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {

        listViewContainer = new Div();
        listView = new RouterLink("Contacts", ListView.class);
        listView.setHighlightCondition(HighlightConditions.sameLocation());
        listViewContainer.add(listView);

        leadManagementContainer = new Div();
        leadManagement = new RouterLink("Lead Management", LeadView.class);
        leadManagement.setHighlightCondition(HighlightConditions.sameLocation());
        leadManagementContainer.add(leadManagement);

        dashboardContainer = new Div();
        dashboard = new RouterLink("Dashboard", DashboardView.class);
        dashboard.setHighlightCondition(HighlightConditions.sameLocation());
        dashboardContainer.add(dashboard);

        ordersContainer = new Div();
        orders = new RouterLink("Orders", OrdersView.class);
        orders.setHighlightCondition(HighlightConditions.sameLocation());
        ordersContainer.add(orders);

        usersContainer = new Div();
        users = new RouterLink("User", UserView.class);
        users.setHighlightCondition(HighlightConditions.sameLocation());
        usersContainer.add(users);

        productContainer = new Div();
        products = new RouterLink("Product", ProductsView.class);
        products.setHighlightCondition(HighlightConditions.sameLocation());
        productContainer.add(products);


        addToDrawer(new VerticalLayout(
                dashboardContainer,
                listViewContainer,
//                leadManagementContainer,
                usersContainer,
                ordersContainer,
                productContainer
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
































