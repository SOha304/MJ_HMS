package hostel_management_system;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) {

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS room (" +
                    "room_id INTEGER PRIMARY KEY," +
                    "type TEXT," +
                    "rent REAL," +
                    "available INTEGER," +
                    "student_name TEXT)";

            stmt.execute(sql);

            System.out.println("Table created successfully!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
