
import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import javax.swing.plaf.metal.*;

public class Booking extends JInternalFrame {

    private JPanel contents,  controls,  general,  pane;
    private JLabel label,  label1,  label2,  label3,  label10,  label4,  label5,  label6,  label7,  label8,  label9;
    private JTextField text1;
    private JComboBox combo1,  combo2,  combo3,  combo4,  combo5,  combo6,  combo7,  combo8,  combo9;
    private JButton button1,  button2,  button3,  button4,  button5,  button6,  button7,  ok,  next;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    String bookNo, pasNo, passName, seatNo, busNo, dte, time, frm, to, amount;
    private DateButton t_date;

    public Booking() {
        super("Booking Process",false,true,false,true);
        label = new JLabel("Booking Number");
        label1 = new JLabel("Passenger Number");
        label2 = new JLabel("Passenger Name");
        label3 = new JLabel("Seat Number");
        label4 = new JLabel("Bus RegNo");
        label5 = new JLabel("Date_of_Travel");
        label6 = new JLabel("Time_of_Travel");
        label7 = new JLabel("From");
        label8 = new JLabel("Destination");
        label9 = new JLabel("Amount Paid");
        label10 = new JLabel("Select passenger route");
        text1 = new JTextField(15);


        t_date = new DateButton();
        t_date.setForeground(Color.red);
        combo1 = new JComboBox();
        combo2 = new JComboBox();
        combo3 = new JComboBox();
        combo4 = new JComboBox();
        combo5 = new JComboBox();
        combo6 = new JComboBox();
        combo7 = new JComboBox();
        combo8 = new JComboBox();
        combo9 = new JComboBox();
        //combo7.addItem("Select");
        for (int i = 1; i <= 45; i++) {
            combo6.addItem(Integer.toString(i));
        }

        button1 = new JButton("Add New", new ImageIcon(ClassLoader.getSystemResource("Images/addnew.png")));
        button3 = new JButton("Search", new ImageIcon(ClassLoader.getSystemResource("Images/search.png")));
        button4 = new JButton("Cancel", new ImageIcon(ClassLoader.getSystemResource("Images/exit.png")));
        button5 = new JButton("Clear", new ImageIcon(ClassLoader.getSystemResource("Images/clear.png")));
        button6 = new JButton("Show Booked", new ImageIcon(ClassLoader.getSystemResource("Images/atten.png")));
        button7 = new JButton("Print", new ImageIcon(ClassLoader.getSystemResource("Images/print.png")));
        ok = new JButton("OK", new ImageIcon(ClassLoader.getSystemResource("Images/ok.png")));
        next = new JButton("Next", new ImageIcon(ClassLoader.getSystemResource("Images/next.png")));
        button1.setEnabled(false);
        button5.setEnabled(false);
        next.setEnabled(false);
        text1.setEnabled(false);
        contents = new JPanel(new GridLayout(10, 2));

        contents.add(label);
        contents.add(text1);
        contents.add(label1);
        contents.add(combo2);
        contents.add(label2);
        contents.add(combo3);
        contents.add(label6);
        contents.add(combo8);
        contents.add(label4);
        contents.add(combo1);
        contents.add(label3);
        contents.add(combo6);
        contents.add(label5);
        contents.add(t_date);

        contents.add(label7);
        contents.add(combo4);
        contents.add(label8);
        contents.add(combo5);
        contents.add(label9);
        contents.add(combo9);
        label10.setForeground(Color.red);

        pane = new JPanel();
        pane.add(label10);
        pane.add(combo7);
        pane.add(ok);

        controls = new JPanel(new GridLayout(3, 2));
        controls.add(button1);

        controls.add(button3);
        controls.add(button4);
        controls.add(button5);
        controls.add(button6);
        controls.add(button7);
        general = new JPanel();
        setLocation((screen.width - 500) / 2, ((screen.height - 350) / 2));

        general.add(pane);
        general.add(contents);
        general.add(controls);
        add(general);
        setSize(550, 420);
        setResizable(false);
        try {

            Statement s = DBConnection.getDBConnection().createStatement();
        } catch (Exception excp) {
            excp.printStackTrace();


        }
        passroute();
        //setCbx();
        generator();
        //setroute();
        //setCombo();
        //amount();
        text1.addFocusListener(new FocusAdapter() {

            /**
             * Invoked when a component loses focus.
             *
             * This method retrieves the text from a JTextField and attempts to parse it as an integer.
             * If the text is not empty and cannot be parsed as an integer, it will display an error message
             * and reset the text field.
             *
             * @param e the FocusEvent that indicates the component has lost focus
             * 
             * @throws NumberFormatException if the content of the text field cannot be parsed as an integer.
             * This exception is caught within the method, and an error message is displayed to the user.
             */
            public void focusLost(FocusEvent e) {
                JTextField textField =
                        (JTextField) e.getSource();
                String content = textField.getText();
                
                if (content.length() != 0) {
                    try {
                        Integer.parseInt(content);
                    } catch (NumberFormatException nfe) {
                        getToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Invalid data entry", "ERROR",
                                JOptionPane.DEFAULT_OPTION);
                        textField.requestFocus();
                        text1.setText("");
                    }
                }
            }
        });

        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                book();
            }
        });

        button4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (combo7.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please note that you can't do\n" +
                            "booking before scheduling is done\n" +
                            "make sure you do the scheduling before booking starts", "ERROR",
                            JOptionPane.DEFAULT_OPTION);
                    return;
                }
                setCbx();
                setroute();
                setCombo();
                
                amount();
                ok.setEnabled(false);
                button1.setEnabled(true);
