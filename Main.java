import java.io.*;
import java.util.*;

// Task class to store task details
class Task {
    int id;
    String description;

    Task(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String toString() {
        return id + ". " + description;
    }
}

public class Main {
    static List<Task> tasks = new ArrayList<>();
    static int taskId = 1;
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadTasks();

        while (true) {
            System.out.println("\n===== STUDENT TASK MANAGER =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Delete Task");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            // Handle invalid input
            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String desc = sc.nextLine();

                    if (desc.trim().isEmpty()) {
                        System.out.println("Task cannot be empty!");
                        break;
                    }

                    tasks.add(new Task(taskId++, desc));
                    saveTasks();
                    System.out.println("✅ Task added successfully!");
                    break;

                case 2:
                    if (tasks.isEmpty()) {
                        System.out.println("⚠️ No tasks available.");
                    } else {
                        System.out.println("\nYour Tasks:");
                        for (Task t : tasks) {
                            System.out.println(t);
                        }
                    }
                    break;

                case 3:
                    if (tasks.isEmpty()) {
                        System.out.println("⚠️ No tasks to delete.");
                        break;
                    }

                    System.out.print("Enter task ID to delete: ");
                    int id;

                    try {
                        id = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid ID!");
                        sc.nextLine();
                        break;
                    }

                    boolean removed = tasks.removeIf(t -> t.id == id);

                    if (removed) {
                        saveTasks();
                        System.out.println("🗑️ Task deleted successfully!");
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting... Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Save tasks to file
    static void saveTasks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                pw.println(t.id + "," + t.description);
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    // Load tasks from file
    static void loadTasks() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return; // No file yet
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);

                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0]);
                    String desc = parts[1];

                    tasks.add(new Task(id, desc));
                    taskId = Math.max(taskId, id + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks.");
        }
    }
}
