import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class KNN_GUI extends JFrame {

    private KNN knnTest = new KNN();
    private List<VectorStructure> trainSet;
    private List<VectorStructure> testSet;

    private JTextField kTextField;
    private JRadioButton testDataButton;
    private JRadioButton customVectorButton;
    private JTextField vectorTextField;
    private JLabel infoLabel;
    private JTextArea resultTextArea;
    private ChartPanel chartPanel;

    public KNN_GUI() {
        super("KNN Classifier");

        trainSet = knnTest.loadFromCsv("data/trainSet.csv");
        testSet = knnTest.loadFromCsv("data/testSet.csv");

        JLabel kLabel = new JLabel("Enter the value of K:");
        kTextField = new JTextField(5);

        testDataButton = new JRadioButton("Use test data");
        customVectorButton = new JRadioButton("Enter custom vector");
        customVectorButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(testDataButton);
        group.add(customVectorButton);

        vectorTextField = new JTextField(20);

        infoLabel = new JLabel();
        infoLabel.setText("<html><center>Number of attributes must be " + trainSet.get(0).attributes.length + "<br>Np. 1.2;3.4;5.3;3.4</center></html>");


        JButton classifyButton = new JButton("Classify");

        classifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classify();
            }
        });

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart("Accuracy vs. K",
                "K",
                "Accuracy",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(kLabel, gbc);

        gbc.gridx = 1;
        panel.add(kTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(testDataButton, gbc);

        gbc.gridy = 2;
        panel.add(customVectorButton, gbc);

        gbc.gridy = 3;
        panel.add(vectorTextField, gbc);

        gbc.gridy = 4;
        panel.add(infoLabel, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(classifyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 7;
        panel.add(chartPanel, gbc);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        updateChart();
    }

    private void classify() {
        int k = Integer.parseInt(kTextField.getText());
        if (testDataButton.isSelected()) {
            showTestResults(k);
        } else {
            String[] vectorStr = vectorTextField.getText().split(";");
            double[] vector = new double[vectorStr.length];
            try {
                for (int i = 0; i < vectorStr.length; i++) {
                    vector[i] = Double.parseDouble(vectorStr[i]);
                }
                String result = knnTest.classify(trainSet, vector, k);
                resultTextArea.setText("Custom Vector: " + Arrays.toString(vector) + " - Result: " + result);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void showTestResults(int k) {
        StringBuilder result = new StringBuilder();
        int correct = 0;
        for (VectorStructure instance : testSet) {
            String classified = knnTest.classify(trainSet, instance.attributes, k);
            if (classified.equals(instance.label)) {
                correct++;
            }
            result.append("Data: ").append(Arrays.toString(instance.attributes)).append(" - Result: ").append(classified).append("\n");
        }
        result.append("Accuracy: ").append((double) correct / testSet.size());
        resultTextArea.setText(result.toString());

        updateChart();
    }

    private void updateChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int k = 1; k <= 40; k++) {
            int correct = 0;
            for (VectorStructure instance : testSet) {
                String classified = knnTest.classify(trainSet, instance.attributes, k);
                if (classified.equals(instance.label)) {
                    correct++;
                }
            }
            double accuracy = (double) correct / testSet.size();
            dataset.addValue(accuracy, "Accuracy", String.valueOf(k));
        }
        JFreeChart chart = ChartFactory.createLineChart("Accuracy vs. K", "K", "Accuracy", dataset, PlotOrientation.VERTICAL, true, true, false);
        chartPanel.setChart(chart);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(KNN_GUI::new);
    }
}
