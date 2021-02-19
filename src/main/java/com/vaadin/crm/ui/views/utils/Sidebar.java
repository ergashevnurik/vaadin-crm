package com.vaadin.crm.ui.views.utils;

import com.vaadin.flow.component.applayout.AppLayout;

public class Sidebar extends AppLayout {

    private static Sidebar instance = new Sidebar();
    public static Sidebar getInstance() {
        return instance;
    }
}
