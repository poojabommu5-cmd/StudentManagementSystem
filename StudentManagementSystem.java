import java.io.*;
import java.util.*;

// Node class (each student is one node)
class Student implements Serializable {
    int rollNo;
    String name;
    double marks;
    Student next;

    Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        this.next = null;
    }
}

// Linked List class for managing students
class StudentList implements Serializable {
    private Student head;

    // Add a student
    public void addStudent(int rollNo, String name, double marks) {
        Student newStudent = new Student(rollNo, name, marks);
        if (head == null) {
            head = newStudent;
        } else {
            Student temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newStudent;
        }
        System.out.println("✅ Student added successfully!");
    }

    // Display all students
    public void displayStudents() {
        if (head == null) {
            System.out.println("❌ No students found!");
            return;
        }
        Student temp = head;
        System.out.println("\n--- 📖 Student Records ---");
        while (temp != null) {
            System.out.println("Roll No: " + temp.rollNo + " | Name: " + temp.name + " | Marks: " + temp.marks);
            temp = temp.next;
        }
    }

    // Search student by roll number
    public void searchStudent(int rollNo) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                System.out.println("🎯 Student Found → Name: " + temp.name + ", Marks: " + temp.marks);
                return;
            }
            temp = temp.next;
        }
        System.out.println("❌ Student not found!");
    }

    // Update student details
    public void updateStudent(int rollNo, String newName, double newMarks) {
        Student temp = head;
        while (temp != null) {
            if (temp.rollNo == rollNo) {
                temp.name = newName;
                temp.marks = newMarks;
                System.out.println("✅ Student details updated successfully!");
                return;
            }
            temp = temp.next;
        }
        System.out.println("❌ Student not found!");
    }

    // Delete student
    public void deleteStudent(int rollNo) {
        if (head == null) {
            System.out.println("❌ No students to delete!");
            return;
        }
        if (head.rollNo == rollNo) {
            head = head.next;
            System.out.println("🗑️ Student deleted successfully!");
            return;
        }
        Student temp = head;
        while (temp.next != null && temp.next.rollNo != rollNo) {
            temp = temp.next;
        }
        if (temp.next == null) {
            System.out.println("❌ Student not found!");
        } else {
            temp.next = temp.next.next;
            System.out.println("🗑️ Student deleted successfully!");
        }
    }

    // Count students
    public void countStudents() {
        int count = 0;
        Student temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        System.out.println("📊 Total Students: " + count);
    }

    // Sort by marks (descending order)
    public void sortByMarks() {
        if (head == null || head.next == null) return;
        for (Student i = head; i != null; i = i.next) {
            for (Student j = i.next; j != null; j = j.next) {
                if (i.marks < j.marks) {
                    double tempMarks = i.marks;
                    i.marks = j.marks;
                    j.marks = tempMarks;

                    String tempName = i.name;
                    i.name = j.name;
                    j.name = tempName;

                    int tempRoll = i.rollNo;
                    i.rollNo = j.rollNo;
                    j.rollNo = tempRoll;
                }
            }
        }
        System.out.println("✅ Students sorted by marks (high → low)!");
    }

    // Save data to file
    public void saveStudents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("⚠️ Error saving data: " + e.getMessage());
        }
    }

    // Load data from file
    public static StudentList loadStudents() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"))) {
            return (StudentList) in.readObject();
        } catch (FileNotFoundException e) {
            return new StudentList(); // if no file found, return empty list
        } catch (Exception e) {
            System.out.println("⚠️ Error loading data: " + e.getMessage());
            return new StudentList();
        }
    }
}

// Main class
public class StudentManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentList list = StudentList.loadStudents();
        int choice;

        do {
            System.out.println("\n===== 🎓 STUDENT DATA MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Count Students");
            System.out.println("7. Sort by Marks");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    list.addStudent(roll, name, marks);
                    list.saveStudents();
                    break;

                case 2:
                    list.displayStudents();
                    break;

                case 3:
                    System.out.print("Enter Roll No to Search: ");
                    int searchRoll = sc.nextInt();
                    list.searchStudent(searchRoll);
                    break;

                case 4:
                    System.out.print("Enter Roll No to Update: ");
                    int updateRoll = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter New Marks: ");
                    double newMarks = sc.nextDouble();
                    list.updateStudent(updateRoll, newName, newMarks);
                    list.saveStudents();
                    break;

                case 5:
                    System.out.print("Enter Roll No to Delete: ");
                    int delRoll = sc.nextInt();
                    list.deleteStudent(delRoll);
                    list.saveStudents();
                    break;

                case 6:
                    list.countStudents();
                    break;

                case 7:
                    list.sortByMarks();
                    list.saveStudents();
                    break;

                case 8:
                    System.out.println("💾 Saving data and exiting... Goodbye!");
                    list.saveStudents();
                    break;

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }

        } while (choice != 8);

        sc.close();
    }
}
