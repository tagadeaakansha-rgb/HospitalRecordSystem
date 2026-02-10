import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PatientGUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    public PatientGUI() {
        setTitle("Hospital Patient Records");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table columns
        String[] columns = {"ID", "Name", "Age", "Gender", "Phone"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load data from database
        fetchPatients();

        setVisible(true);
    }

    private void fetchPatients() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital_db",
                    "root",
                    "root"   // <-- change only if your MySQL password is different
            );


            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patients");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("phone")
                });
            }

            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PatientGUI();
    }
}

