package com.vaadin.crm.ui.views.dashboard;

import com.vaadin.crm.backend.service.CompanyService;
import com.vaadin.crm.backend.service.ContactService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;

@PageTitle("Dashboard | Vaadin CRM")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final ContactService contactService;
    private final CompanyService companyService;

    public DashboardView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        this.companyService = companyService;


        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
          getContactStats(),
          getCompaniesChart()
        );


    }

    private Component getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries =  new DataSeries();
        Map<String, Integer> stats = companyService.getStats();
        stats.forEach((name, number) -> dataSeries.add(new DataSeriesItem(name, number)));
        chart.getConfiguration().setSeries(dataSeries);

        return chart;
    }

    private Span getContactStats() {
        Span stats = new Span(contactService.count() + " contact");
        stats.addClassName("contact-stats");

        return stats;

    }


}
