package com.vaadin.crm.ui.views.orders;

import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Orders | View")
@Route(value = "orders", layout = MainLayout.class)
public class OrdersView extends Div {

    public OrdersView() {

    }

}
