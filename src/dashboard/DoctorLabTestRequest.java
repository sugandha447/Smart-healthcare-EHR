package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class DoctorLabTestRequest extends JFrame {

    private String employeeId;

    private JComboBox<PatientItem> cmbPatient;
    private JComboBox<String> cmbTest;

    private JLabel lblPatientIdValue;
    private JLabel lblStatus;

    private JButton btnRequest;
    private JButton btnBack;

    // =========================================================
    // COLORS
    // =========================================================

    private final Color NAVY = new Color(20, 45, 78);
    private final Color BLUE = new Color(38, 102, 170);
    private final Color LIGHT_BLUE = new Color(235, 244, 253);
    private final Color BORDER = new Color(215, 225, 235);
    private final Color TEXT = new Color(45, 55, 70);
    private final Color MUTED = new Color(105, 118, 135);
    private final Color WHITE = Color.WHITE;
    private final Color GREEN = new Color(35, 145, 95);

    // =========================================================
    // PATIENT ITEM
    // =========================================================

    private static class PatientItem {

        private int patientId;
        private String patientName;

        public PatientItem(int patientId, String patientName) {
            this.patientId = patientId;
            this.patientName = patientName;
        }

        public int getPatientId() {
            return patientId;
        }

        @Override
        public String toString() {
            return patientName;
        }
    }

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DoctorLabTestRequest(String employeeId) {

        this.employeeId = employeeId;

        setTitle("Request Laboratory Test");
        setSize(1200, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 248, 252));
        setContentPane(mainPanel);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(
                new EmptyBorder(18, 25, 18, 25)
        );
        header.setPreferredSize(
                new Dimension(1200, 82)
        );

        JPanel titlePanel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        15,
                        0
                )
        );
        titlePanel.setOpaque(false);

        JLabel icon = new JLabel(
                new LabTestIcon(34, BLUE)
        );

        JPanel titleText = new JPanel();
        titleText.setOpaque(false);
        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                "Request Laboratory Test"
        );

        title.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                23
        ));

        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Create a laboratory test request for an assigned patient"
        );

        subtitle.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                13
        ));

        subtitle.setForeground(MUTED);

        titleText.add(title);
        titleText.add(
                Box.createVerticalStrut(4)
        );
        titleText.add(subtitle);

        titlePanel.add(icon);
        titlePanel.add(titleText);

        JLabel doctorLabel = new JLabel(
                "Doctor  |  " + employeeId
        );

        doctorLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                13
        ));

        doctorLabel.setForeground(BLUE);

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

        JPanel content = new JPanel(
                new BorderLayout()
        );

        content.setBackground(
                new Color(245, 248, 252)
        );

        content.setBorder(
                new EmptyBorder(
                        25, 35, 20, 35
                )
        );

        // =====================================================
        // LEFT PANEL
        // =====================================================

        JPanel leftPanel = new JPanel();

        leftPanel.setBackground(WHITE);

        leftPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                22, 25, 22, 25
                        )
                )
        );

        leftPanel.setLayout(
                new BoxLayout(
                        leftPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel sectionTitle = new JLabel(
                "Laboratory Test Request"
        );

        sectionTitle.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                19
        ));

        sectionTitle.setForeground(NAVY);

        JLabel sectionSubtitle = new JLabel(
                "Select the patient and required laboratory test."
        );

        sectionSubtitle.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                13
        ));

        sectionSubtitle.setForeground(MUTED);

        leftPanel.add(sectionTitle);

        leftPanel.add(
                Box.createVerticalStrut(5)
        );

        leftPanel.add(sectionSubtitle);

        leftPanel.add(
                Box.createVerticalStrut(25)
        );

        // =====================================================
        // PATIENT
        // =====================================================

        JLabel patientLabel =
                createFieldLabel("PATIENT");

        leftPanel.add(patientLabel);

        leftPanel.add(
                Box.createVerticalStrut(8)
        );

        cmbPatient = new JComboBox<>();

        cmbPatient.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                14
        ));

        cmbPatient.setPreferredSize(
                new Dimension(450, 44)
        );

        cmbPatient.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        cmbPatient.setBackground(WHITE);

        cmbPatient.addActionListener(
                e -> updatePatientDetails()
        );

        leftPanel.add(cmbPatient);

        leftPanel.add(
                Box.createVerticalStrut(8)
        );

        JLabel patientHint = new JLabel(
                "Only patients assigned to the logged-in doctor are shown."
        );

        patientHint.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                11
        ));

        patientHint.setForeground(MUTED);

        leftPanel.add(patientHint);

        leftPanel.add(
                Box.createVerticalStrut(25)
        );

        // =====================================================
        // PATIENT ID CARD
        // =====================================================

        JPanel patientInfoCard = new JPanel(
                new BorderLayout()
        );

        patientInfoCard.setBackground(
                LIGHT_BLUE
        );

        patientInfoCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(205, 222, 240)
                        ),
                        new EmptyBorder(
                                12, 15, 12, 15
                        )
                )
        );

        patientInfoCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        65
                )
        );

        JLabel patientIcon = new JLabel(
                new PatientIcon(25, BLUE)
        );

        JPanel patientIconPanel =
                new JPanel(new BorderLayout());

        patientIconPanel.setOpaque(false);

        patientIconPanel.add(
                patientIcon,
                BorderLayout.CENTER
        );

        JPanel patientInfoText = new JPanel();

        patientInfoText.setOpaque(false);

        patientInfoText.setLayout(
                new BoxLayout(
                        patientInfoText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel patientIdLabel = new JLabel(
                "Patient ID"
        );

        patientIdLabel.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                11
        ));

        patientIdLabel.setForeground(MUTED);

        lblPatientIdValue = new JLabel("-");

        lblPatientIdValue.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        lblPatientIdValue.setForeground(NAVY);

        patientInfoText.add(patientIdLabel);

        patientInfoText.add(
                Box.createVerticalStrut(2)
        );

        patientInfoText.add(
                lblPatientIdValue
        );

        patientInfoCard.add(
                patientIconPanel,
                BorderLayout.WEST
        );

        patientInfoCard.add(
                patientInfoText,
                BorderLayout.CENTER
        );

        leftPanel.add(patientInfoCard);

        leftPanel.add(
                Box.createVerticalStrut(28)
        );

        // =====================================================
        // TEST
        // =====================================================

        JLabel testLabel =
                createFieldLabel(
                        "LABORATORY TEST"
                );

        leftPanel.add(testLabel);

        leftPanel.add(
                Box.createVerticalStrut(8)
        );

        cmbTest = new JComboBox<>(
                new String[]{
                        "Select Test",
                        "Blood Test",
                        "Blood Sugar",
                        "Hemoglobin",
                        "Lipid Profile",
                        "Urine Test",
                        "Thyroid Test",
                        "Liver Function Test",
                        "Kidney Function Test"
                }
        );

        cmbTest.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                14
        ));

        cmbTest.setPreferredSize(
                new Dimension(450, 44)
        );

        cmbTest.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        cmbTest.setBackground(WHITE);

        leftPanel.add(cmbTest);

        leftPanel.add(
                Box.createVerticalGlue()
        );

        // =====================================================
        // RIGHT PANEL
        // =====================================================

        JPanel rightPanel = new JPanel();

        rightPanel.setOpaque(false);

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );

        // =====================================================
        // REQUEST INFORMATION CARD
        // =====================================================

        JPanel infoCard = createInfoCard();

        JLabel infoTitle = new JLabel(
                "Request Information"
        );

        infoTitle.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                17
        ));

        infoTitle.setForeground(NAVY);

        infoCard.add(
                infoTitle,
                BorderLayout.NORTH
        );

        JPanel infoText = new JPanel();

        infoText.setOpaque(false);

        infoText.setLayout(
                new BoxLayout(
                        infoText,
                        BoxLayout.Y_AXIS
                )
        );

        infoText.add(
                createBullet(
                        "Only assigned patients can be selected."
                )
        );

        infoText.add(
                createBullet(
                        "Duplicate pending tests are prevented."
                )
        );

        infoText.add(
                createBullet(
                        "New requests are created with Pending status."
                )
        );

        infoText.add(
                createBullet(
                        "Laboratory staff will enter the result."
                )
        );

        infoCard.add(
                infoText,
                BorderLayout.CENTER
        );

        rightPanel.add(infoCard);

        rightPanel.add(
                Box.createVerticalStrut(20)
        );

        // =====================================================
        // STATUS CARD
        // =====================================================

        JPanel statusCard = createInfoCard();

        JLabel statusTitle = new JLabel(
                "Request Status"
        );

        statusTitle.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                17
        ));

        statusTitle.setForeground(NAVY);

        statusCard.add(
                statusTitle,
                BorderLayout.NORTH
        );

        JPanel statusContent = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        5,
                        5
                )
        );

        statusContent.setOpaque(false);

        JLabel statusIcon = new JLabel(
                new StatusIcon(18, GREEN)
        );

        lblStatus = new JLabel(
                "Ready to create a laboratory request"
        );

        lblStatus.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));

        lblStatus.setForeground(MUTED);

        statusContent.add(statusIcon);
        statusContent.add(lblStatus);

        statusCard.add(
                statusContent,
                BorderLayout.CENTER
        );

        rightPanel.add(statusCard);

        rightPanel.add(
                Box.createVerticalGlue()
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerPanel = new JPanel(
                new GridLayout(
                        1,
                        2,
                        25,
                        0
                )
        );

        centerPanel.setOpaque(false);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        content.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BUTTON PANEL
        // =====================================================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        12,
                        10
                )
        );

        bottomPanel.setBackground(WHITE);

        bottomPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1,
                        0,
                        0,
                        0,
                        BORDER
                )
        );

        bottomPanel.setPreferredSize(
                new Dimension(
                        1200,
                        72
                )
        );

        btnBack = new JButton("Back");

        styleSecondaryButton(
                btnBack
        );

        btnRequest = new JButton(
                "Request Laboratory Test"
        );

        stylePrimaryButton(
                btnRequest
        );

        bottomPanel.add(btnBack);
        bottomPanel.add(btnRequest);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // ACTIONS
        // =====================================================

        btnRequest.addActionListener(
                e -> requestTest()
        );

        btnBack.addActionListener(
                e -> dispose()
        );

        // =====================================================
        // LOAD PATIENTS
        // =====================================================

        loadPatients();

        setVisible(true);
    }

    // =========================================================
    // FIELD LABEL
    // =========================================================

    private JLabel createFieldLabel(
            String text
    ) {

        JLabel label = new JLabel(text);

        label.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                11
        ));

        label.setForeground(MUTED);

        return label;
    }

    // =========================================================
    // INFO CARD
    // =========================================================

    private JPanel createInfoCard() {

        JPanel panel = new JPanel(
                new BorderLayout(
                        0,
                        12
                )
        );

        panel.setBackground(WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        return panel;
    }

    // =========================================================
    // BULLET
    // =========================================================

    private JLabel createBullet(
            String text
    ) {

        JLabel label = new JLabel(
                "<html><font color='#2666AA'>"
                        + "&#9679;"
                        + "</font>&nbsp;&nbsp;"
                        + text
                        + "</html>"
        );

        label.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));

        label.setForeground(TEXT);

        label.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        5,
                        0
                )
        );

        return label;
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                13
        ));

        button.setForeground(WHITE);

        button.setBackground(BLUE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        22,
                        10,
                        22
                )
        );

        button.setIcon(
                new SendIcon(
                        18,
                        WHITE
                )
        );
    }

    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private void styleSecondaryButton(
            JButton button
    ) {

        button.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                13
        ));

        button.setForeground(NAVY);

        button.setBackground(WHITE);

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                9,
                                22,
                                9,
                                22
                        )
                )
        );
    }

    // =========================================================
    // UPDATE PATIENT DETAILS
    // =========================================================

    private void updatePatientDetails() {

        PatientItem patient =
                (PatientItem)
                        cmbPatient.getSelectedItem();

        if (patient == null) {

            lblPatientIdValue.setText("-");

            return;
        }

        lblPatientIdValue.setText(
                String.valueOf(
                        patient.getPatientId()
                )
        );

        lblStatus.setText(
                "Patient selected. Choose a laboratory test."
        );
    }

    // =========================================================
    // LOAD DOCTOR'S PATIENTS
    // =========================================================

    private void loadPatients() {

        cmbPatient.removeAllItems();

        String sql =
                "SELECT p.patient_id, p.patient_name " +
                        "FROM patients p " +
                        "INNER JOIN users u " +
                        "ON p.doctor_id = u.user_id " +
                        "WHERE u.employee_id = ? " +
                        "AND u.role = 'Doctor' " +
                        "AND u.status = 'ACTIVE' " +
                        "ORDER BY p.patient_name";

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

            try (
                    ResultSet rs =
                            ps.executeQuery()
            ) {

                while (rs.next()) {

                    cmbPatient.addItem(
                            new PatientItem(
                                    rs.getInt(
                                            "patient_id"
                                    ),
                                    rs.getString(
                                            "patient_name"
                                    )
                            )
                    );
                }
            }

            if (cmbPatient.getItemCount() == 0) {

                lblStatus.setText(
                        "No patients are currently assigned to you."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading patients:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // REQUEST TEST
    // =========================================================

    private void requestTest() {

        PatientItem selectedPatient =
                (PatientItem)
                        cmbPatient.getSelectedItem();

        String testName =
                (String)
                        cmbTest.getSelectedItem();

        // =====================================================
        // PATIENT VALIDATION
        // =====================================================

        if (selectedPatient == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient.",
                    "Patient Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // =====================================================
        // TEST VALIDATION
        // =====================================================

        if (testName == null ||
                testName.equals("Select Test")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a laboratory test.",
                    "Test Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int patientId =
                selectedPatient.getPatientId();

        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            // =================================================
            // GET DOCTOR ID
            // =================================================

            String doctorSql =
                    "SELECT user_id " +
                            "FROM users " +
                            "WHERE employee_id = ? " +
                            "AND role = 'Doctor' " +
                            "AND status = 'ACTIVE'";

            int doctorId;

            try (
                    PreparedStatement doctorPs =
                            con.prepareStatement(
                                    doctorSql
                            )
            ) {

                doctorPs.setString(
                        1,
                        employeeId
                );

                try (
                        ResultSet doctorRs =
                                doctorPs.executeQuery()
                ) {

                    if (!doctorRs.next()) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Doctor information not found.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }

                    doctorId =
                            doctorRs.getInt(
                                    "user_id"
                            );
                }
            }

            // =================================================
            // CHECK DUPLICATE PENDING TEST
            // =================================================

            String checkSql =
                    "SELECT COUNT(*) " +
                            "FROM lab_tests " +
                            "WHERE patient_id = ? " +
                            "AND doctor_id = ? " +
                            "AND test_name = ? " +
                            "AND test_status = 'Pending'";

            try (
                    PreparedStatement checkPs =
                            con.prepareStatement(
                                    checkSql
                            )
            ) {

                checkPs.setInt(
                        1,
                        patientId
                );

                checkPs.setInt(
                        2,
                        doctorId
                );

                checkPs.setString(
                        3,
                        testName
                );

                try (
                        ResultSet checkRs =
                                checkPs.executeQuery()
                ) {

                    if (checkRs.next() &&
                            checkRs.getInt(1) > 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "This test is already pending for this patient.",
                                "Duplicate Request",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }
                }
            }

            // =================================================
            // INSERT LAB REQUEST
            // =================================================

            String insertSql =
                    "INSERT INTO lab_tests " +
                            "(patient_id, doctor_id, test_name, " +
                            "test_result, test_date, test_status) " +
                            "VALUES (?, ?, ?, NULL, NULL, 'Pending')";

            try (
                    PreparedStatement insertPs =
                            con.prepareStatement(
                                    insertSql
                            )
            ) {

                insertPs.setInt(
                        1,
                        patientId
                );

                insertPs.setInt(
                        2,
                        doctorId
                );

                insertPs.setString(
                        3,
                        testName
                );

                insertPs.executeUpdate();
            }

            // =================================================
            // SUCCESS
            // =================================================

            JOptionPane.showMessageDialog(
                    this,
                    "Laboratory test requested successfully!\n\n"
                            + "Patient: "
                            + selectedPatient
                            + "\nTest: "
                            + testName,
                    "Request Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            lblStatus.setText(
                    "Laboratory request created successfully."
            );

            cmbPatient.setSelectedIndex(0);

            cmbTest.setSelectedIndex(0);

            lblPatientIdValue.setText("-");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error requesting lab test:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LAB TEST ICON
    // =========================================================

    private static class LabTestIcon
            implements Icon {

        private int size;
        private Color color;

        public LabTestIcon(
                int size,
                Color color
        ) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            int cx =
                    x + size / 2;

            int top =
                    y + 3;

            g2.drawLine(
                    cx - 7,
                    top,
                    cx + 7,
                    top
            );

            g2.drawLine(
                    cx - 5,
                    top,
                    cx - 5,
                    top + 7
            );

            g2.drawLine(
                    cx + 5,
                    top,
                    cx + 5,
                    top + 7
            );

            Polygon bottle =
                    new Polygon();

            bottle.addPoint(
                    cx - 6,
                    top + 7
            );

            bottle.addPoint(
                    cx + 6,
                    top + 7
            );

            bottle.addPoint(
                    cx + 10,
                    top + 25
            );

            bottle.addPoint(
                    cx - 10,
                    top + 25
            );

            g2.drawPolygon(bottle);

            g2.drawLine(
                    cx - 6,
                    top + 15,
                    cx + 6,
                    top + 15
            );

            g2.dispose();
        }
    }

    // =========================================================
    // PATIENT ICON
    // =========================================================

    private static class PatientIcon
            implements Icon {

        private int size;
        private Color color;

        public PatientIcon(
                int size,
                Color color
        ) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            int center =
                    x + size / 2;

            g2.fillOval(
                    center - 5,
                    y + 2,
                    10,
                    10
            );

            g2.drawArc(
                    center - 10,
                    y + 12,
                    20,
                    12,
                    0,
                    180
            );

            g2.dispose();
        }
    }

    // =========================================================
    // STATUS ICON
    // =========================================================

    private static class StatusIcon
            implements Icon {

        private int size;
        private Color color;

        public StatusIcon(
                int size,
                Color color
        ) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(2.0f)
            );

            g2.drawOval(
                    x + 1,
                    y + 1,
                    size - 2,
                    size - 2
            );

            g2.drawLine(
                    x + 5,
                    y + size / 2,
                    x + size / 2,
                    y + size - 5
            );

            g2.drawLine(
                    x + size / 2,
                    y + size - 5,
                    x + size - 4,
                    y + 4
            );

            g2.dispose();
        }
    }

    // =========================================================
    // SEND ICON
    // =========================================================

    private static class SendIcon
            implements Icon {

        private int size;
        private Color color;

        public SendIcon(
                int size,
                Color color
        ) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() {
            return size;
        }

        public int getIconHeight() {
            return size;
        }

        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            Polygon arrow =
                    new Polygon();

            arrow.addPoint(
                    x + 2,
                    y + 3
            );

            arrow.addPoint(
                    x + size - 2,
                    y + size / 2
            );

            arrow.addPoint(
                    x + 2,
                    y + size - 3
            );

            arrow.addPoint(
                    x + 6,
                    y + size / 2
            );

            g2.fillPolygon(arrow);

            g2.dispose();
        }
    }
}