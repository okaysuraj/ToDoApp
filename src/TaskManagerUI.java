import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TaskManagerUI {
    private JFrame frame;
    private String username;
    private int userId;
    private TaskManager taskManager;
    private JTextArea taskTextArea;

    public TaskManagerUI(String username, int userId, TaskManager taskManager) {
        this.username = username;
        this.userId = userId;
        this.taskManager = taskManager;

        frame = new JFrame("Task Manager");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Welcome, " + username + "!");
        frame.add(usernameLabel, BorderLayout.NORTH);

        taskTextArea = new JTextArea();
        taskTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> {
            String taskDescription = JOptionPane.showInputDialog(frame, "Enter task description:");
            if (taskDescription != null && !taskDescription.trim().isEmpty()) {
                Task task = new Task(taskDescription, userId);
                taskManager.addTask(task);
                updateTaskList();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update Task");
        updateButton.addActionListener(e -> {
            String oldDescription = JOptionPane.showInputDialog(frame, "Enter current task description:");
            String newDescription = JOptionPane.showInputDialog(frame, "Enter new task description:");
            if (oldDescription != null && newDescription != null && !oldDescription.trim().isEmpty() && !newDescription.trim().isEmpty()) {
                taskManager.updateTask(oldDescription, newDescription, userId);
                updateTaskList();
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(e -> {
            String description = JOptionPane.showInputDialog(frame, "Enter task description to delete:");
            if (description != null && !description.trim().isEmpty()) {
                taskManager.deleteTask(description, userId);
                updateTaskList();
            }
        });
        buttonPanel.add(deleteButton);

        JButton completeButton = new JButton("Toggle Complete");
        completeButton.addActionListener(e -> {
            String description = JOptionPane.showInputDialog(frame, "Enter task description to toggle completion:");
            if (description != null && !description.trim().isEmpty()) {
                List<Task> tasks = taskManager.getAllTasks(userId);
                for (Task task : tasks) {
                    if (task.getDescription().equals(description)) {
                        task.setCompleted(!task.isCompleted());
                        taskManager.updateTask(task.getDescription(), task.getDescription(), userId);
                        break;
                    }
                }
                updateTaskList();
            }
        });
        buttonPanel.add(completeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        updateTaskList();
    }

    private void updateTaskList() {
        List<Task> tasks = taskManager.getAllTasks(userId);
        taskTextArea.setText("");
        for (Task task : tasks) {
            String taskText = task.getDescription() + (task.isCompleted() ? " [Completed]" : "");
            taskTextArea.append(taskText + "\n");
        }
    }
}
