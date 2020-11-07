package com.vaadin.crm.ui.views.list;

import com.vaadin.crm.backend.entity.Company;
import com.vaadin.crm.backend.entity.Contact;
import com.vaadin.crm.backend.service.CompanyService;
import com.vaadin.crm.backend.service.ContactService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout {

    private final ContactForm form;
    private ContactService contactService;

    private Grid<Contact> grid = new Grid<>(Contact.class);
    private TextField textField = new TextField();

    public ListView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
        configuredGrid();



        form = new ContactForm(companyService.findAll());
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> clearEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();


        add(getToolBar(), content);

        updateList();
        clearEditor();
    }

    private HorizontalLayout getToolBar() {
        textField.setPlaceholder("Filter by Name");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Contact", click -> addContact());
        HorizontalLayout toolbar = new HorizontalLayout(textField, addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;

    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void deleteContact(ContactForm.DeleteEvent evt) {
        contactService.delete(evt.getContact());
        updateList();
        clearEditor();
    }

    private void saveContact(ContactForm.SaveEvent evt) {
        contactService.save(evt.getContact());
        updateList();
        clearEditor();
    }

    private void clearEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configuredGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "phone", "status");

        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));

    }

    private void editContact(Contact contact) {
        if (contact == null) {
            clearEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList() {
        grid.setItems(contactService.findAll(textField.getValue()));
    }


}
