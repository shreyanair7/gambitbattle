package login;

import backend.UserAuth;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    // Initialize UserAuth instance to handle login
    private UserAuth userAuth;

    public LoginScreen() {
        userAuth = new UserAuth(); // Initialize UserAuth to connect to the database

        // Set up JFrame properties
        setTitle("GAMBIT BATTLE"); // Set the title of the frame
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Load icon image
        ImageIcon icon = new ImageIcon("resources/symphony.png");
        setIconImage(icon.getImage()); // Set icon image

        // Set background color
        getContentPane().setBackground(Color.BLACK);

        // Create components with custom colors
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(Color.BLACK);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.MAGENTA);

        usernameField = new JTextField();
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.MAGENTA);
        usernameField.setCaretColor(Color.MAGENTA);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.MAGENTA);

        passwordField = new JPasswordField();
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.MAGENTA);
        passwordField.setCaretColor(Color.MAGENTA);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.MAGENTA);
        loginButton.setFocusPainted(false);

        // Custom status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.MAGENTA);

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Add padding around the panel
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBackground(Color.BLACK);
        paddedPanel.add(panel, BorderLayout.CENTER);
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Add action listener for login button
        loginButton.addActionListener(new LoginButtonListener());

        // Set up layout
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.NORTH);
        add(paddedPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
    }

    // ActionListener class for handling login button click
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validate credentials
            if (userAuth.loginUser(username, password)) {
                statusLabel.setText("Login successful!");
            } else {
                statusLabel.setText("Invalid username or password.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
