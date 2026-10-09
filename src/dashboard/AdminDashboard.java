package dashboard;

import login.Login;
import accesscontrol.UserAccessControl;
import department.ManageDepartments;
import database.DBConnection;
import appointment.AppointmentScheduling;
import analytics.AnalyticsDashboard;
import patient.ViewPatients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class AdminDashboard extends JFrame
        implements ActionListener {

    // =========================================================
    // BUTTONS
    // =========================================================

    JButton btnDashboard;
    JButton btnDoctors;
    JButton btnStaff;
    JButton btnPatients;
    JButton btnViewPatients;
    JButton btnAppointments;
    JButton btnAnalytics;
    JButton btnUserAccess;
    JButton btnDepartments;
    JButton btnEmployeeManagement;
    JButton btnLogout;

    // =========================================================
    // SUMMARY CARD VALUE LABELS
    // =========================================================

    JLabel patientValue;
    JLabel appointmentValue;
    JLabel recordValue;
    JLabel riskValue;

    // =========================================================
    // COLORS
    // =========================================================

    Color NAVY = new Color(18, 43, 70);
    Color BLUE = new Color(37, 99, 235);
    Color LIGHT_BLUE = new Color(239, 246, 255);

    Color WHITE = Color.WHITE;

    Color TEXT = new Color(35, 48, 62);
    Color MUTED = new Color(100, 116, 139);
    Color BORDER = new Color(226, 232, 240);

    Color GREEN = new Color(16, 185, 129);
    Color RED = new Color(239, 68, 68);

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AdminDashboard() {

        setTitle(
                "Smart Healthcare EHR Management System"
        );

        // Reduced height
        setSize(1200, 620);

        setLayout(
                new BorderLayout()
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        getContentPane().setBackground(
                new Color(248, 250, 252)
        );

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar =
                createSidebar();

        add(
                sidebar,
                BorderLayout.WEST
        );

        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel mainPanel =
                createMainPanel();

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // LOAD DATABASE DATA
        // =====================================================

        loadDashboardData();

        // =====================================================
        // SHOW WINDOW
        // =====================================================

        setVisible(true);
    }

    // =========================================================
    // CREATE SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(245, 620)
        );

        sidebar.setBackground(
                NAVY
        );

        sidebar.setLayout(
                new BorderLayout()
        );

        // =====================================================
        // LOGO AREA
        // =====================================================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setLayout(
                new FlowLayout(
                        FlowLayout.LEFT,
                        18,
                        18
                )
        );

        JLabel logoIcon =
                new JLabel(
                        new HospitalIcon(
                                Color.WHITE,
                                30
                        )
                );

        logoPanel.add(
                logoIcon
        );

        JPanel logoText =
                new JPanel();

        logoText.setOpaque(false);

        logoText.setLayout(
                new BoxLayout(
                        logoText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "SMART HEALTHCARE"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        JLabel subtitle =
                new JLabel(
                        "EHR MANAGEMENT"
                );

        subtitle.setForeground(
                new Color(
                        186,
                        210,
                        235
                )
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        logoText.add(title);
        logoText.add(subtitle);

        logoPanel.add(
                logoText
        );

        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // MENU
        // =====================================================

        JPanel menuPanel =
                new JPanel();

        menuPanel.setOpaque(false);

        menuPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        3,
                        12,
                        3,
                        12
                )
        );

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // Dashboard
        btnDashboard =
                createMenuButton(
                        "Dashboard",
                        new DashboardIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnDashboard
        );

        // Doctors
        btnDoctors =
                createMenuButton(
                        "Manage Doctors",
                        new DoctorIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnDoctors
        );

        // Receptionists
        btnStaff =
                createMenuButton(
                        "Manage Receptionists",
                        new StaffIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnStaff
        );

        // Patients
        btnPatients =
                createMenuButton(
                        "Manage Patients",
                        new PatientIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnPatients
        );

        // View Patients
        btnViewPatients =
                createMenuButton(
                        "View Patients",
                        new PatientListIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnViewPatients
        );

        // Appointments
        btnAppointments =
                createMenuButton(
                        "Appointments",
                        new AppointmentIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnAppointments
        );

        // Analytics
        btnAnalytics =
                createMenuButton(
                        "Analytics",
                        new AnalyticsIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnAnalytics
        );

        // User Access
        btnUserAccess =
                createMenuButton(
                        "User Access",
                        new AccessIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnUserAccess
        );

        // Departments
        btnDepartments =
                createMenuButton(
                        "Departments",
                        new DepartmentIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnDepartments
        );

        // Employee Management
        btnEmployeeManagement =
                createMenuButton(
                        "Employee Management",
                        new EmployeeIcon(
                                Color.WHITE,
                                19
                        )
                );

        menuPanel.add(
                btnEmployeeManagement
        );

        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // LOGOUT
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        12,
                        15,
                        12
                )
        );

        btnLogout =
                createMenuButton(
                        "Logout",
                        new LogoutIcon(
                                Color.WHITE,
                                19
                        )
                );

        bottomPanel.add(
                btnLogout,
                BorderLayout.CENTER
        );

        sidebar.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        return sidebar;
    }

    // =========================================================
    // CREATE SIDEBAR BUTTON
    // =========================================================

    private JButton createMenuButton(
            String text,
            Icon icon) {

        JButton button =
                new JButton(text);

        button.setIcon(
                icon
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setIconTextGap(
                14
        );

        button.setForeground(
                Color.WHITE
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        button.setBackground(
                NAVY
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        15,
                        8,
                        10
                )
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        41
                )
        );

        button.addActionListener(
                this
        );

        return button;
    }

    // =========================================================
    // CREATE MAIN PANEL
    // =========================================================

    private JPanel createMainPanel() {

        JPanel main =
                new JPanel();

        main.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        main.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        28,
                        18,
                        28
                )
        );

        main.setLayout(
                new BorderLayout(
                        0,
                        15
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        JPanel headingPanel =
                new JPanel();

        headingPanel.setOpaque(false);

        headingPanel.setLayout(
                new BoxLayout(
                        headingPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel(
                        "Admin Dashboard"
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        heading.setForeground(
                TEXT
        );

        // Required welcome text
        JLabel welcome =
                new JLabel(
                        "Welcome, Administrator"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        welcome.setForeground(
                MUTED
        );

        headingPanel.add(
                heading
        );

        headingPanel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        headingPanel.add(
                welcome
        );

        header.add(
                headingPanel,
                BorderLayout.WEST
        );

        // =====================================================
        // ADMIN ICON
        // =====================================================

        JLabel adminIcon =
                new JLabel(
                        new AdminIcon(
                                BLUE,
                                30
                        )
                );

        header.add(
                adminIcon,
                BorderLayout.EAST
        );

        main.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel center =
                new JPanel();

        center.setOpaque(false);

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // SUMMARY CARDS
        // =====================================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                14,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        // -----------------------------------------------------
        // PATIENTS CARD
        // -----------------------------------------------------

        JPanel patientCard =
                createStatCard(
                        "Patients",
                        "0",
                        new PatientIcon(
                                BLUE,
                                25
                        ),
                        LIGHT_BLUE,
                        1
                );

        // -----------------------------------------------------
        // APPOINTMENTS CARD
        // -----------------------------------------------------

        JPanel appointmentCard =
                createStatCard(
                        "Appointments",
                        "0",
                        new AppointmentIcon(
                                new Color(
                                        139,
                                        92,
                                        246
                                ),
                                25
                        ),
                        new Color(
                                245,
                                243,
                                255
                        ),
                        2
                );

        // -----------------------------------------------------
        // MEDICAL RECORDS CARD
        // -----------------------------------------------------

        JPanel recordCard =
                createStatCard(
                        "Medical Records",
                        "0",
                        new MedicalRecordIcon(
                                GREEN,
                                25
                        ),
                        new Color(
                                236,
                                253,
                                245
                        ),
                        3
                );

        // -----------------------------------------------------
        // HIGH RISK CARD
        // -----------------------------------------------------

        JPanel riskCard =
                createStatCard(
                        "High Risk",
                        "0",
                        new RiskIcon(
                                RED,
                                25
                        ),
                        new Color(
                                254,
                                242,
                                242
                        ),
                        4
                );

        statsPanel.add(
                patientCard
        );

        statsPanel.add(
                appointmentCard
        );

        statsPanel.add(
                recordCard
        );

        statsPanel.add(
                riskCard
        );

        center.add(
                statsPanel
        );

        // =====================================================
        // SPACE
        // =====================================================

        center.add(
                Box.createVerticalStrut(
                        18
                )
        );

        // =====================================================
        // QUICK ACTION TITLE
        // =====================================================

        JLabel quickTitle =
                new JLabel(
                        "Quick Actions"
                );

        quickTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        quickTitle.setForeground(
                TEXT
        );

        quickTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        center.add(
                quickTitle
        );

        center.add(
                Box.createVerticalStrut(
                        10
                )
        );

        // =====================================================
        // QUICK ACTION PANEL
        // =====================================================

        JPanel actionsPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                3,
                                12,
                                12
                        )
                );

        actionsPanel.setOpaque(false);

        // -----------------------------------------------------
        // MANAGE DOCTORS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Manage Doctors",
                        "Add and manage doctors",
                        new DoctorIcon(
                                BLUE,
                                25
                        ),
                        btnDoctors
                )
        );

        // -----------------------------------------------------
        // MANAGE RECEPTIONISTS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Manage Receptionists",
                        "Manage reception staff",
                        new StaffIcon(
                                new Color(
                                        139,
                                        92,
                                        246
                                ),
                                25
                        ),
                        btnStaff
                )
        );

        // -----------------------------------------------------
        // MANAGE PATIENTS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Manage Patients",
                        "Manage patient records",
                        new PatientIcon(
                                GREEN,
                                25
                        ),
                        btnPatients
                )
        );

        // -----------------------------------------------------
        // VIEW PATIENTS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "View Patients",
                        "View registered patients",
                        new PatientListIcon(
                                BLUE,
                                25
                        ),
                        btnViewPatients
                )
        );

        // -----------------------------------------------------
        // APPOINTMENTS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Appointments",
                        "Manage appointments",
                        new AppointmentIcon(
                                new Color(
                                        139,
                                        92,
                                        246
                                ),
                                25
                        ),
                        btnAppointments
                )
        );

        // -----------------------------------------------------
        // ANALYTICS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Analytics",
                        "View system analytics",
                        new AnalyticsIcon(
                                GREEN,
                                25
                        ),
                        btnAnalytics
                )
        );

        // -----------------------------------------------------
        // USER ACCESS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "User Access",
                        "Control user access",
                        new AccessIcon(
                                BLUE,
                                25
                        ),
                        btnUserAccess
                )
        );

        // -----------------------------------------------------
        // DEPARTMENTS
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Departments",
                        "Manage departments",
                        new DepartmentIcon(
                                new Color(
                                        139,
                                        92,
                                        246
                                ),
                                25
                        ),
                        btnDepartments
                )
        );

        // -----------------------------------------------------
        // EMPLOYEE MANAGEMENT
        // -----------------------------------------------------

        actionsPanel.add(
                createActionCard(
                        "Employee Management",
                        "Manage system employees",
                        new EmployeeIcon(
                                GREEN,
                                25
                        ),
                        btnEmployeeManagement
                )
        );

        center.add(
                actionsPanel
        );

        main.add(
                center,
                BorderLayout.CENTER
        );

        return main;
    }

    // =========================================================
    // CREATE SUMMARY CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            Icon icon,
            Color background,
            int cardNumber) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        card.setBackground(
                background
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                12,
                                15,
                                12,
                                15
                        )
                )
        );

        // =====================================================
        // ICON
        // =====================================================

        JLabel iconLabel =
                new JLabel(
                        icon
                );

        card.add(
                iconLabel,
                BorderLayout.WEST
        );

        // =====================================================
        // TEXT PANEL
        // =====================================================

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // TITLE
        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        titleLabel.setForeground(
                MUTED
        );

        // VALUE
        JLabel valueLabel =
                new JLabel(
                        value
                );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        valueLabel.setForeground(
                TEXT
        );

        // Store value labels
        if (cardNumber == 1) {

            patientValue =
                    valueLabel;
        }
        else if (cardNumber == 2) {

            appointmentValue =
                    valueLabel;
        }
        else if (cardNumber == 3) {

            recordValue =
                    valueLabel;
        }
        else if (cardNumber == 4) {

            riskValue =
                    valueLabel;
        }

        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        textPanel.add(
                valueLabel
        );

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // CARD SIZE
        // =====================================================

        card.setPreferredSize(
                new Dimension(
                        200,
                        82
                )
        );

        return card;
    }

    // =========================================================
    // QUICK ACTION CARD
    // =========================================================

    private JPanel createActionCard(
            String title,
            String description,
            Icon icon,
            JButton originalButton) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                11,
                                13,
                                11,
                                13
                        )
                )
        );

        // =====================================================
        // ICON
        // =====================================================

        JLabel iconLabel =
                new JLabel(
                        icon
                );

        card.add(
                iconLabel,
                BorderLayout.WEST
        );

        // =====================================================
        // TEXT
        // =====================================================

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        titleLabel.setForeground(
                TEXT
        );

        JLabel descriptionLabel =
                new JLabel(
                        description
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        descriptionLabel.setForeground(
                MUTED
        );

        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        textPanel.add(
                descriptionLabel
        );

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // CLICK
        // =====================================================

        card.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        card.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        originalButton.doClick();
                    }

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        card.setBackground(
                                new Color(
                                        248,
                                        250,
                                        252
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        card.setBackground(
                                Color.WHITE
                        );
                    }
                }
        );

        return card;
    }

    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(
            ActionEvent e) {

        // =====================================================
        // DASHBOARD
        // =====================================================

        if (e.getSource() == btnDashboard) {

            // Already on dashboard.
        }

        // =====================================================
        // MANAGE DOCTORS
        // =====================================================

        else if (
                e.getSource() == btnDoctors) {

            new ManageDoctors();
        }

        // =====================================================
        // MANAGE RECEPTIONISTS
        // =====================================================

        else if (
                e.getSource() == btnStaff) {

            new ManageReceptionists();
        }

        // =====================================================
        // MANAGE PATIENTS
        // =====================================================

        else if (
                e.getSource() == btnPatients) {

            new ManagePatients();
        }

        // =====================================================
        // VIEW PATIENTS
        // =====================================================

        else if (
                e.getSource()
                        == btnViewPatients) {

            new ViewPatients();
        }

        // =====================================================
        // APPOINTMENTS
        // =====================================================

        else if (
                e.getSource()
                        == btnAppointments) {

            new AppointmentScheduling();
        }

        // =====================================================
        // ANALYTICS
        // =====================================================

        else if (
                e.getSource()
                        == btnAnalytics) {

            new AnalyticsDashboard();
        }

        // =====================================================
        // USER ACCESS
        // =====================================================

        else if (
                e.getSource()
                        == btnUserAccess) {

            new UserAccessControl();
        }

        // =====================================================
        // DEPARTMENTS
        // =====================================================

        else if (
                e.getSource()
                        == btnDepartments) {

            new ManageDepartments();
        }

        // =====================================================
        // EMPLOYEE MANAGEMENT
        // =====================================================

        else if (
                e.getSource()
                        == btnEmployeeManagement) {

            new EmployeeManagement();
        }

        // =====================================================
        // LOGOUT
        // =====================================================

        else if (
                e.getSource()
                        == btnLogout) {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Confirm Logout",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (
                    confirm
                            == JOptionPane.YES_OPTION) {

                dispose();

                new Login();
            }
        }
    }

    // =========================================================
    // LOAD DASHBOARD DATA
    // =========================================================

    public void loadDashboardData() {

        try {

            Connection con =
                    DBConnection.getConnection();

            Statement st =
                    con.createStatement();

            // =================================================
            // TOTAL PATIENTS
            // =================================================

            ResultSet rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM patients"
                    );

            if (rs.next()) {

                patientValue.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();

            // =================================================
            // TOTAL APPOINTMENTS
            // =================================================

            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM appointments"
                    );

            if (rs.next()) {

                appointmentValue.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();

            // =================================================
            // TOTAL MEDICAL RECORDS
            // =================================================

            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) FROM medical_records"
                    );

            if (rs.next()) {

                recordValue.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();

            // =================================================
            // HIGH RISK PATIENTS
            // =================================================

            rs =
                    st.executeQuery(
                            "SELECT COUNT(*) " +
                                    "FROM medical_records " +
                                    "WHERE LOWER(diagnosis) IN " +
                                    "('heart attack'," +
                                    "'stroke'," +
                                    "'cancer'," +
                                    "'icu'," +
                                    "'high risk')"
                    );

            if (rs.next()) {

                riskValue.setText(
                        String.valueOf(
                                rs.getInt(1)
                        )
                );
            }

            rs.close();

            st.close();

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load dashboard data.\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // BASE ICON
    // =========================================================

    abstract static class BaseIcon
            implements Icon {

        protected Color color;
        protected int size;

        public BaseIcon(
                Color color,
                int size) {

            this.color = color;
            this.size = size;
        }

        @Override
        public int getIconWidth() {

            return size;
        }

        @Override
        public int getIconHeight() {

            return size;
        }

        protected Graphics2D setup(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    color
            );

            g2.setStroke(
                    new BasicStroke(
                            1.8f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            return g2;
        }
    }

    // =========================================================
    // HOSPITAL ICON
    // =========================================================

    static class HospitalIcon
            extends BaseIcon {

        public HospitalIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            int s = size;

            g2.drawRoundRect(
                    x + 4,
                    y + 5,
                    s - 8,
                    s - 7,
                    3,
                    3
            );

            g2.drawLine(
                    x + s / 2,
                    y + 8,
                    x + s / 2,
                    y + 17
            );

            g2.drawLine(
                    x + s / 2 - 4,
                    y + 12,
                    x + s / 2 + 4,
                    y + 12
            );

            g2.dispose();
        }
    }

    // =========================================================
    // DASHBOARD ICON
    // =========================================================

    static class DashboardIcon
            extends BaseIcon {

        public DashboardIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            int d =
                    size / 2 - 3;

            g2.fillRoundRect(
                    x + 2,
                    y + 2,
                    d,
                    d,
                    3,
                    3
            );

            g2.fillRoundRect(
                    x + size / 2 + 1,
                    y + 2,
                    d,
                    d,
                    3,
                    3
            );

            g2.fillRoundRect(
                    x + 2,
                    y + size / 2 + 1,
                    d,
                    d,
                    3,
                    3
            );

            g2.fillRoundRect(
                    x + size / 2 + 1,
                    y + size / 2 + 1,
                    d,
                    d,
                    3,
                    3
            );

            g2.dispose();
        }
    }

    // =========================================================
    // DOCTOR ICON
    // =========================================================

    static class DoctorIcon
            extends BaseIcon {

        public DoctorIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawOval(
                    x + 6,
                    y + 2,
                    size - 12,
                    size - 12
            );

            g2.drawArc(
                    x + 3,
                    y + 9,
                    size - 6,
                    size,
                    20,
                    140
            );

            g2.drawLine(
                    x + size / 2,
                    y + 11,
                    x + size / 2,
                    y + 17
            );

            g2.dispose();
        }
    }

    // =========================================================
    // STAFF ICON
    // =========================================================

    static class StaffIcon
            extends BaseIcon {

        public StaffIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawOval(
                    x + 2,
                    y + 3,
                    7,
                    7
            );

            g2.drawOval(
                    x + 11,
                    y + 3,
                    7,
                    7
            );

            g2.drawArc(
                    x,
                    y + 9,
                    11,
                    10,
                    20,
                    140
            );

            g2.drawArc(
                    x + 8,
                    y + 9,
                    11,
                    10,
                    20,
                    140
            );

            g2.dispose();
        }
    }

    // =========================================================
    // PATIENT ICON
    // =========================================================

    static class PatientIcon
            extends BaseIcon {

        public PatientIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawOval(
                    x + 6,
                    y + 2,
                    size - 12,
                    size - 12
            );

            g2.drawArc(
                    x + 3,
                    y + 10,
                    size - 6,
                    size - 4,
                    20,
                    140
            );

            g2.dispose();
        }
    }

    // =========================================================
    // PATIENT LIST ICON
    // =========================================================

    static class PatientListIcon
            extends BaseIcon {

        public PatientListIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            for (
                    int i = 0;
                    i < 3;
                    i++) {

                int yy =
                        y + 3 + i * 6;

                g2.fillOval(
                        x + 2,
                        yy,
                        3,
                        3
                );

                g2.drawLine(
                        x + 8,
                        yy + 1,
                        x + size - 2,
                        yy + 1
                );
            }

            g2.dispose();
        }
    }

    // =========================================================
    // APPOINTMENT ICON
    // =========================================================

    static class AppointmentIcon
            extends BaseIcon {

        public AppointmentIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawRoundRect(
                    x + 3,
                    y + 5,
                    size - 6,
                    size - 7,
                    3,
                    3
            );

            g2.drawLine(
                    x + 3,
                    y + 10,
                    x + size - 3,
                    y + 10
            );

            g2.drawLine(
                    x + 7,
                    y + 2,
                    x + 7,
                    y + 7
            );

            g2.drawLine(
                    x + size - 7,
                    y + 2,
                    x + size - 7,
                    y + 7
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ANALYTICS ICON
    // =========================================================

    static class AnalyticsIcon
            extends BaseIcon {

        public AnalyticsIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawLine(
                    x + 3,
                    y + size - 3,
                    x + 3,
                    y + 3
            );

            g2.drawLine(
                    x + 3,
                    y + size - 3,
                    x + size - 2,
                    y + size - 3
            );

            g2.drawLine(
                    x + 6,
                    y + 14,
                    x + 10,
                    y + 10
            );

            g2.drawLine(
                    x + 10,
                    y + 10,
                    x + 13,
                    y + 12
            );

            g2.drawLine(
                    x + 13,
                    y + 12,
                    x + 18,
                    y + 5
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ACCESS ICON
    // =========================================================

    static class AccessIcon
            extends BaseIcon {

        public AccessIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawOval(
                    x + 5,
                    y + 2,
                    size - 10,
                    size - 10
            );

            g2.drawArc(
                    x + 2,
                    y + 10,
                    size - 4,
                    size - 3,
                    20,
                    140
            );

            g2.drawLine(
                    x + 4,
                    y + size - 2,
                    x + size - 4,
                    y + size - 2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // DEPARTMENT ICON
    // =========================================================

    static class DepartmentIcon
            extends BaseIcon {

        public DepartmentIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawRect(
                    x + 3,
                    y + 5,
                    size - 6,
                    size - 7
            );

            g2.drawLine(
                    x + 3,
                    y + 9,
                    x + size - 3,
                    y + 9
            );

            g2.drawLine(
                    x + 7,
                    y + 13,
                    x + 7,
                    y + 17
            );

            g2.drawLine(
                    x + 12,
                    y + 13,
                    x + 12,
                    y + 17
            );

            g2.drawLine(
                    x + 7,
                    y + 5,
                    x + 7,
                    y + 2
            );

            g2.drawLine(
                    x + 12,
                    y + 5,
                    x + 12,
                    y + 2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // EMPLOYEE ICON
    // =========================================================

    static class EmployeeIcon
            extends BaseIcon {

        public EmployeeIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawOval(
                    x + 6,
                    y + 2,
                    8,
                    8
            );

            g2.drawArc(
                    x + 3,
                    y + 9,
                    14,
                    11,
                    20,
                    140
            );

            g2.drawLine(
                    x + 16,
                    y + 12,
                    x + 20,
                    y + 12
            );

            g2.drawLine(
                    x + 18,
                    y + 10,
                    x + 18,
                    y + 14
            );

            g2.dispose();
        }
    }

    // =========================================================
    // MEDICAL RECORD ICON
    // =========================================================

    static class MedicalRecordIcon
            extends BaseIcon {

        public MedicalRecordIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawRoundRect(
                    x + 4,
                    y + 2,
                    size - 8,
                    size - 4,
                    3,
                    3
            );

            g2.drawLine(
                    x + 8,
                    y + 7,
                    x + size - 8,
                    y + 7
            );

            g2.drawLine(
                    x + 8,
                    y + 11,
                    x + size - 8,
                    y + 11
            );

            g2.drawLine(
                    x + 8,
                    y + 15,
                    x + size - 11,
                    y + 15
            );

            g2.dispose();
        }
    }

    // =========================================================
    // RISK ICON
    // =========================================================

    static class RiskIcon
            extends BaseIcon {

        public RiskIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            Polygon triangle =
                    new Polygon();

            triangle.addPoint(
                    x + size / 2,
                    y + 2
            );

            triangle.addPoint(
                    x + size - 3,
                    y + size - 3
            );

            triangle.addPoint(
                    x + 3,
                    y + size - 3
            );

            g2.drawPolygon(
                    triangle
            );

            g2.drawLine(
                    x + size / 2,
                    y + 7,
                    x + size / 2,
                    y + 13
            );

            g2.fillOval(
                    x + size / 2 - 1,
                    y + 16,
                    2,
                    2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // LOGOUT ICON
    // =========================================================

    static class LogoutIcon
            extends BaseIcon {

        public LogoutIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawLine(
                    x + 3,
                    y + size / 2,
                    x + size - 5,
                    y + size / 2
            );

            g2.drawLine(
                    x + size - 8,
                    y + size / 2 - 3,
                    x + size - 4,
                    y + size / 2
            );

            g2.drawLine(
                    x + size - 8,
                    y + size / 2 + 3,
                    x + size - 4,
                    y + size / 2
            );

            g2.drawLine(
                    x + 3,
                    y + 4,
                    x + 3,
                    y + size - 4
            );

            g2.drawLine(
                    x + 3,
                    y + 4,
                    x + 9,
                    y + 4
            );

            g2.drawLine(
                    x + 3,
                    y + size - 4,
                    x + 9,
                    y + size - 4
            );

            g2.dispose();
        }
    }

    // =========================================================
    // ADMIN ICON
    // =========================================================

    static class AdminIcon
            extends BaseIcon {

        public AdminIcon(
                Color color,
                int size) {

            super(color, size);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawOval(
                    x + 7,
                    y + 3,
                    size - 14,
                    size - 14
            );

            g2.drawArc(
                    x + 3,
                    y + 12,
                    size - 6,
                    size - 5,
                    20,
                    140
            );

            g2.dispose();
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                AdminDashboard::new
        );
    }
}