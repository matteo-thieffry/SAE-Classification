
package fr.univlille.algoknn;

import fr.univlille.algoknn.distances.Distance;
import fr.univlille.modele.Attribute;
import fr.univlille.modele.DataPoint;

import java.util.*;

public class MethodeKnn {
    private List<DataPoint> datas;

    public MethodeKnn(List<DataPoint> datas) {
        this.datas = datas;
    }

    public DataPoint[] getKnn(int k, DataPoint point, Distance usedDistance, List<Attribute> attributes) {
        HashMap<DataPoint, Double> distances = this.getDistances(point, usedDistance, attributes);
        if (k > distances.size()) k = distances.size();
        List<Map.Entry<DataPoint, Double>> sortedDistances = MethodeKnn.sortValues(distances);
        return getNFirstElements(k, sortedDistances);
    }

    private HashMap<DataPoint, Double> getDistances(DataPoint point, Distance usedDistance, List<Attribute> attributes) {
        List<DataPoint> listVoisins = getVoisins(point);
        HashMap<DataPoint, Double> result = new LinkedHashMap<>(listVoisins.size());
        for (DataPoint voisin : listVoisins) {
            result.put(voisin, usedDistance.distance(point, voisin, attributes));
        }
        return result;
    }

    private List<DataPoint> getVoisins(DataPoint point) {
        List<DataPoint> voisins = new ArrayList<>(List.copyOf(datas));
        voisins.remove(point);
        return voisins;
    }

    private static List<Map.Entry<DataPoint, Double>> sortValues(Map<DataPoint, Double> values) {
        ArrayList<Map.Entry<DataPoint, Double>> result = new ArrayList<>(values.entrySet());
        result.sort(Map.Entry.comparingByValue());
        return result;
    }

    private DataPoint[] getNFirstElements(int n, List<Map.Entry<DataPoint, Double>> elements) {
        DataPoint[] result = new DataPoint[n];
        for (int i = 0; i < n; i++) {
            result[i] = elements.get(i).getKey();
        }
        return result;
    }
}
