package com.vaadin.crm.ui.views.orders;

import com.vaadin.crm.backend.Orders.entity.Order;
import com.vaadin.crm.backend.Orders.services.OrdersService;
import com.vaadin.crm.backend.Products.entity.Products;
import com.vaadin.crm.backend.Products.services.ProductsService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.crm.ui.views.products.ProductForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Orders | View")
@Route(value = "orders", layout = MainLayout.class)
public class OrdersView extends Div {

    private final OrdersService orderService;
    private final OrdersForm orderForm;

    private Button addOrderBtn;
    private Grid<Order> grid = new Grid<>(Order.class);
    private HorizontalLayout horizontalLayout;
    private TextField filterField = new TextField();

    public OrdersView(OrdersService orderService) {
        this.orderService = orderService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        orderForm = new OrdersForm(orderService.getAllOrders());
        orderForm.addListener(OrdersForm.SaveEvent.class, this::save);
        orderForm.addListener(OrdersForm.DeleteEvent.class, this::delete);
        orderForm.addListener(OrdersForm.CancelEvent.class, e -> clearForm());

        Div content = new Div(grid, orderForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        updateList();
        clearForm();
    }

    private void configureGrid() {
        grid.setClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("id", "dueDate", "time", "location", "customerFullName", "phoneNumber", "numberOfProducts", "additionalInfo", "products");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.asSingleSelect().addValueChangeListener(evt -> edit(evt.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filterField.setPlaceholder("Filter by Order Number");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(event -> updateList());

        addOrderBtn = new Button("", click -> addNewOrder());
        Icon addIcon = new Icon(VaadinIcon.PLUS);
        addOrderBtn.setIcon(addIcon);

        horizontalLayout = new HorizontalLayout(addOrderBtn, filterField);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        return horizontalLayout;
    }

    private void addNewOrder() {
        grid.asSingleSelect().clear();
        edit(new Order());
    }

    private void updateList() {
        grid.setItems(orderService.getAllOrders(filterField.getValue()));
    }

    private void clearForm() {
        orderForm.setOrder(null);
        orderForm.setVisible(false);
        removeClassName("editing");
    }

    private void delete(OrdersForm.DeleteEvent deleteEvent) {
        orderService.deleteOrder(deleteEvent.getOrder().getId());
        updateList();
        clearForm();
    }

    private void save(OrdersForm.SaveEvent saveEvent) {
        orderService.saveOrder(saveEvent.getOrder());
        updateList();
        clearForm();
    }

    private void edit(Order order) {
        if (order == null) {
            clearForm();
        } else {
            orderForm.setOrder(order);
            orderForm.setVisible(true);
            addClassName("editing");
        }
    }

}
