import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KNN {
    public List<VectorStructure> loadFromCsv(String filename) {
        List<VectorStructure> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                double[] attributes = new double[parts.length - 1];

                for (int i = 0; i < attributes.length; i++) {
                    attributes[i] = Double.parseDouble(parts[i]);
                }

                data.add(new VectorStructure(attributes, parts[parts.length - 1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public double countDistance(double[] a, double[] b) {
        double result = 0;

        for (int i = 0; i < a.length; i++) {
            result += Math.pow((a[i] - b[i]), 2);
        }

        return Math.sqrt(result);
    }

    public String classify(List<VectorStructure> trainSet, double[] vector, int k) {
        double[][] labeledDistances = new double[trainSet.size()][2];
        for (int i = 0; i < trainSet.size(); i++) {
            double distance = countDistance(trainSet.get(i).attributes, vector);
            labeledDistances[i][0] = distance;
            labeledDistances[i][1] = i;
        }
        Arrays.sort(labeledDistances, Comparator.comparingDouble(a -> a[0]));

        String[] votes = new String[k];
        for (int i = 0; i < k; i++) {
            int index = (int) labeledDistances[i][1];
            String label = trainSet.get(index).label;
            votes[i] = label;
        }

        Map<String, Integer> voteCount = new HashMap<>();
        for (String vote : votes) {
            voteCount.put(vote, voteCount.getOrDefault(vote, 0) + 1);
        }

        int maxVotes = -1;
        String classified = null;
        for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                classified = entry.getKey();
            }
        }
        return classified;
    }
}
