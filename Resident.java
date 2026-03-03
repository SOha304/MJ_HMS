package hostel_management_system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class Resident extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public Resident() {

        setTitle("Resident Students");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🌈 Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 248, 252));

        // 🔹 Title
        JLabel title = new JLabel("Resident Students", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        mainPanel.add(title, BorderLayout.NORTH);

        // 🔹 Table Model
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Student ID", "Name", "Gender", "Hostel Name", "Room ID"
        });

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(0, 123, 255));
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(33, 150, 243));
        header.setForeground(Color.WHITE);

        // Center Align
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // 🔥 Load Data
        loadStudents();

        setVisible(true);
    }

    // 🔥 LOAD DATA USING JOIN
    private void loadStudents() {
        try {
            Connection conn = DBConnection.connect();

            String sql = "SELECT s.student_id, s.name, s.gender, h.name AS hostel_name, s.room_id " +
                         "FROM Students  s " +
                         "LEFT JOIN Hostels h ON s.hostel_id = h.hostel_id";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0);

            while (rs.next()) {

                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String hostelName = rs.getString("hostel_name");
                int roomId = rs.getInt("room_id");

                // Handle NULL hostel
                if (hostelName == null) hostelName = "Not Assigned";

                model.addRow(new Object[]{
                        id, name, gender, hostelName, roomId
                });
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
