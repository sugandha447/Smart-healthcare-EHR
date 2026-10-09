package dashboard;

import login.Login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

import database.DBConnection;

public class PatientDashboard extends JFrame {

    private int patientId;

    private JLabel welcomeLabel;
    private JLabel appointmentValue;
    private JLabel recordValue;

    // ================= COLORS =================

    private final Color SIDEBAR_COLOR = new Color(20, 43, 70);
    private final Color HEADER_COLOR = new Color(245, 248, 252);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(35, 45, 60);
    private final Color SUBTEXT_COLOR = new Color(110, 120, 135);
    private final Color BLUE = new Color(40, 105, 180);
    private final Color GREEN = new Color(40, 155, 105);

    // ================= CONSTRUCTOR =================

    public PatientDashboard(int patientId) {

        this.patientId = patientId;

        setTitle("Smart Healthcare EHR - Patient Dashboard");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= SIDEBAR =================

        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // ================= MAIN PANEL =================

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(HEADER_COLOR);

        // ================= HEADER =================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_COLOR);
        header.setBorder(new EmptyBorder(25, 30, 15, 30));

        JLabel title = new JLabel("Patient Dashboard");

        title.setFont(
                new Font("Arial", Font.BOLD, 27)
        );

        title.setForeground(TEXT_COLOR);

        header.add(title, BorderLayout.WEST);

        welcomeLabel = new JLabel("Welcome, Patient");

        welcomeLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        welcomeLabel.setForeground(SUBTEXT_COLOR);

        header.add(welcomeLabel, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // ================= CONTENT =================

        JPanel content = new JPanel();
        content.setBackground(HEADER_COLOR);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(5, 30, 25, 30));

        // ================= SUMMARY TITLE =================

        JLabel overview = new JLabel("Overview");

        overview.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        overview.setForeground(TEXT_COLOR);

        overview.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(overview);

        content.add(Box.createVerticalStrut(12));

        // ================= SUMMARY CARDS =================

        JPanel statsPanel = new JPanel(
                new GridLayout(1, 3, 18, 0)
        );

        statsPanel.setBackground(HEADER_COLOR);
        statsPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 115)
        );

        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel appointmentCard =
                createStatCard(
                        "Appointments",
                        "0",
                        new CalendarIcon(BLUE)
                );

        appointmentValue =
                getValueLabel(appointmentCard);

        JPanel recordCard =
                createStatCard(
                        "Medical Records",
                        "0",
                        new MedicalIcon(GREEN)
                );

        recordValue =
                getValueLabel(recordCard);

        JPanel statusCard =
                createStatCard(
                        "Patient Status",
                        "Active",
                        new UserIcon(BLUE)
                );

        statsPanel.add(appointmentCard);
        statsPanel.add(recordCard);
        statsPanel.add(statusCard);

        content.add(statsPanel);

        content.add(Box.createVerticalStrut(25));

        // ================= QUICK ACTION TITLE =================

        JLabel quickTitle =
                new JLabel("Quick Actions");

        quickTitle.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        quickTitle.setForeground(TEXT_COLOR);

        quickTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(quickTitle);

        content.add(Box.createVerticalStrut(12));

        // ================= QUICK ACTIONS =================

        JPanel actionsPanel =
                new JPanel(
                        new GridLayout(2, 3, 15, 15)
                );

        actionsPanel.setBackground(HEADER_COLOR);
        actionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ---------------- PROFILE ----------------

        JButton btnProfile =
                createActionButton(
                        "My Profile",
                        new UserIcon(BLUE)
                );

        btnProfile.addActionListener(e -> {

            new PatientProfile(patientId);

        });

        // ---------------- BOOK APPOINTMENT ----------------

        JButton btnBookAppointment =
                createActionButton(
                        "Book Appointment",
                        new CalendarIcon(GREEN)
                );

        btnBookAppointment.addActionListener(e -> {

            new BookAppointment(patientId);

        });

        // ---------------- APPOINTMENTS ----------------

        JButton btnAppointments =
                createActionButton(
                        "My Appointments",
                        new CalendarIcon(BLUE)
                );

        btnAppointments.addActionListener(e -> {

            new MyAppointments(patientId);

        });

        // ---------------- MEDICAL RECORDS ----------------

        JButton btnRecords =
                createActionButton(
                        "My Medical Records",
                        new MedicalIcon(GREEN)
                );

        btnRecords.addActionListener(e -> {

            new MyMedicalRecords(patientId);

        });

        // ---------------- TIMELINE ----------------

        JButton btnTimeline =
                createActionButton(
                        "Medical Timeline",
                        new TimelineIcon(BLUE)
                );

        btnTimeline.addActionListener(e -> {

            new PatientMedicalTimeline(patientId);

        });

        // ---------------- LOGOUT ----------------

        JButton btnLogout =
                createActionButton(
                        "Logout",
                        new LogoutIcon(new Color(200, 70, 70))
                );

        btnLogout.addActionListener(e -> {

            dispose();

            new Login();

        });

        actionsPanel.add(btnProfile);
        actionsPanel.add(btnBookAppointment);
        actionsPanel.add(btnAppointments);
        actionsPanel.add(btnRecords);
        actionsPanel.add(btnTimeline);
        actionsPanel.add(btnLogout);

        content.add(actionsPanel);

        mainPanel.add(
                new JScrollPane(
                        content,
                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                ),
                BorderLayout.CENTER
        );

        add(mainPanel, BorderLayout.CENTER);

        // ================= LOAD DATA =================

        loadPatientData();
        loadDashboardStats();

        setVisible(true);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(230, 650)
        );

        sidebar.setBackground(SIDEBAR_COLOR);

        sidebar.setLayout(
                new BorderLayout()
        );

        // ================= LOGO =================

        JPanel logoPanel = new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        30, 20, 20, 20
                )
        );

        JLabel hospitalIcon =
                new JLabel(
                        new HospitalIcon(Color.WHITE)
                );

        hospitalIcon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoPanel.add(hospitalIcon);

        logoPanel.add(
                Box.createVerticalStrut(12)
        );

        JLabel logo =
                new JLabel("SMART HEALTHCARE");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        logo.setForeground(Color.WHITE);

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoPanel.add(logo);

        JLabel ehr =
                new JLabel("EHR MANAGEMENT");

        ehr.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        ehr.setForeground(
                new Color(190, 205, 220)
        );

        ehr.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoPanel.add(ehr);

        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );

        // ================= MENU =================

        JPanel menu =
                new JPanel();

        menu.setOpaque(false);

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        menu.setBorder(
                new EmptyBorder(
                        10, 12, 10, 12
                )
        );

        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard",
                        new HomeIcon(Color.WHITE)
                );

        JButton profileButton =
                createSidebarButton(
                        "My Profile",
                        new UserIcon(Color.WHITE)
                );

        JButton bookButton =
                createSidebarButton(
                        "Book Appointment",
                        new CalendarIcon(Color.WHITE)
                );

        JButton appointmentsButton =
                createSidebarButton(
                        "My Appointments",
                        new CalendarIcon(Color.WHITE)
                );

        JButton recordsButton =
                createSidebarButton(
                        "Medical Records",
                        new MedicalIcon(Color.WHITE)
                );

        JButton timelineButton =
                createSidebarButton(
                        "Medical Timeline",
                        new TimelineIcon(Color.WHITE)
                );

        JButton logoutButton =
                createSidebarButton(
                        "Logout",
                        new LogoutIcon(Color.WHITE)
                );

        // ================= ACTIONS =================

        dashboardButton.addActionListener(e -> {
            // Already on dashboard
        });

        profileButton.addActionListener(e -> {
            new PatientProfile(patientId);
        });

        bookButton.addActionListener(e -> {
            new BookAppointment(patientId);
        });

        appointmentsButton.addActionListener(e -> {
            new MyAppointments(patientId);
        });

        recordsButton.addActionListener(e -> {
            new MyMedicalRecords(patientId);
        });

        timelineButton.addActionListener(e -> {
            new PatientMedicalTimeline(patientId);
        });

        logoutButton.addActionListener(e -> {

            dispose();

            new Login();

        });

        menu.add(dashboardButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(profileButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(bookButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(appointmentsButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(recordsButton);
        menu.add(Box.createVerticalStrut(5));

        menu.add(timelineButton);

        sidebar.add(
                menu,
                BorderLayout.CENTER
        );

        // ================= FOOTER =================

        JLabel footer =
                new JLabel("Patient Portal");

        footer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        footer.setForeground(
                new Color(180, 195, 210)
        );

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        footer.setBorder(
                new EmptyBorder(
                        10, 0, 20, 0
                )
        );

        sidebar.add(
                footer,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    // =========================================================
    // SIDEBAR BUTTON
    // =========================================================

    private JButton createSidebarButton(
            String text,
            Icon icon
    ) {

        JButton button =
                new JButton(text);

        button.setIcon(icon);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setIconTextGap(15);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        button.setBackground(
                SIDEBAR_COLOR
        );

        button.setBorder(
                new EmptyBorder(
                        12, 15, 12, 10
                )
        );

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            Icon icon
    ) {

        JPanel card =
                new RoundedPanel(
                        18,
                        CARD_COLOR
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        15, 18, 15, 18
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        card.add(
                iconLabel,
                BorderLayout.WEST
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setBorder(
                new EmptyBorder(
                        0, 15, 0, 0
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        titleLabel.setForeground(
                SUBTEXT_COLOR
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        valueLabel.setForeground(
                TEXT_COLOR
        );

        textPanel.add(titleLabel);
        textPanel.add(
                Box.createVerticalStrut(5)
        );
        textPanel.add(valueLabel);

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // GET VALUE LABEL
    // =========================================================

    private JLabel getValueLabel(JPanel card) {

        Component[] components =
                card.getComponents();

        for (Component component : components) {

            if (component instanceof JPanel) {

                Component[] inner =
                        ((JPanel) component)
                                .getComponents();

                for (Component c : inner) {

                    if (c instanceof JLabel) {

                        JLabel label =
                                (JLabel) c;

                        if (label.getFont().getSize() == 24) {
                            return label;
                        }
                    }
                }
            }
        }

        return new JLabel("0");
    }

    // =========================================================
    // QUICK ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Icon icon
    ) {

        JButton button =
                new JButton(text);

        button.setIcon(icon);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setIconTextGap(15);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(
                TEXT_COLOR
        );

        button.setBackground(
                Color.WHITE
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 237)
                        ),
                        new EmptyBorder(
                                12, 15, 12, 15
                        )
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // =========================================================
    // LOAD PATIENT DATA
    // =========================================================

    private void loadPatientData() {

        String sql =
                "SELECT patient_name " +
                        "FROM patients " +
                        "WHERE patient_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    patientId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                String name =
                        rs.getString(
                                "patient_name"
                        );

                welcomeLabel.setText(
                        "Welcome, " + name
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // LOAD DASHBOARD STATISTICS
    // =========================================================

    private void loadDashboardStats() {

        // ---------------- APPOINTMENTS ----------------

        String appointmentSQL =
                "SELECT COUNT(*) " +
                        "FROM appointments " +
                        "WHERE patient_id=?";

        // ---------------- MEDICAL RECORDS ----------------

        String recordSQL =
                "SELECT COUNT(*) " +
                        "FROM medical_records " +
                        "WHERE patient_id=?";

        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            // Appointments

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    appointmentSQL
                            )
            ) {

                ps.setInt(
                        1,
                        patientId
                );

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    appointmentValue.setText(
                            rs.getString(1)
                    );
                }
            }

            // Medical Records

            try (
                    PreparedStatement ps =
                            con.prepareStatement(
                                    recordSQL
                            )
            ) {

                ps.setInt(
                        1,
                        patientId
                );

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    recordValue.setText(
                            rs.getString(1)
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    static class RoundedPanel
            extends JPanel {

        private int radius;
        private Color background;

        public RoundedPanel(
                int radius,
                Color background
        ) {

            this.radius = radius;
            this.background = background;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(background);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // ICON BASE
    // =========================================================

    static abstract class BaseIcon
            implements Icon {

        protected Color color;

        BaseIcon(Color color) {
            this.color = color;
        }

        public int getIconWidth() {
            return 22;
        }

        public int getIconHeight() {
            return 22;
        }
    }

    // =========================================================
    // HOME ICON
    // =========================================================

    static class HomeIcon
            extends BaseIcon {

        HomeIcon(Color color) {
            super(color);
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(
                            2,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            Polygon roof =
                    new Polygon();

            roof.addPoint(
                    x + 3,
                    y + 10
            );

            roof.addPoint(
                    x + 11,
                    y + 3
            );

            roof.addPoint(
                    x + 19,
                    y + 10
            );

            g2.draw(roof);

            g2.drawRoundRect(
                    x + 5,
                    y + 9,
                    12,
                    10,
                    2,
                    2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // USER ICON
    // =========================================================

    static class UserIcon
            extends BaseIcon {

        UserIcon(Color color) {
            super(color);
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(2)
            );

            g2.drawOval(
                    x + 7,
                    y + 2,
                    8,
                    8
            );

            g2.drawArc(
                    x + 4,
                    y + 10,
                    14,
                    10,
                    0,
                    180
            );

            g2.dispose();
        }
    }

    // =========================================================
    // CALENDAR ICON
    // =========================================================

    static class CalendarIcon
            extends BaseIcon {

        CalendarIcon(Color color) {
            super(color);
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(2)
            );

            g2.drawRoundRect(
                    x + 3,
                    y + 5,
                    16,
                    14,
                    2,
                    2
            );

            g2.drawLine(
                    x + 3,
                    y + 9,
                    x + 19,
                    y + 9
            );

            g2.drawLine(
                    x + 7,
                    y + 2,
                    x + 7,
                    y + 7
            );

            g2.drawLine(
                    x + 15,
                    y + 2,
                    x + 15,
                    y + 7
            );

            g2.dispose();
        }
    }

    // =========================================================
    // MEDICAL ICON
    // =========================================================

    static class MedicalIcon
            extends BaseIcon {

        MedicalIcon(Color color) {
            super(color);
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(
                            2,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            g2.drawRoundRect(
                    x + 4,
                    y + 2,
                    14,
                    18,
                    2,
                    2
            );

            g2.drawLine(
                    x + 8,
                    y + 7,
                    x + 14,
                    y + 7
            );

            g2.drawLine(
                    x + 8,
                    y + 11,
                    x + 14,
                    y + 11
            );

            g2.drawLine(
                    x + 8,
                    y + 15,
                    x + 12,
                    y + 15
            );

            g2.dispose();
        }
    }

    // =========================================================
    // TIMELINE ICON
    // =========================================================

    static class TimelineIcon
            extends BaseIcon {

        TimelineIcon(Color color) {
            super(color);
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(2)
            );

            g2.drawLine(
                    x + 6,
                    y + 3,
                    x + 6,
                    y + 19
            );

            g2.fillOval(
                    x + 3,
                    y + 2,
                    6,
                    6
            );

            g2.fillOval(
                    x + 3,
                    y + 8,
                    6,
                    6
            );

            g2.fillOval(
                    x + 3,
                    y + 15,
                    6,
                    6
            );

            g2.drawLine(
                    x + 10,
                    y + 5,
                    x + 19,
                    y + 5
            );

            g2.drawLine(
                    x + 10,
                    y + 11,
                    x + 19,
                    y + 11
            );

            g2.drawLine(
                    x + 10,
                    y + 18,
                    x + 19,
                    y + 18
            );

            g2.dispose();
        }
    }

    // =========================================================
    // LOGOUT ICON
    // =========================================================

    static class LogoutIcon
            extends BaseIcon {

        LogoutIcon(Color color) {
            super(color);
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(
                            2,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            g2.drawRoundRect(
                    x + 3,
                    y + 3,
                    9,
                    16,
                    2,
                    2
            );

            g2.drawLine(
                    x + 10,
                    y + 11,
                    x + 19,
                    y + 11
            );

            g2.drawLine(
                    x + 15,
                    y + 7,
                    x + 19,
                    y + 11
            );

            g2.drawLine(
                    x + 15,
                    y + 15,
                    x + 19,
                    y + 11
            );

            g2.dispose();
        }
    }

    // =========================================================
    // HOSPITAL ICON
    // =========================================================

    static class HospitalIcon
            extends BaseIcon {

        HospitalIcon(Color color) {
            super(color);
        }

        public int getIconWidth() {
            return 32;
        }

        public int getIconHeight() {
            return 32;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(
                            2.5f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            g2.drawRoundRect(
                    x + 5,
                    y + 5,
                    22,
                    22,
                    3,
                    3
            );

            g2.drawLine(
                    x + 16,
                    y + 9,
                    x + 16,
                    y + 20
            );

            g2.drawLine(
                    x + 11,
                    y + 14,
                    x + 21,
                    y + 14
            );

            g2.drawLine(
                    x + 13,
                    y + 27,
                    x + 13,
                    y + 21
            );

            g2.drawLine(
                    x + 19,
                    y + 27,
                    x + 19,
                    y + 21
            );

            g2.dispose();
        }
    }
}