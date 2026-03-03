/*package hostel_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentDashboard extends JFrame {

    private String studentId;
    private JLabel lblName, lblRoom, lblHostel, lblDue;
    private JTable table;
    private DefaultTableModel model;

    public StudentDashboard(String id) {

        this.studentId = id;

        setTitle("Student Dashboard");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(25, 42, 86));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setLayout(new GridLayout(6,1,10,10));

        JLabel logo = new JLabel("HOSTEL", JLabel.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnLogout = new JButton("Logout");

        sidebar.add(logo);
        sidebar.add(new JLabel(""));
        sidebar.add(new JLabel(""));
        sidebar.add(new JLabel(""));
        sidebar.add(new JLabel(""));
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 246, 250));

        // ===== HEADER =====
        JLabel header = new JLabel("Welcome Student ID: " + studentId);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.add(header, BorderLayout.NORTH);

        // ===== CENTER CONTENT =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(245,246,250));

        // ===== INFO CARDS =====
        JPanel cards = new JPanel(new GridLayout(1,3,20,20));
        cards.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        cards.setBackground(new Color(245,246,250));

        lblName = createCard("Name");
        lblRoom = createCard("Room");
        lblHostel = createCard("Hostel");
        lblDue = createCard("Due Amount");

        cards.add(lblName);
        cards.add(lblRoom);
        cards.add(lblHostel);

        centerPanel.add(cards, BorderLayout.NORTH);

        // ===== PAYMENT TABLE =====
        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("Due Amount");
        model.addColumn("Payment Status");

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Payment History"));

        centerPanel.add(scroll, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        loadStudentData();

        btnLogout.addActionListener(e -> {
            dispose();
            new StartPage();
        });

        setVisible(true);
    }

    private JLabel createCard(String title) {
        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        return label;
    }

    private void loadStudentData() {

        try (Connection con = DBConnection.connect()) {

            // Student Info
            String sql1 = "SELECT * FROM Students WHERE student_id=?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setString(1, studentId);
            ResultSet rs1 = pst1.executeQuery();

            if (rs1.next()) {
                lblName.setText("Name: " + rs1.getString("name"));
                lblRoom.setText("Room: " + rs1.getString("room_id"));
                lblHostel.setText("Hostel: " + rs1.getString("hostel_id"));
            }

            // Payment Info
            String sql2 = "SELECT fee_status,due_amount FROM students WHERE student_id=?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setString(1, studentId);
            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {
                model.addRow(new Object[]{
                        rs2.getInt("due_amount"),
                        rs2.getString("fee_status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
package hostel_management_system;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class StudentDashboard extends JFrame {

    private String studentId;
    private JLabel lblName, lblRoom, lblHostel, lblDue;
    private JTable table;
    private DefaultTableModel model;

    public StudentDashboard(String id) {

        this.studentId = id;

        setTitle("Student Dashboard");
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(20, 30, 60));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        JLabel logo = new JLabel("🏠 HOSTEL", JLabel.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(new Color(255, 71, 87));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(20, 30, 60));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 30, 20));
        bottomPanel.add(btnLogout, BorderLayout.SOUTH);

        sidebar.add(logo, BorderLayout.NORTH);
        sidebar.add(bottomPanel, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);

        // ================= MAIN =================
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 246, 250));

        // HEADER
        JLabel header = new JLabel("Dashboard  |  Student ID: " + studentId);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));

        main.add(header, BorderLayout.NORTH);

        // ================= CONTENT =================
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(245, 246, 250));

        // ===== CARDS PANEL =====
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        cardsPanel.setBackground(new Color(245, 246, 250));

        lblName = createCard("Name");
        lblRoom = createCard("Room");
        lblHostel = createCard("Hostel");
        lblDue = createCard("Due Amount");

        cardsPanel.add(lblName);
        cardsPanel.add(lblRoom);
        cardsPanel.add(lblHostel);
        cardsPanel.add(lblDue);

        content.add(cardsPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // ❌ Disable editing
            }
        };

        table = new JTable(model);

        model.addColumn("Due Amount");
        model.addColumn("Payment Status");

        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(100, 149, 237));

        // Header Style
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHeader.setBackground(new Color(20, 30, 60));
        tableHeader.setForeground(Color.WHITE);

        // Alternate Row Colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                           boolean isSelected,
                                                           boolean hasFocus,
                                                           int row, int col) {

                Component c = super.getTableCellRendererComponent(
                        tbl, value, isSelected, hasFocus, row, col);

                if (!isSelected) {
                    if (row % 2 == 0)
                        c.setBackground(new Color(245, 246, 250));
                    else
                        c.setBackground(Color.WHITE);
                }

                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 30, 30, 30),
                BorderFactory.createTitledBorder("Payment History")
        ));

        content.add(scroll, BorderLayout.CENTER);

        main.add(content, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);

        // ================= ACTION =================
        btnLogout.addActionListener(e -> {
            dispose();
            new StartPage();
        });

        loadStudentData();

        setVisible(true);
    }

    // ================= CARD UI =================
    private JLabel createCard(String title) {
        JLabel label = new JLabel(title, JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 10, 30, 10)
        ));
        return label;
    }

    // ================= DATA LOGIC (UNCHANGED) =================
    private void loadStudentData() {

        try (Connection con = DBConnection.connect()) {

            String sql1 = "SELECT * FROM Students WHERE student_id=?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setString(1, studentId);
            ResultSet rs1 = pst1.executeQuery();
            String hostelN;
            if (rs1.next()) {
                if(rs1.getString("hostel_id").equals("1")) hostelN="Gandhi";
                else if(rs1.getString("hostel_id").equals("2")) hostelN="Zakir";
                else hostelN="Begum";
                lblName.setText("Name: " + rs1.getString("name"));
                lblRoom.setText("Room: " + rs1.getString("room_id"));
                lblHostel.setText("Hostel: " + hostelN);
                lblDue.setText("Due: ₹" + rs1.getString("due_amount"));
            }

            String sql2 = "SELECT fee_status,due_amount FROM students WHERE student_id=?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setString(1, studentId);
            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {
                model.addRow(new Object[]{
                        rs2.getInt("due_amount"),
                        rs2.getString("fee_status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}