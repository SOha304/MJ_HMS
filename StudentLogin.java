/*package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentLogin extends JFrame {

    private JTextField txtId;
    private JPasswordField txtPassword;

    public StudentLogin() {

        setTitle("Student Login");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,1,10,10));

        add(new JLabel("Student ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        add(btnLogin);

        btnLogin.addActionListener(e -> loginStudent());

        setVisible(true);
    }

    private void loginStudent() {

        String id = txtId.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection con = DBConnection.connect();

            String query = "SELECT * FROM students WHERE student_id=? AND password=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, id);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new StudentDashboard(id); // pass student id
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password");
            }

            con.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}*/
package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentLogin extends JFrame {

    private JTextField txtId;
    private JPasswordField txtPassword;

    public StudentLogin() {

        setTitle("Student Login");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== MAIN BACKGROUND =====
        JPanel background = new JPanel();
        background.setBackground(new Color(0, 102, 204));
        background.setLayout(new GridBagLayout());

        // ===== CARD PANEL =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 260));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ===== TITLE =====
        JLabel title = new JLabel("Student Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== STUDENT ID =====
        txtId = new JTextField();
        txtId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtId.setBorder(BorderFactory.createTitledBorder("Student ID"));

        // ===== PASSWORD =====
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        txtPassword.setEchoChar('•');

        // ===== SHOW PASSWORD =====
        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        showPass.setBackground(Color.WHITE);

        showPass.addActionListener(e -> {
            if (showPass.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        // ===== LOGIN BUTTON =====
        JButton btnLogin = new JButton("Login");
        btnLogin.setFocusPainted(false);
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Hover effect
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 90, 180));
            }

            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 120, 215));
            }
        });

        btnLogin.addActionListener(e -> loginStudent());

        // ===== ADD COMPONENTS =====
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(txtId);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(txtPassword);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(showPass);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(btnLogin);

        background.add(card);
        add(background);

        setVisible(true);
    }

    // ===== LOGIN LOGIC =====
    private void loginStudent() {

        String id = txtId.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection con = DBConnection.connect();
             PreparedStatement pst = con.prepareStatement(
                     "SELECT * FROM students WHERE student_id=? AND password=?")) {

            pst.setString(1, id);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new StudentDashboard(id);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid ID or Password");
                txtPassword.setText("");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}