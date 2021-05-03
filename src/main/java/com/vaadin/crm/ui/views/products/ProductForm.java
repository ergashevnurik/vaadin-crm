package com.vaadin.crm.ui.views.products;

import com.vaadin.crm.backend.Products.entity.Products;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ProductForm extends FormLayout {

    private Products products;
    private Binder<Products> binder = new Binder<>(Products.class);

    private Div mainLayout;

    private TextField productName = new TextField("Product Name");
    private TextField unitPrice = new TextField("Unit Price");

    private Button saveBtn;
    private Button cancelBtn;

    public ProductForm(List<Products> products) {
        setSizeFull();
        addClassName("list-view");

        add(
                productName,
                unitPrice,
                initView()
        );
    }

    private Products setValues() {
        products.setProductName(productName.getValue());
        products.setUnitPrice(Long.valueOf(unitPrice.getValue()));
        return products;
    }

    private Component initView() {
        mainLayout = new Div();

        saveBtn = new Button();
        saveBtn.setText("Save");
        saveBtn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        binder.addStatusChangeListener(evt -> saveBtn.setEnabled(binder.isValid()));
        saveBtn.addClickListener(e -> validateAndSave());

        cancelBtn = new Button();
        cancelBtn.setText("Cancel");
        cancelBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelBtn.addClickListener(e -> fireEvent(new ProductForm.CancelEvent(this)));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(cancelBtn, saveBtn);
        mainLayout.add(horizontalLayout);
        return mainLayout;
    }

    public void validateAndSave() {
        try {
            binder.writeBean(setValues());
            fireEvent(new ProductForm.SaveEvent(this, products));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setProducts(Products products) {
        this.products = products;
        binder.readBean(products);
    }

    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {
        private Products products;

        public ProductFormEvent(ProductForm source, Products products) {
            super(source, false);
            this.products = products;
        }

        public Products getProducts() {
            return products;
        }
    }

    public static class SaveEvent extends ProductFormEvent {
        public SaveEvent(ProductForm source, Products products) {
            super(source, products);
        }
    }

    public static class DeleteEvent extends ProductFormEvent {

        public DeleteEvent(ProductForm source, Products products) {
            super(source, products);
        }
    }

    public static class CancelEvent extends ProductFormEvent {

        public CancelEvent(ProductForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
