package fr.univlille.view;

import fr.univlille.modele.*;
import fr.univlille.utils.Observable;
import fr.univlille.utils.Observer;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.Math.round;

public class View extends Stage implements Observer {
    protected Controleur modele;
    private final BorderPane borderPane;
    private final ScatterChart<Number, Number> chart;
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private Attribute attrX;
    private Attribute attrY;
    private final ArrayList<CategorieView> catList;
    private Button addPointButton;
    private Button classifyButton;
    private final double NODE_SIZE = 4;
    private static final String FONT = "-fx-FONT-family : Inter; -fx-FONT-size: 15;";
    private static final String BUTTON_STYLE = "-fx-text-fill: white;" + FONT + "-fx-background-radius: 5;";


    public View(Controleur modele) {
        this(modele, modele.getDataset().getAppAttributes().get(0), modele.getDataset().getAppAttributes().get(1));
    }

    public View(Controleur modele, Attribute attr1, Attribute attr2) {
        this.modele = modele;
        this.xAxis = new NumberAxis(0, 0, 0);
        this.yAxis = new NumberAxis(0, 0, 0);
        this.chart = new ScatterChart<>(xAxis, yAxis);
        this.chart.setLegendVisible(false);
        this.catList = new ArrayList<>();
        this.attrX = attr1;
        this.attrY = attr2;
        this.loadData();
        this.normalizeAxes();

        this.setMaximized(true);
        this.borderPane = new BorderPane();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(borderPane, screenBounds.getWidth(), screenBounds.getHeight());
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Inter:opsz,wght@14..32,700&display=swap");
        this.setScene(scene);
        this.setTitle("TurboClass");

        this.generateLeftZone();
        this.generateRightZone(scene);
        this.generateBottomZone();

        this.show();
    }

    private void normalizeAxes() {
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (XYChart.Series<Number, Number> series : chart.getData()) {
            for (XYChart.Data<Number, Number> data : series.getData()) {
                double xValue = data.getXValue().doubleValue();
                double yValue = data.getYValue().doubleValue();

                if (xValue < minX) minX = xValue;
                if (xValue > maxX) maxX = xValue;
                if (yValue < minY) minY = yValue;
                if (yValue > maxY) maxY = yValue;
            }
        }


        xAxis.setLowerBound(round(minX - 1));
        xAxis.setUpperBound(round(maxX + 1));
        yAxis.setLowerBound(round(minY - 1));
        yAxis.setUpperBound(round(maxY + 1));
        xAxis.setTickUnit(0.5);
        yAxis.setTickUnit(0.5);
        xAxis.setTickLabelsVisible(true);
        yAxis.setTickLabelsVisible(true);
    }

    private void generateLeftZone() {
        VBox chartElements = new VBox(this.chart, this.getLegend());
        chartElements.setMinWidth(borderPane.getWidth()/4 * 3);
        chartElements.setStyle(FONT);
        VBox.setVgrow(this.chart, Priority.ALWAYS);
        borderPane.setLeft(chartElements);
    }

    private void generateRightZone(Scene scene) {
        VBox elements = new VBox();
        elements.setSpacing(50.0);
        elements.setAlignment(Pos.CENTER);
        elements.setMinWidth(borderPane.getWidth()/4);
        elements.setMaxWidth(scene.getWidth()/4);

        this.classifyButton = new Button("Classifier");
        Button changerAxes = new Button("Changer Axes");
        classifyButton.setMinWidth(elements.getMinWidth()/2);
        changerAxes.setMinWidth(elements.getMinWidth()/2);

        classifyButton.setStyle("-fx-background-color: #138824;" + BUTTON_STYLE);
        changerAxes.setStyle("-fx-background-color: #138824;" + BUTTON_STYLE);

        classifyButton.setDisable(true);

        changerAxes.addEventHandler(ActionEvent.ACTION, e -> new ChangerAxesView(this));
        classifyButton.addEventHandler(ActionEvent.ACTION, e -> this.classifyAction());

        VBox categorySelection = new VBox();
        categorySelection.setSpacing(10);
        categorySelection.setAlignment(Pos.CENTER);
        categorySelection.setStyle(FONT);

        for (CategorieView catView : this.catList) {
            CheckBox categoryCheckBox = new CheckBox(catView.getCategory().getName());
            categoryCheckBox.setSelected(true);
            categoryCheckBox.setOnAction(event -> updateDisplayedPoints());
            categorySelection.getChildren().add(categoryCheckBox);
        }

        elements.getChildren().addAll(classifyButton,changerAxes,categorySelection);
        borderPane.setRight(elements);
    }

    private void updateDisplayedPoints() {
        this.clear();


        ArrayList<Category> selectedCategories = new ArrayList<>();
        for (CategorieView catView : this.catList) {
            CheckBox categoryCheckBox = findCheckBoxForCategory(catView);
            if(categoryCheckBox!=null){
                if (categoryCheckBox.isSelected()) {
                    selectedCategories.add(catView.getCategory());
                }
            }
        }

        int cpt = 0;
        ArrayList<String> cateString = new ArrayList<>();
        for (DataPoint point : this.modele.getDataset().getDataPoints()) {
            if (selectedCategories.contains(point.getCategory())) {
                CategorieView pointCategory = this.getCategorieView(point.getCategory());


                double x = point.getAttributes().get(attrX);


                double y = point.getAttributes().get(attrY);

                XYChart.Series<Number, Number> series = pointCategory.getSerie();

                if (!this.chart.getData().contains(series)) {
                    this.chart.getData().add(series);
                }

                XYChart.Data<Number, Number> data = new XYChart.Data<>(x, y);
                if (this.modele.getDataset().getAddedPoints().contains(point)) {
                    data.setNode(new Rectangle(NODE_SIZE * 2, NODE_SIZE * 2, pointCategory.getColor()));
                } else {
                    data.setNode(new Circle(NODE_SIZE, pointCategory.getColor()));
                }
                series.getData().add(data);
            }
            cpt++;
        }

        this.configureAxis();
    }

