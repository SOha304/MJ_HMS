/*package hostel_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AllocateStudentUI extends JFrame {

    private JComboBox<String> cmbHostel;
    private JCheckBox chkAC;
    private JLabel lblName, lblFee;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtRoomId, txtStuId;

    public AllocateStudentUI() {

        setTitle("Student Allocation");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(44, 62, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.BOLD, 14);

        // Student ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("Student ID:");
        lblId.setForeground(Color.WHITE);
        topPanel.add(lblId, gbc);

        txtStuId = new JTextField(5);  // ✅ FIXED (no duplicate)
        gbc.gridx = 1;
        topPanel.add(txtStuId, gbc);

        // Name
        gbc.gridx = 2;
        lblName = new JLabel("Name: ---");
        lblName.setForeground(Color.WHITE);
        topPanel.add(lblName, gbc);

        // Hostel
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblHostel = new JLabel("Hostel:");
        lblHostel.setForeground(Color.WHITE);
        topPanel.add(lblHostel, gbc);

        cmbHostel = new JComboBox<>(new String[]{"Gandhi", "Zakir", "Begum"});
        gbc.gridx = 1;
        topPanel.add(cmbHostel, gbc);

        // AC checkbox
        gbc.gridx = 2;
        chkAC = new JCheckBox("AC Room");
        chkAC.setBackground(new Color(44, 62, 80));
        chkAC.setForeground(Color.WHITE);
        topPanel.add(chkAC, gbc);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel();
        table = new JTable(model);

        model.setColumnIdentifiers(new String[]{
                "Floor", "Room ID", "Available", "Type"
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= BOTTOM =================
        JPanel bottom = new JPanel(new BorderLayout(10,10));

        JPanel topPart = new JPanel(new GridLayout(2,2,10,10));

        topPart.add(new JLabel("Enter Room ID:"));
        txtRoomId = new JTextField();
        topPart.add(txtRoomId);

        topPart.add(new JLabel("Fees:"));
        lblFee = new JLabel("Monthly: 0 | Semester: 0 | Yearly: 0");
        topPart.add(lblFee);

        JPanel btnPart = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JButton btnAllocate = new JButton("Allocate");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnClear = new JButton("Clear");

        btnPart.add(btnAllocate);
        btnPart.add(btnClear);
        btnPart.add(btnRefresh);

        bottom.add(topPart, BorderLayout.CENTER);
        bottom.add(btnPart, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ================= EVENTS =================

        // Auto fetch name while typing
        txtStuId.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                showStudentName();
            }
        });

        cmbHostel.addActionListener(e -> loadRooms());
        chkAC.addActionListener(e -> loadRooms());

        txtRoomId.addActionListener(e -> loadFees());

        btnAllocate.addActionListener(e -> allocate());
        btnRefresh.addActionListener(e -> loadRooms());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    // ================= SHOW NAME =================
    private void showStudentName() {

        String text = txtStuId.getText().trim();

        if (text.isEmpty()) {
            lblName.setText("Name: ---");
            return;
        }

        try (Connection conn = DBConnection.connect()) {

            int id = Integer.parseInt(text);

            String sql = "SELECT name FROM Students WHERE student_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblName.setText("Name: " + rs.getString("name"));
            } else {
                lblName.setText("Name: Not Found");
            }

        } catch (Exception e) {
            lblName.setText("Invalid ID");
        }
    }

    // ================= LOAD ROOMS =================
    private void loadRooms() {

        try (Connection conn = DBConnection.connect()) {

            model.setRowCount(0);

            int hostelId = cmbHostel.getSelectedIndex() + 1;
            int type = chkAC.isSelected() ? 1 : 0;

            String sql = "SELECT r.floor, r.room_id, r.capacity, r.is_ac, " +
                    "COUNT(s.student_id) AS occupied " +
                    "FROM Rooms r LEFT JOIN Students s " +
                    "ON r.room_id = s.room_id " +
                    "WHERE r.hostel_id=? AND r.is_ac=? " +
                    "GROUP BY r.room_id";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, hostelId);
            pst.setInt(2, type);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int available = rs.getInt("capacity") - rs.getInt("occupied");

                model.addRow(new Object[]{
                        rs.getInt("floor"),
                        rs.getInt("room_id"),
                        available,
                        rs.getInt("is_ac") == 1 ? "AC" : "Non-AC"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading rooms");
        }
    }

    // ================= LOAD FEES =================
    private void loadFees() {

        try (Connection conn = DBConnection.connect()) {

            int roomId = Integer.parseInt(txtRoomId.getText());
            int hostelId = cmbHostel.getSelectedIndex() + 1;

            String sql = "SELECT fee_m, fee_s, fee_y FROM room_fee_master WHERE room_id=? AND hostel_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, roomId);
            pst.setInt(2, hostelId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblFee.setText("Monthly: " + rs.getInt("fee_m") +
                        " | Semester: " + rs.getInt("fee_s") +
                        " | Yearly: " + rs.getInt("fee_y"));
            } else {
                lblFee.setText("Invalid Room ID");
            }

        } catch (Exception e) {
            lblFee.setText("Invalid Input");
        }
    }

    // ================= ALLOCATE =================
    private void allocate() {

    try (Connection conn = DBConnection.connect()) {

        int studentId = Integer.parseInt(txtStuId.getText());
        int roomId = Integer.parseInt(txtRoomId.getText());

        // 🔥 CHECK IF STUDENT ALREADY ALLOCATED
        String checkStudent = "SELECT room_id FROM Students WHERE student_id=?";
        PreparedStatement pst1 = conn.prepareStatement(checkStudent);
        pst1.setInt(1, studentId);

        ResultSet rs1 = pst1.executeQuery();

        if (rs1.next()) {
            int existingRoom = rs1.getInt("room_id");

            if (!rs1.wasNull()) {
                JOptionPane.showMessageDialog(this, "Student already allocated to Room ID: " + existingRoom + " ❌");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Student not found ❌");
            return;
        }

        // 🔥 CHECK ROOM AVAILABILITY
        String checkRoom = "SELECT capacity - COUNT(s.student_id) AS available " +
                "FROM Rooms r LEFT JOIN Students s ON r.room_id = s.room_id " +
                "WHERE r.room_id=? GROUP BY r.room_id";

        PreparedStatement pst2 = conn.prepareStatement(checkRoom);
        pst2.setInt(1, roomId);

        ResultSet rs2 = pst2.executeQuery();

        if (rs2.next()) {
            int available = rs2.getInt("available");

            if (available <= 0) {
                JOptionPane.showMessageDialog(this, "Room Full ❌");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Room not found ❌");
            return;
        }
        int hostelId = cmbHostel.getSelectedIndex() + 1;
        // ✅ ALLOCATE
        String sql = "UPDATE Students SET room_id=?,hostel_id=? WHERE student_id=?";
        PreparedStatement pst3 = conn.prepareStatement(sql);

        pst3.setInt(1, roomId);
        pst3.setInt(2,hostelId);
        pst3.setInt(3, studentId);

        pst3.executeUpdate();

        JOptionPane.showMessageDialog(this, "Allocated Successfully ✅");

        loadRooms();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid Input ❌");
    }
}

    


    // ================= CLEAR =================
    private void clearFields() {
        txtRoomId.setText("");
        txtStuId.setText("");
        lblFee.setText("Monthly: 0 | Semester: 0 | Yearly: 0");
        lblName.setText("Name: ---");
    }
}
*/
package hostel_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AllocateStudentUI extends JFrame {

    private JComboBox<String> cmbHostel;
    private JCheckBox chkAC;
    private JLabel lblName, lblFee;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtRoomId, txtStuId;

    public AllocateStudentUI() {

        setTitle("Student Allocation");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(44, 62, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("Student ID:");
        lblId.setForeground(Color.WHITE);
        topPanel.add(lblId, gbc);

        txtStuId = new JTextField(5);
        gbc.gridx = 1;
        topPanel.add(txtStuId, gbc);

        // Name
        gbc.gridx = 2;
        lblName = new JLabel("Name: ---");
        lblName.setForeground(Color.WHITE);
        topPanel.add(lblName, gbc);

        // Hostel
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblHostel = new JLabel("Hostel:");
        lblHostel.setForeground(Color.WHITE);
        topPanel.add(lblHostel, gbc);

        cmbHostel = new JComboBox<>(new String[]{"Gandhi", "Zakir", "Begum"});
        gbc.gridx = 1;
        topPanel.add(cmbHostel, gbc);

        // AC checkbox
        gbc.gridx = 2;
        chkAC = new JCheckBox("AC Room");
        chkAC.setBackground(new Color(44, 62, 80));
        chkAC.setForeground(Color.WHITE);
        topPanel.add(chkAC, gbc);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE =================
        model = new DefaultTableModel();
        table = new JTable(model);

        model.setColumnIdentifiers(new String[]{
                "Floor", "Room ID", "Available", "Type"
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= BOTTOM =================
        JPanel bottom = new JPanel(new BorderLayout(10,10));

        JPanel topPart = new JPanel(new GridLayout(2,2,10,10));

        topPart.add(new JLabel("Enter Room ID:"));
        txtRoomId = new JTextField();
        topPart.add(txtRoomId);

        topPart.add(new JLabel("Fees:"));
        lblFee = new JLabel("Monthly: 0 | Semester: 0 | Yearly: 0");
        topPart.add(lblFee);

        JPanel btnPart = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JButton btnAllocate = new JButton("Allocate");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnClear = new JButton("Clear");

        btnPart.add(btnAllocate);
        btnPart.add(btnClear);
        btnPart.add(btnRefresh);

        bottom.add(topPart, BorderLayout.CENTER);
        bottom.add(btnPart, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // ================= EVENTS =================

        txtStuId.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                showStudentName();
            }
        });

        cmbHostel.addActionListener(e -> loadRooms());
        chkAC.addActionListener(e -> loadRooms());
        txtRoomId.addActionListener(e -> loadFees());

        btnAllocate.addActionListener(e -> allocate());
        btnRefresh.addActionListener(e -> loadRooms());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    // ================= SHOW NAME =================
    private void showStudentName() {

        String text = txtStuId.getText().trim();

        if (text.isEmpty()) {
            lblName.setText("Name: ---");
            return;
        }

        try (Connection conn = DBConnection.connect()) {

            int id = Integer.parseInt(text);

            String sql = "SELECT name FROM Students WHERE student_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblName.setText("Name: " + rs.getString("name"));
            } else {
                lblName.setText("Name: Not Found");
            }

        } catch (Exception e) {
            lblName.setText("Invalid ID");
        }
    }

    // ================= LOAD ROOMS =================
    private void loadRooms() {

        try (Connection conn = DBConnection.connect()) {

            model.setRowCount(0);

            int hostelId = cmbHostel.getSelectedIndex() + 1;
            int type = chkAC.isSelected() ? 1 : 0;

            String sql = "SELECT r.floor, r.room_id, r.capacity, r.is_ac, " +
                    "COUNT(s.student_id) AS occupied " +
                    "FROM Rooms r LEFT JOIN Students s " +
                    "ON r.room_id = s.room_id " +
                    "WHERE r.hostel_id=? AND r.is_ac=? " +
                    "GROUP BY r.room_id";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, hostelId);
            pst.setInt(2, type);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int available = rs.getInt("capacity") - rs.getInt("occupied");

                model.addRow(new Object[]{
                        rs.getInt("floor"),
                        rs.getInt("room_id"),
                        available,
                        rs.getInt("is_ac") == 1 ? "AC" : "Non-AC"
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading rooms");
        }
    }

    // ================= LOAD FEES =================
    private void loadFees() {

        try (Connection conn = DBConnection.connect()) {

            int roomId = Integer.parseInt(txtRoomId.getText());
            int hostelId = cmbHostel.getSelectedIndex() + 1;

            String sql = "SELECT fee_m, fee_s, fee_y FROM room_fee_master WHERE room_id=? AND hostel_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, roomId);
            pst.setInt(2, hostelId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblFee.setText("Monthly: " + rs.getInt("fee_m") +
                        " | Semester: " + rs.getInt("fee_s") +
                        " | Yearly: " + rs.getInt("fee_y"));
            } else {
                lblFee.setText("Invalid Room ID");
            }

        } catch (Exception e) {
            lblFee.setText("Invalid Input");
        }
    }

    // ================= ALLOCATE =================
    private void allocate() {

        try (Connection conn = DBConnection.connect()) {

            int studentId = Integer.parseInt(txtStuId.getText());
            int roomId = Integer.parseInt(txtRoomId.getText());
            int hostelId = cmbHostel.getSelectedIndex() + 1;
            String selectedHostel = cmbHostel.getSelectedItem().toString();

            // 🔥 GET STUDENT DATA
            String getStudent = "SELECT gender, room_id FROM Students WHERE student_id=?";
            PreparedStatement pst0 = conn.prepareStatement(getStudent);
            pst0.setInt(1, studentId);

            ResultSet rs0 = pst0.executeQuery();

            if (!rs0.next()) {
                JOptionPane.showMessageDialog(this, "Student not found ❌");
                return;
            }

            String gender = rs0.getString("gender");
            int existingRoom = rs0.getInt("room_id");

            if (!rs0.wasNull()) {
                JOptionPane.showMessageDialog(this, "Student already allocated to Room ID: " + existingRoom + " ❌");
                return;
            }

            // 🔥 FEMALE RULE
            if (gender.equalsIgnoreCase("Female") && !selectedHostel.equalsIgnoreCase("Begum")) {
                JOptionPane.showMessageDialog(this, "Female students can only be allocated to Begum Hostel ❌");
                return;
            }

            // 🔥 MALE RULE
            if (gender.equalsIgnoreCase("Male") && selectedHostel.equalsIgnoreCase("Begum")) {
                JOptionPane.showMessageDialog(this, "Male students cannot be allocated to Begum Hostel ❌");
                return;
            }

            // 🔥 CHECK ROOM AVAILABILITY
            String checkRoom = "SELECT capacity - COUNT(s.student_id) AS available " +
                    "FROM Rooms r LEFT JOIN Students s ON r.room_id = s.room_id " +
                    "WHERE r.room_id=? GROUP BY r.room_id";

            PreparedStatement pst2 = conn.prepareStatement(checkRoom);
            pst2.setInt(1, roomId);

            ResultSet rs2 = pst2.executeQuery();

            if (rs2.next()) {
                int available = rs2.getInt("available");

                if (available <= 0) {
                    JOptionPane.showMessageDialog(this, "Room Full ❌");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Room not found ❌");
                return;
            }

            // ✅ ALLOCATE
            /*String sql = "UPDATE Students SET room_id=?, hostel_id=? WHERE student_id=?";
            PreparedStatement pst3 = conn.prepareStatement(sql);

            pst3.setInt(1, roomId);
            pst3.setInt(2, hostelId);
            pst3.setInt(3, studentId);

            pst3.executeUpdate();

            JOptionPane.showMessageDialog(this, "Allocated Successfully ✅");

            loadRooms();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input ❌");
        }*/
       
        // STEP 1: Get fee_m
        String feeSql = "SELECT fee_m FROM room_fee_master WHERE room_id=? AND hostel_id=?";
        PreparedStatement pstFee = conn.prepareStatement(feeSql);
        pstFee.setInt(1, roomId);
        pstFee.setInt(2, hostelId);

        ResultSet rs = pstFee.executeQuery();

        int fee_m = 0;

        if (rs.next()) {
        fee_m = rs.getInt("fee_m");
        } else {
            JOptionPane.showMessageDialog(null, "Fee not found!");
            return;
        }

    // STEP 2: Update student (including due_amount)
        String sql = "UPDATE Students SET room_id=?, hostel_id=?, due_amount=? WHERE student_id=?";
        PreparedStatement pst3 = conn.prepareStatement(sql);

        pst3.setInt(1, roomId);
        pst3.setInt(2, hostelId);
        pst3.setInt(3, fee_m);   // 🔥 setting due amount
        pst3.setInt(4, studentId);

        pst3.executeUpdate();

        JOptionPane.showMessageDialog(null, "Student Allocated Successfully!");
        clearFields();

    } catch (Exception e) {
        e.printStackTrace();
        }
    }

    // ================= CLEAR =================
    private void clearFields() {
        txtRoomId.setText("");
        txtStuId.setText("");
        lblFee.setText("Monthly: 0 | Semester: 0 | Yearly: 0");
        lblName.setText("Name: ---");
    }
}