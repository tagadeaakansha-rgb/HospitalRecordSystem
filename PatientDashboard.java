import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PatientDashboard extends JFrame {

    private String username;
    private JPanel contentPanel;

    public PatientDashboard(String username) {
        this.username = username;

        setTitle("Patient Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(0, 102, 102));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("  HOSPITAL", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        sidebar.add(title);

        JButton profileBtn = new JButton("My Profile");
        JButton recordsBtn = new JButton("Medical Records");
        JButton logoutBtn = new JButton("Logout");

        styleButton(profileBtn);
        styleButton(recordsBtn);
        styleButton(logoutBtn);

        sidebar.add(profileBtn);
        sidebar.add(recordsBtn);
        sidebar.add(new JLabel()); // space
        sidebar.add(logoutBtn);

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN CONTENT AREA =====
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        // Default page
        showProfile();

        // Button Actions
        profileBtn.addActionListener(e -> showProfile());
        recordsBtn.addActionListener(e -> showMedicalRecords());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginGUI();
        });

        setVisible(true);
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(0, 153, 153));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // ===== PROFILE PAGE =====
    private void showProfile() {
        contentPanel.removeAll();

        JPanel profilePanel = new JPanel(new GridLayout(5, 2, 10, 10));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT full_name, username FROM users WHERE username=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                profilePanel.add(new JLabel("Full Name:"));
                profilePanel.add(new JLabel(rs.getString("full_name")));

                profilePanel.add(new JLabel("Username:"));
                profilePanel.add(new JLabel(rs.getString("username")));

                profilePanel.add(new JLabel("Account Type:"));
                profilePanel.add(new JLabel("Patient"));
            }

        } catch (Exception e) {
            profilePanel.add(new JLabel("Error loading profile"));
        }

        contentPanel.add(profilePanel, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ===== MEDICAL RECORDS PAGE =====
    private void showMedicalRecords() {
        contentPanel.removeAll();

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT p.name, p.age, p.gender, p.phone, m.diagnosis, m.treatment, m.visit_date " +
                    "FROM patients p " +
                    "LEFT JOIN medical_history m ON p.id = m.patient_id " +
                    "JOIN users u ON u.full_name = p.name " +
                    "WHERE u.username = ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{
                    "Name", "Age", "Gender", "Phone",
                    "Diagnosis", "Treatment", "Visit Date"
            });

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getDate("visit_date")
                });
            }

            table.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading records: " + e.getMessage());
        }

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
