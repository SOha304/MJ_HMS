/*package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame {

    public Homepage() {

        setTitle("Hostel Management System");
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        // ===== Sidebar Panel =====
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, getHeight()));
        sidebar.setBackground(new Color(18, 24, 38));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("CATEGORIES");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(40));

        JButton roomBtn = createModernButton("Room");
        //JButton AddStBtn = createModernButton("New Student");
        JButton AllBtn = createModernButton("Allocation Service");
        JButton FeeBtn = createModernButton("Fees Calculator");
        JButton PayBtn = createModernButton("Payment Tracking");
        JButton StuDetailBtn = createModernButton("Student Details");
        JButton ResidentBtn = createModernButton("Residents");
        JButton RegiBtn = createModernButton("Registeration Form");
        JButton Delbtn = createModernButton("Delete Student");


        // Buttons
        sidebar.add(RegiBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(ResidentBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(StuDetailBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(roomBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(FeeBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(AllBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(PayBtn);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(Delbtn);
        sidebar.add(Box.createVerticalStrut(20));

        Delbtn.addActionListener(ev -> {
            new DeleteStudent();
        });
        RegiBtn.addActionListener(ev -> {
            new Registration();
        });
        AllBtn.addActionListener(ev -> {
            new AllocateStudentUI();
        });
        //AddStBtn.addActionListener(ev -> {
          //  new AddStudentUI();
        //});
        roomBtn.addActionListener(ev -> {
            new RoomUI();
        });
        FeeBtn.addActionListener(ev -> {
            new RoomFeeMasterUI();
        });
        PayBtn.addActionListener(ev -> {
            new Payment();
        });
        StuDetailBtn.addActionListener(ev -> {
            new StudentDetails();
        });
        ResidentBtn.addActionListener(ev -> {
            new Resident();
        });




        add(sidebar, BorderLayout.WEST);

        // ===== Main Content Panel (Gradient) =====
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(85, 239, 196),
                        getWidth(), getHeight(), new Color(129, 236, 236));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        mainPanel.setLayout(new GridBagLayout());

        JLabel welcome = new JLabel("Welcome to Hostel Management System");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(new Color(40, 40, 40));

        mainPanel.add(welcome);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createModernButton(String text) {

        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(220, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52, 152, 219));
            }
        });

        return button;
    }
}*/
package hostel_management_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame {

    public Homepage() {

        setTitle("Hostel Management System");
        setSize(1000, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        // ===== Sidebar Panel =====
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, getHeight()));
        sidebar.setBackground(new Color(18, 24, 38));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("CATEGORIES");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(40));

        JButton roomBtn = createModernButton("Room");
        JButton AllBtn = createModernButton("Allocation Service");
        JButton FeeBtn = createModernButton("Fees Calculator");
        JButton PayBtn = createModernButton("Payment Tracking");
        JButton StuDetailBtn = createModernButton("Student Information");
        JButton ResidentBtn = createModernButton("Residents");
        JButton RegiBtn = createModernButton("Registeration Form");
        JButton Delbtn = createModernButton("Delete Student");

        sidebar.add(RegiBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(ResidentBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(StuDetailBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(roomBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(FeeBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(AllBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(PayBtn);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(Delbtn);
        sidebar.add(Box.createVerticalStrut(20));

        Delbtn.addActionListener(ev -> new DeleteStudent());
        RegiBtn.addActionListener(ev -> new Registration());
        AllBtn.addActionListener(ev -> new AllocateStudentUI());
        roomBtn.addActionListener(ev -> new RoomUI());
        FeeBtn.addActionListener(ev -> new RoomFeeMasterUI());
        PayBtn.addActionListener(ev -> new Payment());
        StuDetailBtn.addActionListener(ev -> new StudentDetails());
        ResidentBtn.addActionListener(ev -> new Resident());

        add(sidebar, BorderLayout.WEST);

        // ===== Main Content Panel with Background Image =====
        JPanel mainPanel = new JPanel() {

            Image bg = new ImageIcon("D://soha_c//Hostel1.jpg").getImage(); // <-- change image name if needed

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        mainPanel.setLayout(new GridBagLayout());

        JLabel welcome = new JLabel("Welcome to Hostel Management System");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcome.setForeground(Color.WHITE); // changed to white for visibility

        mainPanel.add(welcome);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createModernButton(String text) {

        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(220, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52, 152, 219));
            }
        });

        return button;
    }
}
