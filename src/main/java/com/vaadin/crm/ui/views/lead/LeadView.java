package com.vaadin.crm.ui.views.lead;

import com.vaadin.crm.backend.entity.Company;
import com.vaadin.crm.backend.entity.Contact;
import com.vaadin.crm.backend.service.ContactService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "leadView", layout = MainLayout.class)
@PageTitle("Lead Management | Vaadin CRM")
public class LeadView extends VerticalLayout {

    private Grid<Contact> grid = new Grid<>(Contact.class);
    private ContactService contactService;

    public LeadView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        add(grid);
        listData();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("firstName", "lastName", "email", "phone", "status");

        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" :company.getName();

        }).setHeader("company");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void listData() {
        grid.setItems(contactService.findAll());
    }

}
