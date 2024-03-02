package com.stocktracker.javafx.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;

public final class DashboardModel {
    
    private final ListProperty<Node> bondNodes = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Node> stockNodes = new SimpleListProperty<>(FXCollections.observableArrayList());

    public ListProperty<Node> getBondNodeProperty() {
        return bondNodes;
    }

    public ListProperty<Node> getStockNodeProperty() {
        return stockNodes;
    }
}
