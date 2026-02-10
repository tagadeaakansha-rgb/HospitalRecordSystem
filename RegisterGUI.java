import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterGUI extends JFrame {

    private JTextField nameField, usernameField, phoneField, emailField;
    private JPasswordField passwordField;

    public RegisterGUI() {
        setTitle("Patient Registration");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2));

        // LEFT PANEL (FORM)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(null);

        JLabel title = new JLabel("PATIENT REGISTER");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(60, 30, 300, 40);
        leftPanel.add(title);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(60, 90, 200, 25);
        leftPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(60, 115, 250, 30);
        leftPanel.add(nameField);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(60, 155, 200, 25);
        leftPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(60, 180, 250, 30);
        leftPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(60, 220, 200, 25);
        leftPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(60, 245, 250, 30);
        leftPanel.add(passwordField);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(60, 285, 200, 25);
        leftPanel.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(60, 310, 250, 30);
        leftPanel.add(phoneField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(60, 350, 200, 25);
        leftPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(60, 375, 250, 30);
        leftPanel.add(emailField);

        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setBounds(60, 415, 250, 35);
        registerBtn.setBackground(new Color(0,150,136));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        leftPanel.add(registerBtn);

        JLabel loginLink = new JLabel("Already have account? Login");
        loginLink.setForeground(new Color(0,150,136));
        loginLink.setBounds(90, 455, 250, 25);
        leftPanel.add(loginLink);

        // RIGHT PANEL (DESIGN SIDE)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(224,247,250));
        rightPanel.setLayout(new BorderLayout());

        JLabel welcome = new JLabel("Join Our Hospital Portal", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 22));
        welcome.setForeground(new Color(0,105,92));
        rightPanel.add(welcome, BorderLayout.NORTH);

        JLabel icon = new JLabel("🩺", SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 100));
        rightPanel.add(icon, BorderLayout.CENTER);

        JLabel footer = new JLabel("Safe • Secure • Smart", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 16));
        footer.setForeground(Color.DARK_GRAY);
        rightPanel.add(footer, BorderLayout.SOUTH);

        add(leftPanel);
        add(rightPanel);

        // BUTTON ACTION
        registerBtn.addActionListener(e -> registerUser());

        loginLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new LoginGUI();
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO users(full_name, username, password, phone, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, nameField.getText());
            pst.setString(2, usernameField.getText());
            pst.setString(3, new String(passwordField.getPassword()));
            pst.setString(4, phoneField.getText());
            pst.setString(5, emailField.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful!");
            new LoginGUI();
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Username already exists!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
