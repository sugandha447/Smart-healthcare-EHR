package dashboard;

import database.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PatientMedicalTimeline extends JFrame {

    private int patientId;

    private JTextArea timelineArea;
    private JButton btnRefresh;
    private JButton btnBack;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public PatientMedicalTimeline(int patientId) {

        this.patientId = patientId;

        setTitle("Medical Timeline");

        setSize(800, 650);

        setLayout(null);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // =====================================================
        // HEADING
        // =====================================================

        JLabel heading =
                new JLabel("MY MEDICAL TIMELINE");

        heading.setBounds(
                260,
                25,
                350,
                40
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        add(heading);


        // =====================================================
        // PATIENT ID
        // =====================================================

        JLabel patientLabel =
                new JLabel(
                        "Patient ID: " + patientId
                );

        patientLabel.setBounds(
                40,
                75,
                250,
                30
        );

        patientLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        add(patientLabel);


        // =====================================================
        // TIMELINE AREA
        // =====================================================

        timelineArea = new JTextArea();

        timelineArea.setEditable(false);

        timelineArea.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        timelineArea.setLineWrap(true);

        timelineArea.setWrapStyleWord(true);


        JScrollPane scrollPane =
                new JScrollPane(timelineArea);

        scrollPane.setBounds(
                40,
                115,
                700,
                400
        );

        add(scrollPane);


        // =====================================================
        // REFRESH BUTTON
        // =====================================================

        btnRefresh =
                new JButton("Refresh");

        btnRefresh.setBounds(
                250,
                540,
                120,
                40
        );

        add(btnRefresh);


        // =====================================================
        // BACK BUTTON
        // =====================================================

        btnBack =
                new JButton("Back");

        btnBack.setBounds(
                400,
                540,
                120,
                40
        );

        add(btnBack);


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        btnRefresh.addActionListener(e ->
                loadTimeline()
        );

        btnBack.addActionListener(e ->
                dispose()
        );


        // =====================================================
        // LOAD TIMELINE
        // =====================================================

        loadTimeline();


        // =====================================================
        // SHOW WINDOW
        // =====================================================

        setVisible(true);
    }


    // =========================================================
    // LOAD MEDICAL TIMELINE
    // =========================================================

    private void loadTimeline() {

        timelineArea.setText("");

        String sql =
                "SELECT record_id, diagnosis, " +
                        "prescription, treatment, " +
                        "visit_date, follow_up_date, doctor_id " +
                        "FROM medical_records " +
                        "WHERE patient_id = ? " +
                        "ORDER BY visit_date ASC";


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


            boolean found = false;

            StringBuilder timeline =
                    new StringBuilder();


            while (rs.next()) {

                found = true;


                int recordId =
                        rs.getInt("record_id");

                String diagnosis =
                        rs.getString("diagnosis");

                String prescription =
                        rs.getString("prescription");

                String treatment =
                        rs.getString("treatment");

                Date visitDate =
                        rs.getDate("visit_date");

                Date followUpDate =
                        rs.getDate("follow_up_date");

                Object doctorId =
                        rs.getObject("doctor_id");


                timeline.append(
                        "==================================================\n"
                );

                timeline.append(
                        "Medical Visit\n"
                );

                timeline.append(
                        "==================================================\n"
                );

                timeline.append(
                        "Record ID      : "
                                + recordId
                                + "\n"
                );

                timeline.append(
                        "Visit Date     : "
                                + visitDate
                                + "\n"
                );

                timeline.append(
                        "Doctor ID      : "
                                + (doctorId != null
                                ? doctorId
                                : "Not Available")
                                + "\n"
                );

                timeline.append(
                        "Diagnosis      : "
                                + (diagnosis != null
                                ? diagnosis
                                : "Not Available")
                                + "\n"
                );

                timeline.append(
                        "Prescription   : "
                                + (prescription != null
                                ? prescription
                                : "Not Available")
                                + "\n"
                );

                timeline.append(
                        "Treatment      : "
                                + (treatment != null
                                ? treatment
                                : "Not Available")
                                + "\n"
                );

                timeline.append(
                        "Follow-up Date : "
                                + (followUpDate != null
                                ? followUpDate
                                : "Not Available")
                                + "\n\n"
                );
            }


            if (found) {

                timelineArea.setText(
                        timeline.toString()
                );

            } else {

                timelineArea.setText(
                        "No medical history found."
                );
            }


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading medical timeline:\n"
                            + e.getMessage()
            );
        }
    }
}