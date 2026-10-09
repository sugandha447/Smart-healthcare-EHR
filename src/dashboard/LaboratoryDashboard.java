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

public class LaboratoryDashboard extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalValue;
    private JLabel pendingValue;
    private JLabel completedValue;

    private final Color NAVY = new Color(20, 45, 78);
    private final Color BLUE = new Color(43, 111, 180);
    private final Color LIGHT_BLUE = new Color(242, 247, 252);
    private final Color BORDER = new Color(220, 228, 238);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color GREEN = new Color(40, 150, 100);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LaboratoryDashboard(String employeeId) {

        setTitle("Smart Healthcare EHR - Laboratory");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(LIGHT_BLUE);
        setLayout(new BorderLayout());


        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(
                new Dimension(230, 650)
        );

        sidebar.setBackground(NAVY);

        sidebar.setLayout(
                new BorderLayout()
        );


        // =====================================================
        // LOGO
        // =====================================================

        JPanel logoPanel = new JPanel();

        logoPanel.setOpaque(false);

        logoPanel.setLayout(
                new BorderLayout()
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        25,
                        20,
                        20,
                        15
                )
        );


        JLabel logoIcon =
                new JLabel(
                        new LaboratoryIcon(
                                30,
                                Color.WHITE
                        )
                );


        JLabel logoText =
                new JLabel(
                        "<html><b>SMART HEALTHCARE</b><br>EHR SYSTEM</html>"
                );

        logoText.setForeground(
                Color.WHITE
        );

        logoText.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );


        JPanel logoTextPanel =
                new JPanel(
                        new BorderLayout()
                );

        logoTextPanel.setOpaque(false);

        logoTextPanel.setBorder(
                new EmptyBorder(
                        0,
                        10,
                        0,
                        0
                )
        );

        logoTextPanel.add(
                logoText,
                BorderLayout.CENTER
        );


        logoPanel.add(
                logoIcon,
                BorderLayout.WEST
        );

        logoPanel.add(
                logoTextPanel,
                BorderLayout.CENTER
        );


        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // SIDEBAR MENU
        // =====================================================

        JPanel menuPanel =
                new JPanel();

        menuPanel.setOpaque(false);

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );


        JButton btnDashboard =
                createSidebarButton(
                        "Dashboard",
                        new DashboardIcon(
                                20,
                                Color.WHITE
                        )
                );


        JButton btnPending =
                createSidebarButton(
                        "Pending Tests",
                        new TestIcon(
                                20,
                                Color.WHITE
                        )
                );


        JButton btnEnter =
                createSidebarButton(
                        "Enter Result",
                        new EditIcon(
                                20,
                                Color.WHITE
                        )
                );


        // IMPORTANT:
        // Sidebar refresh button has a different variable name
        // to avoid duplicate-variable error.

        JButton btnSidebarRefresh =
                createSidebarButton(
                        "Refresh",
                        new RefreshIcon(
                                20,
                                Color.WHITE
                        )
                );


        menuPanel.add(
                btnDashboard
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                btnPending
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                btnEnter
        );

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(
                btnSidebarRefresh
        );


        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // LOGOUT
        // =====================================================

        JButton btnLogout =
                createSidebarButton(
                        "Logout",
                        new LogoutIcon(
                                20,
                                Color.WHITE
                        )
                );


        JPanel logoutPanel =
                new JPanel();

        logoutPanel.setOpaque(false);

        logoutPanel.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        20,
                        12
                )
        );

        logoutPanel.setLayout(
                new BorderLayout()
        );


        logoutPanel.add(
                btnLogout,
                BorderLayout.CENTER
        );


        sidebar.add(
                logoutPanel,
                BorderLayout.SOUTH
        );


        add(
                sidebar,
                BorderLayout.WEST
        );


        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel();

        mainPanel.setBackground(
                LIGHT_BLUE
        );

        mainPanel.setLayout(
                new BorderLayout()
        );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(false);


        JPanel titlePanel =
                new JPanel(
                        new BorderLayout()
                );

        titlePanel.setOpaque(false);


        JLabel heading =
                new JLabel(
                        "Laboratory Dashboard"
                );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        heading.setForeground(
                NAVY
        );


        JLabel welcome =
                new JLabel(
                        "Welcome, Laboratory Staff  •  "
                                + employeeId
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        welcome.setForeground(
                new Color(
                        100,
                        110,
                        125
                )
        );


        titlePanel.add(
                heading,
                BorderLayout.NORTH
        );

        titlePanel.add(
                welcome,
                BorderLayout.SOUTH
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );


        JLabel labIcon =
                new JLabel(
                        new LaboratoryIcon(
                                38,
                                BLUE
                        )
                );


        headerPanel.add(
                labIcon,
                BorderLayout.EAST
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CENTER PANEL
        // =====================================================

        JPanel centerPanel =
                new JPanel();

        centerPanel.setOpaque(false);

        centerPanel.setLayout(
                new BorderLayout(
                        0,
                        15
                )
        );


        // =====================================================
        // STAT CARDS
        // =====================================================

        JPanel statsPanel =
                new JPanel();

        statsPanel.setOpaque(false);

        statsPanel.setLayout(
                new GridLayout(
                        1,
                        3,
                        15,
                        0
                )
        );


        statsPanel.setPreferredSize(
                new Dimension(
                        0,
                        90
                )
        );


        JPanel totalCard =
                createStatCard(
                        "Total Tests",
                        new TestIcon(
                                27,
                                BLUE
                        ),
                        "total"
                );


        JPanel pendingCard =
                createStatCard(
                        "Pending Tests",
                        new ClockIcon(
                                27,
                                new Color(
                                        220,
                                        150,
                                        45
                                )
                        ),
                        "pending"
                );


        JPanel completedCard =
                createStatCard(
                        "Completed Tests",
                        new CheckIcon(
                                27,
                                GREEN
                        ),
                        "completed"
                );


        statsPanel.add(
                totalCard
        );

        statsPanel.add(
                pendingCard
        );

        statsPanel.add(
                completedCard
        );


        centerPanel.add(
                statsPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE PANEL
        // =====================================================

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout()
                );

        tablePanel.setBackground(
                Color.WHITE
        );

        tablePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );


        JPanel tableHeader =
                new JPanel(
                        new BorderLayout()
                );

        tableHeader.setOpaque(false);


        JLabel tableTitle =
                new JLabel(
                        "Laboratory Test Requests"
                );

        tableTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        17
                )
        );

        tableTitle.setForeground(
                NAVY
        );


        JLabel tableHint =
                new JLabel(
                        "Select a pending test to enter result"
                );

        tableHint.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        tableHint.setForeground(
                new Color(
                        120,
                        130,
                        145
                )
        );


        tableHeader.add(
                tableTitle,
                BorderLayout.WEST
        );

        tableHeader.add(
                tableHint,
                BorderLayout.EAST
        );


        tablePanel.add(
                tableHeader,
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


        model =
                new DefaultTableModel(
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


        table =
                new JTable(model);


        table.setRowHeight(
                32
        );


        table.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );


        table.setForeground(
                TEXT
        );


        table.setBackground(
                Color.WHITE
        );


        table.setSelectionBackground(
                new Color(
                        220,
                        235,
                        250
                )
        );


        table.setSelectionForeground(
                TEXT
        );


        table.setGridColor(
                new Color(
                        235,
                        238,
                        243
                )
        );


        table.setShowVerticalLines(
                false
        );


        table.setShowHorizontalLines(
                true
        );


        // =====================================================
        // TABLE HEADER
        // =====================================================

        table.getTableHeader().setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );


        table.getTableHeader().setForeground(
                Color.WHITE
        );


        table.getTableHeader().setBackground(
                NAVY
        );


        table.getTableHeader().setPreferredSize(
                new Dimension(
                        0,
                        38
                )
        );


        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(60);


        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);


        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(150);


        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(160);


        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(230);


        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(100);


        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(100);


        // =====================================================
        // STATUS RENDERER
        // =====================================================

        table.getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        new DefaultTableCellRenderer() {

                            @Override
                            public Component
                            getTableCellRendererComponent(
                                    JTable table,
                                    Object value,
                                    boolean isSelected,
                                    boolean hasFocus,
                                    int row,
                                    int column
                            ) {

                                JLabel label =
                                        (JLabel)
                                                super.getTableCellRendererComponent(
                                                        table,
                                                        value,
                                                        isSelected,
                                                        hasFocus,
                                                        row,
                                                        column
                                                );


                                label.setHorizontalAlignment(
                                        SwingConstants.CENTER
                                );


                                if (!isSelected) {

                                    if (
                                            "Completed"
                                                    .equalsIgnoreCase(
                                                            String.valueOf(
                                                                    value
                                                            )
                                                    )
                                    ) {

                                        label.setForeground(
                                                new Color(
                                                        30,
                                                        130,
                                                        80
                                                )
                                        );

                                    } else {

                                        label.setForeground(
                                                new Color(
                                                        210,
                                                        135,
                                                        25
                                                )
                                        );
                                    }
                                }


                                label.setFont(
                                        new Font(
                                                "Arial",
                                                Font.BOLD,
                                                12
                                        )
                                );


                                return label;
                            }
                        }
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        table
                );


        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );


        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        centerPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM BUTTONS
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.setPreferredSize(
                new Dimension(
                        0,
                        45
                )
        );


        JButton btnEnterResult =
                createActionButton(
                        "Enter Result",
                        new EditIcon(
                                18,
                                Color.WHITE
                        ),
                        BLUE
                );


        // IMPORTANT:
        // Main refresh button has its own name.
        // It does not conflict with btnSidebarRefresh.

        JButton btnRefresh =
                createActionButton(
                        "Refresh",
                        new RefreshIcon(
                                18,
                                Color.WHITE
                        ),
                        new Color(
                                95,
                                110,
                                125
                        )
                );


        buttonPanel.add(
                btnEnterResult
        );


        buttonPanel.add(
                btnRefresh
        );


        centerPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );


        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // ACTIONS
        // =====================================================

        btnDashboard.addActionListener(
                e -> {
                    // Already on dashboard
                }
        );


        btnPending.addActionListener(
                e -> showPendingTests()
        );


        btnEnter.addActionListener(
                e -> enterResult()
        );


        btnSidebarRefresh.addActionListener(
                e -> loadTests()
        );


        btnEnterResult.addActionListener(
                e -> enterResult()
        );


        btnRefresh.addActionListener(
                e -> loadTests()
        );


        btnLogout.addActionListener(
                e -> {

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

                        new login.Login();
                    }
                }
        );


        // =====================================================
        // LOAD DATA
        // =====================================================

        loadTests();

        setVisible(true);
    }


    // =========================================================
    // CREATE SIDEBAR BUTTON
    // =========================================================

    private JButton createSidebarButton(
            String text,
            Icon icon
    ) {

        JButton button =
                new JButton(
                        text,
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
                        Font.BOLD,
                        13
                )
        );


        button.setBackground(
                NAVY
        );


        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        14,
                        10,
                        10
                )
        );


        button.setFocusPainted(
                false
        );


        button.setContentAreaFilled(
                false
        );


        button.setOpaque(
                false
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setOpaque(
                                true
                        );

                        button.setBackground(
                                new Color(
                                        35,
                                        68,
                                        108
                                )
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setOpaque(
                                false
                        );

                        button.setBackground(
                                NAVY
                        );
                    }
                }
        );


        return button;
    }


    // =========================================================
    // CREATE ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Icon icon,
            Color background
    ) {

        JButton button =
                new JButton(
                        text,
                        icon
                );


        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );


        button.setForeground(
                Color.WHITE
        );


        button.setBackground(
                background
        );


        button.setFocusPainted(
                false
        );


        button.setBorder(
                BorderFactory.createEmptyBorder(
                        9,
                        18,
                        9,
                        18
                )
        );


        button.setIconTextGap(
                8
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // =========================================================
    // CREATE STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            Icon icon,
            String type
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
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
                                12,
                                15,
                                12,
                                15
                        )
                )
        );


        JLabel iconLabel =
                new JLabel(
                        icon
                );


        card.add(
                iconLabel,
                BorderLayout.WEST
        );


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
                new Color(
                        105,
                        115,
                        130
                )
        );


        JLabel valueLabel =
                new JLabel(
                        "0"
                );


        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );


        valueLabel.setForeground(
                NAVY
        );


        JPanel textPanel =
                new JPanel();


        textPanel.setOpaque(
                false
        );


        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );


        textPanel.setBorder(
                new EmptyBorder(
                        0,
                        12,
                        0,
                        0
                )
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
                valueLabel
        );


        card.add(
                textPanel,
                BorderLayout.CENTER
        );


        if (
                "total".equals(type)
        ) {

            totalValue =
                    valueLabel;

        } else if (
                "pending".equals(type)
        ) {

            pendingValue =
                    valueLabel;

        } else if (
                "completed".equals(type)
        ) {

            completedValue =
                    valueLabel;
        }


        return card;
    }


    // =========================================================
    // LOAD LAB TESTS
    // =========================================================

    private void loadTests() {

        model.setRowCount(
                0
        );


        String sql =
                "SELECT l.test_id, " +
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

                        "ORDER BY " +
                        "CASE " +
                        "WHEN l.test_status = 'Pending' THEN 0 " +
                        "ELSE 1 " +
                        "END, " +
                        "l.test_id DESC";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                sql
                        );

                ResultSet rs =
                        ps.executeQuery()
        ) {


            while (
                    rs.next()
            ) {

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


                model.addRow(
                        row
                );
            }


            updateStatistics();


        } catch (
                Exception e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading lab tests:\n"
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // UPDATE STATISTICS
    // =========================================================

    private void updateStatistics() {

        int total = 0;
        int pending = 0;
        int completed = 0;


        for (
                int i = 0;
                i < model.getRowCount();
                i++
        ) {

            total++;


            String status =
                    String.valueOf(
                            model.getValueAt(
                                    i,
                                    6
                            )
                    );


            if (
                    "Pending"
                            .equalsIgnoreCase(
                                    status
                            )
            ) {

                pending++;

            } else if (
                    "Completed"
                            .equalsIgnoreCase(
                                    status
                            )
            ) {

                completed++;
            }
        }


        if (
                totalValue != null
        ) {

            totalValue.setText(
                    String.valueOf(
                            total
                    )
            );
        }


        if (
                pendingValue != null
        ) {

            pendingValue.setText(
                    String.valueOf(
                            pending
                    )
            );
        }


        if (
                completedValue != null
        ) {

            completedValue.setText(
                    String.valueOf(
                            completed
                    )
            );
        }
    }


    // =========================================================
    // SHOW PENDING TESTS
    // =========================================================

    private void showPendingTests() {

        for (
                int i = 0;
                i < model.getRowCount();
                i++
        ) {

            String status =
                    String.valueOf(
                            model.getValueAt(
                                    i,
                                    6
                            )
                    );


            if (
                    "Pending"
                            .equalsIgnoreCase(
                                    status
                            )
            ) {

                table.setRowSelectionInterval(
                        i,
                        i
                );


                table.scrollRectToVisible(
                        table.getCellRect(
                                i,
                                0,
                                true
                        )
                );


                return;
            }
        }


        JOptionPane.showMessageDialog(
                this,
                "No pending laboratory tests found."
        );
    }


    // =========================================================
    // ENTER TEST RESULT
    // =========================================================

    private void enterResult() {

        int selectedRow =
                table.getSelectedRow();


        if (
                selectedRow == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a test first."
            );

            return;
        }


        int testId =
                (int) model.getValueAt(
                        selectedRow,
                        0
                );


        String status =
                model.getValueAt(
                        selectedRow,
                        6
                ).toString();


        if (
                "Completed"
                        .equalsIgnoreCase(
                                status
                        )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This test is already completed."
            );

            return;
        }


        String patientName =
                model.getValueAt(
                        selectedRow,
                        1
                ).toString();


        String doctorName =
                model.getValueAt(
                        selectedRow,
                        2
                ).toString();


        String testName =
                model.getValueAt(
                        selectedRow,
                        3
                ).toString();


        String result =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Result for:\n\n"
                                + "Patient: "
                                + patientName
                                + "\nDoctor: "
                                + doctorName
                                + "\nTest: "
                                + testName
                                + "\n\nResult:"
                );


        if (
                result == null ||
                        result.trim().isEmpty()
        ) {

            return;
        }


        String sql =
                "UPDATE lab_tests " +
                        "SET test_result = ?, " +
                        "test_date = CURDATE(), " +
                        "test_status = 'Completed' " +
                        "WHERE test_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(
                                sql
                        )
        ) {


            ps.setString(
                    1,
                    result.trim()
            );


            ps.setInt(
                    2,
                    testId
            );


            ps.executeUpdate();


            JOptionPane.showMessageDialog(
                    this,
                    "Test result updated successfully."
            );


            loadTests();


        } catch (
                Exception e
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating result:\n"
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // LABORATORY ICON
    // =========================================================

    static class LaboratoryIcon
            implements Icon {

        private final int size;
        private final Color color;


        LaboratoryIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            g2.setColor(
                    color
            );


            int w = size;
            int h = size;


            g2.drawRoundRect(
                    x + w / 4,
                    y + h / 5,
                    w / 2,
                    h * 3 / 5,
                    5,
                    5
            );


            g2.drawLine(
                    x + w / 2,
                    y + 2,
                    x + w / 2,
                    y + h / 5
            );


            g2.drawLine(
                    x + w * 3 / 8,
                    y + 2,
                    x + w * 5 / 8,
                    y + 2
            );


            g2.drawLine(
                    x + w * 3 / 8,
                    y + h / 2,
                    x + w * 5 / 8,
                    y + h / 2
            );


            g2.drawLine(
                    x + w * 4 / 8,
                    y + h * 3 / 8,
                    x + w * 4 / 8,
                    y + h * 5 / 8
            );


            g2.dispose();
        }
    }


    // =========================================================
    // TEST ICON
    // =========================================================

    static class TestIcon
            implements Icon {

        private final int size;
        private final Color color;


        TestIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            g2.setColor(
                    color
            );


            g2.drawRoundRect(
                    x + 3,
                    y + 3,
                    size - 6,
                    size - 6,
                    4,
                    4
            );


            g2.drawLine(
                    x + 7,
                    y + 9,
                    x + size - 7,
                    y + 9
            );


            g2.drawLine(
                    x + 7,
                    y + 14,
                    x + size - 7,
                    y + 14
            );


            g2.drawLine(
                    x + 7,
                    y + 19,
                    x + size - 7,
                    y + 19
            );


            g2.dispose();
        }
    }


    // =========================================================
    // DASHBOARD ICON
    // =========================================================

    static class DashboardIcon
            implements Icon {

        private final int size;
        private final Color color;


        DashboardIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            g2.setColor(
                    color
            );


            g2.fillRoundRect(
                    x + 2,
                    y + 2,
                    7,
                    7,
                    2,
                    2
            );


            g2.fillRoundRect(
                    x + 12,
                    y + 2,
                    7,
                    7,
                    2,
                    2
            );


            g2.fillRoundRect(
                    x + 2,
                    y + 12,
                    7,
                    7,
                    2,
                    2
            );


            g2.fillRoundRect(
                    x + 12,
                    y + 12,
                    7,
                    7,
                    2,
                    2
            );


            g2.dispose();
        }
    }


    // =========================================================
    // EDIT ICON
    // =========================================================

    static class EditIcon
            implements Icon {

        private final int size;
        private final Color color;


        EditIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

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
                            2.2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            g2.drawLine(
                    x + 5,
                    y + size - 5,
                    x + size - 5,
                    y + 5
            );


            g2.drawLine(
                    x + 4,
                    y + size - 4,
                    x + 9,
                    y + size - 5
            );


            g2.drawLine(
                    x + size - 7,
                    y + 7,
                    x + size - 4,
                    y + 10
            );


            g2.dispose();
        }
    }


    // =========================================================
    // REFRESH ICON
    // =========================================================

    static class RefreshIcon
            implements Icon {

        private final int size;
        private final Color color;


        RefreshIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

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
                            2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            g2.drawArc(
                    x + 3,
                    y + 3,
                    size - 6,
                    size - 6,
                    45,
                    270
            );


            Polygon arrow =
                    new Polygon();


            arrow.addPoint(
                    x + size - 4,
                    y + 5
            );


            arrow.addPoint(
                    x + size - 4,
                    y + 12
            );


            arrow.addPoint(
                    x + size - 10,
                    y + 8
            );


            g2.fillPolygon(
                    arrow
            );


            g2.dispose();
        }
    }


    // =========================================================
    // LOGOUT ICON
    // =========================================================

    static class LogoutIcon
            implements Icon {

        private final int size;
        private final Color color;


        LogoutIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

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
                            2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            g2.drawLine(
                    x + 3,
                    y + size / 2,
                    x + size - 5,
                    y + size / 2
            );


            g2.drawLine(
                    x + size - 9,
                    y + size / 2 - 4,
                    x + size - 5,
                    y + size / 2
            );


            g2.drawLine(
                    x + size - 9,
                    y + size / 2 + 4,
                    x + size - 5,
                    y + size / 2
            );


            g2.drawRoundRect(
                    x + 3,
                    y + 3,
                    7,
                    size - 6,
                    2,
                    2
            );


            g2.dispose();
        }
    }


    // =========================================================
    // CLOCK ICON
    // =========================================================

    static class ClockIcon
            implements Icon {

        private final int size;
        private final Color color;


        ClockIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

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
                            2f
                    )
            );


            g2.drawOval(
                    x + 3,
                    y + 3,
                    size - 6,
                    size - 6
            );


            g2.drawLine(
                    x + size / 2,
                    y + size / 2,
                    x + size / 2,
                    y + 7
            );


            g2.drawLine(
                    x + size / 2,
                    y + size / 2,
                    x + size - 7,
                    y + size / 2
            );


            g2.dispose();
        }
    }


    // =========================================================
    // CHECK ICON
    // =========================================================

    static class CheckIcon
            implements Icon {

        private final int size;
        private final Color color;


        CheckIcon(
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


        @Override
        public void paintIcon(
                Component c,
                Graphics g,
                int x,
                int y
        ) {

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
                            2.5f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );


            g2.drawLine(
                    x + 4,
                    y + size / 2,
                    x + 9,
                    y + size - 6
            );


            g2.drawLine(
                    x + 9,
                    y + size - 6,
                    x + size - 4,
                    y + 5
            );


            g2.dispose();
        }
    }
}