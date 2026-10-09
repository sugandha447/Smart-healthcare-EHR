package dashboard;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.TableRowSorter;

public class MyPatients extends JFrame {

    private JTable patientTable;
    private DefaultTableModel model;
    private JLabel patientCountLabel;
    private JTextField searchField;

    public MyPatients(String employeeId) {

        setTitle("My Patients");
        setSize(1150, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 248, 252));
        setLayout(new BorderLayout());

        // =========================
        // HEADER
        // =========================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 55, 95));
        header.setBorder(new EmptyBorder(18, 25, 18, 25));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        titlePanel.setOpaque(false);

        JLabel iconLabel = new JLabel(new PatientIcon(28, Color.WHITE));

        JPanel titleTextPanel = new JPanel();
        titleTextPanel.setLayout(new BoxLayout(titleTextPanel, BoxLayout.Y_AXIS));
        titleTextPanel.setOpaque(false);

        JLabel title = new JLabel("My Patients");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Patients assigned to employee ID: " + employeeId);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(215, 228, 242));

        titleTextPanel.add(title);
        titleTextPanel.add(Box.createVerticalStrut(4));
        titleTextPanel.add(subtitle);

        titlePanel.add(iconLabel);
        titlePanel.add(titleTextPanel);

        header.add(titlePanel, BorderLayout.WEST);

        // =========================
        // PATIENT COUNT CARD
        // =========================

