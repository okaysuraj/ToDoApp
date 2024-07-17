import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TaskManagerUI {
    private JFrame frame;
    private String username;
    private TaskManager taskManager;
    private JTextArea taskTextArea;

    public TaskManagerUI(String username, TaskManager taskManager) {
        this.username = username;
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
        buttonPanel.setLayout(new GridLayout(1, 3));

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(e -> {
            String taskDescription = JOptionPane.showInputDialog(frame, "Enter task description:");
            if (taskDescription != null && !taskDescription.isEmpty()) {
                Task task = new Task(taskDescription);
                taskManager.addTask(task);
                updateTaskList();
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update Task");
        updateButton.addActionListener(e -> {
            String oldDescription = JOptionPane.showInputDialog(frame, "Enter task description to update:");
            if (oldDescription != null && !oldDescription.isEmpty()) {
                String newDescription = JOptionPane.showInputDialog(frame, "Enter new task description:");
                if (newDescription != null && !newDescription.isEmpty()) {
                    taskManager.updateTask(oldDescription, newDescription);
                    updateTaskList();
                }
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(e -> {
            String taskDescription = JOptionPane.showInputDialog(frame, "Enter task description to delete:");
            if (taskDescription != null && !taskDescription.isEmpty()) {
                taskManager.deleteTask(taskDescription);
                updateTaskList();
            }
        });
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        updateTaskList();

        frame.setVisible(true);
    }

    private void updateTaskList() {
        taskTextArea.setText("");
        List<Task> tasks = taskManager.getAllTasks();
        for (Task task : tasks) {
            taskTextArea.append(task.getDescription() + (task.isCompleted() ? " (Completed)" : " (Pending)") + "\n");
        }
    }
}
