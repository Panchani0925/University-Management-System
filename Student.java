import java.util.Arrays;

// Student class for storing student information
public class Student {
    private String studentID; // Student ID
    private String studentName; // student name
    private int[] moduleMarks = new int[3]; // Marks for three modules
    private double averageMarks; // Average marks of the student
    private String moduleGrade; // Grade based on average marks

    // Constructor ti initialize student
    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    // Get student ID
    public String getStudentID() {
        return studentID;
    }

    // Get student name
    public String getStudentName() {
        return studentName;
    }

    // Set student name
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Get  marks for modules
    public int[] getModuleMarks() {
        return moduleMarks;
    }

    // Set marks for modules and calculate average marks and grade
    public void setModuleMarks(int[] moduleMarks) {
        this.moduleMarks = moduleMarks;
        calculateAverageMarks();
        calculateGrade();
    }

    // Calculate average marks
    private void calculateAverageMarks() {
        averageMarks = Arrays.stream(moduleMarks).average().orElse(0.0);
    }

    // Method to calculate grade
    private void calculateGrade() {
        if (averageMarks >= 80) {
            moduleGrade = "Distinction";
        } else if (averageMarks >= 70) {
            moduleGrade = "Merit";
        } else if (averageMarks >= 40) {
            moduleGrade = "Pass";
        } else {
            moduleGrade = "Fail";
        }
    }

    // Get average marks
    public double getAverageMarks() {
        return averageMarks;
    }

    // Get module grade
    public String getModuleGrade() {
        return moduleGrade;
    }

    // Convert student details to a string
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%.2f,%s",
                studentID, studentName, Arrays.toString(moduleMarks), averageMarks, moduleGrade);
    }

    // Generate a complete report of the student
    public String completeReport() {
        int total = Arrays.stream(moduleMarks).sum();
        return String.format("ID: %s, Name: %s, Module 1: %d, Module 2: %d, Module 3: %d, Total: %d, Average: %.2f, Grade: %s",
                studentID, studentName, moduleMarks[0], moduleMarks[1], moduleMarks[2], total, averageMarks, moduleGrade);
    }

    // Method to create a student object from a string
    public static Student fromString(String str) {
        String[] parts = str.split(","); // Split string into parts
        Student student = new Student(parts[0], parts[1]); // Create new student
        String marksString = parts[2].substring(1, parts[2].length() - 1).trim(); // Remove brackets from marks
        int[] marks = Arrays.stream(marksString.isEmpty() ? new String[0] : marksString.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray(); //Convert string to int array
        student.setModuleMarks(marks); // Set marks for student
        student.averageMarks = Double.parseDouble(parts[3]); // Set average marks
        student.moduleGrade = parts[4]; // Set grade
        return student;
    }
}