//		button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                button5.setEnabled(true);
            //button6.setEnabled(true);

            }
        });

        button6.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                Show_Booked frm= new Show_Booked();
                MDIWindow.desktop.add(frm);
                frm.setVisible(true);
                try{
                   frm.setSelected(true);
               }catch(Exception ex){}
            }
        });
        button7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               Booking_report frm= new Booking_report();
               MDIWindow.desktop.add(frm);
               frm.setVisible(true);
               try{
                   frm.setSelected(true);
               }catch(Exception ex){}
            }
        });
        button5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
        });
        combo2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                combo3.setSelectedIndex(combo2.getSelectedIndex());
            }
        });
        combo3.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                combo2.setSelectedIndex(combo3.getSelectedIndex());
            }
        });

        combo8.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                combo1.setSelectedIndex(combo8.getSelectedIndex());
            }
        });
        combo7.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e) {
                combo1.removeAllItems();
                combo2.removeAllItems();
                combo3.removeAllItems();
                combo8.removeAllItems();
                combo9.removeAllItems();
                combo4.removeAllItems();
                combo5.removeAllItems();
                ok.setEnabled(true);
                button1.setEnabled(false);
                button3.setEnabled(false);
                button5.setEnabled(false);
            }
        });
        button3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                text1.setEnabled(true);

                try {
                    if (!text1.equals("")) {

                        Statement statement = DBConnection.getDBConnection().createStatement();
                        String query = "SELECT * FROM BOOKING " +
                                "WHERE  Booking_No= " + text1.getText();
                        ResultSet rs = statement.executeQuery(query);
                        display(rs);
                        statement.close();
                    }

                } catch (SQLException sqlex) {
                    sqlex.printStackTrace();
                }

                setVisible(true);



            }
        });

    }

    /**
     * Sets the items for the combo boxes based on the selected route name.
     * This method retrieves bus registration numbers and departure times from the 
     * Schedules table in the database and populates the respective combo boxes.
     *
     * <p>
     * The method establishes a connection to the database and executes a SQL query 
     * to fetch the bus registration numbers and departure times for the selected 
     * route. The results are then added to the combo boxes for user selection.
     * </p>
     *
     * @throws SQLException if a database access error occurs or the SQL statement 
     *                      is not valid.
     * @throws NullPointerException if the selected item from combo7 is null.
     * @throws Exception for any other unexpected errors that may occur during 
     *                   database access or processing.
     */
    private void setCbx() {
        try {
            ResultSet rst = DBConnection.getDBConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT Bus_RegNo,Dept_Time From Schedules where Route_Name='" + combo7.getSelectedItem() + "'");

            while (rst.next()) {
                combo1.addItem(rst.getString(1));
                combo8.addItem(rst.getString(2));
            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }

    /**
     * Sets the items in the combo boxes based on the available passengers 
     * who have not booked a ticket to the selected destination.
     * 
     * This method retrieves data from the database, specifically the 
     * passenger number and name, for passengers whose booking status 
     * is 'not Booked' and whose destination matches the selected item 
     * in the combo box. The retrieved data is then added to two combo 
     * boxes for further use in the application.
     * 
     * @throws SQLException if a database access error occurs or 
     *                      this method is called on a closed connection.
     * @throws NullPointerException if the selected item in combo5 is null.
     * @throws Exception for any other exceptions that may occur during 
     *                   the execution of the method.
     */
    private void setCombo() {
        try {
            ResultSet rst = DBConnection.getDBConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT Pass_No,Pass_Name From "+ "passenger where Booked_status ='not Booked' and To_station='" +combo5.getSelectedItem() +"'");
            //if (rst.next()) 
            System.out.println("Hello");
            while(rst.next())
            {
                combo2.addItem(rst.getString(1));
                combo3.addItem(rst.getString(2));
            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }

    /**
     * Retrieves the fare charged for a specific route and populates a combo box with the results.
     * <p>
     * This method establishes a connection to the database, executes a query to fetch the fare charged
     * for the route selected in the combo box, and adds each fare retrieved to another combo box.
     * </p>
     * 
     * @throws SQLException if a database access error occurs or the SQL statement is invalid.
     * @throws NullPointerException if the selected item in the combo box is null.
     * @throws Exception for any other unexpected errors that may occur during database operations.
     */
    private void amount() {
        try {
            ResultSet rst = DBConnection.getDBConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT Fare_Charged FROM Route where RouteName='" + combo7.getSelectedItem() + "'");

            while (rst.next()) {
                combo9.addItem(rst.getString(1));
            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }

    /**
     * Retrieves route names from the Schedules database and adds them to a combo box.
     *
     * This method establishes a connection to the database and executes a SQL query
     * to fetch all route names. Each route name is then added to the combo box 
     * for user selection.
     *
     * @throws SQLException if a database access error occurs or the SQL statement 
     *         returns anything other than a single ResultSet object.
     * @throws NullPointerException if the combo box is null or if the database 
     *         connection cannot be established.
     */
    private void passroute() {
        try {
            ResultSet rst = DBConnection.getDBConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT Route_Name FROM Schedules");

            while (rst.next()) {
                combo7.addItem(rst.getString(1));


            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }

    /**
     * Sets the route by populating the combo boxes with data retrieved from the database.
     * 
     * This method queries the database for the Depot and Destination associated with the 
     * selected RouteName from the combo7 component. The results are then added to 
     * combo4 and combo5 respectively.
     * 
     * @throws SQLException if a database access error occurs or the SQL statement is 
     *         invalid. This exception is thrown when there is an issue with executing 
     *         the query or retrieving data from the ResultSet.
     * @throws NullPointerException if any of the components (combo7, combo4, or combo5) 
     *         are null when this method is called.
     */
    private void setroute() {
        try {
            ResultSet rst = DBConnection.getDBConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT Depot,Destination FROM Route where RouteName ='" + combo7.getSelectedItem() +"'");

            while (rst.next()) {
                combo4.addItem(rst.getString(1));
                combo5.addItem(rst.getString(2));

            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }

    /**
     * Displays the details of a booking from the provided ResultSet.
     * 
     * This method retrieves booking information from the given ResultSet and updates
     * the corresponding UI components with the retrieved values. If no records are found,
     * it shows an error message dialog indicating that no record was found.
     *
     * @param rs the ResultSet containing booking information. It is expected to have
     *           at least 10 columns corresponding to the booking details.
     * 
     * @throws SQLException if a database access error occurs or this method is called
     *                      on a closed ResultSet. This exception is caught and printed
     *                      to the standard error stream.
     * 
     * @throws NullPointerException if the provided ResultSet is null.
     */
    public void display(ResultSet rs) {
        try {
            boolean recordNumber = rs.next();
            if (recordNumber) {
                bookNo = rs.getString(1);
                pasNo = rs.getString(2);
                passName = rs.getString(3);
                busNo = rs.getString(4);
                seatNo = rs.getString(5);
                dte = rs.getString(6);
                time = rs.getString(7);
                frm = rs.getString(8);
                to = rs.getString(9);
                amount = rs.getString(10);

                text1.setText(bookNo);
                combo2.setSelectedItem(pasNo);
                combo3.setSelectedItem(passName);
                combo1.setSelectedItem(busNo);
                combo6.setSelectedItem(seatNo);
                t_date.setText(dte);
                combo8.setSelectedItem(time);
                combo4.setSelectedItem(frm);
                combo5.setSelectedItem(to);
                combo9.setSelectedItem(amount);
            } else {
                JOptionPane.showMessageDialog(null, "Record Not found", "ERROR",
                        JOptionPane.DEFAULT_OPTION);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }


    private void book() {
        String SQL;
        SQL = "SELECT * FROM BOOKING WHERE SeatNo='" + combo6.getSelectedItem() + "'  AND Time_of_Travel='" + combo8.getSelectedItem() +"'  AND Bus_RegNo='" + combo1.getSelectedItem() + "'AND Date_of_Travel='" + t_date.getText() + "'";

        try {
            Statement st = DBConnection.getDBConnection().createStatement();

            st.execute(SQL);
            ResultSet rs = st.getResultSet();

            boolean recordfound = rs.next();

            if (recordfound) {

                JOptionPane.showMessageDialog(null, "Error Occures.Pliz Compare your Entry and\n" +
                        "the already booked and try again", "Error", JOptionPane.INFORMATION_MESSAGE);
                //new Show_Booked().setVisible(true);
                return;
            } else {
                if (text1.getText() == null ||
                        text1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter Passenger Number", "ERROR",
                            JOptionPane.DEFAULT_OPTION);
                    text1.requestFocus();
                    return;
                }
                if (combo7.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Please note that you can't do\n" +
                            "booking before scheduling is done", "ERROR",
                            JOptionPane.DEFAULT_OPTION);
                    return;
                }



                try {
                    Statement statement = DBConnection.getDBConnection().createStatement();
                    {
                        String temp = "INSERT INTO BOOKING(Booking_No,Pass_No,PassName,Bus_RegNo,SeatNo,Date_of_Travel,Time_of_Travel,Pass_From,Destination,Amount) VALUES ('" +
                                text1.getText() + "', '" +
                                combo2.getSelectedItem() + "', '" +
                                combo3.getSelectedItem() + "', '" +
                                combo1.getSelectedItem() + "', '" +
                                combo6.getSelectedItem() + "', '" +
                                t_date.getText() + "', '" +
                                combo8.getSelectedItem() + "', '" +
                                combo4.getSelectedItem() + "', '" +
                                combo5.getSelectedItem() + "', '" +
                                combo9.getSelectedItem() + "')";

                        int result = statement.executeUpdate(temp);
                        String ObjButtons[] = {"Yes", "No"};
                        int PromptResult = JOptionPane.showOptionDialog(null, "Record succesfully added.Continue with Booking?",
                                "tobiluoch", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                        if (PromptResult == 0) {
                            generator();


                        } else {

                            setVisible(false);

                        }
                    }

                } catch (Exception sqlex) {
                    sqlex.printStackTrace();

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            Statement statement = DBConnection.getDBConnection().createStatement();
            {


                String temp = "UPDATE Passenger SET Booked_status='Booked'" +
                        "WHERE Pass_NO LIKE  '" + combo2.getSelectedItem() + "'";
                int result = statement.executeUpdate(temp);

            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    private void generator() {

        try {
            ResultSet rst = DBConnection.getDBConnection().createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeQuery("SELECT Booking_No FROM BOOKING");
            //if (rst.getInt(1)==null)
            text1.setText("10");

            while (rst.next()) {
                String s;
                int number = rst.getInt(1);
                number = number + 1;

                s = "" + number;
                text1.setText(s);
            }
        } catch (Exception n) {
            n.printStackTrace();
        }
    }
}
