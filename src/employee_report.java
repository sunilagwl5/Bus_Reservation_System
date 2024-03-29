import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class employee_report extends JInternalFrame {

    public Container content;
    public JPanel reportingPanel;
    public JTabbedPane listsTabs;
    public JTextArea listPane;
    public JPanel reportPanel;
    public JPanel statusPanel;
    public JComboBox graphTypesCombo;
    public Color skyblue = new Color(150, 190, 255);
    public final ImageIcon imageIcon = new ImageIcon("Icon/header/cool.png");
    private static Connection dbcon = null;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Statement stmt = null;
    private JButton print, cancel;
    private JPanel panel;

    public employee_report() {

        super("Employee Reports", true, true, true, true);
        content = getContentPane();
        content.setBackground(skyblue);
        listsTabs = new JTabbedPane();
        print = new JButton("Print ", new ImageIcon(ClassLoader.getSystemResource("Images/print.png")));
        cancel = new JButton("Cancel", new ImageIcon(ClassLoader.getSystemResource("Images/exit.png")));
        panel = new JPanel();
        panel.add(print);
        panel.add(cancel);

        reportingPanel = new JPanel();
        reportingPanel.setLayout(new BorderLayout());
        reportingPanel.setBorder(BorderFactory.createEtchedBorder());
        reportingPanel.add(new JLabel("Employee Report"), BorderLayout.NORTH);
        reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(1, 1));
        reportPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.blue));
        reportPanel.setBackground(Color.white);

        reportingPanel.add(new JScrollPane(reportPanel), BorderLayout.CENTER);
        reportingPanel.add(panel, BorderLayout.SOUTH);
        listsTabs.add(reportingPanel);
        setLocation((screen.width - 1270) / 2, ((screen.height - 740) / 2));

        listPane = new JTextArea() {

            Image image = imageIcon.getImage();

            {
                setOpaque(false);
            }

            /**
             * This method is responsible for painting the graphics.
             *
             * @param g the graphics object to be painted
             * @throws NullPointerException if the graphics object is null
             * @throws SecurityException if a security violation occurs
             *
             * Example:
             * <pre>
             * {@code
             * public class MyComponent extends Component {
             *     public void paint(Graphics g) {
             *         super.paint(g);
             *         // additional painting logic here
             *     }
             * }
             * }
             * </pre>
             */
            public void paint(Graphics g) {
                super.paint(g);
            }
        };


        listPane.setEditable(false);
        listPane.setFont(new Font("Serif", Font.BOLD, 12));
        listPane.setForeground(Color.black);

        listPane.setLineWrap(true);
        listPane.setWrapStyleWord(true);
        reportPanel.add(listPane);

        try {
            Statement s = DBConnection.getDBConnection().createStatement();
        } catch (Exception excp) {
            excp.printStackTrace();
        }
        printList();


        content.add(listsTabs, BorderLayout.CENTER);
        setResizable(false);
        setSize(1250, 720);
        setVisible(true);
        cancel.addActionListener(new java.awt.event.ActionListener() {

            /**
             * Performs the action in response to an action event.
             * This method disposes the current window and prints the content of the buffer.
             *
             * @param e the action event
             * @throws NullPointerException if the action event is null
             * @throws IllegalStateException if the buffer creation fails
             * @throws IOException if an I/O error occurs while printing the buffer
             *
             * Example:
             * <pre>
             *     // Assuming 'actionPerformed' is called within a class that extends ActionListener
             *     public void actionPerformed(ActionEvent e) {
             *         //dispose();
             *         print(createBuffer());
             *     }
             * </pre>
             */
            public void actionPerformed(java.awt.event.ActionEvent e) {
                //dispose();
                print(createBuffer());
            }
        });


    }

    /**
     * This method prints the list of employees from the database.
     * It retrieves the employee details from the database and appends them to the listPane.
     *
     * @throws SQLException if an error occurs while accessing the database
     *
     * Example:
     * <pre>
     * {@code
     * printList();
     * }
     * </pre>
     */
    private void printList() {
        try {

            ResultSet rst = DBConnection.getDBConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery("select empNo,Sname,Fname,Gender,Designation,Telephone,E_Mail from Emp");
            listPane.append("\n");
            listPane.append("       Emp_No" + "\t\t" + "Sname" + "\t\t" + "Fname" + "\t\t" + "Gender\t\t" + "Designation" + "\t\t" + "Telephone\t\t" + "E-Mail\n");
            listPane.append("\n");
            while (rst.next()) {
                listPane.append("       ");
                listPane.append(rst.getString(1).trim());
                listPane.append("\t\t");
                listPane.append(rst.getString(2).trim());
                listPane.append("\t\t");
                listPane.append(rst.getString(3).trim());
                listPane.append("\t\t");
                listPane.append(rst.getString(4).trim());
                listPane.append("\t\t");
                listPane.append(rst.getString(5).trim());
                listPane.append("\t\t");
                listPane.append(rst.getString(6).trim());
                listPane.append("\t\t");
                listPane.append(rst.getString(7).trim());
                listPane.append("\n\n");
            }
            if (rst != null) {
                rst.close();
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, " No Records found" + sqle.getMessage());
            return;
        }
    }

    /**
     * Retrieves the text from the list pane and returns it as a string buffer.
     *
     * @return The text from the list pane as a string buffer.
     * @throws NullPointerException if the list pane is null.
     *
     * Example:
     * <pre>
     * {@code
     * String buffer = createBuffer();
     * System.out.println(buffer);
     * }
     * </pre>
     */
    public String createBuffer() {
        String buffer;
        buffer = listPane.getText();
        return buffer;

    }

    /**
     * Prints the given string to a print job.
     *
     * @param s the string to be printed
     * @throws IOException if an I/O error occurs while reading the string
     * @throws PrinterException if a printing error occurs
     *
     * Example:
     *
     * <pre>{@code
     * String text = "This is a sample text to be printed.";
     * print(text);
     * }</pre>
     */
    private void print(String s) {

        /*StringReader sr = new StringReader(s);
        LineNumberReader lnr = new LineNumberReader(sr);
        Font typeface = new Font("Monospaced", Font.PLAIN, 12);
        Properties p = new Properties();
        PrintJob pjob = getToolkit().getPrintJob(this, "Print report", p);
        
        if (pjob != null) {
            Graphics pg = pjob.getGraphics();
            if (pg != null) {
                FontMetrics fm = pg.getFontMetrics(typeface);
                int margin = 20;
                int pageHeight = pjob.getPageDimension().height - margin;
                int fontHeight = fm.getHeight();
                int fontDescent = fm.getDescent();
                int curHeight = margin;

                String nextLine;
                pg.setFont(listPane.getFont());

                try {
                    do {
                        nextLine = lnr.readLine();
                        if (nextLine == null) {
                            if ((curHeight + fontHeight) > pageHeight) {
                                pg.dispose();
                                pg = pjob.getGraphics();
                                curHeight = margin;
                            }

                            curHeight += fontHeight;

                            if (pg != null) {
                                pg.setFont(typeface);
                                pg.drawString(nextLine, margin, curHeight - fontDescent);
                            }
                        }
                    } while (nextLine != null);

                } catch (EOFException eof) {
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            pg.dispose();
        }
        if (pjob != null) {
            pjob.end();
        }*/
    }
}
