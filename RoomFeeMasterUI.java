package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoomFeeMasterUI extends JFrame {

    private JTextField txtHostelId, txtRoomId, txtFloorId;
    private JTextField txtMonthly, txtSemester, txtYearly;

    public RoomFeeMasterUI() {

        setTitle("Room Fee Master");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Main Panel (Card Style)
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // 🔥 Title
        JLabel title = new JLabel("Room Fee Master", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(30, 60, 90));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        // 🔹 Helper method
        int y = 1;

        y = addField(panel, gbc, "Hostel ID:", txtHostelId = new JTextField(), y, labelFont, fieldFont);
        y = addField(panel, gbc, "Room ID:", txtRoomId = new JTextField(), y, labelFont, fieldFont);
        y = addField(panel, gbc, "Floor ID:", txtFloorId = new JTextField(), y, labelFont, fieldFont);
        y = addField(panel, gbc, "Monthly Fee:", txtMonthly = new JTextField(), y, labelFont, fieldFont);
        y = addField(panel, gbc, "Semester Fee:", txtSemester = new JTextField(), y, labelFont, fieldFont);
        y = addField(panel, gbc, "Yearly Fee:", txtYearly = new JTextField(), y, labelFont, fieldFont);

        // 🔹 Buttons
        JButton btnUpdate = new JButton("Update Fees");
        JButton btnClear = new JButton("Clear");

        styleButton(btnUpdate);
        styleButton(btnClear);

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(btnUpdate, gbc);

        gbc.gridx = 1;
        panel.add(btnClear, gbc);

        add(panel, BorderLayout.CENTER);

        // 🔥 Actions
        btnUpdate.addActionListener(e -> updateFees());
        btnClear.addActionListener(e -> clearFields());

        setVisible(true);
    }

    // 🔹 Reusable Field Creator
    private int addField(JPanel panel, GridBagConstraints gbc,
                         String label, JTextField field, int y,
                         Font labelFont, Font fieldFont) {

        JLabel lbl = new JLabel(label);
        lbl.setFont(labelFont);

        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(200, 30));

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);

        return y + 1;
    }

    // 🔹 Button Style
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // 🔥 Update Logic (Self Connection)
    private void updateFees() {

        if (txtHostelId.getText().isEmpty() ||
            txtRoomId.getText().isEmpty() ||
            txtFloorId.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill all required fields ❗");
            return;
        }

        try {
            Connection conn = DBConnection.connect();

            int hostelId = Integer.parseInt(txtHostelId.getText());
            int roomId = Integer.parseInt(txtRoomId.getText());
            int floorId = Integer.parseInt(txtFloorId.getText());
            int feeM = Integer.parseInt(txtMonthly.getText());
            int feeS = Integer.parseInt(txtSemester.getText());
            int feeY = Integer.parseInt(txtYearly.getText());

            String sql = "UPDATE room_fee_master SET fee_m=?, fee_s=?, fee_y=? " +
                         "WHERE hostel_id=? AND room_id=? AND floor_id=?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, feeM);
            pst.setInt(2, feeS);
            pst.setInt(3, feeY);
            pst.setInt(4, hostelId);
            pst.setInt(5, roomId);
            pst.setInt(6, floorId);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Fees Updated Successfully ✅");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Record Not Found ❌");
            }
            

            pst.close();
            conn.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // 🔹 Clear Fields
    private void clearFields() {
        txtHostelId.setText("");
        txtRoomId.setText("");
        txtFloorId.setText("");
        txtMonthly.setText("");
        txtSemester.setText("");
        txtYearly.setText("");
    }
}
