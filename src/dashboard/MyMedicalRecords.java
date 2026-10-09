package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class MyMedicalRecords extends JFrame {

    private final int patientId;

    private JTable recordsTable;
    private DefaultTableModel tableModel;

    private JLabel totalRecordsLabel;
    private JLabel diagnosisCountLabel;
    private JLabel visitCountLabel;
    private JLabel followUpCountLabel;

    private JTextField searchField;

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color NAVY = new Color(24, 55, 88);
    private static final Color BLUE = new Color(45, 105, 170);
    private static final Color LIGHT_BLUE = new Color(238, 246, 253);
    private static final Color VERY_LIGHT = new Color(247, 250, 253);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(45, 55, 65);
    private static final Color MUTED = new Color(105, 115, 125);
    private static final Color BORDER = new Color(220, 228, 236);
    private static final Color GREEN = new Color(48, 145, 105);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MyMedicalRecords(int patientId) {

        this.patientId = patientId;

        setTitle("My Medical Records");
        setSize(1200, 650);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar = createSidebar();

        add(sidebar, BorderLayout.WEST);


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(VERY_LIGHT);

        add(mainPanel, BorderLayout.CENTER);


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = createHeader();

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(
                content,
                BoxLayout.Y_AXIS
        ));

        content.setBackground(VERY_LIGHT);

        content.setBorder(
                new EmptyBorder(
                        18,
                        22,
                        18,
                        22
                )
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );


        // =====================================================
        // WELCOME CARD
        // =====================================================

        JPanel welcomeCard = createWelcomeCard();

        content.add(welcomeCard);

        content.add(Box.createVerticalStrut(15));


        // =====================================================
        // SUMMARY CARDS
        // =====================================================

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                12,
                                0
                        )
                );

        summaryPanel.setOpaque(false);

        summaryPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        82
                )
        );


        JPanel totalCard =
                createSummaryCard(
                        "Total Records",
                        "0",
                        BLUE,
                        new MedicalIcon(25, BLUE)
                );

        totalRecordsLabel =
                getValueLabel(totalCard);

        JPanel diagnosisCard =
                createSummaryCard(
                        "Diagnoses",
                        "0",
                        new Color(120, 90, 170),
                        new DiagnosisIcon(
                                25,
                                new Color(120, 90, 170)
                        )
                );

        diagnosisCountLabel =
                getValueLabel(diagnosisCard);

        JPanel visitCard =
                createSummaryCard(
                        "Visits",
                        "0",
                        GREEN,
                        new CalendarIcon(
                                25,
                                GREEN
                        )
                );

        visitCountLabel =
                getValueLabel(visitCard);

        JPanel followUpCard =
                createSummaryCard(
                        "Follow-ups",
                        "0",
                        new Color(220, 145, 55),
                        new FollowUpIcon(
                                25,
                                new Color(220, 145, 55)
                        )
                );

        followUpCountLabel =
                getValueLabel(followUpCard);


        summaryPanel.add(totalCard);
        summaryPanel.add(diagnosisCard);
        summaryPanel.add(visitCard);
        summaryPanel.add(followUpCard);

        content.add(summaryPanel);

        content.add(Box.createVerticalStrut(15));


        // =====================================================
        // RECORDS HEADER
        // =====================================================

        JPanel recordsHeader =
                new JPanel(
                        new BorderLayout()
                );

        recordsHeader.setOpaque(false);

        recordsHeader.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );


        JLabel recordsTitle =
                new JLabel("Medical Records");

        recordsTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        recordsTitle.setForeground(TEXT);


        JLabel hint =
                new JLabel(
                        "Double-click a record to view details"
                );

        hint.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        hint.setForeground(MUTED);


        recordsHeader.add(
                recordsTitle,
                BorderLayout.WEST
        );

        recordsHeader.add(
                hint,
                BorderLayout.EAST
        );

        content.add(recordsHeader);

        content.add(Box.createVerticalStrut(8));


        // =====================================================
        // TABLE CARD
        // =====================================================

        JPanel tableCard =
                new JPanel(new BorderLayout(0, 10));

        tableCard.setBackground(WHITE);

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                12,
                                12,
                                12,
                                12
                        )
                )
        );


        // =====================================================
        // SEARCH PANEL
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(10, 0)
                );

        searchPanel.setOpaque(false);

        searchField =
                new JTextField();

        searchField.setPreferredSize(
                new Dimension(
                        300,
                        36
                )
        );

        searchField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                0,
                                12,
                                0,
                                12
                        )
                )
        );

        searchField.setToolTipText(
                "Search diagnosis, prescription or treatment"
        );


        JButton searchButton =
                createButton(
                        "Search",
                        BLUE,
                        100
                );

        JButton refreshButton =
                createButton(
                        "Refresh",
                        GREEN,
                        100
                );


        JPanel leftSearch =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        leftSearch.setOpaque(false);

        leftSearch.add(searchField);
        leftSearch.add(Box.createHorizontalStrut(8));
        leftSearch.add(searchButton);


        JPanel rightSearch =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                0,
                                0
                        )
                );

        rightSearch.setOpaque(false);

        rightSearch.add(refreshButton);


        searchPanel.add(
                leftSearch,
                BorderLayout.WEST
        );

        searchPanel.add(
                rightSearch,
                BorderLayout.EAST
        );


        tableCard.add(
                searchPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {

                "Record ID",
                "Diagnosis",
                "Prescription",
                "Treatment",
                "Visit Date",
                "Follow-up",
                "Doctor ID"
        };


        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        recordsTable =
                new JTable(tableModel);

        recordsTable.setRowHeight(36);

        recordsTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        recordsTable.setForeground(TEXT);

        recordsTable.setBackground(WHITE);

        recordsTable.setGridColor(
                new Color(230, 235, 240)
        );

        recordsTable.setSelectionBackground(
                LIGHT_BLUE
        );

        recordsTable.setSelectionForeground(
                TEXT
        );

        recordsTable.setShowVerticalLines(false);

        recordsTable.setFillsViewportHeight(true);


        // Header

        recordsTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                38
                        )
                );

        recordsTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

        recordsTable.getTableHeader()
                .setForeground(Color.WHITE);

        recordsTable.getTableHeader()
                .setBackground(NAVY);


        // Center renderer

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        recordsTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        recordsTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(center);

        recordsTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(center);

        recordsTable.getColumnModel()
                .getColumn(6)
                .setCellRenderer(center);


        // Column widths

        recordsTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        recordsTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(145);

        recordsTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(180);

        recordsTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(165);

        recordsTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(105);

        recordsTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(105);

        recordsTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(75);


        JScrollPane scrollPane =
                new JScrollPane(recordsTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );


        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );


        content.add(tableCard);


        // =====================================================
        // ACTIONS
        // =====================================================

        searchButton.addActionListener(
                e -> filterRecords()
        );

        searchField.addActionListener(
                e -> filterRecords()
        );

        refreshButton.addActionListener(
                e -> {
                    searchField.setText("");
                    loadMedicalRecords();
                }
        );


        // =====================================================
        // DOUBLE CLICK RECORD
        // =====================================================

        recordsTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e) {

                        if (e.getClickCount() == 2) {

                            int row =
                                    recordsTable.getSelectedRow();

                            if (row >= 0) {

                                showRecordDetails(row);
                            }
                        }
                    }
                }
        );


        // =====================================================
        // LOAD DATA
        // =====================================================

        loadMedicalRecords();


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
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(
                        225,
                        650
                )
        );

        sidebar.setBackground(NAVY);

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );


        // Logo area

        JPanel logoPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                18,
                                18
                        )
                );

        logoPanel.setOpaque(false);

        JLabel icon =
                new JLabel(
                        new MedicalIcon(
                                28,
                                Color.WHITE
                        )
                );

        JLabel title =
                new JLabel(
                        "<html><b>SMART HEALTHCARE</b><br>"
                                + "<font size='3'>EHR SYSTEM</font></html>"
                );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        logoPanel.add(icon);
        logoPanel.add(title);

        sidebar.add(logoPanel);


        // Separator

        JSeparator separator =
                new JSeparator();

        separator.setForeground(
                new Color(
                        70,
                        100,
                        130
                )
        );

        separator.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        1
                )
        );

        sidebar.add(separator);

        sidebar.add(
                Box.createVerticalStrut(15)
        );


        // Sidebar buttons

        addSidebarButton(
                sidebar,
                "Dashboard",
                new DashboardIcon(
                        19,
                        Color.WHITE
                ),
                false
        );

        addSidebarButton(
                sidebar,
                "My Profile",
                new UserIcon(
                        19,
                        Color.WHITE
                ),
                false
        );

        addSidebarButton(
                sidebar,
                "Book Appointment",
                new CalendarIcon(
                        19,
                        Color.WHITE
                ),
                false
        );

        addSidebarButton(
                sidebar,
                "My Appointments",
                new AppointmentIcon(
                        19,
                        Color.WHITE
                ),
                false
        );

        addSidebarButton(
                sidebar,
                "Medical Records",
                new MedicalIcon(
                        19,
                        Color.WHITE
                ),
                true
        );

        addSidebarButton(
                sidebar,
                "Medical Timeline",
                new TimelineIcon(
                        19,
                        Color.WHITE
                ),
                false
        );


        sidebar.add(
                Box.createVerticalGlue()
        );


        addSidebarButton(
                sidebar,
                "Back",
                new BackIcon(
                        19,
                        Color.WHITE
                ),
                false
        );

        return sidebar;
    }


    // =========================================================
    // SIDEBAR BUTTON
    // =========================================================

    private void addSidebarButton(
            JPanel sidebar,
            String text,
            Icon icon,
            boolean selected) {

        JButton button =
                new JButton(text);

        button.setIcon(icon);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setIconTextGap(12);

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(
                selected
                        ? new Color(45, 105, 170)
                        : NAVY
        );

        button.setFocusPainted(false);

        button.setBorder(
                new EmptyBorder(
                        11,
                        20,
                        11,
                        10
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        sidebar.add(button);

        sidebar.add(
                Box.createVerticalStrut(2)
        );
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(WHITE);

        header.setPreferredSize(
                new Dimension(
                        0,
                        68
                )
        );

        header.setBorder(
                new EmptyBorder(
                        0,
                        25,
                        0,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "Medical Records"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        title.setForeground(NAVY);


        JLabel patient =
                new JLabel(
                        "Patient ID: " + patientId
                );

        patient.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        patient.setForeground(MUTED);


        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                patient,
                BorderLayout.EAST
        );

        return header;
    }


    // =========================================================
    // WELCOME CARD
    // =========================================================

    private JPanel createWelcomeCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                LIGHT_BLUE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        210,
                                        225,
                                        240
                                )
                        ),
                        new EmptyBorder(
                                13,
                                18,
                                13,
                                18
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        75
                )
        );


        JPanel left =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                0
                        )
                );

        left.setOpaque(false);


        JLabel icon =
                new JLabel(
                        new MedicalIcon(
                                30,
                                BLUE
                        )
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


        JLabel welcome =
                new JLabel(
                        "Your Medical History"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        welcome.setForeground(NAVY);


        JLabel subtitle =
                new JLabel(
                        "View your diagnosis, treatment and visit information"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(MUTED);


        textPanel.add(welcome);
        textPanel.add(
                Box.createVerticalStrut(3)
        );
        textPanel.add(subtitle);


        left.add(icon);
        left.add(textPanel);


        card.add(
                left,
                BorderLayout.WEST
        );

        return card;
    }


    // =========================================================
    // SUMMARY CARD
    // =========================================================

    private JPanel createSummaryCard(
            String title,
            String value,
            Color color,
            Icon icon) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );


        JLabel iconLabel =
                new JLabel(icon);


        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        titleLabel.setForeground(MUTED);


        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        valueLabel.setForeground(color);


        text.add(titleLabel);

        text.add(
                Box.createVerticalStrut(2)
        );

        text.add(valueLabel);


        card.add(
                iconLabel,
                BorderLayout.WEST
        );

        card.add(
                text,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // GET VALUE LABEL
    // =========================================================

    private JLabel getValueLabel(
            JPanel card) {

        for (Component component :
                card.getComponents()) {

            if (component instanceof JPanel) {

                JPanel panel =
                        (JPanel) component;

                for (Component child :
                        panel.getComponents()) {

                    if (child instanceof JLabel) {

                        JLabel label =
                                (JLabel) child;

                        if (label.getFont()
                                .getStyle()
                                == Font.BOLD) {

                            return label;
                        }
                    }
                }
            }
        }

        return new JLabel("0");
    }


    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color color,
            int width) {

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
                        "Arial",
                        Font.BOLD,
                        12
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

        return button;
    }


    // =========================================================
    // LOAD MEDICAL RECORDS
    // =========================================================

    private void loadMedicalRecords() {

        tableModel.setRowCount(0);


        String sql =
                "SELECT record_id, diagnosis, " +
                        "prescription, treatment, " +
                        "visit_date, follow_up_date, doctor_id " +
                        "FROM medical_records " +
                        "WHERE patient_id = ? " +
                        "ORDER BY visit_date DESC";


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


            int total = 0;
            int diagnoses = 0;
            int visits = 0;
            int followUps = 0;


            while (rs.next()) {

                total++;
                diagnoses++;
                visits++;


                Date followUp =
                        rs.getDate(
                                "follow_up_date"
                        );

                if (followUp != null) {

                    followUps++;
                }


                Object[] row = {

                        rs.getInt(
                                "record_id"
                        ),

                        rs.getString(
                                "diagnosis"
                        ),

                        rs.getString(
                                "prescription"
                        ),

                        rs.getString(
                                "treatment"
                        ),

                        rs.getDate(
                                "visit_date"
                        ),

                        followUp,

                        rs.getObject(
                                "doctor_id"
                        )
                };


                tableModel.addRow(row);
            }


            totalRecordsLabel.setText(
                    String.valueOf(total)
            );

            diagnosisCountLabel.setText(
                    String.valueOf(diagnoses)
            );

            visitCountLabel.setText(
                    String.valueOf(visits)
            );

            followUpCountLabel.setText(
                    String.valueOf(followUps)
            );


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading medical records:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // SEARCH / FILTER
    // =========================================================

    private void filterRecords() {

        String search =
                searchField.getText()
                        .trim()
                        .toLowerCase();


        if (search.isEmpty()) {

            for (int i = 0;
                 i < recordsTable.getRowCount();
                 i++) {

                recordsTable
                        .setRowSelectionInterval(
                                -1,
                                -1
                        );
            }

            loadMedicalRecords();

            return;
        }


        for (int row = 0;
             row < recordsTable.getRowCount();
             row++) {

            boolean found = false;


            for (int col = 1;
                 col <= 3;
                 col++) {

                Object value =
                        recordsTable
                                .getValueAt(
                                        row,
                                        col
                                );

                if (value != null &&
                        value.toString()
                                .toLowerCase()
                                .contains(search)) {

                    found = true;
                    break;
                }
            }


            recordsTable
                    .setRowSelectionInterval(
                            -1,
                            -1
                    );


            if (!found) {

                recordsTable
                        .removeRowSelectionInterval(
                                row,
                                row
                        );
            }
        }


        // Simple message if no matching data
        boolean match = false;

        for (int row = 0;
             row < recordsTable.getRowCount();
             row++) {

            for (int col = 1;
                 col <= 3;
                 col++) {

                Object value =
                        recordsTable.getValueAt(
                                row,
                                col
                        );

                if (value != null &&
                        value.toString()
                                .toLowerCase()
                                .contains(search)) {

                    match = true;
                    break;
                }
            }

            if (match) {
                break;
            }
        }


        if (!match) {

            JOptionPane.showMessageDialog(
                    this,
                    "No matching medical record found.",
                    "Search",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    // =========================================================
    // RECORD DETAILS
    // =========================================================

    private void showRecordDetails(int row) {

        Object recordId =
                recordsTable.getValueAt(
                        row,
                        0
                );

        Object diagnosis =
                recordsTable.getValueAt(
                        row,
                        1
                );

        Object prescription =
                recordsTable.getValueAt(
                        row,
                        2
                );

        Object treatment =
                recordsTable.getValueAt(
                        row,
                        3
                );

        Object visitDate =
                recordsTable.getValueAt(
                        row,
                        4
                );

        Object followUp =
                recordsTable.getValueAt(
                        row,
                        5
                );

        Object doctorId =
                recordsTable.getValueAt(
                        row,
                        6
                );


        JTextArea details =
                new JTextArea();

        details.setEditable(false);

        details.setLineWrap(true);

        details.setWrapStyleWord(true);

        details.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        details.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        details.setText(
                "Record ID       : " + recordId +
                        "\n\nDiagnosis       : " + diagnosis +
                        "\n\nPrescription    : " + prescription +
                        "\n\nTreatment       : " + treatment +
                        "\n\nVisit Date      : " + visitDate +
                        "\n\nFollow-up Date  : " + followUp +
                        "\n\nDoctor ID       : " + doctorId
        );


        JScrollPane scrollPane =
                new JScrollPane(details);

        scrollPane.setPreferredSize(
                new Dimension(
                        520,
                        330
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Medical Record Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // ICON CLASSES
    // =========================================================

    private abstract static class BaseIcon
            implements Icon {

        protected int size;
        protected Color color;

        BaseIcon(
                int size,
                Color color) {

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

        protected Graphics2D setup(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.setStroke(
                    new BasicStroke(
                            2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            return g2;
        }
    }


    private static class MedicalIcon
            extends BaseIcon {

        MedicalIcon(
                int size,
                Color color) {

            super(size, color);
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
                    4,
                    4
            );

            int cx = x + size / 2;
            int cy = y + size / 2;

            g2.drawLine(
                    cx - 6,
                    cy,
                    cx + 6,
                    cy
            );

            g2.drawLine(
                    cx,
                    cy - 6,
                    cx,
                    cy + 6
            );

            g2.dispose();
        }
    }


    private static class DiagnosisIcon
            extends BaseIcon {

        DiagnosisIcon(
                int size,
                Color color) {

            super(size, color);
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
                    x + 3,
                    y + 3,
                    size - 6,
                    size - 6
            );

            g2.drawLine(
                    x + 7,
                    y + size / 2,
                    x + size - 7,
                    y + size / 2
            );

            g2.drawLine(
                    x + size / 2,
                    y + 7,
                    x + size / 2,
                    y + size - 7
            );

            g2.dispose();
        }
    }


    private static class CalendarIcon
            extends BaseIcon {

        CalendarIcon(
                int size,
                Color color) {

            super(size, color);
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
                    x + 8,
                    y + 2,
                    x + 8,
                    y + 7
            );

            g2.drawLine(
                    x + size - 8,
                    y + 2,
                    x + size - 8,
                    y + 7
            );

            g2.dispose();
        }
    }


    private static class FollowUpIcon
            extends BaseIcon {

        FollowUpIcon(
                int size,
                Color color) {

            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            g2.drawArc(
                    x + 3,
                    y + 3,
                    size - 7,
                    size - 7,
                    40,
                    280
            );

            Polygon arrow =
                    new Polygon();

            arrow.addPoint(
                    x + size - 3,
                    y + 6
            );

            arrow.addPoint(
                    x + size - 10,
                    y + 5
            );

            arrow.addPoint(
                    x + size - 5,
                    y + 12
            );

            g2.fillPolygon(arrow);

            g2.dispose();
        }
    }


    private static class DashboardIcon
            extends BaseIcon {

        DashboardIcon(
                int size,
                Color color) {

            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            int s = size / 2 - 2;

            g2.drawRoundRect(
                    x + 2,
                    y + 2,
                    s,
                    s,
                    2,
                    2
            );

            g2.drawRoundRect(
                    x + s + 4,
                    y + 2,
                    s,
                    s,
                    2,
                    2
            );

            g2.drawRoundRect(
                    x + 2,
                    y + s + 4,
                    s,
                    s,
                    2,
                    2
            );

            g2.drawRoundRect(
                    x + s + 4,
                    y + s + 4,
                    s,
                    s,
                    2,
                    2
            );

            g2.dispose();
        }
    }


    private static class UserIcon
            extends BaseIcon {

        UserIcon(
                int size,
                Color color) {

            super(size, color);
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
                    x + size / 3,
                    y + 2,
                    size / 3,
                    size / 3
            );

            g2.drawArc(
                    x + 3,
                    y + size / 2,
                    size - 6,
                    size / 2 - 2,
                    0,
                    180
            );

            g2.dispose();
        }
    }


    private static class AppointmentIcon
            extends BaseIcon {

        AppointmentIcon(
                int size,
                Color color) {

            super(size, color);
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
                    y + 4,
                    size - 6,
                    size - 6,
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

            g2.drawLine(
                    x + 7,
                    y + 16,
                    x + 12,
                    y + 19
            );

            g2.drawLine(
                    x + 12,
                    y + 19,
                    x + 18,
                    y + 13
            );

            g2.dispose();
        }
    }


    private static class TimelineIcon
            extends BaseIcon {

        TimelineIcon(
                int size,
                Color color) {

            super(size, color);
        }

        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 =
                    setup(g);

            int cx =
                    x + size / 2;

            g2.drawLine(
                    cx,
                    y + 2,
                    cx,
                    y + size - 2
            );

            g2.fillOval(
                    cx - 3,
                    y + 4,
                    6,
                    6
            );

            g2.fillOval(
                    cx - 3,
                    y + size / 2 - 3,
                    6,
                    6
            );

            g2.fillOval(
                    cx - 3,
                    y + size - 10,
                    6,
                    6
            );

            g2.dispose();
        }
    }


    private static class BackIcon
            extends BaseIcon {

        BackIcon(
                int size,
                Color color) {

            super(size, color);
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
                    x + 4,
                    y + size / 2,
                    x + size - 4,
                    y + size / 2
            );

            g2.drawLine(
                    x + 4,
                    y + size / 2,
                    x + 10,
                    y + 6
            );

            g2.drawLine(
                    x + 4,
                    y + size / 2,
                    x + 10,
                    y + size - 6
            );

            g2.dispose();
        }
    }
}