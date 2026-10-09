package healthrisk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class HealthRiskAssessment extends JFrame implements ActionListener {

    JLabel heading;
    JLabel lblPatientId;
    JLabel lblAge;
    JLabel lblBP;
    JLabel lblSugar;
    JLabel lblBMI;
    JLabel lblHeartRate;
    JLabel lblRisk;
    JLabel lblRecommendation;

    JTextField txtPatientId;
    JTextField txtAge;
    JTextField txtBP;
    JTextField txtSugar;
    JTextField txtBMI;
    JTextField txtHeartRate;
    JTextField txtRisk;

    JTextArea txtRecommendation;

    JButton btnAssess;
    JButton btnClear;

    // Logged-in Doctor Employee ID
    private String doctorId;

    // ================= DATABASE DETAILS =================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/healthcare_ehr";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "Mysql@1234";

    // =====================================================


    // ================= CONSTRUCTOR =================

    // Used from DoctorDashboard
    public HealthRiskAssessment(String doctorId) {

        this.doctorId = doctorId;

        createUI();
    }


    // Optional no-argument constructor
    public HealthRiskAssessment() {

        this.doctorId = null;

        createUI();
    }


    // ================= CREATE UI =================

    private void createUI() {

        setTitle("Health Risk Assessment");

        setSize(700, 650);

        setLayout(null);

        getContentPane().setBackground(
                new Color(240, 248, 255)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        // ================= HEADING =================

        heading = new JLabel("HEALTH RISK ASSESSMENT");

        heading.setBounds(170, 20, 400, 35);

        heading.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        add(heading);


        // ================= PATIENT ID =================

        lblPatientId = new JLabel("Patient ID");

        lblPatientId.setBounds(60, 80, 120, 30);

        add(lblPatientId);

        txtPatientId = new JTextField();

        txtPatientId.setBounds(220, 80, 180, 30);

        add(txtPatientId);


        // ================= AGE =================

        lblAge = new JLabel("Age");

        lblAge.setBounds(60, 130, 120, 30);

        add(lblAge);

        txtAge = new JTextField();

        txtAge.setBounds(220, 130, 180, 30);

        add(txtAge);


        // ================= BLOOD PRESSURE =================

        lblBP = new JLabel("Blood Pressure");

        lblBP.setBounds(60, 180, 120, 30);

        add(lblBP);

        txtBP = new JTextField();

        txtBP.setBounds(220, 180, 180, 30);

        add(txtBP);


        // ================= BLOOD SUGAR =================

        lblSugar = new JLabel("Blood Sugar");

        lblSugar.setBounds(60, 230, 120, 30);

        add(lblSugar);

        txtSugar = new JTextField();

        txtSugar.setBounds(220, 230, 180, 30);

        add(txtSugar);


        // ================= BMI =================

        lblBMI = new JLabel("BMI");

        lblBMI.setBounds(60, 280, 120, 30);

        add(lblBMI);

        txtBMI = new JTextField();

        txtBMI.setBounds(220, 280, 180, 30);

        add(txtBMI);


        // ================= HEART RATE =================

        lblHeartRate = new JLabel("Heart Rate");

        lblHeartRate.setBounds(60, 330, 120, 30);

        add(lblHeartRate);

        txtHeartRate = new JTextField();

        txtHeartRate.setBounds(220, 330, 180, 30);

        add(txtHeartRate);


        // ================= ASSESS BUTTON =================

        btnAssess = new JButton("Assess Risk");

        btnAssess.setBounds(120, 390, 150, 40);

        btnAssess.addActionListener(this);

        add(btnAssess);


        // ================= CLEAR BUTTON =================

        btnClear = new JButton("Clear");

        btnClear.setBounds(320, 390, 150, 40);

        btnClear.addActionListener(this);

        add(btnClear);


        // ================= RISK =================

        lblRisk = new JLabel("Risk Level");

        lblRisk.setBounds(60, 460, 120, 30);

        add(lblRisk);

        txtRisk = new JTextField();

        txtRisk.setBounds(220, 460, 180, 30);

        txtRisk.setEditable(false);

        add(txtRisk);


        // ================= RECOMMENDATION =================

        lblRecommendation = new JLabel("Recommendation");

        lblRecommendation.setBounds(60, 510, 120, 30);

        add(lblRecommendation);

        txtRecommendation = new JTextArea();

        txtRecommendation.setBounds(220, 510, 300, 60);

        txtRecommendation.setEditable(false);

        txtRecommendation.setLineWrap(true);

        txtRecommendation.setWrapStyleWord(true);

        add(txtRecommendation);


        setVisible(true);
    }


    // ================= BUTTON ACTION =================

    @Override
    public void actionPerformed(ActionEvent e) {


        // =================================================
        // ASSESS RISK
        // =================================================

        if (e.getSource() == btnAssess) {


            // ---------- Check Patient ID ----------

            String patientText =
                    txtPatientId.getText().trim();


            if (patientText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Patient ID."
                );

                return;
            }


            int patientId;

            try {

                patientId =
                        Integer.parseInt(patientText);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Patient ID must be a number."
                );

                return;
            }


            // =================================================
            // CHECK WHETHER PATIENT IS ASSIGNED TO LOGGED-IN DOCTOR
            // =================================================

            if (doctorId != null) {

                boolean assigned =
                        isPatientAssigned(
                                patientId,
                                doctorId
                        );


                // ---------- UNAUTHORIZED PATIENT ----------

                if (!assigned) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Access Denied!\n\n" +
                                    "This patient is not assigned to you.",
                            "Access Denied",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return;
                }
            }


            // =================================================
            // PARSE HEALTH DETAILS ONLY AFTER AUTHORIZATION
            // =================================================

            String ageText =
                    txtAge.getText().trim();

            String bpText =
                    txtBP.getText().trim();

            String sugarText =
                    txtSugar.getText().trim();

            String bmiText =
                    txtBMI.getText().trim();

            String heartRateText =
                    txtHeartRate.getText().trim();


            // ---------- Check Empty Fields ----------

            if (ageText.isEmpty() ||
                    bpText.isEmpty() ||
                    sugarText.isEmpty() ||
                    bmiText.isEmpty() ||
                    heartRateText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all health details."
                );

                return;
            }


            // ---------- Convert Values ----------

            int age;
            int bp;
            int sugar;
            double bmi;
            int heartRate;


            try {

                age =
                        Integer.parseInt(ageText);

                bp =
                        Integer.parseInt(bpText);

                sugar =
                        Integer.parseInt(sugarText);

                bmi =
                        Double.parseDouble(bmiText);

                heartRate =
                        Integer.parseInt(heartRateText);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid numeric values."
                );

                return;
            }


            // =================================================
            // BASIC RANGE VALIDATION
            // =================================================

            if (age <= 0 || age > 120) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid age between 1 and 120."
                );

                return;
            }

            if (bp <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid blood pressure."
                );

                return;
            }

            if (sugar <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid blood sugar value."
                );

                return;
            }

            if (bmi <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid BMI."
                );

                return;
            }

            if (heartRate <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid heart rate."
                );

                return;
            }


            // =================================================
            // RISK CALCULATION
            // =================================================

            String riskLevel;

            String recommendation;


            if (bp >= 160 ||
                    sugar >= 250 ||
                    bmi >= 30 ||
                    heartRate >= 120) {

                riskLevel = "HIGH";

                recommendation =
                        "Immediate medical attention required.";

            }

            else if (bp >= 140 ||
                    sugar >= 180 ||
                    bmi >= 25 ||
                    heartRate >= 100) {

                riskLevel = "MEDIUM";

                recommendation =
                        "Consult a doctor and monitor health regularly.";

            }

            else {

                riskLevel = "LOW";

                recommendation =
                        "Healthy condition. Continue regular checkups.";
            }


            // ---------- Display Result ----------

            txtRisk.setText(riskLevel);

            txtRecommendation.setText(
                    recommendation
            );
        }


        // =================================================
        // CLEAR BUTTON
        // =================================================

        if (e.getSource() == btnClear) {

            txtPatientId.setText("");

            txtAge.setText("");

            txtBP.setText("");

            txtSugar.setText("");

            txtBMI.setText("");

            txtHeartRate.setText("");

            txtRisk.setText("");

            txtRecommendation.setText("");
        }
    }


    // =====================================================
    // CHECK WHETHER PATIENT IS ASSIGNED TO LOGGED-IN DOCTOR
    // =====================================================

    private boolean isPatientAssigned(
            int patientId,
            String doctorEmployeeId
    ) {


        /*
         * patients.doctor_id contains users.user_id.
         *
         * Example:
         *
         * DOC003 -> users.user_id = 5
         * Patient 6 -> patients.doctor_id = 5
         *
         * Therefore we JOIN patients and users
         * instead of converting DOC003 into number 3.
         */

        String sql =
                "SELECT p.patient_id " +
                        "FROM patients p " +
                        "JOIN users u ON p.doctor_id = u.user_id " +
                        "WHERE p.patient_id = ? " +
                        "AND u.employee_id = ?";


        try {

            // Load MySQL JDBC Driver

            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );


            // Connect to database

            Connection con =
                    DriverManager.getConnection(
                            DB_URL,
                            DB_USER,
                            DB_PASSWORD
                    );


            // Prepare SQL

            PreparedStatement pst =
                    con.prepareStatement(sql);


            // Patient ID

            pst.setInt(
                    1,
                    patientId
            );


            // Doctor Employee ID
            // Example: DOC003

            pst.setString(
                    2,
                    doctorEmployeeId
            );


            // Execute query

            ResultSet rs =
                    pst.executeQuery();


            // If record exists,
            // patient belongs to this doctor

            boolean assigned =
                    rs.next();


            // Close resources

            rs.close();

            pst.close();

            con.close();


            return assigned;


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n" +
                            ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }
}