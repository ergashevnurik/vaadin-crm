package com.vaadin.crm.ui.views.products;

import com.vaadin.crm.backend.Products.entity.Products;
import com.vaadin.crm.backend.Products.services.ProductsService;
import com.vaadin.crm.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "products", layout = MainLayout.class)
@PageTitle("Products | View")
public class ProductsView extends VerticalLayout {

    private Button addProductBtn;
    private Grid<Products> grid = new Grid<>(Products.class);
    private HorizontalLayout horizontalLayout;
    private TextField filterField = new TextField();

    private final ProductForm productForm;
    private final ProductsService productsService;

    public ProductsView(ProductsService productsService) {
        this.productsService = productsService;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        productForm = new ProductForm(productsService.getAllProducts());
        productForm.addListener(ProductForm.SaveEvent.class, this::save);
        productForm.addListener(ProductForm.DeleteEvent.class, this::delete);
        productForm.addListener(ProductForm.CancelEvent.class, e -> clearForm());

        Div content = new Div(grid, productForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);

        updateList();
        clearForm();

    }

    private void configureGrid() {
        grid.setClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("productName", "unitPrice");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.asSingleSelect().addValueChangeListener(evt -> edit(evt.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filterField.setPlaceholder("Filter by Product Name");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(event -> updateList());

        addProductBtn = new Button("", click -> addNewProduct());
        Icon addIcon = new Icon(VaadinIcon.PLUS);
        addProductBtn.setIcon(addIcon);

        horizontalLayout = new HorizontalLayout(addProductBtn, filterField);
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        return horizontalLayout;
    }

    private void addNewProduct() {
        grid.asSingleSelect().clear();
        edit(new Products());
    }

    private void updateList() {
        grid.setItems(productsService.getAllProducts(filterField.getValue()));
    }

    private void clearForm() {
        productForm.setProducts(null);
        productForm.setVisible(false);
        removeClassName("editing");
    }

    private void delete(ProductForm.DeleteEvent deleteEvent) {
        productsService.deleteProduct(deleteEvent.getProducts().getId());
        updateList();
        clearForm();
    }

    private void save(ProductForm.SaveEvent saveEvent) {
        productsService.saveProducts(saveEvent.getProducts());
        updateList();
        clearForm();
    }

    private void edit(Products products) {
        if (products == null) {
            clearForm();
        } else {
            productForm.setProducts(products);
            productForm.setVisible(true);
            addClassName("editing");
        }
    }
}
