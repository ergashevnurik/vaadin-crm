package com.vaadin.crm.ui.views.orders;

import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Orders | View")
@Route(value = "orders", layout = MainLayout.class)
public class OrdersView extends Div {

    private TextField dueField;
    private TextField customerFullNameField;
    private TextField phoneNumber;
    private TextField timeInHours;
    private TextField location;
    private TextField additionalDetails;
    private TextField product;

    public OrdersView() {

    }

}
