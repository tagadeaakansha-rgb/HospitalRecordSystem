import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/hospital_db";
            String user = "root";
            String password = "root"; // 👉 change to your MySQL password

            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

