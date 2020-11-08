package com.vaadin.crm.ui.views.lead;

import com.vaadin.crm.backend.entity.Contact;
import com.vaadin.crm.backend.service.CompanyService;
import com.vaadin.crm.backend.service.ContactService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "leadView", layout = MainLayout.class)
@PageTitle("Lead Management | Vaadin CRM")
public class LeadView extends VerticalLayout {

    private final LeadForm form;

    private Grid<Contact> grid = new Grid<>(Contact.class);
    private ContactService contactService;
    private TextField filter = new TextField();

    public LeadView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();

        form = new LeadForm(companyService.findAll());
        form.addListener(LeadForm.SaveEvent.class, this::save);
        form.addListener(LeadForm.DeleteEvent.class, this::delete);
        form.addListener(LeadForm.CancelEvent.class, e -> clearForm());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        configureGrid();
        add(getToolBar(), content);

        updateList();
        clearForm();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email", "phone", "status");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> edit(evt.getValue()));

    }

    private HorizontalLayout getToolBar() {
        filter.setPlaceholder("Filter by Name");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> updateList());

        Button add = new Button("", click -> addNewLead());
        Icon addIcon = new Icon(VaadinIcon.PLUS);
        addIcon.getStyle().set("color", "green");

        add.setIcon(addIcon);

        HorizontalLayout toolBar = new HorizontalLayout(add, filter);
        toolBar.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        return toolBar;
    }

    private void addNewLead() {
        grid.asSingleSelect().clear();
        edit(new Contact());
    }

    private void updateList() {
        grid.setItems(contactService.findAll(filter.getValue()));
    }

    private void clearForm() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void delete(LeadForm.DeleteEvent deleteEvent) {
        contactService.delete(deleteEvent.getContact());
        updateList();
        clearForm();
    }

    private void save(LeadForm.SaveEvent saveEvent) {
        contactService.save(saveEvent.getContact());
        updateList();
        clearForm();
    }

    private void edit(Contact contact) {
        if (contact == null) {
            clearForm();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
