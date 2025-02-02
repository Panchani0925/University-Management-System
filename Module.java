import java.util.Arrays;

public class Module {
    // Array to store marks for 3 modules
    private int[] moduleMarks = new int[3];
    //  String to store the grade for the modules
    private String moduleGrade;

    // Get marks for modules
    public int[] getModuleMarks() {
        return moduleMarks;
    }

    // Method to set the module marks and calculate the grade
    public void setModuleMarks(int[] moduleMarks) {
        this.moduleMarks = moduleMarks; //Set the marks
        calculateGrade(); // Calculate the grade based on the marks
    }

    // Method to get the average marks
    public double getAverageMarks() {
        return Arrays.stream(moduleMarks).average().orElse(0.0); // Calculate and return the average marks
    }

    // Private method to calculate the grade
    private void calculateGrade() {
        double average = getAverageMarks(); // Get the average marks
        if (average >= 80) {
            moduleGrade = "Distinction"; // If average is 80 or above, grade is Distinction
        } else if (average >= 70) {
            moduleGrade = "Merit"; // If average is 70 to 79, grade is Merit
        } else if (average >= 40) {
            moduleGrade = "Pass"; // If average is 40 to 69, grade is Pass
        } else {
            moduleGrade = "Fail"; // If below 40, grade is Fail
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(moduleMarks) + "," + moduleGrade;
    }
}
