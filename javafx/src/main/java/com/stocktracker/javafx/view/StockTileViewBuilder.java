package com.stocktracker.javafx.view;

import com.stocktracker.javafx.model.StockTileModel;
import com.stocktracker.javafx.util.ViewConstants;

import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public final class StockTileViewBuilder implements Builder<Node> {

    private static final String STOCKTILE_STYLECLASS = "stock-tile";
    private static final String STOCKNAME_STYLECLASS = "stock-name";
    private static final String UNDER_TITLE = "UL";
    private static final String CEDAR_TITLE = "AR";
    private static final String CEDUS_TITLE = "US";
    private static final PseudoClass COMPARATOR_PSEUDOCLASS = //
            PseudoClass.getPseudoClass("comparator-text");
    
    private final StockTileModel model;

    public StockTileViewBuilder(StockTileModel model) {
        this.model = model;
    }

    @Override
    public Node build() {
        VBox result = new VBox(createInfoHud(), new Separator(), createComparator());
        result.getStyleClass().add(STOCKTILE_STYLECLASS);
        return result;
    }

    private Node createInfoHud() {
        Label titleLabel = new Label();
        titleLabel.textProperty().bind(model.getSymbol());
        titleLabel.getStyleClass().add(STOCKNAME_STYLECLASS);
        Region fillerRegion = new Region();
        HBox.setHgrow(fillerRegion, Priority.ALWAYS);
        Label priceLabel = new Label();
        priceLabel.textProperty().bind(model.getPriceUnderl().asString(ViewConstants.PRICE_FMT));
        Label percentLabel = new Label();
        percentLabel.textProperty().bind(model.getChangeUnderl().asString(ViewConstants.PRCNT_FMT));
        return new HBox(titleLabel, fillerRegion, priceLabel, percentLabel);
    }

    private Node createComparator() {
        return new VBox(
            createPriceDisplayUnderl(),
            createPriceDisplayCedear(),
            createPriceDisplayCedeus()
        );
    }

    private Node createPriceDisplayUnderl() {
        Node display = createKeyValueDisplay(UNDER_TITLE, model.getPriceUnderlRatio().asString(ViewConstants.PRICE_FMT));
        display.pseudoClassStateChanged(COMPARATOR_PSEUDOCLASS, true);
        model.getPriceUnderlRatio().addListener(a -> //
                display.pseudoClassStateChanged(COMPARATOR_PSEUDOCLASS, false));
        return display;
    }

    private Node createPriceDisplayCedear() {
        Node priceDisplay = createKeyValueDisplay(CEDAR_TITLE, model.getPriceCedearDollar().asString(ViewConstants.PRICE_FMT));
        Label percentLabel = new Label();
        percentLabel.textProperty().bind(model.getMarkupCedearDollar().asString(ViewConstants.PRCNT_FMT));
        Node display = new HBox(priceDisplay, percentLabel);
        display.pseudoClassStateChanged(COMPARATOR_PSEUDOCLASS, true);
        model.getPriceCedearDollar().addListener(a -> //
                display.pseudoClassStateChanged(COMPARATOR_PSEUDOCLASS, false));
        return display;
    }

    private Node createPriceDisplayCedeus() {
        Node priceDisplay = createKeyValueDisplay(CEDUS_TITLE, model.getPriceCedeus().asString(ViewConstants.PRICE_FMT));
        Label percentUnderlLabel = new Label();
        percentUnderlLabel.textProperty().bind(model.getMarkupCedeusUnderl().asString(ViewConstants.PRCNT_FMT));
        Label percentCedearLabel = new Label();
        percentCedearLabel.textProperty().bind(model.getMarkupCedeusCedear().asString(ViewConstants.PRCNT_FMT));
        Node display = new HBox(priceDisplay, percentUnderlLabel, percentCedearLabel);
        display.pseudoClassStateChanged(COMPARATOR_PSEUDOCLASS, true);
        model.getPriceCedeus().addListener(a -> //
                display.pseudoClassStateChanged(COMPARATOR_PSEUDOCLASS, false));
        return display;
    }

    private static Node createKeyValueDisplay(String key, StringBinding valueBinding) {
        Label keyLabel = new Label(key);
        Label valueLabel = new Label();
        valueLabel.textProperty().bind(valueBinding);
        return new HBox(keyLabel, valueLabel);
    }
}
