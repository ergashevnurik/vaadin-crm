package com.vaadin.crm.ui.views.orders;

import com.vaadin.crm.backend.Orders.entity.Order;
import com.vaadin.crm.backend.Products.entity.Products;
import com.vaadin.crm.backend.Products.services.ProductsService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.crm.ui.views.products.ProductForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.style.ButtonTheme;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    private Button save, cancel, delete;
    private HorizontalLayout btnWrapper;

    private Order order;
    private Binder<Order> binder = new Binder<>(Order.class);

    private Select<String> productsSelect;

    public OrdersForm(List<Order> orders) {
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

        productsSelect = new Select<>();
        productsSelect.setSizeFull();
        //productsSelect.setItems(ProductsService.getInstance().getAllProducts());
        productsSelect.setItems("Hello", "World");
        productsSelect.addToPrefix(new Icon(VaadinIcon.BOOK));
        productsSelect.setLabel("Products Available");
        customerDetailsContainer.add(productsSelect);


        phoneNumberDetailsContainer = new VerticalLayout();

        phoneNumberField = new TextField();
        phoneNumberField.setRequired(true);
        phoneNumberField.setLabel("Phone Number");
        phoneNumberField.setPlaceholder("Enter Phone Number");
        phoneNumberField.setPrefixComponent(new Icon(VaadinIcon.PHONE));
        phoneNumberField.setWidth("100%");

        phoneNumberDetailsContainer.add(phoneNumberField);

        cancel = new Button();
        cancel.setText("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addClickListener(e -> fireEvent(new CancelEvent(this)));

        delete = new Button();
        delete.setText("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> fireEvent(new DeleteEvent(this, order)));

        save = new Button();
        save.setText("Save");
        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));
        save.addClickListener(e -> validateAndSave());
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        btnWrapper = new HorizontalLayout();
        btnWrapper.add(cancel, delete, save);
        dateContainer.add(btnWrapper);

        mainLayout.add(dateContainer, customerDetailsContainer, phoneNumberDetailsContainer);
        return mainLayout;
    }

    private Order setValues() {
        order.setDueDate(dueDate.getValue());
        order.setTime(timeRange.getValue());
        order.setLocation(location.getValue());
        order.setCustomerFullName(customerFullNameField.getValue());
        order.setAdditionalInfo(additionalInfoField.getValue());
        order.setPhoneNumber(phoneNumberField.getValue());
        //order.setProducts((List<Products>) productsSelect.getValue());
        return order;
    }

    private void validateAndSave() {
        try {
            binder.writeBean(setValues());
            fireEvent(new SaveEvent(this, order));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setOrder(Order order) {
        this.order = order;
        binder.readBean(order);
    }


    public static abstract class OrderFormEvent extends ComponentEvent<OrdersForm> {
        private Order order;

        public OrderFormEvent(OrdersForm source, Order order) {
            super(source, false);
            this.order = order;
        }

        public Order getOrder() {
            return order;
        }
    }

    public static class SaveEvent extends OrderFormEvent {
        public SaveEvent(OrdersForm source, Order order) {
            super(source, order);
        }
    }

    public static class DeleteEvent extends OrderFormEvent {
        public DeleteEvent(OrdersForm source, Order order) {
            super(source, order);
        }
    }

    public static class CancelEvent extends OrderFormEvent {
        public CancelEvent(OrdersForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
