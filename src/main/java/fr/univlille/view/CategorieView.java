
package fr.univlille.view;

import fr.univlille.modele.Category;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.Random;

public class CategorieView {
    private final Category category;
    private XYChart.Series<Number, Number> serie;
    private final Color color;
    private static Color[] colorstab = new Color[]{Color.rgb(55,114,255), Color.rgb(253,202,64),Color.rgb(30,165,0),Color.rgb(223,41,53),Color.rgb(174,1,254),Color.rgb(8,7,8)};

    public CategorieView(Category category, View view) {
        this.category = category;
        this.serie = new XYChart.Series<>();
        this.color = CategorieView.generateRandomColor(view.getCatList().size()+1);
        view.getCatList().add(this);

    }

    public static Color generateRandomColor(int numColor) {
        Random rd = new Random();
        return Color.rgb(rd.nextInt(255),rd.nextInt(255),rd.nextInt(255));
    }

    public Category getCategory() {
        return category;
    }

    public XYChart.Series<Number, Number> getSerie() {
        return serie;
    }

    public Color getColor() {
        return color;
    }

    public void setSerie(XYChart.Series<Number, Number> serie) {
        this.serie = serie;
    }
}
