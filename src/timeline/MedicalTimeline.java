package timeline;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MedicalTimeline extends JFrame implements ActionListener {

    private JTextField txtPatientId;
    private JTextArea txtTimeline;

    private JButton btnSearch;
    private JButton btnClear;

    private JLabel lblPatientName;
    private JLabel lblPatientInfo;
    private JLabel lblRecordCount;
    private JLabel lblAppointmentCount;

    private String employeeId;


    // =========================================================
    // COLORS
    // =========================================================

    private static final Color NAVY =
            new Color(15, 43, 70);

    private static final Color BLUE =
            new Color(30, 91, 150);

    private static final Color LIGHT_BLUE =
            new Color(235, 244, 252);

    private static final Color BORDER =
            new Color(215, 225, 235);

    private static final Color TEXT =
            new Color(45, 55, 65);

    private static final Color MUTED =
            new Color(105, 115, 125);

    private static final Color BACKGROUND =
            new Color(245, 248, 252);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MedicalTimeline(String employeeId) {

        this.employeeId = employeeId;

        setTitle("Medical Timeline");

        setSize(1200, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        getContentPane().setBackground(
                BACKGROUND
        );

        setLayout(
                new BorderLayout()
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                NAVY
        );

        header.setPreferredSize(
                new Dimension(
                        1200,
                        82
                )
        );

        header.setBorder(
                new EmptyBorder(
                        12,
                        25,
                        12,
                        25
                )
        );


        // =====================================================
        // LEFT HEADER
        // =====================================================

        JPanel headerLeft =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                7
                        )
                );

        headerLeft.setOpaque(false);


        TimelineIcon timelineIcon =
                new TimelineIcon(42);

        headerLeft.add(
                timelineIcon
        );


        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);


        JLabel title =
                new JLabel(
                        "PATIENT MEDICAL TIMELINE"
                );

        title.setForeground(
                Color.WHITE
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );


        JLabel subtitle =
                new JLabel(
                        "View medical history and appointment timeline"
                );

        subtitle.setForeground(
                new Color(
                        195,
                        215,
                        232
                )
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(3)
        );

        titlePanel.add(subtitle);

        headerLeft.add(
                titlePanel
        );


        header.add(
                headerLeft,
                BorderLayout.WEST
        );


        // =====================================================
        // RIGHT HEADER
        // =====================================================

        JPanel doctorPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                9
                        )
                );

        doctorPanel.setOpaque(false);


        JLabel doctorLabel =
                new JLabel(
                        "DOCTOR"
                );

        doctorLabel.setForeground(
                Color.WHITE
        );

        doctorLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        JLabel employeeLabel =
                new JLabel(
                        employeeId
                );

        employeeLabel.setForeground(
                new Color(
                        205,
                        220,
                        235
                )
        );

        employeeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        doctorPanel.add(
                doctorLabel
        );

        doctorPanel.add(
                employeeLabel
        );


        header.add(
                doctorPanel,
                BorderLayout.EAST
        );


        add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        14,
                        20,
                        10,
                        20
                )
        );


        // =====================================================
        // SEARCH + PATIENT INFO
        // =====================================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        topPanel.setOpaque(false);


        // =====================================================
        // SEARCH CARD
        // =====================================================

        JPanel searchCard =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        searchCard.setBackground(
                Color.WHITE
        );

        searchCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                14,
                                10,
                                14
                        )
                )
        );

        searchCard.setPreferredSize(
                new Dimension(
                        430,
                        72
                )
        );


        JLabel searchTitle =
                new JLabel(
                        "Patient ID"
                );

        searchTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        searchTitle.setForeground(
                TEXT
        );


        txtPatientId =
                new JTextField();

        txtPatientId.setPreferredSize(
                new Dimension(
                        110,
                        34
                )
        );

        txtPatientId.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        txtPatientId.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                5,
                                9,
                                5,
                                9
                        )
                )
        );


        btnSearch =
                createButton(
                        "Search Timeline",
                        BLUE,
                        140
                );


        JPanel searchFields =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        searchFields.setOpaque(false);


        searchFields.add(
                searchTitle
        );

        searchFields.add(
                txtPatientId
        );

        searchFields.add(
                btnSearch
        );


        searchCard.add(
                searchFields,
                BorderLayout.CENTER
        );


        topPanel.add(
                searchCard,
                BorderLayout.WEST
        );


        // =====================================================
        // PATIENT INFO CARD
        // =====================================================

        JPanel patientCard =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        patientCard.setBackground(
                Color.WHITE
        );

        patientCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                15,
                                10,
                                15
                        )
                )
        );


        PatientIcon patientIcon =
                new PatientIcon(38);

        patientCard.add(
                patientIcon,
                BorderLayout.WEST
        );


        JPanel patientText =
                new JPanel();

        patientText.setLayout(
                new BoxLayout(
                        patientText,
                        BoxLayout.Y_AXIS
                )
        );

        patientText.setOpaque(false);


        lblPatientName =
                new JLabel(
                        "No Patient Selected"
                );

        lblPatientName.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        lblPatientName.setForeground(
                NAVY
        );


        lblPatientInfo =
                new JLabel(
                        "Enter a Patient ID and search timeline"
                );

        lblPatientInfo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        lblPatientInfo.setForeground(
                MUTED
        );


        patientText.add(
                lblPatientName
        );

        patientText.add(
                Box.createVerticalStrut(4)
        );

        patientText.add(
                lblPatientInfo
        );


        patientCard.add(
                patientText,
                BorderLayout.CENTER
        );


        topPanel.add(
                patientCard,
                BorderLayout.CENTER
        );


        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TIMELINE PANEL
        // =====================================================

        JPanel timelinePanel =
                new JPanel(
                        new BorderLayout()
                );

        timelinePanel.setBackground(
                Color.WHITE
        );

        timelinePanel.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );


        // =====================================================
        // TIMELINE HEADER
        // =====================================================

        JPanel timelineHeader =
                new JPanel(
                        new BorderLayout()
                );

        timelineHeader.setBackground(
                LIGHT_BLUE
        );

        timelineHeader.setBorder(
                new EmptyBorder(
                        9,
                        14,
                        9,
                        14
                )
        );


        JLabel timelineTitle =
                new JLabel(
                        "Medical History & Appointment Timeline"
                );

        timelineTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        timelineTitle.setForeground(
                NAVY
        );


        JPanel countPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                0
                        )
                );

        countPanel.setOpaque(false);


        lblRecordCount =
                new JLabel(
                        "Records: 0"
                );

        lblRecordCount.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        lblRecordCount.setForeground(
                BLUE
        );


        lblAppointmentCount =
                new JLabel(
                        "Appointments: 0"
                );

        lblAppointmentCount.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        lblAppointmentCount.setForeground(
                BLUE
        );


        countPanel.add(
                lblRecordCount
        );

        countPanel.add(
                lblAppointmentCount
        );


        timelineHeader.add(
                timelineTitle,
                BorderLayout.WEST
        );

        timelineHeader.add(
                countPanel,
                BorderLayout.EAST
        );


        timelinePanel.add(
                timelineHeader,
                BorderLayout.NORTH
        );


        // =====================================================
        // TIMELINE TEXT AREA
        // =====================================================

        txtTimeline =
                new JTextArea();

        txtTimeline.setEditable(false);

        txtTimeline.setLineWrap(true);

        txtTimeline.setWrapStyleWord(true);

        txtTimeline.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        txtTimeline.setForeground(
                TEXT
        );

        txtTimeline.setBackground(
                Color.WHITE
        );

        txtTimeline.setMargin(
                new Insets(
                        15,
                        18,
                        15,
                        18
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        txtTimeline
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );


        timelinePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        mainPanel.add(
                timelinePanel,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM BUTTON
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        bottomPanel.setBackground(
                BACKGROUND
        );


        btnClear =
                createButton(
                        "Clear",
                        new Color(
                                110,
                                120,
                                130
                        ),
                        100
                );


        bottomPanel.add(
                btnClear
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // ACTION LISTENERS
        // =====================================================

        btnSearch.addActionListener(
                this
        );

        btnClear.addActionListener(
                this
        );


        setVisible(true);
    }


    // =========================================================
    // BUTTON DESIGN
    // =========================================================

    private JButton createButton(
            String text,
            Color background,
            int width
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        width,
                        36
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                background
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }


    // =========================================================
    // BUTTON ACTION
    // =========================================================

    @Override
    public void actionPerformed(
            ActionEvent e
    ) {

        // =====================================================
        // SEARCH
        // =====================================================

        if (e.getSource() == btnSearch) {

            searchTimeline();

            return;
        }


        // =====================================================
        // CLEAR
        // =====================================================

        if (e.getSource() == btnClear) {

            txtPatientId.setText("");

            txtTimeline.setText("");

            lblPatientName.setText(
                    "No Patient Selected"
            );

            lblPatientInfo.setText(
                    "Enter a Patient ID and search timeline"
            );

            lblRecordCount.setText(
                    "Records: 0"
            );

            lblAppointmentCount.setText(
                    "Appointments: 0"
            );
        }
    }


    // =========================================================
    // SEARCH TIMELINE
    // =========================================================

    private void searchTimeline() {

        String patientId =
                txtPatientId.getText().trim();


        // =====================================================
        // VALIDATION
        // =====================================================

        if (patientId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Patient ID",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int patientIdNumber;


        try {

            patientIdNumber =
                    Integer.parseInt(
                            patientId
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


        try {

            con =
                    DBConnection.getConnection();


            // =================================================
            // STEP 1:
            // FIND LOGGED-IN DOCTOR
            // =================================================

            String doctorQuery =
                    "SELECT user_id, full_name " +
                            "FROM users " +
                            "WHERE employee_id = ? " +
                            "AND role = 'Doctor'";


            PreparedStatement doctorPs =
                    con.prepareStatement(
                            doctorQuery
                    );


            doctorPs.setString(
                    1,
                    employeeId
            );


            ResultSet doctorRs =
                    doctorPs.executeQuery();


            if (!doctorRs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Doctor account not found.",
                        "Access Error",
                        JOptionPane.ERROR_MESSAGE
                );

                doctorRs.close();
                doctorPs.close();

                return;
            }


            int doctorId =
                    doctorRs.getInt(
                            "user_id"
                    );


            String doctorName =
                    doctorRs.getString(
                            "full_name"
                    );


            doctorRs.close();
            doctorPs.close();


            // =================================================
            // STEP 2:
            // VERIFY PATIENT BELONGS TO DOCTOR
            // =================================================

            String patientQuery =
                    "SELECT patient_name, age, gender, " +
                            "blood_group, contact " +
                            "FROM patients " +
                            "WHERE patient_id = ? " +
                            "AND doctor_id = ?";


            PreparedStatement patientPs =
                    con.prepareStatement(
                            patientQuery
                    );


            patientPs.setInt(
                    1,
                    patientIdNumber
            );


            patientPs.setInt(
                    2,
                    doctorId
            );


            ResultSet patientRs =
                    patientPs.executeQuery();


            if (!patientRs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Access Denied!\n\n" +
                                "This patient is not assigned to you.",
                        "Access Denied",
                        JOptionPane.WARNING_MESSAGE
                );


                patientRs.close();
                patientPs.close();

                return;
            }


            String patientName =
                    patientRs.getString(
                            "patient_name"
                    );


            int age =
                    patientRs.getInt(
                            "age"
                    );


            String gender =
                    patientRs.getString(
                            "gender"
                    );


            String bloodGroup =
                    patientRs.getString(
                            "blood_group"
                    );


            String contact =
                    patientRs.getString(
                            "contact"
                    );


            patientRs.close();
            patientPs.close();


            // =================================================
            // DISPLAY PATIENT INFORMATION
            // =================================================

            lblPatientName.setText(
                    patientName
            );


            lblPatientInfo.setText(
                    "Age: " + age
                            + "   |   Gender: "
                            + gender
                            + "   |   Blood Group: "
                            + bloodGroup
                            + "   |   Contact: "
                            + contact
            );


            // =================================================
            // BUILD TIMELINE
            // =================================================

            StringBuilder timeline =
                    new StringBuilder();


            timeline.append(
                    "PATIENT INFORMATION\n"
            );

            timeline.append(
                    "Patient ID    : "
                            + patientId
                            + "\n"
            );

            timeline.append(
                    "Patient Name  : "
                            + patientName
                            + "\n"
            );

            timeline.append(
                    "Age           : "
                            + age
                            + "\n"
            );

            timeline.append(
                    "Gender        : "
                            + gender
                            + "\n"
            );

            timeline.append(
                    "Blood Group   : "
                            + bloodGroup
                            + "\n"
            );

            timeline.append(
                    "Contact       : "
                            + contact
                            + "\n"
            );

            timeline.append(
                    "Doctor        : "
                            + doctorName
                            + "\n\n"
            );


            timeline.append(
                    "============================================================\n"
            );

            timeline.append(
                    "MEDICAL HISTORY\n"
            );

            timeline.append(
                    "============================================================\n\n"
            );


            // =================================================
            // STEP 3:
            // MEDICAL RECORDS
            // =================================================

            String recordQuery =
                    "SELECT diagnosis, prescription, " +
                            "treatment, visit_date, follow_up_date " +
                            "FROM medical_records " +
                            "WHERE patient_id = ? " +
                            "AND doctor_id = ? " +
                            "ORDER BY visit_date ASC";


            PreparedStatement recordPs =
                    con.prepareStatement(
                            recordQuery
                    );


            recordPs.setInt(
                    1,
                    patientIdNumber
            );


            recordPs.setInt(
                    2,
                    doctorId
            );


            ResultSet recordRs =
                    recordPs.executeQuery();


            int recordCount = 0;


            while (recordRs.next()) {

                recordCount++;


                timeline.append(
                        "VISIT "
                                + recordCount
                                + "\n"
                );


                timeline.append(
                        "Visit Date       : "
                                + recordRs.getDate(
                                "visit_date"
                        )
                                + "\n"
                );


                timeline.append(
                        "Diagnosis        : "
                                + recordRs.getString(
                                "diagnosis"
                        )
                                + "\n"
                );


                timeline.append(
                        "Prescription     : "
                                + recordRs.getString(
                                "prescription"
                        )
                                + "\n"
                );


                timeline.append(
                        "Treatment        : "
                                + recordRs.getString(
                                "treatment"
                        )
                                + "\n"
                );


                timeline.append(
                        "Follow-up Date   : "
                                + recordRs.getDate(
                                "follow_up_date"
                        )
                                + "\n"
                );


                timeline.append(
                        "\n------------------------------------------------------------\n\n"
                );
            }


            if (recordCount == 0) {

                timeline.append(
                        "No medical records found for this patient.\n\n"
                );
            }


            recordRs.close();
            recordPs.close();


            // =================================================
            // STEP 4:
            // APPOINTMENT HISTORY
            // =================================================

            timeline.append(
                    "============================================================\n"
            );

            timeline.append(
                    "APPOINTMENT HISTORY\n"
            );

            timeline.append(
                    "============================================================\n\n"
            );


            String appointmentQuery =
                    "SELECT doctor_name, appointment_date, " +
                            "appointment_time " +
                            "FROM appointments " +
                            "WHERE patient_id = ? " +
                            "ORDER BY appointment_date ASC, " +
                            "appointment_time ASC";


            PreparedStatement appointmentPs =
                    con.prepareStatement(
                            appointmentQuery
                    );


            appointmentPs.setInt(
                    1,
                    patientIdNumber
            );


            ResultSet appointmentRs =
                    appointmentPs.executeQuery();


            int appointmentCount = 0;


            while (appointmentRs.next()) {

                appointmentCount++;


                timeline.append(
                        "APPOINTMENT "
                                + appointmentCount
                                + "\n"
                );


                timeline.append(
                        "Appointment Date : "
                                + appointmentRs.getDate(
                                "appointment_date"
                        )
                                + "\n"
                );


                timeline.append(
                        "Appointment Time : "
                                + appointmentRs.getTime(
                                "appointment_time"
                        )
                                + "\n"
                );


                timeline.append(
                        "Doctor           : "
                                + appointmentRs.getString(
                                "doctor_name"
                        )
                                + "\n"
                );


                timeline.append(
                        "\n------------------------------------------------------------\n\n"
                );
            }


            if (appointmentCount == 0) {

                timeline.append(
                        "No appointment history found.\n"
                );
            }


            appointmentRs.close();
            appointmentPs.close();


            // =================================================
            // UPDATE COUNTS
            // =================================================

            lblRecordCount.setText(
                    "Records: "
                            + recordCount
            );


            lblAppointmentCount.setText(
                    "Appointments: "
                            + appointmentCount
            );


            // =================================================
            // DISPLAY TIMELINE
            // =================================================

            txtTimeline.setText(
                    timeline.toString()
            );


            txtTimeline.setCaretPosition(0);


        } catch (Exception ex) {

            ex.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load medical timeline.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );


        } finally {

            if (con != null) {

                try {

                    con.close();

                } catch (SQLException ignored) {
                }
            }
        }
    }


    // =========================================================
    // TIMELINE ICON
    // =========================================================

    private static class TimelineIcon
            extends JPanel {

        private final int size;


        TimelineIcon(int size) {

            this.size = size;

            setPreferredSize(
                    new Dimension(
                            size,
                            size
                    )
            );

            setOpaque(false);
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);


            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            int w = getWidth();

            int h = getHeight();


            // =================================================
            // CIRCLE
            // =================================================

            g2.setColor(
                    new Color(
                            225,
                            239,
                            252
                    )
            );


            g2.fillOval(
                    1,
                    1,
                    w - 2,
                    h - 2
            );


            // =================================================
            // TIMELINE LINE
            // =================================================

            g2.setColor(
                    BLUE
            );


            g2.setStroke(
                    new BasicStroke(
                            3f
                    )
            );


            g2.drawLine(
                    w / 2,
                    h / 5,
                    w / 2,
                    h * 4 / 5
            );


            // =================================================
            // TIMELINE POINTS
            // =================================================

            g2.fillOval(
                    w / 2 - 5,
                    h / 4 - 5,
                    10,
                    10
            );


            g2.fillOval(
                    w / 2 - 5,
                    h / 2 - 5,
                    10,
                    10
            );


            g2.fillOval(
                    w / 2 - 5,
                    h * 3 / 4 - 5,
                    10,
                    10
            );


            g2.dispose();
        }
    }


    // =========================================================
    // PATIENT ICON
    // =========================================================

    private static class PatientIcon
            extends JPanel {

        private final int size;


        PatientIcon(int size) {

            this.size = size;

            setPreferredSize(
                    new Dimension(
                            size,
                            size
                    )
            );

            setOpaque(false);
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);


            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            int w = getWidth();

            int h = getHeight();


            // =================================================
            // CIRCLE
            // =================================================

            g2.setColor(
                    new Color(
                            225,
                            239,
                            252
                    )
            );


            g2.fillOval(
                    1,
                    1,
                    w - 2,
                    h - 2
            );


            // =================================================
            // PATIENT COLOR
            // =================================================

            g2.setColor(
                    BLUE
            );


            // =================================================
            // HEAD
            // =================================================

            g2.fillOval(
                    w / 2 - 7,
                    h / 5,
                    14,
                    14
            );


            // =================================================
            // BODY
            // =================================================

            g2.fillRoundRect(
                    w / 2 - 12,
                    h / 2,
                    24,
                    15,
                    8,
                    8
            );


            g2.dispose();
        }
    }
}