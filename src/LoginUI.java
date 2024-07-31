import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserManager userManager;
    private TaskManager taskManager;

    public LoginUI(UserManager userManager, TaskManager taskManager) {
        this.userManager = userManager;
        this.taskManager = taskManager;

        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.authenticateUser(username, password)) {
                    int userId = userManager.getUserId(username);
                    new TaskManagerUI(username, userId, taskManager);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                userManager.registerUser(username, password);
                JOptionPane.showMessageDialog(frame, "Registration successful! Please login.");
            }
        });
        panel.add(registerButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        TaskManager taskManager = new TaskManager();
        new LoginUI(userManager, taskManager);
    }
}
