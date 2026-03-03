/*(package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentDetails extends JFrame {

    private JTextField txtStudentId;
    private JLabel lblName, lblGender, lblRoom, lblHostel;

    public StudentDetails() {

        setTitle("Student Details");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🌈 HEADER
        JLabel header = new JLabel("STUDENT DETAILS", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        // 🧾 MAIN PANEL (Card Style)
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        // 🔹 Input Field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEnter = new JLabel("Enter Student ID:");
        lblEnter.setFont(labelFont);
        card.add(lblEnter, gbc);

        txtStudentId = new JTextField(5);
        txtStudentId.setFont(valueFont);
        gbc.gridx = 1;
        card.add(txtStudentId, gbc);

        // 🔹 Name
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Name:", labelFont), gbc);

        lblName = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblName, gbc);

        // 🔹 Gender
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Gender:", labelFont), gbc);

        lblGender = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblGender, gbc);

        // 🔹 Room ID
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Room ID:", labelFont), gbc);

        lblRoom = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblRoom, gbc);

        // 🔹 Hostel Name
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Hostel:", labelFont), gbc);

        lblHostel = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblHostel, gbc);

        add(card, BorderLayout.CENTER);

        // 🎯 EVENT (auto fetch on Enter)
        txtStudentId.addActionListener(e -> loadStudentDetails());

        setVisible(true);
    }

    // 🔹 Helper Methods
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        return lbl;
    }

    private JLabel createValueLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(new Color(41, 128, 185));
        return lbl;
    }

    // 🔥 MAIN LOGIC
    private void loadStudentDetails() {

        String input = txtStudentId.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Student ID");
            return;
        }

        try (Connection conn = DBConnection.connect()) {

            int id = Integer.parseInt(input);

            String sql = "SELECT s.name as student_name, s.gender, s.room_id, h.name as Hostel_name " +
                         "FROM Students s " +
                         "LEFT JOIN Hostels h ON s.hostel_id = h.hostel_id " +
                         "WHERE s.student_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                lblName.setText(rs.getString("student_name"));
                lblGender.setText(rs.getString("gender"));

                int room = rs.getInt("room_id");
                if (rs.wasNull()) {
                    lblRoom.setText("Not Allocated");
                } else {
                    lblRoom.setText(String.valueOf(room));
                }

                String hostel = rs.getString("Hostel_name");
                lblHostel.setText(hostel != null ? hostel : "N/A");

            } else {
                clearLabels();
                JOptionPane.showMessageDialog(this, "Student Not Found ❌");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID ❌");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error ❌"+e);
        }
    }

    private void clearLabels() {
        lblName.setText("---");
        lblGender.setText("---");
        lblRoom.setText("---");
        lblHostel.setText("---");
    }
}*/
package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentDetails extends JFrame {

    private JTextField txtStudentId;
    private JLabel lblName, lblGender, lblRoom, lblHostel, lblContact, lblCheckin;

    public StudentDetails() {

        setTitle("Student Details");
        setSize(600, 500); // थोड़ा increase किया
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🌈 HEADER
        JLabel header = new JLabel("STUDENT INFORMATION", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        // 🧾 MAIN PANEL
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        // 🔹 Student ID
        gbc.gridx = 0; gbc.gridy = 0;
        card.add(createLabel("Enter Student ID:", labelFont), gbc);

        txtStudentId = new JTextField(5);
        gbc.gridx = 1;
        card.add(txtStudentId, gbc);

        // 🔹 Name
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Name:", labelFont), gbc);
        lblName = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblName, gbc);

        // 🔹 Gender
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Gender:", labelFont), gbc);
        lblGender = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblGender, gbc);

        // 🔹 Room
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Room ID:", labelFont), gbc);
        lblRoom = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblRoom, gbc);

        // 🔹 Hostel
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Hostel:", labelFont), gbc);
        lblHostel = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblHostel, gbc);

        // 🆕 🔹 Guardian Contact
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Guardian Contact:", labelFont), gbc);
        lblContact = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblContact, gbc);

        // 🆕 🔹 Check-in Date
        gbc.gridx = 0; gbc.gridy++;
        card.add(createLabel("Check-in Date:", labelFont), gbc);
        lblCheckin = createValueLabel("---", valueFont);
        gbc.gridx = 1;
        card.add(lblCheckin, gbc);

        add(card, BorderLayout.CENTER);

        // 🎯 EVENT
        txtStudentId.addActionListener(e -> loadStudentDetails());

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        return lbl;
    }

    private JLabel createValueLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(new Color(41, 128, 185));
        return lbl;
    }

    // 🔥 MAIN LOGIC
    private void loadStudentDetails() {

        String input = txtStudentId.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Student ID");
            return;
        }

        try (Connection conn = DBConnection.connect()) {

            int id = Integer.parseInt(input);

            String sql = "SELECT s.name as student_name, s.gender, s.room_id, " +
                         "s.guardian_contact, s.check_in_date, " +
                         "h.name as hostel_name " +
                         "FROM Students s " +
                         "LEFT JOIN Hostels h ON s.hostel_id = h.hostel_id " +
                         "WHERE s.student_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                lblName.setText(rs.getString("student_name"));
                lblGender.setText(rs.getString("gender"));

                int room = rs.getInt("room_id");
                if (rs.wasNull()) {
                    lblRoom.setText("Not Allocated");
                } else {
                    lblRoom.setText(String.valueOf(room));
                }

                String hostel = rs.getString("hostel_name");
                lblHostel.setText(hostel != null ? hostel : "N/A");

                // 🆕 NEW FIELDS
                lblContact.setText(rs.getString("guardian_contact"));

                String checkin = rs.getString("check_in_date");
                lblCheckin.setText(checkin != null ? checkin : "N/A");

            } else {
                clearLabels();
                JOptionPane.showMessageDialog(this, "Student Not Found ❌");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID ❌");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error ❌ " + e);
        }
    }

    private void clearLabels() {
        lblName.setText("---");
        lblGender.setText("---");
        lblRoom.setText("---");
        lblHostel.setText("---");
        lblContact.setText("---");
        lblCheckin.setText("---");
    }
}

