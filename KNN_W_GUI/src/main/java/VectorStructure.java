import java.util.Arrays;

public class VectorStructure {
    double[] attributes;
    String label;

    public VectorStructure(double[] attributes, String label) {
        this.attributes = attributes;
        this.label = label;
    }

    public String toString() {
        return Arrays.toString(attributes) + " : " + label;
    }
}
