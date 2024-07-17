public class Task {
    private int id;
    private String description;
    private boolean completed;

    // Constructor for creating a new task without an ID (for inserting into the database)
    public Task(String description) {
        this.description = description;
        this.completed = false; // By default, a new task is incomplete
    }

    // Constructor for creating a task with an ID (for retrieving from the database)
    public Task(int id, String description, boolean completed) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
