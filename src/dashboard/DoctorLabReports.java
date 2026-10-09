package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class DoctorLabReports extends JFrame {

    private final String employeeId;

    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    private JLabel lblReportCount;
    private JTextField txtSearch;


    // =========================================================
    // COLORS
    // =========================================================

    private final Color NAVY = new Color(15, 43, 70);
    private final Color BLUE = new Color(30, 91, 150);
    private final Color LIGHT_BLUE = new Color(235, 244, 252);
    private final Color BORDER = new Color(215, 225, 235);
    private final Color TEXT = new Color(45, 55, 65);
    private final Color MUTED = new Color(105, 115, 125);
    private final Color WHITE = Color.WHITE;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DoctorLabReports(String employeeId) {

        this.employeeId = employeeId;

        setTitle("Doctor - Lab Reports");
        setSize(1200, 620);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 248, 252));


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setPreferredSize(new Dimension(1200, 82));
        header.setBorder(new EmptyBorder(12, 25, 12, 25));


        // LEFT HEADER
        JPanel headerLeft = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                12,
                8
        ));

        headerLeft.setOpaque(false);


        LabReportIcon reportIcon = new LabReportIcon(40);

        headerLeft.add(reportIcon);


        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(
                titlePanel,
                BoxLayout.Y_AXIS
        ));
        titlePanel.setOpaque(false);


        JLabel title = new JLabel("LABORATORY REPORTS");

        title.setForeground(Color.WHITE);
        title.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                21
        ));


        JLabel subtitle = new JLabel(
                "View completed laboratory test reports"
        );

        subtitle.setForeground(new Color(
                195, 215, 232
        ));

        subtitle.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));


        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        headerLeft.add(titlePanel);

        header.add(headerLeft, BorderLayout.WEST);


        // RIGHT HEADER

        JPanel doctorPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        8
                )
        );

        doctorPanel.setOpaque(false);


        JLabel doctorIcon = new JLabel("DOCTOR");

        doctorIcon.setForeground(Color.WHITE);
        doctorIcon.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                12
        ));


        JLabel employeeLabel = new JLabel(
                employeeId
        );

        employeeLabel.setForeground(
                new Color(205, 220, 235)
        );

        employeeLabel.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));


        doctorPanel.add(doctorIcon);
        doctorPanel.add(employeeLabel);

        header.add(
                doctorPanel,
                BorderLayout.EAST
        );


        add(header, BorderLayout.NORTH);


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel mainPanel = new JPanel(
                new BorderLayout(0, 12)
        );

        mainPanel.setBackground(
                new Color(245, 248, 252)
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        12,
                        20
                )
        );


        // =====================================================
        // TOP INFORMATION PANEL
        // =====================================================

        JPanel topPanel = new JPanel(
                new BorderLayout(15, 0)
        );

        topPanel.setOpaque(false);


        // REPORT COUNT CARD

        JPanel countCard = createCountCard();

        topPanel.add(
                countCard,
                BorderLayout.WEST
        );


        // SEARCH CARD

        JPanel searchCard = new JPanel(
                new BorderLayout(10, 0)
        );

        searchCard.setBackground(Color.WHITE);

        searchCard.setBorder(
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


        JLabel searchLabel = new JLabel(
                "Search Reports"
        );

        searchLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                13
        ));

        searchLabel.setForeground(TEXT);


        txtSearch = new JTextField();

        txtSearch.setPreferredSize(
                new Dimension(360, 35)
        );

        txtSearch.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                13
        ));

        txtSearch.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );


        JLabel searchHint = new JLabel(
                "Patient, test or result"
        );

        searchHint.setForeground(MUTED);
        searchHint.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                11
        ));


        JPanel searchFieldPanel = new JPanel(
                new BorderLayout(8, 0)
        );

        searchFieldPanel.setOpaque(false);

        searchFieldPanel.add(
                txtSearch,
                BorderLayout.CENTER
        );

        searchFieldPanel.add(
                searchHint,
                BorderLayout.EAST
        );


        searchCard.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchCard.add(
                searchFieldPanel,
                BorderLayout.CENTER
        );


        topPanel.add(
                searchCard,
                BorderLayout.CENTER
        );


        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {

                "Test ID",
                "Patient Name",
                "Doctor Name",
                "Test Name",
                "Result",
                "Test Date",
                "Status"
        };


        model = new DefaultTableModel(
                columns,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };


        table = new JTable(model);

        table.setRowHeight(34);

        table.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));

        table.setForeground(TEXT);

        table.setBackground(Color.WHITE);

        table.setGridColor(
                new Color(230, 235, 240)
        );

        table.setSelectionBackground(
                LIGHT_BLUE
        );

        table.setSelectionForeground(
                TEXT
        );

        table.setShowVerticalLines(false);

        table.setIntercellSpacing(
                new Dimension(0, 1)
        );


        // TABLE HEADER

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );

        table.getTableHeader().setBackground(
                NAVY
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        // COLUMN WIDTHS

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(170);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(160);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(170);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(300);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(100);


        // CELL ALIGNMENT

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        table.getColumnModel()
                .getColumn(5)
                .setCellRenderer(centerRenderer);

        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(centerRenderer);


        // STATUS RENDERER

        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        new StatusRenderer()
                );


        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        scrollPane.getViewport()
                .setBackground(Color.WHITE);


        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM BUTTON PANEL
        // =====================================================

        JPanel bottomPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        5
                )
        );

        bottomPanel.setBackground(
                new Color(245, 248, 252)
        );


        JButton btnBack = createButton(
                "Back",
                new Color(110, 120, 130)
        );


        JButton btnRefresh = createButton(
                "Refresh Reports",
                BLUE
        );


        bottomPanel.add(btnBack);
        bottomPanel.add(btnRefresh);


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // SEARCH
        // =====================================================

        sorter =
                new TableRowSorter<>(model);

        table.setRowSorter(sorter);


        txtSearch.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            private void filter() {

                                String text =
                                        txtSearch
                                                .getText()
                                                .trim();

                                if (text.isEmpty()) {

                                    sorter.setRowFilter(
                                            null
                                    );

                                } else {

                                    sorter.setRowFilter(
                                            RowFilter.regexFilter(
                                                    "(?i)"
                                                            + java.util.regex.Pattern
                                                            .quote(text)
                                            )
                                    );
                                }
                            }


                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filter();
                            }


                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filter();
                            }


                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                filter();
                            }
                        }
                );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        btnRefresh.addActionListener(
                e -> {

                    txtSearch.setText("");

                    loadReports();
                }
        );


        btnBack.addActionListener(
                e -> dispose()
        );


        // =====================================================
        // DOUBLE CLICK REPORT
        // =====================================================

        table.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (e.getClickCount() == 2
                                && SwingUtilities.isLeftMouseButton(e)) {

                            int selectedRow =
                                    table.getSelectedRow();

                            if (selectedRow >= 0) {

                                int modelRow =
                                        table.convertRowIndexToModel(
                                                selectedRow
                                        );

                                showReportDetails(
                                        modelRow
                                );
                            }
                        }
                    }
                }
        );


        // =====================================================
        // LOAD DATA
        // =====================================================

        loadReports();


        setVisible(true);
    }


    // =========================================================
    // COUNT CARD
    // =========================================================

    private JPanel createCountCard() {

        JPanel card = new JPanel(
                new BorderLayout(12, 0)
        );

        card.setPreferredSize(
                new Dimension(240, 68)
        );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
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


        LabReportIcon icon =
                new LabReportIcon(36);

        card.add(
                icon,
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


        JLabel label = new JLabel(
                "Completed Reports"
        );

        label.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                11
        ));

        label.setForeground(MUTED);


        lblReportCount = new JLabel(
                "0"
        );

        lblReportCount.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        lblReportCount.setForeground(
                BLUE
        );


        textPanel.add(label);

        textPanel.add(
                Box.createVerticalStrut(2)
        );

        textPanel.add(
                lblReportCount
        );


        card.add(
                textPanel,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // BUTTON DESIGN
    // =========================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        text.equals("Back")
                                ? 100
                                : 155,
                        38
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
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
                        15,
                        5,
                        15
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
    // LOAD COMPLETED REPORTS
    // =========================================================

    private void loadReports() {

        model.setRowCount(0);


        String sql =
                "SELECT " +
                        "l.test_id, " +
                        "p.patient_name, " +
                        "u.full_name AS doctor_name, " +
                        "l.test_name, " +
                        "l.test_result, " +
                        "l.test_date, " +
                        "l.test_status " +

                        "FROM lab_tests l " +

                        "INNER JOIN patients p " +
                        "ON l.patient_id = p.patient_id " +

                        "INNER JOIN users u " +
                        "ON l.doctor_id = u.user_id " +

                        "WHERE u.employee_id = ? " +
                        "AND u.role = 'Doctor' " +
                        "AND l.test_status = 'Completed' " +

                        "ORDER BY l.test_date DESC";


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


            try (ResultSet rs =
                         ps.executeQuery()) {


                while (rs.next()) {

                    Object[] row = {

                            rs.getInt(
                                    "test_id"
                            ),

                            rs.getString(
                                    "patient_name"
                            ),

                            rs.getString(
                                    "doctor_name"
                            ),

                            rs.getString(
                                    "test_name"
                            ),

                            rs.getString(
                                    "test_result"
                            ),

                            rs.getDate(
                                    "test_date"
                            ),

                            rs.getString(
                                    "test_status"
                            )
                    };


                    model.addRow(row);
                }
            }


            lblReportCount.setText(
                    String.valueOf(
                            model.getRowCount()
                    )
            );


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading lab reports:\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // REPORT DETAILS
    // =========================================================

    private void showReportDetails(
            int modelRow
    ) {

        String patient =
                String.valueOf(
                        model.getValueAt(
                                modelRow,
                                1
                        )
                );

        String doctor =
                String.valueOf(
                        model.getValueAt(
                                modelRow,
                                2
                        )
                );

        String test =
                String.valueOf(
                        model.getValueAt(
                                modelRow,
                                3
                        )
                );

        String result =
                String.valueOf(
                        model.getValueAt(
                                modelRow,
                                4
                        )
                );

        String date =
                String.valueOf(
                        model.getValueAt(
                                modelRow,
                                5
                        )
                );


        JTextArea area =
                new JTextArea();

        area.setText(
                "Patient Name : " + patient
                        + "\n\n"
                        + "Doctor       : " + doctor
                        + "\n\n"
                        + "Test         : " + test
                        + "\n\n"
                        + "Test Date    : " + date
                        + "\n\n"
                        + "Result       :\n"
                        + result
        );


        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        area.setEditable(false);

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setBackground(
                Color.WHITE
        );

        area.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );


        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(
                        480,
                        300
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Laboratory Report Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private static class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            if (!isSelected) {

                setForeground(
                        new Color(
                                35,
                                125,
                                75
                        )
                );

                setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                11
                        )
                );
            }


            return component;
        }
    }


    // =========================================================
    // LAB REPORT ICON
    // =========================================================

    private static class LabReportIcon
            extends JPanel {

        private final int size;


        LabReportIcon(int size) {

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


            // Circle

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


            // Document

            g2.setColor(
                    new Color(
                            30,
                            91,
                            150
                    )
            );

            g2.fillRoundRect(
                    w / 4,
                    h / 5,
                    w / 2,
                    h * 3 / 5,
                    4,
                    4
            );


            // Lines

            g2.setColor(Color.WHITE);

            g2.setStroke(
                    new BasicStroke(
                            2f
                    )
            );


            g2.drawLine(
                    w / 3,
                    h / 2,
                    w * 2 / 3,
                    h / 2
            );

            g2.drawLine(
                    w / 3,
                    h * 3 / 5,
                    w * 2 / 3,
                    h * 3 / 5
            );

            g2.drawLine(
                    w / 3,
                    h * 7 / 10,
                    w * 3 / 5,
                    h * 7 / 10
            );


            g2.dispose();
        }
    }
}