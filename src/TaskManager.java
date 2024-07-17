import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private Connection connection;

    public TaskManager() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addTask(Task task) {
        String query = "INSERT INTO tasks (description, completed) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, task.getDescription());
            stmt.setBoolean(2, task.isCompleted());
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

    public void updateTask(String oldDescription, String newDescription) {
        String query = "UPDATE tasks SET description = ? WHERE description = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newDescription);
            stmt.setString(2, oldDescription);
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

    public void deleteTask(String description) {
        String query = "DELETE FROM tasks WHERE description = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, description);
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

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                boolean completed = rs.getBoolean("completed");
                Task task = new Task(id, description, completed);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
