/*package hostel_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Payment extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JCheckBox chkPaid, chkUnpaid;
    private JTextField txtStudentId;
    private JButton btnUpdate;

    Connection con;

    public Payment() {

        setTitle("Student Payment Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        connectDB();

        // Top Panel
        JPanel top = new JPanel();

        chkPaid = new JCheckBox("Show Paid");
        chkUnpaid = new JCheckBox("Show Unpaid");

        top.add(chkPaid);
        top.add(chkUnpaid);

        add(top, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Student ID", "Name", "Room ID", "Status", "Due Amount"
        });

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottom = new JPanel();

        bottom.add(new JLabel("Student ID:"));
        txtStudentId = new JTextField(10);
        bottom.add(txtStudentId);

        btnUpdate = new JButton("Mark as Paid");
        bottom.add(btnUpdate);

        add(bottom, BorderLayout.SOUTH);

        // Load Data Initially
        loadStudents(null);

        // Events
        chkPaid.addActionListener(e -> filterData());
        chkUnpaid.addActionListener(e -> filterData());

        btnUpdate.addActionListener(e -> updatePayment());

        setVisible(true);
    }

    private void connectDB() {
        try {
            con = DBConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load students with optional filter
    private void loadStudents(String filter) {
        model.setRowCount(0);

        try {
            String query = "SELECT s.student_id, s.name, s.room_id, s.fee_status, " +
                    "IFNULL(rfm.fee_m, 0) AS fee " +
                    "FROM Students s " +
                    "LEFT JOIN room_fee_master rfm ON s.room_id = rfm.room_id";

            if (filter != null) {
                query += " WHERE s.fee_status = ?";
            }

            PreparedStatement pst = con.prepareStatement(query);

            if (filter != null) {
                pst.setString(1, filter);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int studentId = rs.getInt("student_id");
                String name = rs.getString("name");
                int roomId = rs.getInt("room_id");
                String status = rs.getString("fee_status");
                int fee = rs.getInt("fee");

                int due = status.equalsIgnoreCase("Paid") ? 0 : fee;

                model.addRow(new Object[]{
                        studentId, name, roomId, status, due
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterData() {
        if (chkPaid.isSelected() && !chkUnpaid.isSelected()) {
            loadStudents("Paid");
        } else if (!chkPaid.isSelected() && chkUnpaid.isSelected()) {
            loadStudents("Pending");
        } else {
            loadStudents(null);
        }
    }

    private void updatePayment() {
        try {
            int id = Integer.parseInt(txtStudentId.getText());

            String sql = "UPDATE Students SET fee_status = 'Paid' WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Payment Updated!");
                loadStudents(null);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error!");
        }
    }

}*/
package hostel_management_system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class Payment extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JCheckBox chkPaid, chkUnpaid;
    private JTextField txtStudentId;
    private JButton btnUpdate;

    Connection con;

    public Payment() {

        setTitle("💳 Student Payment Management");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        connectDB();

        // 🌈 MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(mainPanel);

        // 🔷 TOP PANEL
        JPanel top = new JPanel();
        top.setBackground(new Color(30, 136, 229));
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        chkPaid = new JCheckBox("Show Paid");
        chkUnpaid = new JCheckBox("Show Unpaid");

        chkPaid.setForeground(Color.WHITE);
        chkUnpaid.setForeground(Color.WHITE);

        chkPaid.setBackground(new Color(30,136,229));
        chkUnpaid.setBackground(new Color(30,136,229));

        top.add(chkPaid);
        top.add(chkUnpaid);

        mainPanel.add(top, BorderLayout.NORTH);

        // 📊 TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Student ID", "Name", "Room ID", "Status", "Due Amount"
        });

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(63, 81, 181));
        header.setForeground(Color.WHITE);

        // Center align
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // 🔥 Highlight Pending Rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String status = table.getValueAt(row, 3).toString();

                if (status.equalsIgnoreCase("Pending")) {
                    c.setBackground(new Color(255, 235, 238)); // light red
                } else {
                    c.setBackground(Color.WHITE);
                }

                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        mainPanel.add(scroll, BorderLayout.CENTER);

        // 🔻 BOTTOM PANEL
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245,247,250));
        bottom.setBorder(BorderFactory.createTitledBorder("Update Payment"));

        bottom.add(new JLabel("Student ID:"));

        txtStudentId = new JTextField(10);
        txtStudentId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottom.add(txtStudentId);

        btnUpdate = new JButton("Mark as Paid");
        btnUpdate.setBackground(new Color(76, 175, 80));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 13));

        bottom.add(btnUpdate);

        mainPanel.add(bottom, BorderLayout.SOUTH);

        // Load Data
        loadStudents(null);

        // Events
        chkPaid.addActionListener(e -> filterData());
        chkUnpaid.addActionListener(e -> filterData());
        btnUpdate.addActionListener(e -> updatePayment());

        setVisible(true);
    }

    private void connectDB() {
        try {
            con = DBConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudents(String filter) {
        model.setRowCount(0);

        try {
            String query = "SELECT s.student_id, s.name, s.room_id, s.fee_status, " +
                    "IFNULL(rfm.fee_m, 0) AS fee " +
                    "FROM Students s " +
                    "LEFT JOIN room_fee_master rfm ON s.room_id = rfm.room_id";

            if (filter != null) {
                query += " WHERE s.fee_status = ?";
            }

            PreparedStatement pst = con.prepareStatement(query);

            if (filter != null) {
                pst.setString(1, filter);
            }

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int studentId = rs.getInt("student_id");
                String name = rs.getString("name");
                int roomId = rs.getInt("room_id");
                String status = rs.getString("fee_status");
                int fee = rs.getInt("fee");

                int due = status.equalsIgnoreCase("Paid") ? 0 : fee;

                model.addRow(new Object[]{
                        studentId, name, roomId, status, due
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterData() {
        if (chkPaid.isSelected() && !chkUnpaid.isSelected()) {
            loadStudents("Paid");
        } else if (!chkPaid.isSelected() && chkUnpaid.isSelected()) {
            loadStudents("Pending");
        } else {
            loadStudents(null);
        }
    }

    private void updatePayment() {
        try {
            int id = Integer.parseInt(txtStudentId.getText());

            String sql = "UPDATE Students SET fee_status = 'Paid',due_amount = 0 WHERE student_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Payment Updated!");
                loadStudents(null);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Student not found!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠ Error!");
        }
    }
}

