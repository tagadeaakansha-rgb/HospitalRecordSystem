import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setTitle("Hospital Login");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);  // simple layout for beginners

        // Title
        JLabel title = new JLabel("HOSPITAL LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(330, 40, 300, 30);
        add(title);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(250, 150, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(350, 150, 200, 30);
        add(usernameField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(250, 200, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(350, 200, 200, 30);
        add(passwordField);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(350, 260, 200, 35);
        add(loginBtn);

        // Button Action (just demo)
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = usernameField.getText();
                String pass = new String(passwordField.getPassword());

                if (user.equals("admin") && pass.equals("admin123")) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    dispose(); // close login window
                    new PatientDashboard(); // open next screen

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                }

            }
        });

        setVisible(true);
    }

    // Main method to run this page directly
    public static void main(String[] args) {
        new LoginGUI();
    }
}
