/*package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPage extends JFrame {

    public StartPage() {

        setTitle("Hostel Management System");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Welcome to Hostel Management System", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        add(heading, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1,20,20));
        panel.setBorder(BorderFactory.createEmptyBorder(40,100,40,100));

        JButton btnStudent = new JButton("Login as Student");
        JButton btnAdmin = new JButton("Login as Admin / Warden");

        panel.add(btnStudent);
        panel.add(btnAdmin);

        add(panel, BorderLayout.CENTER);

        // Student button
        btnStudent.addActionListener(e -> {
            new StudentLogin();
            dispose();
        });

        // Admin button
        btnAdmin.addActionListener(e -> {
            new loginForm(); // your existing login page
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new StartPage();
    }
}*/
package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPage extends JFrame {

    public StartPage() {

        setTitle("Hostel Management System");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== BACKGROUND PANEL WITH GRADIENT =====
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color c1 = new Color(58, 123, 213);
                Color c2 = new Color(58, 213, 151);
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new GridBagLayout());

        // ===== GLASS CARD =====
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(400, 280));
        card.setBackground(new Color(255, 255, 255, 200));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ===== TITLE =====
        JLabel title = new JLabel("Hostel Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Select your role to continue");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== BUTTONS =====
        JButton btnStudent = createModernButton("Student Login");
        JButton btnAdmin = createModernButton("Admin / Warden Login");

        // ACTIONS
        btnStudent.addActionListener(e -> {
            new StudentLogin();
            dispose();
        });

        btnAdmin.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        // ===== ADD COMPONENTS =====
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(subtitle);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(btnStudent);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(btnAdmin);

        background.add(card);
        add(background);

        setVisible(true);
    }

    // ===== MODERN BUTTON DESIGN =====
    private JButton createModernButton(String text) {

        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(45, 140, 255));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(20, 110, 220));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(45, 140, 255));
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new StartPage();
    }
}