    private CheckBox findCheckBoxForCategory(CategorieView catView) {
        VBox categorySelection = (VBox) ((VBox) borderPane.getRight()).getChildren().get(2);
        for (Node node : categorySelection.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                if (checkBox.getText().equals(catView.getCategory().getName())) {
                    return checkBox;
                }
            }
        }
        return null;
    }


    private void generateBottomZone() {
        HBox elements = new HBox();
        elements.setAlignment(Pos.CENTER);
        elements.setSpacing(50.0);
        elements.setMinHeight(borderPane.getHeight()/6);

        this.addPointButton = new Button("Ajouter un point");
        Button closeButton = new Button("Fermer");
        Button newVueButton = new Button("Ouvrir nouvelle vue");

        addPointButton.setStyle("-fx-background-color: #138824;" + BUTTON_STYLE);
        closeButton.setStyle("-fx-background-color: #FF0000;" + BUTTON_STYLE);
        newVueButton.setStyle("-fx-background-color: #138824;" + BUTTON_STYLE);

        newVueButton.setMinWidth(borderPane.getWidth() / 8);
        closeButton.setMinWidth(borderPane.getWidth() / 8);
        addPointButton.setMinWidth(borderPane.getWidth() / 8);

        closeButton.addEventHandler(ActionEvent.ACTION, e -> this.closeAction());
        addPointButton.addEventHandler(ActionEvent.ACTION, e -> new AjoutDonneeView(this));
        newVueButton.addEventHandler(ActionEvent.ACTION, e -> this.nouvelleVueAction());

        elements.getChildren().addAll(addPointButton, newVueButton, closeButton);
        borderPane.setBottom(elements);
    }

    public Button getAddPointButton() {
        return this.addPointButton;
    }

    public Button getClassifyButton() {
        return this.classifyButton;
    }

    private void closeAction() {
        this.close();
        System.exit(0);
    }

    private void nouvelleVueAction() {
        View newView = new View(modele);
        this.modele.attach(newView);
    }

    private void classifyAction() {
        ClassificationView classView = new ClassificationView(this, modele);
        this.classifyButton.setDisable(true);
        this.addPointButton.setDisable(false);
    }

    public ArrayList<CategorieView> getCatList() {
        return catList;
    }

    public void changeAttributes(Attribute attr1, Attribute attr2) {
        this.attrX = attr1;
        this.attrY = attr2;
        this.loadData();
        this.configureAxis();
    }

    private void configureAxis() {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLabel(this.attrX.getNom());
        yAxis.setLabel(this.attrY.getNom());
        normalizeAxes();
    }

    public void loadData() {
        int cpt = 0;

        this.chart.getData().clear();
        this.clearSeries();


        for (DataPoint point : this.modele.getDataset().getDataPoints()) {
            CategorieView pointCategory = this.getCategorieView(point.getCategory());

            double x = this.modele.getDataset().getDataPoints().get(cpt).getAttributes().get(attrX);

            double y = this.modele.getDataset().getDataPoints().get(cpt).getAttributes().get(attrY);


            XYChart.Series<Number, Number> series = pointCategory.getSerie();
            if (!this.chart.getData().contains(series)) {
                this.chart.getData().add(series);
            }

            XYChart.Data<Number, Number> data = new XYChart.Data<>(x, y);

            if (this.modele.getDataset().getAddedPoints().contains(point)) {
                data.setNode(new Rectangle(NODE_SIZE * 2, NODE_SIZE * 2, pointCategory.getColor()));
            } else {
                data.setNode(new Circle(NODE_SIZE, pointCategory.getColor()));
            }

            series.getData().add(data);
            series.setName(point.getCategory().getName());

            cpt++;
        }

        this.configureAxis();
    }


    private void clearSeries() {
        for (DataPoint point: this.modele.getDataset().getDataPoints()) {
            CategorieView pointCategory = this.getCategorieView(point.getCategory());
            pointCategory.setSerie(new XYChart.Series<>());
        }
    }

    @Override
    public void update(Observable observable) {
        this.clear();
        this.loadData();
        for (XYChart.Series<Number, Number> series : this.getSeries()) {
            if (!this.chart.getData().contains(series)) {
                this.chart.getData().add(series);
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {}

    public void clear() {
        for (XYChart.Series<Number, Number> series : this.getSeries()) {
            series.getData().clear();
        }
    }

    public HBox getLegend() {
        HBox legend = new HBox();
        legend.setAlignment(Pos.CENTER);
        legend.setSpacing(10);
        legend.setMinHeight(40);
        for (CategorieView cate : catList) {
            HBox legendItem = new HBox();
            legendItem.setAlignment(Pos.CENTER);
            legendItem.setSpacing(4);
            legendItem.getChildren().add(new Circle(this.NODE_SIZE, cate.getColor()));
            legendItem.getChildren().add(new Label(cate.getCategory().getName()));
            legend.getChildren().add(legendItem);
        }
        return legend;
    }

    private CategorieView getCategorieView(Category category) {
        for (CategorieView tmp: this.catList) {
            if (tmp.getCategory().equals(category)) return tmp;
        }
        return new CategorieView(category, this);
    }

    private ArrayList<XYChart.Series<Number, Number>> getSeries() {
        ArrayList<XYChart.Series<Number, Number>> series = new ArrayList<>();
        for (CategorieView cat : this.catList) {
            series.add(cat.getSerie());
        }
        return series;
    }

    public String getFont() {
        return View.FONT;
    }
}
