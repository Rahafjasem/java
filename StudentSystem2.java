package StudentSystem2;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class StudentSystem2 {

    // Student Data + Static Array (Unsorted List)

    static class Student {
        int rollNo;
        String name;
        double marks;

        Student(int r, String n, double m) {
            rollNo = r;
            name = n;
            marks = m;
        }

        public String toString() {
            return rollNo + " | " + name + " | " + marks;
        }
    }

    static Student[] staticArray = new Student[50];
    static int count = 0;

    static void addToArray(Student s) {
        staticArray[count++] = s;
    }

    static void displayUnsorted() {
        System.out.println("Unsorted List:");
        for (int i = 0; i < count; i++) {
            System.out.println(staticArray[i]);
        }
    }

    static void searchStudent(int roll) {
        for (int i = 0; i < count; i++) {
            if (staticArray[i].rollNo == roll) {
                System.out.println("Found: " + staticArray[i]);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    
    // Dynamic Linked List + Sorted List

    static class Node {
        Student data;
        Node next;

        Node(Student s) {
            data = s;
            next = null;
        }
    }

    static Node head = null;

    static void addToLinkedList(Student s) {
        Node newNode = new Node(s);
        if (head == null)
            head = newNode;
        else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }
    }

    static void displaySortedByMarks() {
        Student[] temp = staticArray.clone();

        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                if (temp[i].marks > temp[j].marks) {
                    Student t = temp[i];
                    temp[i] = temp[j];
                    temp[j] = t;
                }
            }
        }

        System.out.println("Sorted List (by marks):");
        for (int i = 0; i < count; i++) {
            System.out.println(temp[i]);
        }
    }

    // Stack (Undo) + Queue (Registration)
    

    static Stack<Student> undoStack = new Stack<>();
    static Queue<Student> registrationQueue = new LinkedList<>();

    static void undoLastAdd() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Student removed = undoStack.pop();
        count--;

        if (head != null) {
            if (head.data == removed)
                head = head.next;
            else {
                Node prev = head;
                Node curr = head.next;
                while (curr != null && curr.data != removed) {
                    prev = curr;
                    curr = curr.next;
                }
                if (curr != null)
                    prev.next = curr.next;
            }
        }

        System.out.println("Undo successful.");
    }

    static void addToQueue(Student s) {
        registrationQueue.add(s);
        System.out.println("Student added to registration queue.");
    }

    static void processQueue() {
        if (registrationQueue.isEmpty())
            System.out.println("Queue is empty.");
        else
            System.out.println("Processing: " + registrationQueue.poll());
    }

    
    // Main Menu 

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Display Unsorted");
            System.out.println("3. Display Sorted");
            System.out.println("4. Search Student");
            System.out.println("5. Undo Last Add");
            System.out.println("6. Add to Registration Queue");
            System.out.println("7. Process Registration Queue");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            choice = in.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Roll No: ");
                    int r = in.nextInt();
                    System.out.print("Name: ");
                    String n = in.next();
                    System.out.print("Marks: ");
                    double m = in.nextDouble();

                    Student s = new Student(r, n, m);
                    addToArray(s);
                    addToLinkedList(s);
                    undoStack.push(s);
                    break;

                case 2:
                    displayUnsorted();
                    break;

                case 3:
                    displaySortedByMarks();
                    break;

                case 4:
                    System.out.print("Enter roll no: ");
                    searchStudent(in.nextInt());
                    break;

                case 5:
                    undoLastAdd();
                    break;

                case 6:
                    if (count > 0)
                        addToQueue(staticArray[count - 1]);
                    break;

                case 7:
                    processQueue();
                    break;
            }

        } while (choice != 0);

        in.close();
    }
}