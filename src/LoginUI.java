import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private TaskManager taskManager;
    private UserManager userManager;

    public LoginUI() {
        taskManager = new TaskManager();  // Instantiate TaskManager for task operations
        userManager = new UserManager();  // Instantiate UserManager for user operations

        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));  // Adjust grid layout to fit all components

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);  // Convert char array to String

        // Validate username and password against database using UserManager
        boolean isValidUser = userManager.authenticateUser(username, password);

        if (isValidUser) {
            JOptionPane.showMessageDialog(frame, "Login successful!");
            openTaskManagerUI(username);  // Open Task Manager UI after successful login
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void register() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);  // Convert char array to String

        boolean isRegistered = userManager.registerUser(username, password);

        if (isRegistered) {
            JOptionPane.showMessageDialog(frame, "Registration successful! You can now log in.");
        } else {
            JOptionPane.showMessageDialog(frame, "Registration failed. Username may already be taken.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openTaskManagerUI(String username) {
        frame.dispose();  // Close the login window
        new TaskManagerUI(username, taskManager);  // Pass username and TaskManager instance to TaskManagerUI
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });
    }
}
