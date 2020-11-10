package com.vaadin.crm.ui.views.lead;

import com.vaadin.crm.backend.entity.Company;
import com.vaadin.crm.backend.entity.Order;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class OrderForm extends FormLayout {

    private Order order;

    private TextField firstName = new TextField("Firs Name");
    private TextField lastName = new TextField("Last Name");
    private TextField phone = new TextField("Phone");
    private EmailField email = new EmailField("Email");
    private ComboBox<Order.OrderStatus> status = new ComboBox<>("Status");
    private ComboBox<Company> company = new ComboBox<>("Company");

    private HorizontalLayout horizontalLayout;

    private Button save;
    private Button delete;
    private Button cancel;

    private Binder<Order> binder = new Binder<>(Order.class);


    public OrderForm(List<Company> companies) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(Order.OrderStatus.values());

        add(firstName,lastName,phone,email, status,company, addButton());
    }

    private HorizontalLayout addButton() {
        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        save = new Button("Add");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(click -> validateAndSave());

        delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(click -> fireEvent(new OrderForm.DeleteEvent(this, order)));

        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(click -> fireEvent(new CancelEvent(this)));

        horizontalLayout = new HorizontalLayout(save, delete, cancel);
        horizontalLayout.setWidth("100%");

        return horizontalLayout;
    }

    private void validateAndSave() {

        /*if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }*/

        try {
            binder.writeBean(order);
            fireEvent(new OrderForm.SaveEvent(this, order));
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

    public void setOrder(Order order) {
        this.order = order;
        binder.readBean(order);
    }

    public static abstract class OrderFormEvent extends ComponentEvent<OrderForm> {
        private Order order;

        public OrderFormEvent(OrderForm source, Order order) {
            super(source, false);
            this.order = order;
        }

        public Order getOrder() {
            return order;
        }
    }

    public static class SaveEvent extends OrderFormEvent {
        public SaveEvent(OrderForm source, Order order) {
            super(source, order);
        }
    }

    public static class DeleteEvent extends OrderFormEvent {
        public DeleteEvent(OrderForm source, Order order) {
            super(source, order);
        }
    }

    public static class CancelEvent extends OrderFormEvent {
        public CancelEvent(OrderForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
