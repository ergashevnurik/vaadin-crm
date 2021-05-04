package com.vaadin.crm.ui.views.orders;

import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;

@Route(value = "orderForm", layout = MainLayout.class)
public class OrdersForm extends FormLayout {

    private DatePicker dueDate;
    private VerticalLayout dateContainer;
    private TimePicker timeRange;
    private Select<String> location;

    private TextField customerFullNameField;
    private TextArea additionalInfoField;
    private VerticalLayout customerDetailsContainer;

    private TextField phoneNumberField;
    private VerticalLayout phoneNumberDetailsContainer;

    private HorizontalLayout mainLayout;

    public OrdersForm() {
        setSizeFull();
        addClassName("contact-form");

        add(initView());
    }

    private Component initView() {
        mainLayout = new HorizontalLayout(); // Horizontal Main Layout
        mainLayout.setSizeFull();

        dateContainer = new VerticalLayout(); // Vertical Layout Date Details

        dueDate = new DatePicker(); // due Date
        dueDate.setWidth("100%");
        dueDate.setPlaceholder("Choose Appropriate Date");
        dueDate.setLabel("Due Date");
        LocalDate now = LocalDate.now();
        dueDate.setValue(now);
        dueDate.setClearButtonVisible(true);
        dueDate.setRequired(true);
        dateContainer.add(dueDate);

        timeRange = new TimePicker(); // Time Range Combo box
        timeRange.setClearButtonVisible(true);
        timeRange.setWidth("100%");
        timeRange.setValue(LocalTime.now());
        timeRange.setLabel("Pick Time");
        timeRange.setPlaceholder("Choose Appropriate Hour");
        timeRange.setRequired(true);
        dateContainer.add(timeRange);

        location = new Select<>(); // Location Select
        location.setItems("Store", "Bakery");
        location.setPlaceholder("Choose Location");
        location.setWidth("100%");
        location.setLabel("Location");
        location.addToPrefix(new Icon(VaadinIcon.LOCATION_ARROW));
        dateContainer.add(location);


        customerDetailsContainer = new VerticalLayout(); // Vertical Layout Customer Details

        customerFullNameField = new TextField(); // Customer Info Text Field
        customerFullNameField.setPrefixComponent(new Icon(VaadinIcon.USER));
        customerFullNameField.setPlaceholder("Enter Customer Full Name");
        customerFullNameField.setWidth("100%");
        customerFullNameField.setLabel("Customer Full Name");
        customerFullNameField.setRequired(true);
        customerDetailsContainer.add(customerFullNameField);

        additionalInfoField = new TextArea(); // Additional Info Text Area
        additionalInfoField.setLabel("Additional Info");
        additionalInfoField.setWidth("100%");
        additionalInfoField.setClearButtonVisible(true);
        customerDetailsContainer.add(additionalInfoField);


        phoneNumberDetailsContainer = new VerticalLayout();

        phoneNumberField = new TextField();
        phoneNumberField.setRequired(true);
        phoneNumberField.setLabel("Phone Number");
        phoneNumberField.setPlaceholder("Enter Phone Number");
        phoneNumberField.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        phoneNumberField.setWidth("100%");

        phoneNumberDetailsContainer.add(phoneNumberField);

        mainLayout.add(dateContainer, customerDetailsContainer, phoneNumberDetailsContainer);
        return mainLayout;
    }

}
