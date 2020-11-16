package com.vaadin.crm.ui.views.lead;

import com.vaadin.crm.backend.entity.Company;
import com.vaadin.crm.backend.entity.Lead;
import com.vaadin.crm.backend.service.CompanyService;
import com.vaadin.crm.backend.service.LeadService;
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
@PageTitle("Order Management | Vaadin CRM")
public class LeadView extends VerticalLayout {

    private final LeadForm form;

    private Grid<Lead> grid = new Grid<>(Lead.class);
    private LeadService leadService;
    private TextField filter = new TextField();

    public LeadView(LeadService leadService, CompanyService companyService) {
        this.leadService = leadService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new LeadForm(companyService.findAll());
        form.addListener(LeadForm.SaveEvent.class, this::save);
        form.addListener(LeadForm.DeleteEvent.class, this::delete);
        form.addListener(LeadForm.CancelEvent.class, e -> clearForm());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        updateList();
        clearForm();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("name", "orderStatus");

        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

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
        edit(new Lead());
    }

    private void updateList() {
        grid.setItems(leadService.findAll(filter.getValue()));
    }

    private void clearForm() {
        form.setOrder(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void delete(LeadForm.DeleteEvent deleteEvent) {
        leadService.delete(deleteEvent.getLead());
        updateList();
        clearForm();
    }

    private void save(LeadForm.SaveEvent saveEvent) {
        leadService.save(saveEvent.getLead());
        updateList();
        clearForm();
    }

    private void edit(Lead order) {
        if (order == null) {
            clearForm();
        } else {
            form.setOrder(order);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
