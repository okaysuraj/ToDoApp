import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Connection connection;

    public TaskManager() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public void addTask(Task task) {
        String query = "INSERT INTO tasks (description, completed, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, task.getDescription());
            stmt.setBoolean(2, task.isCompleted());
            stmt.setInt(3, task.getUserId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task added successfully.");
            } else {
                System.out.println("Task addition failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(String oldDescription, String newDescription, int userId) {
        String query = "UPDATE tasks SET description = ? WHERE description = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newDescription);
            stmt.setString(2, oldDescription);
            stmt.setInt(3, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task updated successfully.");
            } else {
                System.out.println("Task update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String description, int userId) {
        String query = "DELETE FROM tasks WHERE description = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, description);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("Task deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks(int userId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String description = rs.getString("description");
                    boolean completed = rs.getBoolean("completed");
                    Task task = new Task(id, description, completed, userId);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
