package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoomUI extends JFrame {

    public RoomUI() {

        setTitle("Room Management");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 240));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔹 Title
        JLabel title = new JLabel("Room Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        // 🔹 Hostel
        JLabel lblHostel = new JLabel("Hostel:");
        lblHostel.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblHostel, gbc);

        String[] hostels = {"Gandhi", "Zakir", "Begum"};
        JComboBox<String> cmbHostel = new JComboBox<>(hostels);
        cmbHostel.setFont(fieldFont);

        gbc.gridx = 1;
        add(cmbHostel, gbc);

        // 🔹 Floor
        JLabel lblFloor = new JLabel("Floor:");
        lblFloor.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblFloor, gbc);

        JTextField txtFloor = new JTextField();
        txtFloor.setFont(fieldFont);

        gbc.gridx = 1;
        add(txtFloor, gbc);

        // 🔹 Room Number
        JLabel lblRoom = new JLabel("Room Number:");
        lblRoom.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblRoom, gbc);

        JTextField txtRoom = new JTextField();
        txtRoom.setFont(fieldFont);

        gbc.gridx = 1;
        add(txtRoom, gbc);

        // 🔹 AC / Non-AC
        JLabel lblAC = new JLabel("AC Room:");
        lblAC.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblAC, gbc);

        JCheckBox chkAC = new JCheckBox("Yes");
        chkAC.setFont(fieldFont);

        gbc.gridx = 1;
        add(chkAC, gbc);

        // 🔹 Capacity (Auto)
        JLabel lblCapacity = new JLabel("Capacity:");
        lblCapacity.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblCapacity, gbc);

        JTextField txtCapacity = new JTextField();
        txtCapacity.setFont(fieldFont);
        txtCapacity.setEditable(false);

        gbc.gridx = 1;
        add(txtCapacity, gbc);

        // 🔹 Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(240, 240, 240));

        JButton btnAdd = new JButton("Add Room");
        JButton btnClear = new JButton("Clear");
        JButton btnBack = new JButton("Back");

        btnPanel.add(btnAdd);
        btnPanel.add(btnClear);
        btnPanel.add(btnBack);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // 🔥 Auto Capacity Logic
        cmbHostel.addActionListener(e -> updateCapacity(cmbHostel, txtFloor, txtCapacity));
        txtFloor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateCapacity(cmbHostel, txtFloor, txtCapacity);
            }
        });

        // 🔥 Add Room
        btnAdd.addActionListener(e -> {
            try {
                Connection conn = DBConnection.connect();

                String hostel = cmbHostel.getSelectedItem().toString();
                //int room_no = Integer.parseInt(txtRoom.getText());
                int floor = Integer.parseInt(txtFloor.getText());
                String roomNo = txtRoom.getText();
                int roomNoI = Integer.parseInt(roomNo);
                int capacity = Integer.parseInt(txtCapacity.getText());
                int isAC = chkAC.isSelected() ? 1 : 0;
                int hid;
                if(hostel.equals("Gandhi")) 
                    {hid=1;}
                else if(hostel.equals("Zakir")) {hid=2;}
                else {hid =3;}
                int room_id = hid*100+floor*10+roomNoI;
                /*String sql = "INSERT INTO Rooms (hostel_id, floor, room_number, capacity, is_ac) " +
                             "VALUES ((SELECT hostel_id FROM Hostels WHERE name=?), ?, ?, ?, ?)";*/
                 String sql = "INSERT INTO Rooms (room_id,hostel_id, floor, room_number, capacity, is_ac) " +
                             "VALUES (?,(SELECT hostel_id FROM Hostels WHERE name=?), ?, ?, ?, ?)";


                PreparedStatement pst = conn.prepareStatement(sql);
                 pst.setInt(1, room_id);
                pst.setString(2, hostel);
                //pst.setInt(2, room_no)
                pst.setInt(3, floor);
                pst.setString(4, roomNo);
                pst.setInt(5, capacity);
                pst.setInt(6, isAC);

                pst.executeUpdate(); // insert into Rooms

                // 👉 Insert into room_fee_master with default fees = 0
                String feeSql = "INSERT INTO room_fee_master (hostel_id, room_id, floor_id, fee_m, fee_s, fee_y, room_type) " +
                        "VALUES (?, ?, ?, 0, 0, 0, ?)";

                PreparedStatement pst2 = conn.prepareStatement(feeSql);

                pst2.setInt(1, hid);          // hostel_id
                pst2.setInt(2, room_id);      // room_id
                pst2.setInt(3, floor);        // floor_id
                pst2.setInt(4, isAC);         // room_type (assuming AC = type)

                pst2.executeUpdate();

                pst2.close();
                pst.close();
                conn.close();

                JOptionPane.showMessageDialog(this, "Room Added Successfully ✅");

                txtFloor.setText("");
                txtRoom.setText("");
                txtCapacity.setText("");
                chkAC.setSelected(false);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // 🔹 Clear
        btnClear.addActionListener(e -> {
            txtFloor.setText("");
            txtRoom.setText("");
            txtCapacity.setText("");
            chkAC.setSelected(false);
        });

        // 🔹 Back
        btnBack.addActionListener(e -> dispose());

        setVisible(true);
    }

    // 🔥 Capacity Logic Method
    private void updateCapacity(JComboBox<String> hostelBox, JTextField floorField, JTextField capacityField) {
        try {
            String hostel = hostelBox.getSelectedItem().toString();
            int floor = Integer.parseInt(floorField.getText());

            int capacity = 0;

            if (hostel.equals("Gandhi")) {
                capacity = (floor == 1) ? 4 : 3;
            } 
            else if (hostel.equals("Zakir")) {
                capacity = 3; // default (can improve later)
            } 
            else if (hostel.equals("Begum")) {
                capacity = 4;
            }

            capacityField.setText(String.valueOf(capacity));

        } catch (Exception e) {
            capacityField.setText("");
        }
    }
}

