package com.vaadin.crm.ui.views.lead;

import com.vaadin.crm.backend.entity.Company;
import com.vaadin.crm.backend.entity.Lead;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class LeadForm extends FormLayout {

    private Lead order;

    private TextField name = new TextField("Name");
    private ComboBox<Lead.OrderStatus> status = new ComboBox<>("Status");
    private ComboBox<Company> company = new ComboBox<>("Company");

    private HorizontalLayout horizontalLayout;

    private Button save;
    private Button delete;
    private Button cancel;

    private Binder<Lead> binder = new Binder<>(Lead.class);


    public LeadForm(List<Company> companies) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(Lead.OrderStatus.values());

        add(name, status, company, addButton());
    }

    private HorizontalLayout addButton() {
        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        save = new Button("Add");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(click -> validateAndSave());

        delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(click -> fireEvent(new LeadForm.DeleteEvent(this, order)));

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
            fireEvent(new LeadForm.SaveEvent(this, order));
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

    public void setOrder(Lead order) {
        this.order = order;
        binder.readBean(order);
    }

    public static abstract class LeadFormEvent extends ComponentEvent<LeadForm> {
        private Lead lead;

        public LeadFormEvent(LeadForm source, Lead lead) {
            super(source, false);
            this.lead = lead;
        }

        public Lead getLead() {
            return lead;
        }
    }

    public static class SaveEvent extends LeadFormEvent {
        public SaveEvent(LeadForm source, Lead lead) {
            super(source, lead);
        }
    }

    public static class DeleteEvent extends LeadFormEvent {
        public DeleteEvent(LeadForm source, Lead lead) {
            super(source, lead);
        }
    }

    public static class CancelEvent extends LeadFormEvent {
        public CancelEvent(LeadForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
