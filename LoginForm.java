/*package hostel_management_system;

import java.awt.*;
import javax.swing.*;

public class loginForm extends JFrame {

    loginForm() {

        setTitle("Login");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center layout
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(255, 102, 252));

        Color darkGreen = new Color(8, 8, 8);

        // Bigger Fonts
        Font labelFont = new Font("Segoe UI", Font.BOLD, 26);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 22);
        Font btnFont = new Font("Segoe UI", Font.BOLD, 22);

        // Panel for form
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 102, 252));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label
        JLabel lblUser = new JLabel("Username:");
        lblUser.setFont(labelFont);
        lblUser.setForeground(darkGreen);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblUser, gbc);

        // Username Field
        JTextField txtUser = new JTextField(20);
        txtUser.setFont(fieldFont);
        txtUser.setHorizontalAlignment(JTextField.CENTER);

        gbc.gridx = 1;
        panel.add(txtUser, gbc);

        // Password Label
        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(labelFont);
        lblPass.setForeground(darkGreen);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblPass, gbc);

        // Password Field
        JPasswordField txtPass = new JPasswordField(20);
        txtPass.setFont(fieldFont);
        txtPass.setHorizontalAlignment(JTextField.CENTER);

        gbc.gridx = 1;
        panel.add(txtPass, gbc);

        // Show Password Checkbox
        JCheckBox chkShow = new JCheckBox("Show Password");
        chkShow.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        chkShow.setBackground(new Color(255, 102, 252));
        chkShow.setFocusable(false);
        chkShow.setForeground(darkGreen);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(chkShow, gbc);

        // Show/Hide Logic
        chkShow.addActionListener(e -> {
            if (chkShow.isSelected()) {
                txtPass.setEchoChar((char) 0);
            } else {
                txtPass.setEchoChar('*');
            }
        });

        // Submit Button
        JButton btnSubmit = new JButton("SUBMIT");
        btnSubmit.setFont(btnFont);
        btnSubmit.setFocusable(false);
        btnSubmit.setPreferredSize(new Dimension(180, 50));

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnSubmit, gbc);

        // Button Action
        btnSubmit.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            if ((user.equals("Admin") && pass.equals("1234")) ||(user.equals("Warden") && pass.equals("abcd") )) {
                dispose();
                new Homepage();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Incorrect UserName OR Password Entered");
            }
        });

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    
}*/
package hostel_management_system;

import java.awt.*;
import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import java.awt.event.*;

public class LoginForm extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginForm() {

        setTitle("Admin / Warden Login");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== Gradient Background =====
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(44, 62, 80),
                        getWidth(), getHeight(), new Color(52, 152, 219)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new GridBagLayout());

        // ===== Glass Card Panel =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(350, 280));
        card.setBackground(new Color(255, 255, 255, 230));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // ===== Title =====
        JLabel title = new JLabel("Admin / Warden Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Username Field =====
        txtUser = new JTextField();
        txtUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUser.setBorder(BorderFactory.createTitledBorder("Username"));
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // ===== Password Field =====
        txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPass.setBorder(BorderFactory.createTitledBorder("Password"));
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPass.setEchoChar('•');

        // ===== Show Password =====
        JCheckBox chkShow = new JCheckBox("Show Password");
        chkShow.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ✅ Fix background issue
        chkShow.setOpaque(false);  

        // ✅ Better visibility
        chkShow.setForeground(Color.DARK_GRAY);
        chkShow.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // ✅ Password toggle logic (correct)
        chkShow.addActionListener(e -> {
            if (chkShow.isSelected()) {
                txtPass.setEchoChar((char) 0);
            } else {
                txtPass.setEchoChar('•'); // use same char consistently
            }
        });

        // ===== Submit Button =====
        JButton btnSubmit = new JButton("LOGIN");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setBackground(new Color(41, 128, 185));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // Hover Effect
        btnSubmit.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSubmit.setBackground(new Color(31, 97, 141));
            }

            public void mouseExited(MouseEvent e) {
                btnSubmit.setBackground(new Color(41, 128, 185));
            }
        });

        // ===== Button Action =====
        btnSubmit.addActionListener(e -> authenticate());

        // ===== Add Components =====
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(txtUser);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(txtPass);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(chkShow);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(btnSubmit);

        background.add(card);
        add(background);

        setVisible(true);
    }

    private void authenticate() {

        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if ((user.equals("Admin") && pass.equals("1234")) ||
            (user.equals("Warden") && pass.equals("abcd"))) {

            dispose();
            new Homepage();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrect Username or Password");
            //txtPass.setText("");
        }
    }
}
