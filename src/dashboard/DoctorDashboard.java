package dashboard;

import database.DBConnection;
import login.Login;
import medicalrecord.MedicalRecords;
import reminder.AppointmentReminder;
import timeline.MedicalTimeline;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorDashboard extends JFrame {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 630;
    private static final int SIDEBAR_WIDTH = 235;

    private final String employeeId;

    private JLabel welcomeLabel;
    private JLabel doctorNameLabel;

    private JLabel patientCountLabel;
    private JLabel appointmentCountLabel;
    private JLabel labCountLabel;
    private JLabel recordCountLabel;

    private JButton btnDashboard;
    private JButton btnPatient;
    private JButton btnMedicalRecord;
    private JButton btnLabTest;
    private JButton btnLabReports;
    private JButton btnTimeline;
    private JButton btnReminder;
    private JButton btnAppointments;
    private JButton btnLogout;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DoctorDashboard(String employeeId) {

        this.employeeId = employeeId;

        setTitle("Smart Healthcare EHR - Doctor Dashboard");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
        loadDashboardCounts();

        // Required when opening from Login
        setVisible(true);
    }

    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 248, 252));

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        setContentPane(root);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(SIDEBAR_WIDTH, FRAME_HEIGHT)
        );

        sidebar.setBackground(
                new Color(20, 47, 78)
        );

        // -----------------------------------------------------
        // LOGO AREA
        // -----------------------------------------------------

        JPanel logoPanel = new JPanel();

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBackground(
                new Color(20, 47, 78)
        );

        logoPanel.setBorder(
                new EmptyBorder(25, 20, 20, 20)
        );

        JPanel logoRow = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );

        logoRow.setOpaque(false);

        JLabel logoIcon = new JLabel(
                new HospitalIcon(
                        30,
                        Color.WHITE
                )
        );

        JLabel logoText = new JLabel(
                "<html><b>SMART HEALTHCARE</b><br>" +
                        "<span style='font-size:10px;'>EHR MANAGEMENT SYSTEM</span></html>"
        );

        logoText.setForeground(Color.WHITE);
        logoText.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        logoRow.add(logoIcon);
        logoRow.add(logoText);

        logoPanel.add(logoRow);

        JLabel panelTitle = new JLabel(
                "DOCTOR PANEL"
        );

        panelTitle.setForeground(
                new Color(170, 195, 220)
        );

        panelTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        panelTitle.setBorder(
                new EmptyBorder(
                        18,
                        5,
                        0,
                        0
                )
        );

        logoPanel.add(panelTitle);

        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // MENU
        // -----------------------------------------------------

        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBackground(
                new Color(20, 47, 78)
        );

        menuPanel.setBorder(
                new EmptyBorder(
                        5,
                        12,
                        5,
                        12
                )
        );

        btnDashboard = createMenuButton(
                "Dashboard",
                new DashboardIcon(
                        19,
                        Color.WHITE
                )
        );

        btnPatient = createMenuButton(
                "My Patients",
                new PatientIcon(
                        19,
                        Color.WHITE
                )
        );

        btnMedicalRecord = createMenuButton(
                "Medical Records",
                new MedicalRecordIcon(
                        19,
                        Color.WHITE
                )
        );

        btnLabTest = createMenuButton(
                "Lab Test",
                new LabIcon(
                        19,
                        Color.WHITE
                )
        );

        btnLabReports = createMenuButton(
                "Lab Reports",
                new ReportIcon(
                        19,
                        Color.WHITE
                )
        );

        btnTimeline = createMenuButton(
                "Timeline",
                new TimelineIcon(
                        19,
                        Color.WHITE
                )
        );

        btnReminder = createMenuButton(
                "Reminder",
                new ReminderIcon(
                        19,
                        Color.WHITE
                )
        );

        btnAppointments = createMenuButton(
                "Requests",
                new AppointmentIcon(
                        19,
                        Color.WHITE
                )
        );

        btnLogout = createMenuButton(
                "Logout",
                new LogoutIcon(
                        19,
                        Color.WHITE
                )
        );

        menuPanel.add(btnDashboard);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnPatient);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnMedicalRecord);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnLabTest);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnLabReports);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnTimeline);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnReminder);
        menuPanel.add(Box.createVerticalStrut(5));

        menuPanel.add(btnAppointments);

        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // LOGOUT
        // -----------------------------------------------------

        JPanel logoutPanel = new JPanel(
                new BorderLayout()
        );

        logoutPanel.setBackground(
                new Color(20, 47, 78)
        );

        logoutPanel.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        20,
                        12
                )
        );

        logoutPanel.add(
                btnLogout,
                BorderLayout.CENTER
        );

        sidebar.add(
                logoutPanel,
                BorderLayout.SOUTH
        );

        // -----------------------------------------------------
        // BUTTON ACTIONS
        // -----------------------------------------------------

        btnDashboard.addActionListener(e -> {
            // Already on dashboard
        });

        btnPatient.addActionListener(e -> {
            new MyPatients(employeeId);
        });

        btnMedicalRecord.addActionListener(e -> {
            new MedicalRecords(employeeId);
        });

        btnLabTest.addActionListener(e -> {
            new DoctorLabTestRequest(employeeId);
        });

        btnLabReports.addActionListener(e -> {
            new DoctorLabReports(employeeId);
        });

        btnTimeline.addActionListener(e -> {
            new MedicalTimeline(employeeId);
        });

        btnReminder.addActionListener(e -> {
            new AppointmentReminder(employeeId);
        });

        btnAppointments.addActionListener(e -> {
            new DoctorAppointments(employeeId);
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new Login();
        });

        return sidebar;
    }

    // =========================================================
    // MAIN AREA
    // =========================================================

    private JPanel createMainArea() {

        JPanel main = new JPanel(
                new BorderLayout()
        );

        main.setBackground(
                new Color(245, 248, 252)
        );

        main.add(
                createHeader(),
                BorderLayout.NORTH
        );

        JPanel content = new JPanel(
                new BorderLayout()
        );

        content.setBackground(
                new Color(245, 248, 252)
        );

        content.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        content.add(
                createDashboardContent(),
                BorderLayout.CENTER
        );

        main.add(
                content,
                BorderLayout.CENTER
        );

        return main;
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setPreferredSize(
                new Dimension(0, 72)
        );

        header.setBackground(Color.WHITE);

        header.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        new Color(225, 230, 236)
                )
        );

        // -----------------------------------------------------
        // LEFT SIDE
        // -----------------------------------------------------

        JPanel left = new JPanel();

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.setOpaque(false);

        left.setBorder(
                new EmptyBorder(
                        14,
                        25,
                        10,
                        10
                )
        );

        welcomeLabel = new JLabel(
                "Welcome, Doctor"
        );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        welcomeLabel.setForeground(
                new Color(25, 45, 70)
        );

        JLabel subtitle = new JLabel(
                "Manage patients, appointments and medical information"
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(
                new Color(120, 130, 145)
        );

        left.add(welcomeLabel);
        left.add(
                Box.createVerticalStrut(4)
        );
        left.add(subtitle);

        header.add(
                left,
                BorderLayout.WEST
        );

        // -----------------------------------------------------
        // RIGHT SIDE
        // -----------------------------------------------------

        JPanel profile = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        15,
                        15
                )
        );

        profile.setOpaque(false);

        JPanel doctorIconPanel = new JPanel(
                new GridBagLayout()
        );

        doctorIconPanel.setPreferredSize(
                new Dimension(40, 40)
        );

        doctorIconPanel.setBackground(
                new Color(229, 240, 250)
        );

        JLabel doctorIconLabel = new JLabel(
                new DoctorIcon(
                        22,
                        new Color(
                                35,
                                105,
                                165
                        )
                )
        );

        doctorIconPanel.add(
                doctorIconLabel
        );

        profile.add(
                doctorIconPanel
        );

        JPanel doctorInfo = new JPanel();

        doctorInfo.setLayout(
                new BoxLayout(
                        doctorInfo,
                        BoxLayout.Y_AXIS
                )
        );

        doctorInfo.setOpaque(false);

        doctorNameLabel = new JLabel(
                "Doctor"
        );

        doctorNameLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        doctorNameLabel.setForeground(
                new Color(
                        35,
                        55,
                        80
                )
        );

        JLabel roleLabel = new JLabel(
                "Doctor"
        );

        roleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        roleLabel.setForeground(
                new Color(
                        120,
                        130,
                        145
                )
        );

        doctorInfo.add(
                doctorNameLabel
        );

        doctorInfo.add(
                roleLabel
        );

        profile.add(
                doctorInfo
        );

        header.add(
                profile,
                BorderLayout.EAST
        );

        String doctorName =
                getDoctorName(employeeId);

        welcomeLabel.setText(
                "Welcome, " + doctorName
        );

        doctorNameLabel.setText(
                doctorName
        );

        return header;
    }

    // =========================================================
    // DASHBOARD CONTENT
    // =========================================================

    private JPanel createDashboardContent() {

        JPanel dashboard = new JPanel(
                new BorderLayout(
                        0,
                        20
                )
        );

        dashboard.setBackground(
                new Color(
                        245,
                        248,
                        252
                )
        );

        // -----------------------------------------------------
        // BLUE WELCOME CARD
        // -----------------------------------------------------

        JPanel welcomeCard = new JPanel(
                new BorderLayout()
        );

        welcomeCard.setPreferredSize(
                new Dimension(
                        0,
                        105
                )
        );

        welcomeCard.setBackground(
                new Color(
                        35,
                        105,
                        165
                )
        );

        welcomeCard.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        JPanel welcomeText = new JPanel();

        welcomeText.setLayout(
                new BoxLayout(
                        welcomeText,
                        BoxLayout.Y_AXIS
                )
        );

        welcomeText.setOpaque(false);

        JLabel title = new JLabel(
                "Doctor Dashboard"
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        23
                )
        );

        title.setForeground(Color.WHITE);

        JLabel message = new JLabel(
                "Manage your patients and healthcare activities efficiently."
        );

        message.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        message.setForeground(
                new Color(
                        225,
                        240,
                        250
                )
        );

        welcomeText.add(title);

        welcomeText.add(
                Box.createVerticalStrut(8)
        );

        welcomeText.add(message);

        welcomeCard.add(
                welcomeText,
                BorderLayout.WEST
        );

        dashboard.add(
                welcomeCard,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // CENTER
        // -----------------------------------------------------

        JPanel center = new JPanel(
                new BorderLayout(
                        0,
                        18
                )
        );

        center.setOpaque(false);

        center.add(
                createSummaryCards(),
                BorderLayout.NORTH
        );

        center.add(
                createQuickActions(),
                BorderLayout.CENTER
        );

        dashboard.add(
                center,
                BorderLayout.CENTER
        );

        return dashboard;
    }

    // =========================================================
    // SUMMARY CARDS
    // =========================================================

    private JPanel createSummaryCards() {

        JPanel panel = new JPanel(
                new GridLayout(
                        1,
                        4,
                        15,
                        0
                )
        );

        panel.setOpaque(false);

        JPanel patients = createSummaryCard(
                "Patients",
                "0",
                new PatientIcon(
                        27,
                        new Color(
                                35,
                                105,
                                165
                        )
                )
        );

        patientCountLabel =
                findNumberLabel(patients);

        JPanel appointments = createSummaryCard(
                "Appointments",
                "0",
                new AppointmentIcon(
                        27,
                        new Color(
                                42,
                                145,
                                95
                        )
                )
        );

        appointmentCountLabel =
                findNumberLabel(appointments);

        JPanel lab = createSummaryCard(
                "Lab Tests",
                "0",
                new LabIcon(
                        27,
                        new Color(
                                220,
                                145,
                                45
                        )
                )
        );

        labCountLabel =
                findNumberLabel(lab);

        JPanel records = createSummaryCard(
                "Medical Records",
                "0",
                new MedicalRecordIcon(
                        27,
                        new Color(
                                135,
                                80,
                                170
                        )
                )
        );

        recordCountLabel =
                findNumberLabel(records);

        panel.add(patients);
        panel.add(appointments);
        panel.add(lab);
        panel.add(records);

        return panel;
    }

    // =========================================================
    // SUMMARY CARD
    // =========================================================

    private JPanel createSummaryCard(
            String title,
            String number,
            Icon icon
    ) {

        JPanel card = new JPanel(
                new BorderLayout()
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        236
                                )
                        ),
                        new EmptyBorder(
                                15,
                                17,
                                15,
                                17
                        )
                )
        );

        JPanel iconPanel = new JPanel(
                new GridBagLayout()
        );

        iconPanel.setPreferredSize(
                new Dimension(
                        52,
                        52
                )
        );

        iconPanel.setBackground(
                new Color(
                        238,
                        245,
                        251
                )
        );

        // IMPORTANT:
        // Icon is inside JLabel, so it is a Component.
        iconPanel.add(
                new JLabel(icon)
        );

        card.add(
                iconPanel,
                BorderLayout.WEST
        );

        JPanel textPanel = new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        textPanel.setBorder(
                new EmptyBorder(
                        3,
                        13,
                        3,
                        3
                )
        );

        JLabel titleLabel = new JLabel(
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
                new Color(
                        120,
                        130,
                        145
                )
        );

        JLabel numberLabel = new JLabel(
                number
        );

        numberLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        numberLabel.setForeground(
                new Color(
                        35,
                        55,
                        80
                )
        );

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(numberLabel);

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // FIND NUMBER LABEL
    // =========================================================

    private JLabel findNumberLabel(
            JPanel card
    ) {

        for (Component component :
                card.getComponents()) {

            if (component instanceof JPanel) {

                JPanel panel =
                        (JPanel) component;

                for (Component child :
                        panel.getComponents()) {

                    if (child instanceof JPanel) {

                        JPanel textPanel =
                                (JPanel) child;

                        for (Component item :
                                textPanel.getComponents()) {

                            if (item instanceof JLabel) {

                                JLabel label =
                                        (JLabel) item;

                                if (label.getFont()
                                        .getSize() >= 20) {

                                    return label;
                                }
                            }
                        }
                    }
                }
            }
        }

        return new JLabel("0");
    }

    // =========================================================
    // QUICK ACTIONS
    // =========================================================

    private JPanel createQuickActions() {

        JPanel wrapper = new JPanel(
                new BorderLayout()
        );

        wrapper.setOpaque(false);

        JLabel heading = new JLabel(
                "Quick Actions"
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        heading.setForeground(
                new Color(
                        35,
                        55,
                        80
                )
        );

        wrapper.add(
                heading,
                BorderLayout.NORTH
        );

        JPanel cards = new JPanel(
                new GridLayout(
                        2,
                        3,
                        15,
                        15
                )
        );

        cards.setOpaque(false);

        cards.setBorder(
                new EmptyBorder(
                        12,
                        0,
                        0,
                        0
                )
        );

        // 1. MY PATIENTS
        cards.add(
                createActionCard(
                        "My Patients",
                        "View assigned patients",
                        new PatientIcon(
                                25,
                                new Color(
                                        35,
                                        105,
                                        165
                                )
                        ),
                        btnPatient
                )
        );

        // 2. MEDICAL RECORDS
        cards.add(
                createActionCard(
                        "Medical Records",
                        "View and manage records",
                        new MedicalRecordIcon(
                                25,
                                new Color(
                                        135,
                                        80,
                                        170
                                )
                        ),
                        btnMedicalRecord
                )
        );

        // 3. REQUEST LAB TEST
        cards.add(
                createActionCard(
                        "Request Lab Test",
                        "Send a laboratory request",
                        new LabIcon(
                                25,
                                new Color(
                                        220,
                                        145,
                                        45
                                )
                        ),
                        btnLabTest
                )
        );

        // 4. LAB REPORTS
        cards.add(
                createActionCard(
                        "Lab Reports",
                        "View completed reports",
                        new ReportIcon(
                                25,
                                new Color(
                                        42,
                                        145,
                                        95
                                )
                        ),
                        btnLabReports
                )
        );

        // 5. MEDICAL TIMELINE
        cards.add(
                createActionCard(
                        "Medical Timeline",
                        "View patient history",
                        new TimelineIcon(
                                25,
                                new Color(
                                        60,
                                        120,
                                        180
                                )
                        ),
                        btnTimeline
                )
        );

        // 6. APPOINTMENTS
        cards.add(
                createActionCard(
                        "Appointments",
                        "Manage appointment requests",
                        new AppointmentIcon(
                                25,
                                new Color(
                                        40,
                                        145,
                                        95
                                )
                        ),
                        btnAppointments
                )
        );

        wrapper.add(
                cards,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =========================================================
    // ACTION CARD
    // =========================================================

    private JPanel createActionCard(
            String title,
            String description,
            Icon icon,
            JButton originalButton
    ) {

        JPanel card = new JPanel(
                new BorderLayout()
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        225,
                                        230,
                                        236
                                )
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JPanel iconPanel = new JPanel(
                new GridBagLayout()
        );

        iconPanel.setPreferredSize(
                new Dimension(
                        52,
                        52
                )
        );

        iconPanel.setBackground(
                new Color(
                        238,
                        245,
                        251
                )
        );

        // IMPORTANT:
        // Icon wrapped in JLabel.
        iconPanel.add(
                new JLabel(icon)
        );

        card.add(
                iconPanel,
                BorderLayout.WEST
        );

        JPanel textPanel = new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        textPanel.setBorder(
                new EmptyBorder(
                        3,
                        13,
                        3,
                        3
                )
        );

        JLabel titleLabel = new JLabel(
                title
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        titleLabel.setForeground(
                new Color(
                        35,
                        55,
                        80
                )
        );

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        descriptionLabel.setForeground(
                new Color(
                        125,
                        135,
                        150
                )
        );

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(descriptionLabel);

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // CARD CLICK
        // -----------------------------------------------------

        MouseAdapter adapter =
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {
                        originalButton.doClick();
                    }

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                new Color(
                                                        35,
                                                        105,
                                                        165
                                                )
                                        ),
                                        new EmptyBorder(
                                                15,
                                                15,
                                                15,
                                                15
                                        )
                                )
                        );

                        card.setCursor(
                                new Cursor(
                                        Cursor.HAND_CURSOR
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createCompoundBorder(
                                        BorderFactory.createLineBorder(
                                                new Color(
                                                        225,
                                                        230,
                                                        236
                                                )
                                        ),
                                        new EmptyBorder(
                                                15,
                                                15,
                                                15,
                                                15
                                        )
                                )
                        );

                        card.setCursor(
                                new Cursor(
                                        Cursor.DEFAULT_CURSOR
                                )
                        );
                    }
                };

        addMouseListenerRecursively(
                card,
                adapter
        );

        return card;
    }

    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JButton createMenuButton(
            String text,
            Icon icon
    ) {

        JButton button =
                new JButton(text);

        button.setIcon(icon);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setIconTextGap(13);

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        button.setForeground(
                new Color(
                        235,
                        242,
                        250
                )
        );

        button.setBackground(
                new Color(
                        20,
                        47,
                        78
                )
        );

        button.setBorder(
                new EmptyBorder(
                        11,
                        14,
                        11,
                        10
                )
        );

        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        35,
                                        78,
                                        118
                                )
                        );

                        button.setCursor(
                                new Cursor(
                                        Cursor.HAND_CURSOR
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                new Color(
                                        20,
                                        47,
                                        78
                                )
                        );

                        button.setCursor(
                                new Cursor(
                                        Cursor.DEFAULT_CURSOR
                                )
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // DATABASE COUNTS
    // =========================================================

    private void loadDashboardCounts() {

        int doctorId = getDoctorId();

        if (doctorId == -1) {
            return;
        }

        int patients = 0;
        int appointments = 0;
        int labTests = 0;
        int records = 0;

        // -----------------------------------------------------
        // PATIENTS
        // -----------------------------------------------------

        String patientSql =
                "SELECT COUNT(*) " +
                        "FROM patients " +
                        "WHERE doctor_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                patientSql
                        )
        ) {

            ps.setInt(
                    1,
                    doctorId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                patients =
                        rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // -----------------------------------------------------
        // APPOINTMENTS
        // -----------------------------------------------------

        String appointmentSql =
                "SELECT COUNT(*) " +
                        "FROM appointments a " +
                        "INNER JOIN users u " +
                        "ON a.doctor_name=u.full_name " +
                        "WHERE u.user_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                appointmentSql
                        )
        ) {

            ps.setInt(
                    1,
                    doctorId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                appointments =
                        rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // -----------------------------------------------------
        // LAB TESTS
        // -----------------------------------------------------

        String labSql =
                "SELECT COUNT(*) " +
                        "FROM lab_tests " +
                        "WHERE doctor_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                labSql
                        )
        ) {

            ps.setInt(
                    1,
                    doctorId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                labTests =
                        rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // -----------------------------------------------------
        // MEDICAL RECORDS
        // -----------------------------------------------------

        String recordSql =
                "SELECT COUNT(*) " +
                        "FROM medical_records " +
                        "WHERE doctor_id=?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                recordSql
                        )
        ) {

            ps.setInt(
                    1,
                    doctorId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {
                records =
                        rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        patientCountLabel.setText(
                String.valueOf(patients)
        );

        appointmentCountLabel.setText(
                String.valueOf(appointments)
        );

        labCountLabel.setText(
                String.valueOf(labTests)
        );

        recordCountLabel.setText(
                String.valueOf(records)
        );
    }

    // =========================================================
    // GET DOCTOR ID
    // =========================================================

    private int getDoctorId() {

        String sql =
                "SELECT user_id " +
                        "FROM users " +
                        "WHERE employee_id=? " +
                        "AND role='Doctor' " +
                        "AND status='ACTIVE'";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    employeeId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(
                        "user_id"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // =========================================================
    // GET DOCTOR NAME
    // =========================================================

    private String getDoctorName(
            String employeeId
    ) {

        String sql =
                "SELECT full_name " +
                        "FROM users " +
                        "WHERE employee_id=? " +
                        "AND role='Doctor' " +
                        "AND status='ACTIVE'";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    employeeId
            );

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                String name =
                        rs.getString(
                                "full_name"
                        );

                if (name != null &&
                        !name.trim().isEmpty()) {

                    return name;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Doctor";
    }

    // =========================================================
    // ADD MOUSE LISTENER TO CARD + CHILDREN
    // =========================================================

    private void addMouseListenerRecursively(
            Component component,
            MouseAdapter adapter
    ) {

        component.addMouseListener(
                adapter
        );

        if (component instanceof Container) {

            Component[] children =
                    ((Container) component)
                            .getComponents();

            for (Component child :
                    children) {

                addMouseListenerRecursively(
                        child,
                        adapter
                );
            }
        }
    }

    // =========================================================
    // BASE ICON
    // =========================================================

    private abstract static class BaseIcon
            implements Icon {

        protected final int size;
        protected final Color color;

        BaseIcon(
                int size,
                Color color
        ) {

            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        protected Graphics2D getGraphics(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

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

    private static class HospitalIcon
            extends BaseIcon {

        HospitalIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawRoundRect(
                    x + 5,
                    y + 9,
                    s - 10,
                    s - 12,
                    3,
                    3
            );

            g2.drawLine(
                    x + s / 2,
                    y + 2,
                    x + s / 2,
                    y + 9
            );

            g2.drawLine(
                    x + s / 2 - 4,
                    y + 5,
                    x + s / 2 + 4,
                    y + 5
            );

            g2.drawLine(
                    x + s / 2,
                    y + 15,
                    x + s / 2,
                    y + s - 5
            );

            g2.drawLine(
                    x + 9,
                    y + s / 2,
                    x + s - 9,
                    y + s / 2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // DASHBOARD ICON
    // =========================================================

    private static class DashboardIcon
            extends BaseIcon {

        DashboardIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawRoundRect(
                    x + 2,
                    y + 2,
                    s - 4,
                    s - 4,
                    4,
                    4
            );

            g2.drawLine(
                    x + s / 2,
                    y + 3,
                    x + s / 2,
                    y + s - 3
            );

            g2.drawLine(
                    x + 3,
                    y + s / 2,
                    x + s - 3,
                    y + s / 2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // PATIENT ICON
    // =========================================================

    private static class PatientIcon
            extends BaseIcon {

        PatientIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawOval(
                    x + s / 3,
                    y + 2,
                    s / 3,
                    s / 3
            );

            g2.drawArc(
                    x + s / 5,
                    y + s / 2,
                    s * 3 / 5,
                    s / 2,
                    0,
                    180
            );

            g2.dispose();
        }
    }

    // =========================================================
    // DOCTOR ICON
    // =========================================================

    private static class DoctorIcon
            extends BaseIcon {

        DoctorIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawOval(
                    x + s / 3,
                    y + 2,
                    s / 3,
                    s / 3
            );

            g2.drawArc(
                    x + s / 5,
                    y + s / 2,
                    s * 3 / 5,
                    s / 2,
                    0,
                    180
            );

            g2.drawLine(
                    x + s / 2,
                    y + s / 2,
                    x + s / 2,
                    y + s * 4 / 5
            );

            g2.drawLine(
                    x + s / 2,
                    y + s * 4 / 5,
                    x + s * 2 / 3,
                    y + s / 2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // MEDICAL RECORD ICON
    // =========================================================

    private static class MedicalRecordIcon
            extends BaseIcon {

        MedicalRecordIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawRoundRect(
                    x + 3,
                    y + 2,
                    s - 6,
                    s - 4,
                    3,
                    3
            );

            g2.drawLine(
                    x + 7,
                    y + 7,
                    x + s - 7,
                    y + 7
            );

            g2.drawLine(
                    x + 7,
                    y + 11,
                    x + s - 7,
                    y + 11
            );

            g2.drawLine(
                    x + 7,
                    y + 15,
                    x + s - 10,
                    y + 15
            );

            g2.dispose();
        }
    }

    // =========================================================
    // LAB ICON
    // =========================================================

    private static class LabIcon
            extends BaseIcon {

        LabIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawLine(
                    x + s / 3,
                    y + 2,
                    x + s * 2 / 3,
                    y + 2
            );

            g2.drawLine(
                    x + s * 2 / 5,
                    y + 2,
                    x + s * 2 / 5,
                    y + s / 2
            );

            g2.drawLine(
                    x + s * 3 / 5,
                    y + 2,
                    x + s * 3 / 5,
                    y + s / 2
            );

            g2.drawLine(
                    x + s * 2 / 5,
                    y + s / 2,
                    x + s / 5,
                    y + s - 3
            );

            g2.drawLine(
                    x + s * 3 / 5,
                    y + s / 2,
                    x + s * 4 / 5,
                    y + s - 3
            );

            g2.drawLine(
                    x + s / 5,
                    y + s - 3,
                    x + s * 4 / 5,
                    y + s - 3
            );

            g2.dispose();
        }
    }

    // =========================================================
    // REPORT ICON
    // =========================================================

    private static class ReportIcon
            extends BaseIcon {

        ReportIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawRoundRect(
                    x + 3,
                    y + 2,
                    s - 6,
                    s - 4,
                    3,
                    3
            );

            g2.drawLine(
                    x + 7,
                    y + s - 7,
                    x + 7,
                    y + s / 2
            );

            g2.drawLine(
                    x + s / 2,
                    y + s - 7,
                    x + s / 2,
                    y + s / 3
            );

            g2.drawLine(
                    x + s - 7,
                    y + s - 7,
                    x + s - 7,
                    y + s / 4
            );

            g2.dispose();
        }
    }

    // =========================================================
    // TIMELINE ICON
    // =========================================================

    private static class TimelineIcon
            extends BaseIcon {

        TimelineIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawLine(
                    x + 3,
                    y + s / 2,
                    x + s - 3,
                    y + s / 2
            );

            g2.fillOval(
                    x + 1,
                    y + s / 2 - 3,
                    6,
                    6
            );

            g2.fillOval(
                    x + s / 2 - 3,
                    y + s / 2 - 3,
                    6,
                    6
            );

            g2.fillOval(
                    x + s - 7,
                    y + s / 2 - 3,
                    6,
                    6
            );

            g2.dispose();
        }
    }

    // =========================================================
    // REMINDER ICON
    // =========================================================

    private static class ReminderIcon
            extends BaseIcon {

        ReminderIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawArc(
                    x + 4,
                    y + 3,
                    s - 8,
                    s - 6,
                    20,
                    140
            );

            g2.drawLine(
                    x + 5,
                    y + s / 2,
                    x + 5,
                    y + s - 5
            );

            g2.drawLine(
                    x + s - 5,
                    y + s / 2,
                    x + s - 5,
                    y + s - 5
            );

            g2.drawLine(
                    x + 5,
                    y + s - 5,
                    x + s - 5,
                    y + s - 5
            );

            g2.fillOval(
                    x + s / 2 - 2,
                    y + 1,
                    4,
                    4
            );

            g2.dispose();
        }
    }

    // =========================================================
    // APPOINTMENT ICON
    // =========================================================

    private static class AppointmentIcon
            extends BaseIcon {

        AppointmentIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawRoundRect(
                    x + 3,
                    y + 4,
                    s - 6,
                    s - 6,
                    3,
                    3
            );

            g2.drawLine(
                    x + 3,
                    y + 9,
                    x + s - 3,
                    y + 9
            );

            g2.drawLine(
                    x + 7,
                    y + 2,
                    x + 7,
                    y + 6
            );

            g2.drawLine(
                    x + s - 7,
                    y + 2,
                    x + s - 7,
                    y + 6
            );

            g2.drawLine(
                    x + s / 2,
                    y + 12,
                    x + s / 2,
                    y + s - 6
            );

            g2.drawLine(
                    x + 7,
                    y + s / 2,
                    x + s - 7,
                    y + s / 2
            );

            g2.dispose();
        }
    }

    // =========================================================
    // LOGOUT ICON
    // =========================================================

    private static class LogoutIcon
            extends BaseIcon {

        LogoutIcon(
                int size,
                Color color
        ) {
            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    getGraphics(g);

            int s = size;

            g2.drawRoundRect(
                    x + 2,
                    y + 3,
                    s / 2,
                    s - 6,
                    3,
                    3
            );

            g2.drawLine(
                    x + s / 2,
                    y + s / 2,
                    x + s - 3,
                    y + s / 2
            );

            g2.drawLine(
                    x + s - 3,
                    y + s / 2,
                    x + s - 8,
                    y + s / 2 - 5
            );

            g2.drawLine(
                    x + s - 3,
                    y + s / 2,
                    x + s - 8,
                    y + s / 2 + 5
            );

            g2.dispose();
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new DoctorDashboard("DOC001")
        );
    }
}