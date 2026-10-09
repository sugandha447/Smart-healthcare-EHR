package medicalrecord;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MedicalRecords extends JFrame implements ActionListener {

    // =========================
    // COLORS
    // =========================
    private final Color NAVY = new Color(20, 45, 78);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color GREEN = new Color(22, 163, 74);
    private final Color RED = new Color(220, 38, 38);
    private final Color BORDER = new Color(210, 220, 232);
    private final Color TEXT = new Color(35, 45, 60);
    private final Color MUTED = new Color(100, 116, 139);

    // =========================
    // FIELDS
    // =========================
    private JTextField txtPatientId;
    private JTextField txtPatientName;
    private JTextField txtVisitDate;
    private JTextField txtFollowUpDate;

    private JTextArea txtDiagnosis;
    private JTextArea txtPrescription;
    private JTextArea txtTreatment;
    private JTextArea txtRecommendedTests;

    private JButton btnSave;
    private JButton btnClear;
    private JButton btnHistory;

    private String employeeId;

    private final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // =========================
    // CONSTRUCTOR
    // =========================
    public MedicalRecords(String employeeId) {

        this.employeeId = employeeId;

        setTitle("Medical Records");
        setSize(1200, 630);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        createUI();

        // Visit date = today's date
        txtVisitDate.setText(
                LocalDate.now().format(DATE_FORMAT)
        );

        setVisible(true);
    }

    // =========================
    // CREATE UI
    // =========================
    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 248, 252));

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setPreferredSize(new Dimension(1200, 80));
        header.setBorder(
                new EmptyBorder(12, 25, 12, 25)
        );

        JPanel titlePanel =
                new JPanel(new BorderLayout(12, 0));

        titlePanel.setOpaque(false);

        JLabel icon = new JLabel("✚");
        icon.setForeground(Color.WHITE);
        icon.setFont(
                new Font("Segoe UI", Font.BOLD, 30)
        );

        titlePanel.add(icon, BorderLayout.WEST);

        JPanel titleText = new JPanel();
        titleText.setOpaque(false);

        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("Medical Records");

        title.setForeground(Color.WHITE);
        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        23
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Create and manage patient medical records"
                );

        subtitle.setForeground(
                new Color(190, 210, 235)
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        titleText.add(title);
        titleText.add(
                Box.createVerticalStrut(3)
        );
        titleText.add(subtitle);

        titlePanel.add(
                titleText,
                BorderLayout.CENTER
        );

        JLabel doctorLabel =
                new JLabel(
                        "Doctor: " + employeeId
                );

        doctorLabel.setForeground(Color.WHITE);
        doctorLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                doctorLabel,
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
                new JPanel(
                        new BorderLayout(15, 15)
                );

        content.setBackground(
                new Color(245, 248, 252)
        );

        content.setBorder(
                new EmptyBorder(
                        18, 20, 10, 20
                )
        );

        // =====================================================
        // LEFT PANEL
        // =====================================================

        JPanel leftPanel = new JPanel();

        leftPanel.setOpaque(false);

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // PATIENT INFORMATION CARD
        // =====================================================

        JPanel patientCard =
                createCard();

        JLabel patientTitle =
                sectionTitle(
                        "Patient Information"
                );

        patientCard.add(
                patientTitle,
                BorderLayout.NORTH
        );

        JPanel patientGrid =
                new JPanel(
                        new GridLayout(
                                2, 2, 12, 10
                        )
                );

        patientGrid.setOpaque(false);

        txtPatientId =
                createTextField();

        txtPatientName =
                createTextField();

        // Doctor cannot manually change patient name
        txtPatientName.setEditable(false);

        txtPatientName.setBackground(
                new Color(248, 250, 252)
        );

        patientGrid.add(
                createField(
                        "Patient ID",
                        txtPatientId
                )
        );

        patientGrid.add(
                createField(
                        "Patient Name",
                        txtPatientName
                )
        );

        // =====================================================
        // IMPORTANT:
        // PATIENT ID -> PATIENT NAME
        // =====================================================

        txtPatientId.addActionListener(
                e -> loadPatientName()
        );

        txtPatientId.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        loadPatientName();
                    }
                }
        );

        patientCard.add(
                patientGrid,
                BorderLayout.CENTER
        );

        leftPanel.add(patientCard);

        leftPanel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // CLINICAL INFORMATION
        // =====================================================

        JPanel clinicalCard =
                createCard();

        JLabel clinicalTitle =
                sectionTitle(
                        "Clinical Information"
                );

        clinicalCard.add(
                clinicalTitle,
                BorderLayout.NORTH
        );

        JPanel clinicalContent =
                new JPanel();

        clinicalContent.setOpaque(false);

        clinicalContent.setLayout(
                new BoxLayout(
                        clinicalContent,
                        BoxLayout.Y_AXIS
                )
        );

        txtDiagnosis =
                createTextArea();

        txtPrescription =
                createTextArea();

        txtTreatment =
                createTextArea();

        clinicalContent.add(
                createAreaField(
                        "Diagnosis",
                        txtDiagnosis,
                        60
                )
        );

        clinicalContent.add(
                Box.createVerticalStrut(8)
        );

        clinicalContent.add(
                createAreaField(
                        "Prescription",
                        txtPrescription,
                        60
                )
        );

        clinicalContent.add(
                Box.createVerticalStrut(8)
        );

        clinicalContent.add(
                createAreaField(
                        "Treatment",
                        txtTreatment,
                        60
                )
        );

        clinicalCard.add(
                clinicalContent,
                BorderLayout.CENTER
        );

        leftPanel.add(clinicalCard);

        // =====================================================
        // RIGHT PANEL
        // =====================================================

        JPanel rightPanel =
                new JPanel();

        rightPanel.setOpaque(false);

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // DATE CARD
        // =====================================================

        JPanel dateCard =
                createCard();

        JLabel dateTitle =
                sectionTitle(
                        "Visit & Follow-up"
                );

        dateCard.add(
                dateTitle,
                BorderLayout.NORTH
        );

        JPanel dateContent =
                new JPanel();

        dateContent.setOpaque(false);

        dateContent.setLayout(
                new BoxLayout(
                        dateContent,
                        BoxLayout.Y_AXIS
                )
        );

        // VISIT DATE

        txtVisitDate =
                createTextField();

        txtVisitDate.setEditable(false);

        txtVisitDate.setBackground(
                new Color(248, 250, 252)
        );

        JPanel visitPanel =
                createField(
                        "Visit Date",
                        txtVisitDate
                );

        JLabel visitInfo =
                new JLabel(
                        "Automatically set to today's date"
                );

        visitInfo.setForeground(MUTED);

        visitInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        visitPanel.add(
                visitInfo,
                BorderLayout.SOUTH
        );

        // FOLLOW-UP DATE

        txtFollowUpDate =
                createTextField();

        JPanel followPanel =
                createField(
                        "Follow-up Date",
                        txtFollowUpDate
                );

        JLabel followInfo =
                new JLabel(
                        "Doctor enters the required date (YYYY-MM-DD)"
                );

        followInfo.setForeground(MUTED);

        followInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        followPanel.add(
                followInfo,
                BorderLayout.SOUTH
        );

        dateContent.add(visitPanel);

        dateContent.add(
                Box.createVerticalStrut(15)
        );

        dateContent.add(followPanel);

        dateCard.add(
                dateContent,
                BorderLayout.CENTER
        );

        rightPanel.add(dateCard);

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // RECOMMENDED TESTS
        // =====================================================

        JPanel testsCard =
                createCard();

        JLabel testsTitle =
                sectionTitle(
                        "Recommended Tests"
                );

        testsCard.add(
                testsTitle,
                BorderLayout.NORTH
        );

        txtRecommendedTests =
                new JTextArea();

        txtRecommendedTests.setEditable(false);

        txtRecommendedTests.setLineWrap(true);

        txtRecommendedTests.setWrapStyleWord(true);

        txtRecommendedTests.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        txtRecommendedTests.setForeground(TEXT);

        txtRecommendedTests.setBackground(
                new Color(248, 250, 252)
        );

        txtRecommendedTests.setBorder(
                new EmptyBorder(
                        10, 10, 10, 10
                )
        );

        txtRecommendedTests.setText(
                "Recommended tests will appear here based on diagnosis."
        );

        testsCard.add(
                new JScrollPane(
                        txtRecommendedTests
                ),
                BorderLayout.CENTER
        );

        rightPanel.add(testsCard);

        rightPanel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // ACCESS CONTROL
        // =====================================================

        JPanel accessCard =
                createCard();

        JLabel accessTitle =
                sectionTitle(
                        "Access Control"
                );

        accessCard.add(
                accessTitle,
                BorderLayout.NORTH
        );

        JLabel accessText =
                new JLabel(
                        "<html><div style='width:350px'>" +
                                "Medical records can only be created " +
                                "for patients assigned to the logged-in doctor." +
                                "</div></html>"
                );

        accessText.setForeground(MUTED);

        accessText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        accessCard.add(
                accessText,
                BorderLayout.CENTER
        );

        rightPanel.add(accessCard);

        // =====================================================
        // ADD PANELS
        // =====================================================

        content.add(
                leftPanel,
                BorderLayout.CENTER
        );

        content.add(
                rightPanel,
                BorderLayout.EAST
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BUTTON BAR
        // =====================================================

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                12
                        )
                );

        bottom.setBackground(Color.WHITE);

        bottom.setBorder(
                BorderFactory.createMatteBorder(
                        1, 0, 0, 0,
                        BORDER
                )
        );

        btnClear =
                createButton(
                        "Clear",
                        new Color(100, 116, 139)
                );

        btnHistory =
                createButton(
                        "View History",
                        new Color(71, 85, 105)
                );

        btnSave =
                createButton(
                        "Save Medical Record",
                        GREEN
                );

        btnClear.addActionListener(this);
        btnHistory.addActionListener(this);
        btnSave.addActionListener(this);

        bottom.add(btnClear);
        bottom.add(btnHistory);
        bottom.add(btnSave);

        mainPanel.add(
                bottom,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    // =====================================================
    // CREATE CARD
    // =====================================================

    private JPanel createCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10, 12
                        )
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        return card;
    }

    // =====================================================
    // SECTION TITLE
    // =====================================================

    private JLabel sectionTitle(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        label.setForeground(NAVY);

        return label;
    }

    // =====================================================
    // TEXT FIELD
    // =====================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        field.setForeground(TEXT);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                8, 10, 8, 10
                        )
                )
        );

        return field;
    }

    // =====================================================
    // TEXT AREA
    // =====================================================

    private JTextArea createTextArea() {

        JTextArea area =
                new JTextArea();

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        area.setForeground(TEXT);

        area.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                6, 8, 6, 8
                        )
                )
        );

        return area;
    }

    // =====================================================
    // FIELD PANEL
    // =====================================================

    private JPanel createField(
            String labelText,
            JComponent component
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0, 5
                        )
                );

        panel.setOpaque(false);

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(TEXT);

        panel.add(
                label,
                BorderLayout.NORTH
        );

        panel.add(
                component,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =====================================================
    // AREA FIELD
    // =====================================================

    private JPanel createAreaField(
            String labelText,
            JTextArea area,
            int height
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0, 4
                        )
                );

        panel.setOpaque(false);

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(TEXT);

        JScrollPane scrollPane =
                new JScrollPane(area);

        scrollPane.setPreferredSize(
                new Dimension(
                        600,
                        height
                )
        );

        panel.add(
                label,
                BorderLayout.NORTH
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =====================================================
    // BUTTON
    // =====================================================

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(color);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10, 20, 10, 20
                )
        );

        return button;
    }

    // =====================================================
    // ACTION LISTENER
    // =====================================================

    @Override
    public void actionPerformed(
            ActionEvent e
    ) {

        if (e.getSource() == btnSave) {

            saveRecord();

        } else if (e.getSource() == btnClear) {

            clearFields();

        } else if (e.getSource() == btnHistory) {

            viewHistory();
        }
    }

    // =====================================================
    // LOAD PATIENT NAME
    // =====================================================

    private void loadPatientName() {

        String idText =
                txtPatientId.getText().trim();

        // Empty ID
        if (idText.isEmpty()) {

            txtPatientName.setText("");

            return;
        }

        int patientId;

        try {

            patientId =
                    Integer.parseInt(idText);

        } catch (NumberFormatException ex) {

            txtPatientName.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Patient ID must contain numbers only.",
                    "Invalid Patient ID",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // DATABASE QUERY
        // =================================================

        String query =
                "SELECT patient_name " +
                        "FROM patients " +
                        "WHERE patient_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(query)
        ) {

            ps.setInt(
                    1,
                    patientId
            );

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                if (rs.next()) {

                    String patientName =
                            rs.getString(
                                    "patient_name"
                            );

                    if (patientName != null) {

                        txtPatientName.setText(
                                patientName
                        );

                    } else {

                        txtPatientName.setText("");
                    }

                } else {

                    txtPatientName.setText("");

                    JOptionPane.showMessageDialog(
                            this,
                            "Patient ID " +
                                    patientId +
                                    " does not exist.",
                            "Patient Not Found",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (SQLException ex) {

            txtPatientName.setText("");

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patient name.\n\n" +
                            ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // SAVE RECORD
    // =====================================================

    private void saveRecord() {

        String patientIdText =
                txtPatientId.getText().trim();

        String diagnosis =
                txtDiagnosis.getText().trim();

        String prescription =
                txtPrescription.getText().trim();

        String treatment =
                txtTreatment.getText().trim();

        String followUpText =
                txtFollowUpDate.getText().trim();

        // =================================================
        // VALIDATION
        // =================================================

        if (patientIdText.isEmpty()
                || diagnosis.isEmpty()
                || prescription.isEmpty()
                || treatment.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int patientId;

        try {

            patientId =
                    Integer.parseInt(
                            patientIdText
                    );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient ID must be a valid number.",
                    "Invalid Patient ID",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =================================================
        // VISIT DATE = TODAY
        // =================================================

        LocalDate visitDate =
                LocalDate.now();

        // =================================================
        // FOLLOW-UP DATE = DOCTOR ENTERS
        // =================================================

        LocalDate followUpDate = null;

        if (!followUpText.isEmpty()) {

            try {

                followUpDate =
                        LocalDate.parse(
                                followUpText,
                                DATE_FORMAT
                        );

            } catch (DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Follow-up Date must be in YYYY-MM-DD format.",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (followUpDate.isBefore(
                    visitDate
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Follow-up Date cannot be before Visit Date.",
                        "Invalid Follow-up Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        // =================================================
        // LOAD PATIENT NAME AGAIN BEFORE SAVE
        // =================================================

        loadPatientName();

        if (txtPatientName.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Patient ID.",
                    "Invalid Patient",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con =
                    DBConnection.getConnection();

            // =================================================
            // GET DOCTOR
            // =================================================

            String doctorQuery =
                    "SELECT user_id, full_name " +
                            "FROM users " +
                            "WHERE employee_id=? " +
                            "AND role='Doctor' " +
                            "AND status='ACTIVE'";

            ps =
                    con.prepareStatement(
                            doctorQuery
                    );

            ps.setString(
                    1,
                    employeeId
            );

            rs =
                    ps.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Logged-in doctor not found.",
                        "Access Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int doctorId =
                    rs.getInt("user_id");

            rs.close();
            ps.close();

            // =================================================
            // VERIFY PATIENT BELONGS TO DOCTOR
            // =================================================

            String patientQuery =
                    "SELECT patient_id, patient_name " +
                            "FROM patients " +
                            "WHERE patient_id=? " +
                            "AND doctor_id=?";

            ps =
                    con.prepareStatement(
                            patientQuery
                    );

            ps.setInt(
                    1,
                    patientId
            );

            ps.setInt(
                    2,
                    doctorId
            );

            rs =
                    ps.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "This patient is not assigned to you.",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String patientName =
                    rs.getString(
                            "patient_name"
                    );

            rs.close();
            ps.close();

            // =================================================
            // RECOMMENDED TESTS
            // =================================================

            String tests =
                    getRecommendedTests(
                            diagnosis
                    );

            txtRecommendedTests.setText(
                    tests
            );

            // =================================================
            // INSERT MEDICAL RECORD
            // =================================================

            String insertQuery =
                    "INSERT INTO medical_records " +
                            "(patient_id, doctor_id, diagnosis, " +
                            "prescription, treatment, visit_date, " +
                            "follow_up_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps =
                    con.prepareStatement(
                            insertQuery
                    );

            ps.setInt(
                    1,
                    patientId
            );

            ps.setInt(
                    2,
                    doctorId
            );

            ps.setString(
                    3,
                    diagnosis
            );

            ps.setString(
                    4,
                    prescription
            );

            ps.setString(
                    5,
                    treatment
            );

            ps.setDate(
                    6,
                    Date.valueOf(
                            visitDate
                    )
            );

            if (followUpDate != null) {

                ps.setDate(
                        7,
                        Date.valueOf(
                                followUpDate
                        )
                );

            } else {

                ps.setNull(
                        7,
                        Types.DATE
                );
            }

            ps.executeUpdate();

            // =================================================
            // CRITICAL DIAGNOSIS
            // =================================================

            String lowerDiagnosis =
                    diagnosis.toLowerCase();

            if (lowerDiagnosis.contains(
                    "heart attack"
            )
                    || lowerDiagnosis.contains(
                    "stroke"
            )
                    || lowerDiagnosis.contains(
                    "cancer"
            )
                    || lowerDiagnosis.contains(
                    "icu"
            )
                    || lowerDiagnosis.contains(
                    "high risk"
            )) {

                JOptionPane.showMessageDialog(
                        this,
                        "Critical diagnosis detected.\n" +
                                "Please ensure appropriate medical attention " +
                                "and follow-up care.",
                        "Clinical Alert",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            // =================================================
            // SUCCESS
            // =================================================

            String message =
                    "Medical record saved successfully.\n\n" +
                            "Patient: " +
                            patientName +
                            "\nVisit Date: " +
                            visitDate;

            if (followUpDate != null) {

                message +=
                        "\nFollow-up Date: " +
                                followUpDate;

            } else {

                message +=
                        "\nFollow-up Date: Not specified";
            }

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ignored) {
            }

            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ignored) {
            }

            try {
                if (con != null)
                    con.close();
            } catch (SQLException ignored) {
            }
        }
    }

    // =====================================================
    // RECOMMENDED TESTS
    // =====================================================

    private String getRecommendedTests(
            String diagnosis
    ) {

        String lower =
                diagnosis.toLowerCase();

        if (lower.contains("diabetes")) {

            return "Recommended Tests:\n" +
                    "• HbA1c\n" +
                    "• Blood Sugar\n" +
                    "• Urine Test";
        }

        if (lower.contains(
                "heart disease"
        )) {

            return "Recommended Tests:\n" +
                    "• ECG\n" +
                    "• Echo\n" +
                    "• Lipid Profile";
        }

        if (lower.contains("fever")) {

            return "Recommended Tests:\n" +
                    "• CBC\n" +
                    "• Malaria Test\n" +
                    "• Dengue Test";
        }

        return "No Special Test Required";
    }

    // =====================================================
    // CLEAR
    // =====================================================

    private void clearFields() {

        txtPatientId.setText("");

        txtPatientName.setText("");

        txtDiagnosis.setText("");

        txtPrescription.setText("");

        txtTreatment.setText("");

        txtVisitDate.setText(
                LocalDate.now()
                        .format(DATE_FORMAT)
        );

        // Follow-up date is manually entered
        txtFollowUpDate.setText("");

        txtRecommendedTests.setText(
                "Recommended tests will appear here based on diagnosis."
        );

        txtPatientId.requestFocus();
    }

    // =====================================================
    // VIEW HISTORY
    // =====================================================

    private void viewHistory() {

        String patientIdText =
                txtPatientId.getText().trim();

        if (patientIdText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Patient ID first.",
                    "Patient ID Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int patientId;

        try {

            patientId =
                    Integer.parseInt(
                            patientIdText
                    );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Patient ID must be a valid number.",
                    "Invalid Patient ID",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con =
                    DBConnection.getConnection();

            // =================================================
            // GET DOCTOR
            // =================================================

            String doctorQuery =
                    "SELECT user_id " +
                            "FROM users " +
                            "WHERE employee_id=? " +
                            "AND role='Doctor' " +
                            "AND status='ACTIVE'";

            ps =
                    con.prepareStatement(
                            doctorQuery
                    );

            ps.setString(
                    1,
                    employeeId
            );

            rs =
                    ps.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor not found.",
                        "Access Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int doctorId =
                    rs.getInt("user_id");

            rs.close();
            ps.close();

            // =================================================
            // VERIFY PATIENT
            // =================================================

            String verifyQuery =
                    "SELECT patient_id, patient_name " +
                            "FROM patients " +
                            "WHERE patient_id=? " +
                            "AND doctor_id=?";

            ps =
                    con.prepareStatement(
                            verifyQuery
                    );

            ps.setInt(
                    1,
                    patientId
            );

            ps.setInt(
                    2,
                    doctorId
            );

            rs =
                    ps.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "This patient is not assigned to you.",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String patientName =
                    rs.getString(
                            "patient_name"
                    );

            rs.close();
            ps.close();

            // =================================================
            // FETCH HISTORY
            // =================================================

            String historyQuery =
                    "SELECT diagnosis, prescription, " +
                            "treatment, visit_date, follow_up_date " +
                            "FROM medical_records " +
                            "WHERE patient_id=? " +
                            "AND doctor_id=? " +
                            "ORDER BY visit_date DESC";

            ps =
                    con.prepareStatement(
                            historyQuery
                    );

            ps.setInt(
                    1,
                    patientId
            );

            ps.setInt(
                    2,
                    doctorId
            );

            rs =
                    ps.executeQuery();

            StringBuilder history =
                    new StringBuilder();

            history.append(
                    "PATIENT: "
            );

            history.append(
                    patientName
            );

            history.append(
                    "\n========================================\n\n"
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                history.append(
                        "Visit Date: "
                );

                history.append(
                        rs.getDate(
                                "visit_date"
                        )
                );

                history.append("\n");

                Date followUp =
                        rs.getDate(
                                "follow_up_date"
                        );

                history.append(
                        "Follow-up Date: "
                );

                history.append(
                        followUp != null
                                ? followUp
                                : "Not specified"
                );

                history.append("\n");

                history.append(
                        "Diagnosis: "
                );

                history.append(
                        rs.getString(
                                "diagnosis"
                        )
                );

                history.append("\n");

                history.append(
                        "Prescription: "
                );

                history.append(
                        rs.getString(
                                "prescription"
                        )
                );

                history.append("\n");

                history.append(
                        "Treatment: "
                );

                history.append(
                        rs.getString(
                                "treatment"
                        )
                );

                history.append(
                        "\n\n----------------------------------------\n\n"
                );
            }

            if (!found) {

                history.append(
                        "No medical records found."
                );
            }

            JTextArea historyArea =
                    new JTextArea(
                            history.toString()
                    );

            historyArea.setEditable(false);

            historyArea.setLineWrap(true);

            historyArea.setWrapStyleWord(true);

            historyArea.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            13
                    )
            );

            JScrollPane scrollPane =
                    new JScrollPane(
                            historyArea
                    );

            scrollPane.setPreferredSize(
                    new Dimension(
                            650,
                            450
                    )
            );

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Medical History - " +
                            patientName,
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ignored) {
            }

            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ignored) {
            }

            try {
                if (con != null)
                    con.close();
            } catch (SQLException ignored) {
            }
        }
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> new MedicalRecords(
                        "DOC001"
                )
        );
    }
}