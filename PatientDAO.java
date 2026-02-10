import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PatientDAO {

    static final String URL = "jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "root";   // your MySQL password

    public static void insertPatient(String name, int age, String gender, String phone,
                                     String address, String bloodGroup, String date) {

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String query = "INSERT INTO patients(name, age, gender, phone, address, blood_group, admission_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, bloodGroup);
            ps.setString(7, date);

            ps.executeUpdate();
            System.out.println("Patient added successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

