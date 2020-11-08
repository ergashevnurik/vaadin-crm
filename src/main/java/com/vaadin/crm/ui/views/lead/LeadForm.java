package com.vaadin.crm.ui.views.lead;

import com.vaadin.crm.backend.entity.Company;
import com.vaadin.crm.backend.entity.Contact;
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

public class LeadForm extends FormLayout {

    private Contact contact;

    private TextField firstName = new TextField("Firs Name");
    private TextField lastName = new TextField("Last Name");
    private TextField phone = new TextField("Phone");
    private EmailField email = new EmailField("Email");
    private ComboBox<Contact.Status> status = new ComboBox<>("Status");
    private ComboBox<Company> company = new ComboBox<>("Company");

    private HorizontalLayout horizontalLayout;

    private Button save;
    private Button delete;
    private Button cancel;

    private Binder<Contact> binder = new Binder<>(Contact.class);


    public LeadForm(List<Company> companies) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(Contact.Status.values());

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
        delete.addClickListener(click -> fireEvent(new LeadForm.DeleteEvent(this, contact)));

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
            binder.writeBean(contact);
            fireEvent(new LeadForm.SaveEvent(this, contact));
        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }

    public void setContact(Contact contact) {
        this.contact = contact;
        binder.readBean(contact);
    }

    public static abstract class LeadFormEvent extends ComponentEvent<LeadForm> {
        private Contact contact;

        public LeadFormEvent(LeadForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends LeadFormEvent {
        public SaveEvent(LeadForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends LeadFormEvent {
        public DeleteEvent(LeadForm source, Contact contact) {
            super(source, contact);
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
