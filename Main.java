import java.io.*; // import for file handling
import java.util.*;// import for utility classes

//Main Class for managing student records
public class Main {
    private static final int MAX_STUDENTS = 100; // Maximum number of students allowed
    private static List<Student> students = new ArrayList<>(); // List to store students

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        while (true) {
            printMenu(); // Display the menu options
            String choice = scanner.nextLine().trim(); // read the user's choice
            switch (choice) {
                case "1":
                    checkAvailableSeats(); // Check and display available seats
                    break;
                case "2":
                    // Register a new student
                    String studentID = promptForStudentID(scanner, "Enter Student ID: ");
                    String studentName = promptForStudentName(scanner, "Enter Student Name: ");
                    registerStudent(studentID, studentName);
                    break;
                case "3":
                    // Delete a student by ID
                    studentID = promptForStudentID(scanner, "Enter Student ID to delete: ");
                    deleteStudent(studentID);
                    break;
                case "4":
                    // Find a student by ID
                    studentID = promptForStudentID(scanner, "Enter Student ID to find: ");
                    findStudent(studentID);
                    break;
                case "5":
                    storeStudentDetails(scanner); // Save student details to a file
                    break;
                case "6":
                    loadStudentDetails(scanner); // Load student details from a file
                    break;
                case "7":
                    sortStudentsByName(); // sort and display students by name
                    break;
                case "8":
                    additionalControls(scanner); // Show additional controls
                    break;
                case "0":
                    System.out.println("Exiting..."); // Exit the program
                    scanner.close(); // close the scanner
                    return;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
    }
    // Method to display the menu options
    private static void printMenu() {
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID and Name)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from a file");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. More controls");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to check and display available seats
    private static void checkAvailableSeats() {
        System.out.println("Available seats: " + (MAX_STUDENTS - students.size()));
    }

    // Method to register a new student
    private static void registerStudent(String studentID, String studentName) {
        if (students.size() < MAX_STUDENTS) {
            Student student = new Student(studentID, studentName); // Create a new student object
            students.add(student); // Add student to the list
            System.out.println("Student registered successfully.");
        } else {
            System.out.println("No available seats.");
        }
    }

