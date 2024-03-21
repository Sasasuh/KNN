# KNN Classifier GUI

This project contains an implementation of the k-nearest neighbors (KNN) algorithm along with a graphical user interface (GUI) written in Java. The KNN algorithm is used for classifying data based on their features and known class labels.

## Features

### 1. Classification

The user interface allows the user to input the value of the K parameter and choose the classification option:
- Use test data: The algorithm classifies data using the test dataset and displays the classification accuracy.
- Enter a custom vector: The user can input a custom feature vector and receive the classification result.
- Sample trainSet and testSet files are located in the data folder

### 2. Accuracy chart

The project also includes a chart displaying the classification accuracy based on the value of the K parameter.

## Usage

To use the project, follow these steps:
1. Clone the repository to your local machine.
2. Run the project in a Java-compatible development environment.
3. Launch the main class `KNN_GUI` to start the graphical user interface.
4. Input the value of the K parameter and select a classification option.
5. Check the classification results in the text area.

## Requirements

To use the project, you need:
- A Java development environment (Java 8 or newer recommended).
- JFreeChart library for displaying charts (included in the project).
