package dashboard;

import login.Login;
import patient.PatientRegistration;
import patient.ViewPatients;
import appointment.AppointmentScheduling;
import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReceptionistDashboard extends JFrame
        implements ActionListener {

    JButton btnPatient;
    JButton btnViewPatients;
    JButton btnAppointment;
    JButton btnLogout;

    JLabel patientCard;
    JLabel appointmentCard;
    JLabel recordCard;
    JLabel riskCard;

    private final Color SIDEBAR_COLOR =
            new Color(20, 43, 70);

    private final Color BACKGROUND_COLOR =
            new Color(245, 248, 252);

    private final Color TEXT_COLOR =
            new Color(35, 45, 60);

    private final Color SUBTEXT_COLOR =
            new Color(110, 120, 135);

    private final Color BLUE =
            new Color(40, 105, 180);

    private final Color GREEN =
            new Color(40, 155, 105);

    private final Color RED =
            new Color(200, 70, 70);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ReceptionistDashboard() {

        setTitle(
                "Smart Healthcare EHR Management System"
        );

        setSize(1200, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(new BorderLayout());


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
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND_COLOR
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                BACKGROUND_COLOR
        );

        header.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        15,
                        30
                )
        );


        JLabel title =
                new JLabel(
                        "Receptionist Dashboard"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(
                TEXT_COLOR
        );

        header.add(
                title,
                BorderLayout.WEST
        );


        JLabel welcome =
                new JLabel(
                        "Welcome, Receptionist"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        welcome.setForeground(
                SUBTEXT_COLOR
        );

        header.add(
                welcome,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content =
                new JPanel();

        content.setBackground(
                BACKGROUND_COLOR
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBorder(
                new EmptyBorder(
                        5,
                        30,
                        25,
                        30
                )
        );


        // =====================================================
        // OVERVIEW
        // =====================================================

        JLabel overview =
                new JLabel("Overview");

        overview.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        overview.setForeground(
                TEXT_COLOR
        );

        overview.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(overview);

        content.add(
                Box.createVerticalStrut(12)
        );


        // =====================================================
        // STATISTICS
        // =====================================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                18,
                                0
                        )
                );

        statsPanel.setBackground(
                BACKGROUND_COLOR
        );

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        115
                )
        );

        statsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JPanel patientStat =
                createStatCard(
                        "Patients",
                        "0",
                        new UserIcon(BLUE)
                );

        patientCard =
                getValueLabel(
                        patientStat
                );


        JPanel appointmentStat =
                createStatCard(
                        "Appointments",
                        "0",
                        new CalendarIcon(GREEN)
                );

        appointmentCard =
                getValueLabel(
                        appointmentStat
                );


        JPanel recordStat =
                createStatCard(
                        "Medical Records",
                        "0",
                        new MedicalIcon(BLUE)
                );

        recordCard =
                getValueLabel(
                        recordStat
                );


        JPanel riskStat =
                createStatCard(
                        "High Risk",
                        "0",
                        new WarningIcon(RED)
                );

        riskCard =
                getValueLabel(
                        riskStat
                );


        statsPanel.add(
                patientStat
        );

        statsPanel.add(
                appointmentStat
        );

        statsPanel.add(
                recordStat
        );

        statsPanel.add(
                riskStat
        );


        content.add(
                statsPanel
        );


        content.add(
                Box.createVerticalStrut(25)
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
                        18
                )
        );

        quickTitle.setForeground(
                TEXT_COLOR
        );

        quickTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(
                quickTitle
        );

        content.add(
                Box.createVerticalStrut(12)
        );


        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        JPanel actionsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                18,
                                0
                        )
                );

        actionsPanel.setBackground(
                BACKGROUND_COLOR
        );

        actionsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        // =====================================================
        // PATIENT REGISTRATION
        // =====================================================

        btnPatient =
                createActionButton(
                        "Patient Registration",
                        new AddPatientIcon(BLUE)
                );

        btnPatient.addActionListener(this);


        // =====================================================
        // VIEW PATIENTS
        // =====================================================

        btnViewPatients =
                createActionButton(
                        "View Patients",
                        new UserIcon(GREEN)
                );

        btnViewPatients.addActionListener(this);


        // =====================================================
        // APPOINTMENT
        // =====================================================

        btnAppointment =
                createActionButton(
                        "Appointment Scheduling",
                        new CalendarIcon(BLUE)
                );

        btnAppointment.addActionListener(this);


        actionsPanel.add(
                btnPatient
        );

        actionsPanel.add(
                btnViewPatients
        );

        actionsPanel.add(
                btnAppointment
        );


        content.add(
                actionsPanel
        );


        mainPanel.add(
                new JScrollPane(
                        content,
                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                ),
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // LOAD DATA
        // =====================================================

        loadDashboardData();


        // =====================================================
        // SHOW
        // =====================================================

        setVisible(true);
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(
                        230,
                        650
                )
        );

        sidebar.setBackground(
                SIDEBAR_COLOR
        );


        // =====================================================
        // LOGO
        // =====================================================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        30,
                        20,
                        20,
                        20
                )
        );


        JLabel hospitalIcon =
                new JLabel(
                        new HospitalIcon(
                                Color.WHITE
                        )
                );

        hospitalIcon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoPanel.add(
                hospitalIcon
        );


        logoPanel.add(
                Box.createVerticalStrut(12)
        );


        JLabel logo =
                new JLabel(
                        "SMART HEALTHCARE"
                );

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        logo.setForeground(
                Color.WHITE
        );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoPanel.add(
                logo
        );


        JLabel ehr =
                new JLabel(
                        "EHR MANAGEMENT"
                );

        ehr.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        ehr.setForeground(
                new Color(
                        190,
                        205,
                        220
                )
        );

        ehr.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logoPanel.add(
                ehr
        );


        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // MENU
        // =====================================================

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
                        10,
                        12,
                        10,
                        12
                )
        );


        JButton dashboardButton =
                createSidebarButton(
                        "Dashboard",
                        new HomeIcon(
                                Color.WHITE
                        )
                );


        JButton patientButton =
                createSidebarButton(
                        "Patient Registration",
                        new AddPatientIcon(
                                Color.WHITE
                        )
                );


        JButton viewButton =
                createSidebarButton(
                        "View Patients",
                        new UserIcon(
                                Color.WHITE
                        )
                );


        JButton appointmentButton =
                createSidebarButton(
                        "Appointments",
                        new CalendarIcon(
                                Color.WHITE
                        )
                );


        JButton logoutButton =
                createSidebarButton(
                        "Logout",
                        new LogoutIcon(
                                Color.WHITE
                        )
                );


        // =====================================================
        // SIDEBAR ACTIONS
        // =====================================================

        dashboardButton.addActionListener(e -> {
            // Already on dashboard
        });


        patientButton.addActionListener(e -> {

            new PatientRegistration();

        });


        viewButton.addActionListener(e -> {

            new ViewPatients();

        });


        appointmentButton.addActionListener(e -> {

            new AppointmentScheduling();

        });


        logoutButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout Confirmation",
                            JOptionPane.YES_NO_OPTION
                    );

            if (
                    choice ==
                            JOptionPane.YES_OPTION
            ) {

                dispose();

                new Login();
            }

        });


        menu.add(
                dashboardButton
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                patientButton
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                viewButton
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                appointmentButton
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                logoutButton
        );


        sidebar.add(
                menu,
                BorderLayout.CENTER
        );


        // =====================================================
        // FOOTER
        // =====================================================

        JLabel footer =
                new JLabel(
                        "Receptionist Portal"
                );

        footer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        footer.setForeground(
                new Color(
                        180,
                        195,
                        210
                )
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
                        10,
                        0,
                        20,
                        0
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

        button.setForeground(
                Color.WHITE
        );

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
                        12,
                        15,
                        12,
                        10
                )
        );

        button.setFocusPainted(false);

        button.setContentAreaFilled(false);

        button.setOpaque(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
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
                        Color.WHITE
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        15,
                        18,
                        15,
                        18
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
                        0,
                        15,
                        0,
                        0
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


        textPanel.add(
                titleLabel
        );

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(
                valueLabel
        );


        card.add(
                textPanel,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // GET VALUE LABEL
    // =========================================================

    private JLabel getValueLabel(
            JPanel card
    ) {

        Component[] components =
                card.getComponents();


        for (
                Component component :
                components
        ) {

            if (
                    component instanceof JPanel
            ) {

                Component[] inner =
                        (
                                (JPanel)
                                        component
                        ).getComponents();


                for (
                        Component c :
                        inner
                ) {

                    if (
                            c instanceof JLabel
                    ) {

                        JLabel label =
                                (JLabel) c;


                        if (
                                label.getFont()
                                        .getSize()
                                        == 24
                        ) {

                            return label;
                        }
                    }
                }
            }
        }


        return new JLabel("0");
    }


    // =========================================================
    // ACTION BUTTON
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
                                new Color(
                                        225,
                                        230,
                                        237
                                )
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );


        button.setFocusPainted(false);


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // =========================================================
    // LOAD DASHBOARD DATA
    // =========================================================

    private void loadDashboardData() {

        int totalPatients = 0;

        int totalAppointments = 0;

        int totalRecords = 0;

        int highRisk = 0;


        try {

            Connection con =
                    DBConnection.getConnection();


            // =================================================
            // TOTAL PATIENTS
            // =================================================

            String patientSQL =
                    "SELECT COUNT(*) " +
                            "FROM patients";


            PreparedStatement psPatients =
                    con.prepareStatement(
                            patientSQL
                    );


            ResultSet rsPatients =
                    psPatients.executeQuery();


            if (
                    rsPatients.next()
            ) {

                totalPatients =
                        rsPatients.getInt(1);
            }


            rsPatients.close();

            psPatients.close();


            // =================================================
            // TOTAL APPOINTMENTS
            // =================================================

            String appointmentSQL =
                    "SELECT COUNT(*) " +
                            "FROM appointments";


            PreparedStatement psAppointments =
                    con.prepareStatement(
                            appointmentSQL
                    );


            ResultSet rsAppointments =
                    psAppointments.executeQuery();


            if (
                    rsAppointments.next()
            ) {

                totalAppointments =
                        rsAppointments.getInt(1);
            }


            rsAppointments.close();

            psAppointments.close();


            // =================================================
            // MEDICAL RECORDS
            // =================================================

            String recordSQL =
                    "SELECT COUNT(*) " +
                            "FROM medical_records";


            PreparedStatement psRecords =
                    con.prepareStatement(
                            recordSQL
                    );


            ResultSet rsRecords =
                    psRecords.executeQuery();


            if (
                    rsRecords.next()
            ) {

                totalRecords =
                        rsRecords.getInt(1);
            }


            rsRecords.close();

            psRecords.close();


            // =================================================
            // HIGH RISK
            // =================================================

            String riskSQL =
                    "SELECT COUNT(*) " +
                            "FROM health_risk " +
                            "WHERE LOWER(risk_level) = 'high'";


            PreparedStatement psRisk =
                    con.prepareStatement(
                            riskSQL
                    );


            ResultSet rsRisk =
                    psRisk.executeQuery();


            if (
                    rsRisk.next()
            ) {

                highRisk =
                        rsRisk.getInt(1);
            }


            rsRisk.close();

            psRisk.close();


            con.close();


            // =================================================
            // UPDATE CARDS
            // =================================================

            patientCard.setText(
                    String.valueOf(
                            totalPatients
                    )
            );


            appointmentCard.setText(
                    String.valueOf(
                            totalAppointments
                    )
            );


            recordCard.setText(
                    String.valueOf(
                            totalRecords
                    )
            );


            riskCard.setText(
                    String.valueOf(
                            highRisk
                    )
                    );


        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading dashboard data."
            );
        }
    }


    // =========================================================
    // BUTTON ACTIONS
    // =========================================================

    @Override
    public void actionPerformed(
            ActionEvent e
    ) {


        // =====================================================
        // PATIENT REGISTRATION
        // =====================================================

        if (
                e.getSource()
                        == btnPatient
        ) {

            new PatientRegistration();
        }


        // =====================================================
        // VIEW PATIENTS
        // =====================================================

        if (
                e.getSource()
                        == btnViewPatients
        ) {

            new ViewPatients();
        }


        // =====================================================
        // APPOINTMENT
        // =====================================================

        if (
                e.getSource()
                        == btnAppointment
        ) {

            new AppointmentScheduling();
        }


        // =====================================================
        // LOGOUT
        // =====================================================

        if (
                e.getSource()
                        == btnLogout
        ) {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout Confirmation",
                            JOptionPane.YES_NO_OPTION
                    );


            if (
                    choice ==
                            JOptionPane.YES_OPTION
            ) {

                dispose();

                new Login();
            }
        }
    }


    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    static class RoundedPanel
            extends JPanel {

        private final int radius;

        private final Color background;


        RoundedPanel(
                int radius,
                Color background
        ) {

            this.radius = radius;

            this.background =
                    background;

            setOpaque(false);
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            g2.setColor(
                    background
            );


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
    // BASE ICON
    // =========================================================

    static abstract class BaseIcon
            implements Icon {

        protected Color color;


        BaseIcon(Color color) {

            this.color = color;
        }


        @Override
        public int getIconWidth() {

            return 22;
        }


        @Override
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


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
    // ADD PATIENT ICON
    // =========================================================

    static class AddPatientIcon
            extends BaseIcon {

        AddPatientIcon(Color color) {

            super(color);
        }


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


            g2.setColor(color);


            g2.setStroke(
                    new BasicStroke(
                            2,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            g2.drawOval(
                    x + 6,
                    y + 2,
                    7,
                    7
            );


            g2.drawArc(
                    x + 3,
                    y + 10,
                    13,
                    9,
                    0,
                    180
            );


            g2.drawLine(
                    x + 17,
                    y + 7,
                    x + 17,
                    y + 17
            );


            g2.drawLine(
                    x + 12,
                    y + 12,
                    x + 22,
                    y + 12
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


            g2.setColor(color);


            g2.setStroke(
                    new BasicStroke(2)
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
    // WARNING ICON
    // =========================================================

    static class WarningIcon
            extends BaseIcon {

        WarningIcon(Color color) {

            super(color);
        }


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


            g2.setColor(color);


            g2.setStroke(
                    new BasicStroke(
                            2,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            Polygon triangle =
                    new Polygon();


            triangle.addPoint(
                    x + 11,
                    y + 2
            );


            triangle.addPoint(
                    x + 20,
                    y + 19
            );


            triangle.addPoint(
                    x + 2,
                    y + 19
            );


            g2.draw(triangle);


            g2.drawLine(
                    x + 11,
                    y + 7,
                    x + 11,
                    y + 13
            );


            g2.fillOval(
                    x + 10,
                    y + 15,
                    3,
                    3
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


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


        @Override
        public int getIconWidth() {

            return 32;
        }


        @Override
        public int getIconHeight() {

            return 32;
        }


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();


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