    // Method to delete a student by ID
    private static void deleteStudent(String studentID) {
        boolean removed = students.removeIf(student -> student.getStudentID().equals(studentID)); // Remove student if ID matches
        if (removed) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Method to find and display a student by ID
    private static void findStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                System.out.println(student); // Display student details
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Methos to store student details to a file
    private static void storeStudentDetails(Scanner scanner) {
        System.out.print("Enter filename to store student details: ");
        String filename = scanner.nextLine().trim(); // Get the filename from user
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.println(student); // Write each student's details to the file
            }
            System.out.println("Student details stored in " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to load student details from a file
    private static void loadStudentDetails(Scanner scanner) {
        String filename;
        while (true) {
            filename = promptForFilename(scanner, "Enter filename to load student details: "); // Get the filename from user
            if (filename != null) {
                try (Scanner fileScanner = new Scanner(new File(filename))) {
                    students.clear(); // Clear current student list
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        if (!line.trim().isEmpty()) {
                            students.add(Student.fromString(line)); // Add student details from file
                        }
                    }
                    System.out.println("Student details loaded from " + filename);
                    break;
                } catch (FileNotFoundException e) {
                    System.err.println("File not found: " + filename);
                }
            }
        }
    }

    // Helper method to prompt user for filename
    private static String promptForFilename(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String filename = scanner.nextLine().trim(); // Get the filename from user
        if (filename.isEmpty()) {
            System.out.println("Filename cannot be empty. Please enter a filename.");
            return null;
        }
        return filename;
    }

    // Method to sort and display students by name
    private static void sortStudentsByName() {
        students.sort(Comparator.comparing(Student::getStudentName)); // Sort students by name
        for (Student student : students) {
            System.out.println(student); //Display sorted student details
        }
    }

    // Method for additional controls
    private static void additionalControls(Scanner scanner) {
        while (true) {
            System.out.println("a. Add name");
            System.out.println("b. Add module marks");
            System.out.println("c. Generate summary");
            System.out.println("d. Generate complete report");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim(); // Get user choice
            switch (choice) {
                case "a":
                    addStudentName(scanner); // Add student name
                    break;
                case "b":
                    addModuleMarks(scanner); // Add marks for modules
                    break;
                case "c":
                    generateSummary(); // Generate summary of student marks
                    break;
                case "d":
                    generateCompleteReport(); // Generate complete report of student marks
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add student name
    private static void addStudentName(Scanner scanner) {
        String studentID = promptForStudentID(scanner, "Enter Student ID: ");
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                String studentName = promptForStudentName(scanner, "Enter Student Name: ");
                student.setStudentName(studentName); // Set student name
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to add module marks for a student
    private static void addModuleMarks(Scanner scanner) {
        String studentID = promptForStudentID(scanner, "Enter Student ID: ");
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                int[] marks = new int[3]; // Array to store marks
                for (int i = 0; i < 3; i++) {
                    marks[i] = promptForModuleMark(scanner, "Enter Module " + (i + 1) + " marks: ");
                }
                student.setModuleMarks(marks); // Set marks for student
                return;
            }
        }
        System.out.println("Student not found.");
    }

    //Method to generate summary of student marks
    private static void generateSummary() {
        int totalRegistrations = students.size(); // Total number of students
        int[] modulePassCounts = new int[3];// Array to store pass counts for each module
        for (Student student : students) {
            int[] marks = student.getModuleMarks(); //Get marks for each student
            for (int i = 0; i < 3; i++) {
                if (marks[i] >= 40) { // Check if student passed the module
                    modulePassCounts[i]++;
                }
            }
        }
        System.out.println("Total student registrations: " + totalRegistrations);
        for (int i = 0; i < 3; i++) {
            System.out.println("Students passed in Module " + (i + 1) + ": " + modulePassCounts[i]);
        }
    }

    // Method to generate complete report of student marks
    private static void generateCompleteReport() {
        bubbleSortStudentsByAverageMarks(); //Sort student by average marks
        for (Student student : students) {
            System.out.println(student.completeReport());// Display complete report for each student
        }
    }

    // Method to sort students by average marks using  sort
    private static void bubbleSortStudentsByAverageMarks() {
        int n = students.size(); // Number of students
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (students.get(i).getAverageMarks() < students.get(i + 1).getAverageMarks()) {
                    students.add(i, students.remove(i + 1)); // Swap students
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    // Prompt user for a student ID
    private static String promptForStudentID(Scanner scanner, String prompt) {
        String studentID;
        while (true) {
            System.out.print(prompt);
            studentID = scanner.nextLine().trim();
            if (!studentID.isEmpty() && studentID.matches("\\w\\d{7}")) {
                break;
            } else {
                System.out.println("Invalid Student ID. Please try again.");
            }
        }
        return studentID;
    }

    // Prompt user for a student name
    private static String promptForStudentName(Scanner scanner, String prompt) {
        String studentName;
        while (true) {
            System.out.print(prompt);
            studentName = scanner.nextLine().trim();
            if (!studentName.isEmpty() && studentName.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Student name must contain only English letters and spaces. Please try again.");
            }
        }
        return studentName;
    }

    // Prompt user for module marks
    private static int promptForModuleMark(Scanner scanner, String prompt) {
        int mark;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    mark = Integer.parseInt(input);
                    if (mark >= 0 && mark <= 100) {
                        break;
                    } else {
                        System.out.println("Invalid mark. Please enter a value between 0 and 100.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numeric value.");
                }
            } else {
                System.out.println("Input cannot be empty. Please enter a mark.");
            }
        }
        return mark;
    }
}

