package com.student.grade;

import java.util.Scanner;

public class StudentGradeAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] studentNames = new String[0];
        double[] studentGrades = new double[0];
        double average = 0, highest = 0, lowest = 0;
        
        System.out.println("Student Grade Analyzer");
        System.out.println("----------------------");
        
        boolean running = true;
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Enter new student data");
            System.out.println("2. Search for a student");
            System.out.println("3. Update a grade");
            System.out.println("4. Display current data");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            int choice = getValidInteger(scanner);
            
            switch (choice) {
                case 1:
                    System.out.print("Enter number of students: ");
                    int numStudents = getValidInteger(scanner);
                    
                    studentNames = new String[numStudents];
                    studentGrades = new double[numStudents];
                    
                    collectStudentData(scanner, studentNames, studentGrades);
                    
                    average = calculateAverage(studentGrades);
                    highest = findHighestGrade(studentGrades);
                    lowest = findLowestGrade(studentGrades);
                    
                    displayResults(studentNames, studentGrades, average, highest, lowest);
                    break;
                case 2:
                    if (studentNames.length == 0) {
                        System.out.println("No student data available. Please enter data first.");
                    } else {
                        searchStudent(scanner, studentNames, studentGrades);
                    }
                    break;
                case 3:
                    if (studentNames.length == 0) {
                        System.out.println("No student data available. Please enter data first.");
                    } else {
                        updateGrade(scanner, studentNames, studentGrades);
                        average = calculateAverage(studentGrades);
                        highest = findHighestGrade(studentGrades);
                        lowest = findLowestGrade(studentGrades);
                        displayResults(studentNames, studentGrades, average, highest, lowest);
                    }
                    break;
                case 4:
                    if (studentNames.length == 0) {
                        System.out.println("No student data available to display.");
                    } else {
                        displayResults(studentNames, studentGrades, average, highest, lowest);
                    }
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        System.out.println("Thank you for using Student Grade Analyzer!");
        scanner.close();
    }
    
    private static void collectStudentData(Scanner scanner, String[] names, double[] grades) {
        for (int i = 0; i < names.length; i++) {
            System.out.printf("\nStudent #%d:\n", i + 1);
            names[i] = getValidName(scanner);
            grades[i] = getValidGrade(scanner);
        }
    }
    
    private static String getValidName(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name;
        while (true) {
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                return name;
            }
            System.out.print("Name cannot be empty. Please enter a valid name: ");
        }
    }
    
    private static double getValidGrade(Scanner scanner) {
        System.out.print("Enter student grade (0-100): ");
        while (true) {
            try {
                double grade = Double.parseDouble(scanner.nextLine());
                if (grade >= 0 && grade <= 100) {
                    return grade;
                }
                System.out.print("Grade must be between 0 and 100. Please try again: ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a numeric grade: ");
            }
        }
    }
    
    private static int getValidInteger(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    private static int getValidIntegerInRange(Scanner scanner, int min, int max) {
        while (true) {
            int num = getValidInteger(scanner);
            if (num >= min && num <= max) {
                return num;
            }
            System.out.printf("Please enter a number between %d and %d: ", min, max);
        }
    }
    
    private static double calculateAverage(double[] grades) {
        if (grades.length == 0) return 0;
        
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }
    
    private static double findHighestGrade(double[] grades) {
        if (grades.length == 0) return 0;
        
        double highest = grades[0];
        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return highest;
    }
    
    private static double findLowestGrade(double[] grades) {
        if (grades.length == 0) return 0;
        
        double lowest = grades[0];
        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return lowest;
    }
    
    private static void displayResults(String[] names, double[] grades, 
                                     double average, double highest, double lowest) {
        if (names.length == 0) {
            System.out.println("No student data available to display.");
            return;
        }
        
        // Create copies to avoid modifying original arrays
        String[] sortedNames = names.clone();
        double[] sortedGrades = grades.clone();
        
        sortStudentsByGrade(sortedNames, sortedGrades);
        
        System.out.println("\nStudent Grade Analysis (Sorted by Grade)");
        System.out.println("----------------------------------------");
        
        // Display all students and their grades
        for (int i = 0; i < sortedNames.length; i++) {
            System.out.printf("%s: %.2f (%s)\n", 
                sortedNames[i], 
                sortedGrades[i], 
                convertToLetterGrade(sortedGrades[i]));
        }
        
        System.out.println("\nStatistics:");
        System.out.printf("Average Grade: %.2f (%s)\n", average, convertToLetterGrade(average));
        System.out.printf("Highest Grade: %.2f (%s) - %s\n", 
            highest, 
            convertToLetterGrade(highest),
            names[findIndex(grades, highest)]);
        System.out.printf("Lowest Grade: %.2f (%s) - %s\n", 
            lowest, 
            convertToLetterGrade(lowest),
            names[findIndex(grades, lowest)]);
    }
    
    private static String convertToLetterGrade(double grade) {
        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }
    
    private static void sortStudentsByGrade(String[] names, double[] grades) {
        // Using bubble sort for simplicity
        for (int i = 0; i < grades.length - 1; i++) {
            for (int j = 0; j < grades.length - i - 1; j++) {
                if (grades[j] < grades[j + 1]) {
                    // Swap grades
                    double tempGrade = grades[j];
                    grades[j] = grades[j + 1];
                    grades[j + 1] = tempGrade;
                    
                    // Swap names
                    String tempName = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempName;
                }
            }
        }
    }
    
    private static int findIndex(double[] array, double value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    private static void searchStudent(Scanner scanner, String[] names, double[] grades) {
        System.out.print("\nEnter student name to search: ");
        String searchName = scanner.nextLine().trim().toLowerCase();
        
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (names[i].toLowerCase().contains(searchName)) {
                System.out.printf("%s: %.2f (%s)\n", 
                    names[i], 
                    grades[i], 
                    convertToLetterGrade(grades[i]));
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No students found with that name.");
        }
    }
    
    private static void updateGrade(Scanner scanner, String[] names, double[] grades) {
        System.out.println("\nCurrent Students:");
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%d. %s: %.2f (%s)\n", 
                i + 1, 
                names[i], 
                grades[i],
                convertToLetterGrade(grades[i]));
        }
        
        System.out.print("\nEnter student number to update: ");
        int studentNum = getValidIntegerInRange(scanner, 1, names.length) - 1;
        
        System.out.printf("Current grade for %s: %.2f (%s)\n", 
            names[studentNum], 
            grades[studentNum],
            convertToLetterGrade(grades[studentNum]));
        System.out.print("Enter new grade: ");
        grades[studentNum] = getValidGrade(scanner);
        
        System.out.println("Grade updated successfully.");
    }
}