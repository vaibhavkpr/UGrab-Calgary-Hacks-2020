import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainGUI extends Application {
    private ItemCSV foodItems;
    
    private Scene mainMenu = null;
    private Scene resultsMenu = null;
    private Scene itemMenu = null;

    private VBox itemScroller = new VBox();

    private ObservableList<String> restrictions = FXCollections.observableArrayList(
        "None", "Halal", "Vegetarian", "Vegan", "Lactose Free", "Gluten Free", "Nut Free", "Seafood Free"
    );

    private ObservableList<String> restaurants = FXCollections.observableArrayList(
        "None", "Bake Chef", "Oriental Wok", "A & W", "Coffee Company", "Korean BBQ House", "Carl's Jr", "Tim Hortons"
    );

    private ObservableList<String> foodTypes = FXCollections.observableArrayList(
        "None", "Baked Goods", "BBQ", "Breakfast", "Burgers", "Cold Beverages", "Hot Beverages", "Combo", "Seafood", "Pizza", "Soups", "Subs", "Wraps", "Snacks", "Sweets"
    );

    private ObservableList<String> sortings = FXCollections.observableArrayList(
        "Low to High", "High to Low"
    );

    private ArrayList<FoodItem> currentItemList = new ArrayList<>();

    private ScrollPane scrollMenu = new ScrollPane();
    private ComboBox<String> sortingComboBox = new ComboBox<>(sortings);

    private String currentItemName = "";

    public static void main(String[] args) {
        launch(args);   
    }

    public void init() {
        foodItems = new ItemCSV("items.csv");
    }

    public void start(Stage primaryStage) throws Exception {
        // ----- Create Main Menu -----

        // Set Font
        Font f1 = new Font("Arial", 14);
        String fontStyle = "-fx-font: 14 arial";

        // Sizes
        int labelWidth = 140;
        int textBoxWidth = 200;

        // Store Search Field
        // TextField storeField = new TextField();
        // storeField.setPrefWidth(textBoxWidth);

        ComboBox<String> storeField = new ComboBox<>(restaurants);
        storeField.setMinWidth(labelWidth);

        Label storeLabel = new Label("Store Name: ");
        storeLabel.setAlignment(Pos.CENTER_RIGHT);
        storeLabel.setMinWidth(labelWidth);
        storeLabel.setFont(f1);

        HBox storeBox = new HBox();
        storeBox.getChildren().addAll(storeLabel, storeField);
        storeBox.setAlignment(Pos.CENTER);

        // Price Search Fields
        TextField priceFieldLow = new TextField();
        priceFieldLow.setPrefWidth(textBoxWidth / 2 - 16);
        TextField priceFieldHigh = new TextField();
        priceFieldHigh.setPrefWidth(textBoxWidth / 2 - 16);

        Label toLabel = new Label(" to ");
        toLabel.setAlignment(Pos.CENTER);

        Label priceLabel = new Label("Price Range: ");
        priceLabel.setAlignment(Pos.CENTER_RIGHT);
        priceLabel.setMinWidth(labelWidth);
        priceLabel.setFont(f1);

        HBox priceBox = new HBox();
        priceBox.getChildren().addAll(priceLabel, priceFieldLow, toLabel, priceFieldHigh);
        priceBox.setAlignment(Pos.CENTER);

        // Food Type Search Field
        ComboBox<String> foodTypeField = new ComboBox<>(foodTypes);
        foodTypeField.setMinWidth(labelWidth);

        Label foodTypeLabel = new Label("Food Type: ");
        foodTypeLabel.setAlignment(Pos.CENTER_RIGHT);
        foodTypeLabel.setMinWidth(labelWidth);
        foodTypeLabel.setFont(f1);

        HBox foodTypeBox = new HBox();
        foodTypeBox.getChildren().addAll(foodTypeLabel, foodTypeField);
        foodTypeBox.setAlignment(Pos.CENTER);

        // Name Search Field
        TextField foodNameField = new TextField();
        foodNameField.setPrefWidth(textBoxWidth);

        Label foodNameLabel = new Label("Food Name: ");
        foodNameLabel.setAlignment(Pos.CENTER_RIGHT);
        foodNameLabel.setMinWidth(labelWidth);
        foodNameLabel.setFont(f1);

        HBox foodNameBox = new HBox();
        foodNameBox.getChildren().addAll(foodNameLabel, foodNameField);
        foodNameBox.setAlignment(Pos.CENTER);

        // Create group of all Search Bars
        VBox searchBars = new VBox();
        searchBars.getChildren().addAll(foodNameBox, storeBox, priceBox, foodTypeBox);
        searchBars.setSpacing(30);
        searchBars.setAlignment(Pos.CENTER);

        Button searchButton = new Button("Search");
        searchButton.setMinWidth(labelWidth + textBoxWidth);
        searchButton.setMinHeight(50);
        searchButton.setStyle(fontStyle);


        // Restriction Area
        Label restrictionsLabel = new Label("Restrictions: ");
        foodNameLabel.setAlignment(Pos.CENTER_RIGHT);
        foodNameLabel.setMinWidth(labelWidth);
        foodNameLabel.setFont(f1);

        ComboBox<String> resButton = new ComboBox<>(restrictions);
        resButton.setMinWidth(labelWidth);

        HBox resArea = new HBox();
        resArea.setAlignment(Pos.CENTER);
        resArea.getChildren().addAll(restrictionsLabel, resButton);
        resArea.setPadding(new Insets(0, 0, 0, 10));

        // Grouping
        GridPane mainMenuGroup = new GridPane();
        mainMenuGroup.add(searchBars, 0, 0);
        mainMenuGroup.add(resArea, 0, 1);
        mainMenuGroup.add(searchButton, 0, 2);
        mainMenuGroup.setAlignment(Pos.CENTER);
        mainMenuGroup.setVgap(30);
        mainMenuGroup.setPadding(new Insets(120, 0, 0, 0));

        mainMenu = new Scene(mainMenuGroup, 640, 940);
        mainMenu.getStylesheets().addAll(this.getClass().getResource("mainmenu.css").toExternalForm());

        // ----- Create Results Menu -----

        Button backButton = new Button();
        backButton.setMinWidth(81);
        backButton.setMinHeight(55);
        backButton.setStyle("-fx-background-image: url('back_button.png')");
        backButton.setOnAction(event -> {
            primaryStage.setScene(mainMenu);
        });

        HBox resultsTop = new HBox();
        resultsTop.getChildren().addAll(backButton);
        resultsTop.setPadding(new Insets(0, 0, 50, 0));
        resultsTop.setAlignment(Pos.TOP_LEFT);
        resultsTop.setMinHeight(190);
        resultsTop.setMaxHeight(190);

        scrollMenu.setMinWidth(583);
        scrollMenu.setMaxWidth(583);
        scrollMenu.setMinHeight(612);
        scrollMenu.setMaxHeight(612);
        scrollMenu.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollMenu.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollMenu.setPadding(new Insets(0, 29, 0, 29));

        // Scroller Area

        // Search Button Action (From Main Menu)
        searchButton.setOnAction(event -> {
            ArrayList<FoodItem> itemsToList = foodItems.getFoodList();

            // Check the inputs
            if (!foodNameField.getText().isEmpty()) {
                itemsToList = ItemCSV.searchFilter(itemsToList, foodNameField.getText());
            }
            if (resButton.getValue() != null && !resButton.getValue().isEmpty() && !resButton.getValue().equals("None")) {
                itemsToList = ItemCSV.foodRestrictionFilter(itemsToList, resButton.getValue());
            }
            if (storeField.getValue() != null && !storeField.getValue().isEmpty() && !storeField.getValue().equals("None")) {
                itemsToList = ItemCSV.storeFilter(itemsToList, storeField.getValue());
            }
            if (foodTypeField.getValue() != null && !foodTypeField.getValue().isEmpty() && !foodTypeField.getValue().equals("None")) {
                itemsToList = ItemCSV.foodTypeFilter(itemsToList, foodTypeField.getValue());
            } 
            if (priceFieldLow.getText().isEmpty() && priceFieldHigh.getText().isEmpty()) {

            } else if (priceFieldLow.getText().isEmpty() && !priceFieldHigh.getText().isEmpty()) {
                itemsToList = ItemCSV.priceFilter(itemsToList, 0, Double.parseDouble(priceFieldHigh.getText()));
            } else if (!priceFieldLow.getText().isEmpty() && priceFieldHigh.getText().isEmpty()) {
                itemsToList = ItemCSV.priceFilter(itemsToList, Double.parseDouble(priceFieldLow.getText()), 1000);
            } else if (priceFieldLow.getText().isEmpty() && priceFieldHigh.getText().isEmpty()) {
                itemsToList = ItemCSV.priceFilter(itemsToList, Double.parseDouble(priceFieldLow.getText()), Double.parseDouble(priceFieldHigh.getText()));
            }

            currentItemList = itemsToList;
            ItemCSV.lowestToHighest(currentItemList);
            sortingComboBox.setValue("Low to High");
            updater(primaryStage);

            primaryStage.setScene(resultsMenu);
        });

        sortingComboBox.setMinWidth(labelWidth);
        sortingComboBox.setOnAction(e -> {
            if (sortingComboBox.getValue().equals("Low to High")) {
                ItemCSV.lowestToHighest(currentItemList);
            } else if (sortingComboBox.getValue().equals("High to Low")) {
                ItemCSV.highestToLowest(currentItemList);
            }

            updater(primaryStage);
        });

        HBox sortingGroup = new HBox();
        sortingGroup.setMinWidth(583);
        sortingGroup.setMaxWidth(583);
        sortingGroup.getChildren().add(sortingComboBox);
        sortingGroup.setAlignment(Pos.CENTER_RIGHT);
        sortingGroup.setPadding(new Insets(0, 50, 10, 0));


        GridPane resultsGroup = new GridPane();
        resultsGroup.add(resultsTop, 0, 0);
        resultsGroup.add(sortingGroup, 0, 1);
        resultsGroup.add(scrollMenu, 0, 2);
        resultsGroup.setAlignment(Pos.BOTTOM_CENTER);
        resultsGroup.setPadding(new Insets(0, 0, 75, 0));
        resultsMenu = new Scene(resultsGroup, 640, 940);
        resultsMenu.getStylesheets().addAll(this.getClass().getResource("results.css").toExternalForm());

        // ----- Item Manu -----

        Button itemBackButton = new Button();
        itemBackButton.setMinWidth(81);
        itemBackButton.setMinHeight(55);
        itemBackButton.setStyle("-fx-background-image: url('back_button.png')");
        itemBackButton.setOnAction(event -> {
            primaryStage.setScene(resultsMenu);
        });

        GridPane itemMenuGroup = new GridPane();
        itemMenuGroup.add(itemBackButton, 0, 0);
        itemMenuGroup.setAlignment(Pos.TOP_LEFT);
        itemMenuGroup.setPadding(new Insets(27, 0, 0, 29));

        itemMenu = new Scene(itemMenuGroup, 640, 940);
        itemMenu.getStylesheets().addAll(this.getClass().getResource("itemmenu.css").toExternalForm());


        // Add grouping to scene and add it to the stage
        primaryStage.setScene(mainMenu);
        primaryStage.setTitle("UGrab");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void updater(Stage primaryStage) {
        ArrayList<FoodItem> itemsToList = currentItemList;

        itemScroller = new VBox();
        for (int i = 0; i < itemsToList.size(); i++) {
            FoodItem currentItem = itemsToList.get(i);

            HBox itemBox = new HBox();
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setMinWidth(583);
            itemBox.setMaxWidth(583);   
            Image itemImage = new Image(currentItem.getImage());
            ImageView itemImageView = new ImageView(itemImage);

            VBox itemFullLabel = new VBox();
            itemFullLabel.setAlignment(Pos.CENTER_LEFT);
            itemFullLabel.setMinWidth(340);
            itemFullLabel.setMaxWidth(340);
            itemFullLabel.setPadding(new Insets(0, 0, 0, 10));
            
            Label itemLabel = new Label(currentItem.getName());
            itemLabel.setAlignment(Pos.CENTER_LEFT);
            itemLabel.setStyle("-fx-font-size: 20; -fx-font-family: 'Segoe UI Semibold';");

            Label itemLabelStoreName = new Label(currentItem.getStoreName());
            itemLabelStoreName.setAlignment(Pos.CENTER_LEFT);
            itemLabelStoreName.setStyle("-fx-font: 15 arial;");

            itemFullLabel.getChildren().addAll(itemLabel, itemLabelStoreName);

            Label itemPrice = new Label(String.format("$%.2f", currentItem.getPrice()));
            itemPrice.setAlignment(Pos.CENTER_RIGHT);
            itemPrice.setStyle("-fx-font-size: 23; -fx-font-family: 'Segoe UI Semibold'; -fx-text-fill: #f38a00;");

            itemBox.getChildren().addAll(itemImageView, itemFullLabel, itemPrice);
            itemBox.setOnMousePressed(e -> {
                primaryStage.setScene(itemMenu);
                currentItemName = ((Label)((VBox)itemBox.getChildren().get(1)).getChildren().get(0)).getText();
            });

            itemScroller.getChildren().add(itemBox);
        }

        scrollMenu.setContent(itemScroller);
    }

}