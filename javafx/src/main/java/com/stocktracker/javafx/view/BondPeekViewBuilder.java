package com.stocktracker.javafx.view;

import com.stocktracker.javafx.model.BondPeekModel;
import com.stocktracker.javafx.util.ViewConstants;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public final class BondPeekViewBuilder implements Builder<Node> {
    
    private static final String MEP_TITLE = "MEP";
    private static final String AL30D_TITLE = "AL30D";
    private static final String GD30D_TITLE = "GD30D";

    private final BondPeekModel model;

    public BondPeekViewBuilder(BondPeekModel model) {
        this.model = model;
    }

    @Override
    public Node build() {
        HBox result = new HBox(createMepDisplay(), createFillerRegion(), createBondGroup());
        HBox.setHgrow(result, Priority.ALWAYS);
        return result;
    }

    private Node createMepDisplay() {
        return createKeyValueDisplay(MEP_TITLE, model.getMepValueBinding().asString(ViewConstants.WHOLE_FMT));
    }

    private Node createFillerRegion() {
        Region fillerRegion = new Region();
        HBox.setHgrow(fillerRegion, Priority.ALWAYS);
        return fillerRegion;
    }

    private Node createBondGroup() {
        return new HBox(
            createKeyValueDisplay(AL30D_TITLE, model.getChangeAl30dProperty().asString(ViewConstants.PRCNT_FMT)),
            new Separator(Orientation.VERTICAL),
            createKeyValueDisplay(GD30D_TITLE, model.getChangeGd30dProperty().asString(ViewConstants.PRCNT_FMT))
        );
    }

    private static Node createKeyValueDisplay(String key, StringBinding valueBinding) {
        Label keyLabel = new Label(key);
        Label valueLabel = new Label();
        valueLabel.textProperty().bind(valueBinding);
        return new HBox(keyLabel, valueLabel);
    }
}
