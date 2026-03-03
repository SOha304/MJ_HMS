package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class Registration extends JFrame {

    private JTextField txtName, txtGuardian;
    private JLabel lblDate;
    private JComboBox<String> cmbGender;
    private JButton btnRegister;

    public Registration() {

        setTitle("Student Registration");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 🌈 Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250)); // soft background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // 🏷️ Title
        JLabel title = new JLabel("Student Registration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(40, 40, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // Name
        gbc.gridy++;
        panel.add(createLabel("Name:", labelFont), gbc);

        gbc.gridx = 1;
        txtName = createTextField(fieldFont);
        panel.add(txtName, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Gender:", labelFont), gbc);

        gbc.gridx = 1;
        cmbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cmbGender.setFont(fieldFont);
        panel.add(cmbGender, gbc);

        // Guardian
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Guardian Contact:", labelFont), gbc);

        gbc.gridx = 1;
        txtGuardian = createTextField(fieldFont);
        panel.add(txtGuardian, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Check-in Date:", labelFont), gbc);

        gbc.gridx = 1;
        lblDate = new JLabel(LocalDate.now().toString());
        lblDate.setFont(fieldFont);
        lblDate.setForeground(new Color(80, 80, 80));
        panel.add(lblDate, gbc);

        // Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        btnRegister = new JButton("Register");
        styleButton(btnRegister);

        panel.add(btnRegister, gbc);

        add(panel);

        // Action
        btnRegister.addActionListener(e -> registerStudent());

        setVisible(true);
    }

    // 🎨 Styled Label
    private JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(new Color(60, 60, 60));
        return lbl;
    }

    // 🎨 Styled TextField
    private JTextField createTextField(Font font) {
        JTextField txt = new JTextField(15);
        txt.setFont(font);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return txt;
    }

    // 🎨 Styled Button
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(70, 130, 180)); // blue
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ✅ Validation
    private boolean validateFields(String name, String guardian) {

        if (name.isEmpty() || guardian.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return false;
        }

        if (!guardian.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Enter valid 10-digit number!");
            return false;
        }

        return true;
    }

    // 💾 Insert Data
    private void registerStudent() {

        String name = txtName.getText().trim();
        String gender = cmbGender.getSelectedItem().toString();
        String guardian = txtGuardian.getText().trim();
        String date = lblDate.getText();

        if (!validateFields(name, guardian)) return;

        try (Connection con = DBConnection.connect()) {
             String generatedPassword = generatePassword(8);
            String query = "INSERT INTO Students(name, gender, guardian_contact, check_in_date, fee_status,password) VALUES(?,?,?,?,?,?)";

            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, name);
            pst.setString(2, gender);
            pst.setString(3, guardian);
            pst.setString(4, date);
            pst.setString(5, "Pending");
            pst.setString(6, generatedPassword);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    JOptionPane.showMessageDialog(this,
                            "✅ Registered Successfully!\nStudent ID: " + id+ "\n" +
                                        "Password: "+generatedPassword);

                    clearFields();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    private String generatePassword(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#";
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < length; i++) {
        int index = (int) (Math.random() * chars.length());
        password.append(chars.charAt(index));
    }

    return password.toString();
}

    private void clearFields() {
        txtName.setText("");
        txtGuardian.setText("");
        cmbGender.setSelectedIndex(0);
    }

    
}