        JPanel countCard = new JPanel(new BorderLayout());
        countCard.setPreferredSize(new Dimension(150, 65));
        countCard.setBackground(Color.WHITE);
        countCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 228, 238)
                        ),
                        new EmptyBorder(8, 15, 8, 15)
                )
        );

        JLabel countTitle = new JLabel("TOTAL PATIENTS");
        countTitle.setFont(new Font("Arial", Font.BOLD, 10));
        countTitle.setForeground(new Color(100, 115, 135));

        patientCountLabel = new JLabel("0");
        patientCountLabel.setFont(new Font("Arial", Font.BOLD, 22));
        patientCountLabel.setForeground(new Color(25, 105, 170));

        countCard.add(countTitle, BorderLayout.NORTH);
        countCard.add(patientCountLabel, BorderLayout.CENTER);

        header.add(countCard, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(new Color(245, 248, 252));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 232, 240)
                        ),
                        new EmptyBorder(12, 15, 12, 15)
                )
        );

        JLabel searchLabel = new JLabel("Search Patient");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setForeground(new Color(45, 60, 80));

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(205, 215, 228)
                        ),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        JButton refreshButton = createButton(
                "Refresh",
                new Color(25, 105, 170)
        );

        JPanel searchLeft = new JPanel(new BorderLayout(12, 0));
        searchLeft.setOpaque(false);

        searchLeft.add(searchLabel, BorderLayout.WEST);
        searchLeft.add(searchField, BorderLayout.CENTER);

        searchPanel.add(searchLeft, BorderLayout.CENTER);
        searchPanel.add(refreshButton, BorderLayout.EAST);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // =========================
        // TABLE
        // =========================

        model = new DefaultTableModel(
                new Object[]{
                        "Patient ID",
                        "Patient Name",
                        "Age",
                        "Gender",
                        "Blood Group",
                        "Contact",
                        "Department"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(model);

        patientTable.setRowHeight(38);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 13));
        patientTable.setForeground(new Color(45, 55, 70));
        patientTable.setBackground(Color.WHITE);
        patientTable.setSelectionBackground(new Color(220, 237, 250));
        patientTable.setSelectionForeground(new Color(20, 55, 90));
        patientTable.setGridColor(new Color(230, 235, 242));
        patientTable.setShowVerticalLines(false);
        patientTable.setShowHorizontalLines(true);
        patientTable.setIntercellSpacing(new Dimension(0, 1));

        // Header styling

        JTableHeader tableHeader = patientTable.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(0, 42));
        tableHeader.setFont(new Font("Arial", Font.BOLD, 13));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBackground(new Color(35, 75, 120));
        tableHeader.setReorderingAllowed(false);

        // Center alignment for selected columns

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        patientTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        patientTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        patientTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

        patientTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(centerRenderer);

        // Column widths

        patientTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(80);

        patientTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(180);

        patientTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(60);

        patientTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(80);

        patientTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(100);

        patientTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(130);

        patientTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 228, 238)
                )
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // =========================
        // SEARCH FUNCTION
        // =========================

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    private void filter() {

                        String search =
                                searchField.getText()
                                        .trim()
                                        .toLowerCase();

                        for (int i = model.getRowCount() - 1; i >= 0; i--) {

                            String patientName =
                                    String.valueOf(
                                            model.getValueAt(i, 1)
                                    ).toLowerCase();

                            String patientId =
                                    String.valueOf(
                                            model.getValueAt(i, 0)
                                    ).toLowerCase();

                            if (!patientName.contains(search)
                                    && !patientId.contains(search)) {

                                ((javax.swing.table.TableRowSorter<?>)
                                        patientTable.getRowSorter())
                                        .modelStructureChanged();
                            }
                        }
                    }

                    @Override
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        applyFilter();
                    }

                    @Override
                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        applyFilter();
                    }

                    @Override
                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        applyFilter();
                    }

                    private void applyFilter() {

                        String text =
                                searchField.getText()
                                        .trim()
                                        .toLowerCase();

                        TableRowSorter<DefaultTableModel> sorter =
                                new TableRowSorter<>(model);

                        patientTable.setRowSorter(sorter);

                        if (text.isEmpty()) {
                            sorter.setRowFilter(null);
                        } else {
                            sorter.setRowFilter(
                                    RowFilter.regexFilter(
                                            "(?i)" +
                                                    java.util.regex.Pattern
                                                            .quote(text)
                                    )
                            );
                        }
                    }
                }
        );

        // =========================
        // REFRESH
        // =========================

        refreshButton.addActionListener(e -> {

            model.setRowCount(0);

            loadPatients(employeeId);

            searchField.setText("");
        });

        // =========================
        // DOUBLE CLICK
        // =========================

        patientTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (e.getClickCount() == 2
                                && SwingUtilities.isLeftMouseButton(e)) {

                            int row =
                                    patientTable.getSelectedRow();

                            if (row != -1) {

                                int modelRow =
                                        patientTable.convertRowIndexToModel(row);

                                String patientName =
                                        String.valueOf(
                                                model.getValueAt(
                                                        modelRow,
                                                        1
                                                )
                                        );

                                JOptionPane.showMessageDialog(
                                        MyPatients.this,
                                        "Selected Patient: "
                                                + patientName,
                                        "Patient Information",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        }
                    }
                }
        );

        // =========================
        // LOAD DATA
        // =========================

        loadPatients(employeeId);

        setVisible(true);
    }

    // =========================================================
    // LOAD PATIENTS
    // =========================================================

    private void loadPatients(String employeeId) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT " +
                            "p.patient_id, " +
                            "p.patient_name, " +
                            "p.age, " +
                            "p.gender, " +
                            "p.blood_group, " +
                            "p.contact, " +
                            "u.department " +
                            "FROM patients p " +
                            "JOIN users u " +
                            "ON p.doctor_id = u.user_id " +
                            "WHERE u.employee_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, employeeId);

            ResultSet rs =
                    ps.executeQuery();

            int count = 0;

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("patient_id"),

                        rs.getString("patient_name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("blood_group"),

                        rs.getString("contact"),

                        rs.getString("department")
                });

                count++;
            }

            patientCountLabel.setText(
                    String.valueOf(count)
            );

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load patients.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                new EmptyBorder(
                        10, 18, 10, 18
                )
        );

        return button;
    }

    // =========================================================
    // PATIENT ICON
    // =========================================================

    private static class PatientIcon implements Icon {

        private final int size;
        private final Color color;

        PatientIcon(int size, Color color) {
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

            g2.setColor(color);

            int headSize = size / 3;

            int headX =
                    x + (size - headSize) / 2;

            int headY =
                    y + 2;

            g2.fillOval(
                    headX,
                    headY,
                    headSize,
                    headSize
            );

            int bodyWidth =
                    (int) (size * 0.65);

            int bodyHeight =
                    (int) (size * 0.42);

            int bodyX =
                    x + (size - bodyWidth) / 2;

            int bodyY =
                    y + size / 2;

            g2.fillRoundRect(
                    bodyX,
                    bodyY,
                    bodyWidth,
                    bodyHeight,
                    8,
                    8
            );

            g2.dispose();
        }
    }
}