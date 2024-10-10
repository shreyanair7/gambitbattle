package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.ChessBoard; // Import the ChessBoard class
import backend.DBConnection; // Import your DBConnection class
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginScreen extends JFrame {
    private JTextField player1Field;
    private JTextField player2Field;

    public LoginScreen() {
        // Frame settings
        setTitle("GAMBIT Battle Login");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background Image
        JLabel background = new JLabel(new ImageIcon("resources/symphony.png"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to GAMBIT BATTLE!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLACK);

        // Center Panel (for input fields and buttons)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);  // Reduced gaps to bring components closer

        // Position the title higher
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        centerPanel.add(titleLabel, gbc);

        // Player 1 Name Input
        JLabel player1Label = new JLabel("Player 1 Name:");
        player1Label.setForeground(Color.BLACK);
        player1Field = new JTextField(12);
        player1Field.setPreferredSize(new Dimension(200, 30)); // Thinner input box height

        // Player 2 Name Input
        JLabel player2Label = new JLabel("Player 2 Name:");
        player2Label.setForeground(Color.BLACK);
        player2Field = new JTextField(12);
        player2Field.setPreferredSize(new Dimension(200, 30)); // Thinner input box height

        // Add player labels and fields to the center panel
        gbc.gridwidth = 1; // Resetting gridwidth for each individual component

        // Player 1 Label (centered horizontally)
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST; // Aligns label to the right
        centerPanel.add(player1Label, gbc);

        // Player 1 Field (centered horizontally)
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Aligns field to the left of label
        centerPanel.add(player1Field, gbc);

        // Player 2 Label (centered horizontally)
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST; // Aligns label to the right
        centerPanel.add(player2Label, gbc);

        // Player 2 Field (centered horizontally)
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Aligns field to the left of label
        centerPanel.add(player2Field, gbc);

        // Guest Login Button
        JButton guestLoginButton = new JButton("Guest Login");
        guestLoginButton.setFocusPainted(false);
        guestLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Default guest names
                player1Field.setText("Guest 1");
                player2Field.setText("Guest 2");
            }
        });

        // Start Game Button
        JButton loginButton = new JButton("Start Game");
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String player1Name = player1Field.getText();
                String player2Name = player2Field.getText();
                // Validate input and proceed to game
                if (player1Name.isEmpty() || player2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter names for both players or use guest login.");
                } else {
                    // Insert player names into the database
                    insertPlayersIntoDatabase(player1Name, player2Name);

                    // Start the game with player names
                    System.out.println("Starting game: " + player1Name + " vs " + player2Name);
                    dispose(); // Close the login screen
                    new ChessBoard(player1Name, player2Name); // Create and show the chessboard
                }
            }
        });

        // Add the buttons with reduced gaps and move them up
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(guestLoginButton);
        buttonPanel.add(loginButton);
        centerPanel.add(buttonPanel, gbc);

        // Spacer to push elements higher
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        centerPanel.add(Box.createVerticalGlue(), gbc);

        // Add title, input, and button panels to the layout
        background.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to insert players into the database
    private void insertPlayersIntoDatabase(String player1, String player2) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.connect();
    
        // Use separate INSERT statements for each player
        String insertQuery1 = "INSERT INTO users (username, player_type) VALUES (?, ?)";
        String insertQuery2 = "INSERT INTO users (username, player_type) VALUES (?, ?)";
    
        try {
            // Prepare the first insert statement
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertQuery1);
            preparedStatement1.setString(1, player1);
            preparedStatement1.setString(2, "Player");
            preparedStatement1.executeUpdate();
    
            // Prepare the second insert statement
            PreparedStatement preparedStatement2 = connection.prepareStatement(insertQuery2);
            preparedStatement2.setString(1, player2);
            preparedStatement2.setString(2, "Guest");
            preparedStatement2.executeUpdate();
    
            System.out.println("Players inserted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            dbConnection.close();
        }
    }
    

    public static void main(String[] args) {
        new LoginScreen();
    }
}
