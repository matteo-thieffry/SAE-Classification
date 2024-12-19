
package fr.univlille.datamanipulation;

import com.opencsv.bean.CsvBindByName;
import fr.univlille.modele.AttributeType;

import java.util.Arrays;
import java.util.List;

public class IrisDonneeBrut extends FormatDonneebrut{
    public final static int NB_ATTRIBUTES = 5;
    @CsvBindByName(column = "sepal.length")
    public double sepalLength;
    @CsvBindByName(column = "sepal.width")
    public double sepalWidth;
    @CsvBindByName(column = "petal.length")
    public double petalLength;
    @CsvBindByName(column = "petal.width")
    public double petalWidth;
    @CsvBindByName(column = "variety")
    public String variety;

    public static List<String> columnList = Arrays.asList("sepal.length","sepal.width","petal.length","petal.width","variety");
    public static List<AttributeType> attributeTypeList = Arrays.asList(AttributeType.NUM,AttributeType.NUM,AttributeType.NUM,AttributeType.NUM,AttributeType.STR);

    public double getSepalLength() {
        return sepalLength;
    }

    public double getSepalWidth() {
        return sepalWidth;
    }

    public double getPetalLength() {
        return petalLength;
    }

    public double getPetalWidth() {
        return petalWidth;
    }

    public String getVariety() {
        return variety;
    }

    public String toString() {
        return "Sepal length : " + this.sepalLength + ", Sepal width : " + this.sepalWidth + ", Petal length : " + this.petalLength + ", Petal width : " + this.petalWidth + ", Variety : " + this.variety;
    }

    public static Object[][] getValues(List<? extends FormatDonneebrut> list) {
        Object[][] result = new Object[list.size()][NB_ATTRIBUTES];
        for (int idxList = 0; idxList < list.size(); idxList++) {
            IrisDonneeBrut tmp = (IrisDonneeBrut) list.get(idxList);
            result[idxList][0] = tmp.getSepalLength();
            result[idxList][1] = tmp.getSepalWidth();
            result[idxList][2] = tmp.getPetalLength();
            result[idxList][3] = tmp.getPetalWidth();
            result[idxList][4] = tmp.getVariety();
        }
        return result;
    }

    public static void displayData(List<IrisDonneeBrut> donnees) {
        for (IrisDonneeBrut tmp: donnees) {
            System.out.println(tmp);
        }
    }
}
