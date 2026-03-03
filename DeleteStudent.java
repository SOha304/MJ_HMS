package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteStudent extends JFrame {

    private JTextField txtId;
    private JButton btnDelete, btnClear;
    private Connection con;

    public DeleteStudent() {

        setTitle("🗑 Delete Student");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        connectDB();

        // 🌈 MAIN PANEL
        JPanel main = new JPanel(new BorderLayout(10,10));
        main.setBackground(new Color(245,247,250));
        main.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        add(main);

        // 🔴 HEADER
        JLabel title = new JLabel("Delete Student Record", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(211, 47, 47)); // red header
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        main.add(title, BorderLayout.NORTH);

        // 📦 CENTER PANEL
        JPanel center = new JPanel(new GridLayout(2,1,10,10));
        center.setBackground(new Color(245,247,250));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(245,247,250));

        JLabel lblId = new JLabel("Enter Student ID:");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtId = new JTextField(15);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        inputPanel.add(lblId);
        inputPanel.add(txtId);

        // ⚠️ WARNING TEXT
        JLabel warning = new JLabel("⚠ This action cannot be undone!", JLabel.CENTER);
        warning.setForeground(new Color(183, 28, 28));
        warning.setFont(new Font("Segoe UI", Font.BOLD, 13));

        center.add(inputPanel);
        center.add(warning);

        main.add(center, BorderLayout.CENTER);

        // 🔻 BUTTON PANEL
        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245,247,250));

        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(244,67,54));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);

        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(158,158,158));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnClear.setFocusPainted(false);

        bottom.add(btnDelete);
        bottom.add(btnClear);

        main.add(bottom, BorderLayout.SOUTH);

        // EVENTS
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> txtId.setText(""));

        setVisible(true);
    }

    private void connectDB() {
        try {
            con = DBConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(txtId.getText());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this student?",
                    "⚠ Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {

                String sql = "DELETE FROM Students WHERE student_id=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, id);

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Student Deleted Successfully!");
                    txtId.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Student Not Found!");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠ Invalid Input or Error!");
        }
    }
}