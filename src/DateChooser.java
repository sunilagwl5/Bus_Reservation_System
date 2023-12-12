import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateChooser extends JDialog implements ItemListener, MouseListener, FocusListener, KeyListener, ActionListener {

    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String[] MONTHS = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private static final String[] DAYS = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final Color WEEK_DAYS_FOREGROUND = Color.black;
    private static final Color DAYS_FOREGROUND = Color.blue;
    private static final Color SELECTED_DAY_FOREGROUND = Color.white;
    private static final Color SELECTED_DAY_BACKGROUND = Color.blue;
    private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(1, 1, 1, 1);
    private static final Border FOCUSED_BORDER = BorderFactory.createLineBorder(Color.yellow, 1);
    private static final int FIRST_YEAR = 2000;
    private static final int LAST_YEAR = 2100;
    private GregorianCalendar calendar;
    private JLabel[][] days;
    private FocusablePanel daysGrid;
    private JComboBox month;
    private JComboBox year;
    private JButton ok;
    private JButton cancel;
    private int offset;
    private int lastDay;
    private JLabel day;
    private boolean okClicked;

    private static class FocusablePanel extends JPanel {

        public FocusablePanel(LayoutManager layout) {
            super(layout);
        }

        /**
         * Returns whether this component should be traversable by focus.
         *
         * @return true if this component should be traversable by focus; otherwise, false
         * @throws SomeException if there is a specific exception that can be thrown
         *
         * Example:
         * <pre>
         * {@code
         *     boolean focusTraversable = isFocusTraversable();
         *     if (focusTraversable) {
         *         // Do something
         *     } else {
         *         // Do something else
         *     }
         * }
         * </pre>
         */
        public boolean isFocusTraversable() {
            return true;

        }
    }

    /**
     * Constructs the calendar GUI components and sets up event listeners.
     *
     * @throws NullPointerException if any of the GUI components are not initialized properly
     * @throws IllegalArgumentException if the year range is invalid
     *
     * Example:
     *
     * <pre>
     * CalendarGUI calendar = new CalendarGUI();
     * calendar.construct();
     * </pre>
     */
    private void construct() {
        calendar = new GregorianCalendar();

        month = new JComboBox(MONTHS);
        month.addItemListener(this);

        year = new JComboBox();
        for (int i = FIRST_YEAR; i <= LAST_YEAR; i++) {
            year.addItem(Integer.toString(i));
        }
        year.addItemListener(this);

        days = new JLabel[7][7];
        for (int i = 0; i < 7; i++) {
            days[0][i] = new JLabel(DAYS[i], JLabel.RIGHT);
            days[0][i].setForeground(WEEK_DAYS_FOREGROUND);
        }
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                days[i][j] = new JLabel(" ", JLabel.RIGHT);
                days[i][j].setForeground(DAYS_FOREGROUND);
                days[i][j].setBackground(SELECTED_DAY_BACKGROUND);
                days[i][j].setBorder(EMPTY_BORDER);
                days[i][j].addMouseListener(this);
            }
        }

        ok = new JButton("Ok");
        ok.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        JPanel monthYear = new JPanel();
        monthYear.add(month);
        monthYear.add(year);

        daysGrid = new FocusablePanel(new GridLayout(7, 7, 5, 0));
        daysGrid.addFocusListener(this);
        daysGrid.addKeyListener(this);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                daysGrid.add(days[i][j]);
            }
        }
        daysGrid.setBackground(Color.white);
        daysGrid.setBorder(BorderFactory.createLoweredBevelBorder());
        JPanel daysPanel = new JPanel();
        daysPanel.add(daysGrid);

        JPanel buttons = new JPanel();
        buttons.add(ok);
        buttons.add(cancel);

        Container dialog = getContentPane();
        dialog.add("North", monthYear);
        dialog.add("Center", daysPanel);
        dialog.add("South", buttons);

        pack();
        setResizable(false);

    }

    /**
     * Returns the selected day as an integer.
     *
     * @return The selected day as an integer, or -1 if the day is null or cannot be parsed as an integer.
     * @throws NumberFormatException if the text of the day field cannot be parsed as an integer.
     *
     * Example:
     * <pre>
     * {@code
     * // Assuming day is a JTextField
     * int selectedDay = getSelectedDay();
     * }
     * </pre>
     */
    private int getSelectedDay() {
        if (day == null) {
            return -1;
        }
        try {
            return Integer.parseInt(day.getText());
        } catch (NumberFormatException e) {
        }

        return -1;
    }

    /**
     * Sets the selected day based on the new day value.
     *
     * @param newDay the new day value to set
     * @throws ArrayIndexOutOfBoundsException if the newDay value is out of bounds
     * @throws IllegalArgumentException if the offset is invalid
     * @throws NullPointerException if the days array is null
     * @throws IndexOutOfBoundsException if the calculated index is out of bounds
     * @example
     * <pre>
     *     // Example usage:
     *     setSelected(10);
     * </pre>
     */
    private void setSelected(JLabel newDay) {
        if (day != null) {
            day.setForeground(DAYS_FOREGROUND);
            day.setOpaque(false);
            day.setBorder(EMPTY_BORDER);
        }
        day = newDay;
        day.setForeground(SELECTED_DAY_FOREGROUND);
        day.setOpaque(true);
        if (daysGrid.hasFocus()) {
            day.setBorder(FOCUSED_BORDER);
        }

    }

    /**
     * Sets the selected day based on the new day value.
     *
     * @param newDay the new day value to be set
     * @throws ArrayIndexOutOfBoundsException if the newDay value is out of bounds
     * @throws NullPointerException if the days array is null
     * @throws IllegalArgumentException if the offset is invalid
     * @see Day#setSelected(Day)
     *
     * Example:
     * <pre>
     *     setSelected(10);
     * </pre>
     */
    private void setSelected(int newDay) {
        setSelected(days[(newDay + offset - 1) / 7 + 1][(newDay + offset - 1) % 7]);

    }

    /**
     * Updates the calendar display based on the selected month and year.
     *
     * @throws IllegalArgumentException if the selected day is invalid
     *
     * Example:
     * <pre>
     *     // Update the calendar display
     *     update();
     * </pre>
     */
    private void update() {
        int iday = getSelectedDay();
        for (int i = 0; i < 7; i++) {
            days[1][i].setText(" ");
            days[5][i].setText(" ");
            days[6][i].setText(" ");
        }
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month.getSelectedIndex() + Calendar.JANUARY);
        calendar.set(Calendar.YEAR, year.getSelectedIndex() + FIRST_YEAR);

        offset = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        lastDay = calendar.getActualMaximum(Calendar.DATE);

        for (int i = 0; i < lastDay; i++) {
            days[(i + offset) / 7 + 1][(i + offset) % 7].setText(String.valueOf(i + 1));
        }
        if (iday != -1) {
            if (iday > lastDay) {
                iday = lastDay;
            }
            setSelected(iday);
        }
    }

    /**
     * This method is called when an action is performed.
     *
     * @param e the ActionEvent that occurred
     * @throws NullPointerException if the ActionEvent e is null
     *
     * Example:
     * <pre>
     * public void actionPerformed(ActionEvent e) {
     *     if (e.getSource() == ok) {
     *         okClicked = true;
     *     }
     *     hide();
     * }
     * </pre>
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            okClicked = true;
        }
        hide();

    }

    public void focusGained(FocusEvent e) {
        setSelected(day);
    }

    public void focusLost(FocusEvent e) {
        setSelected(day);
    }

    public void itemStateChanged(ItemEvent e) {
        update();
    }

            /**
             * Responds to a key being pressed.
             *
             * @param e the KeyEvent representing the key press
             * @throws IllegalArgumentException if the selected day is invalid
             * @throws IllegalStateException if the last day is not set
             *
             * Example:
             * <pre>
             *     // Assuming the lastDay is set and getSelectedDay() returns the currently selected day
             *     public void keyPressed(KeyEvent e) {
             *         int iday = getSelectedDay();
             *
             *         switch (e.getKeyCode()) {
             *             case KeyEvent.VK_LEFT:
             *                 if (iday > 1) {
             *                     setSelected(iday - 1);
             *                 }
             *                 break;
             *             case KeyEvent.VK_RIGHT:
             *                 if (iday < lastDay) {
             *                     setSelected(iday + 1);
             *                 }
             *                 break;
             *             case KeyEvent.VK_UP:
             *                 if (iday > 7) {
             *                     setSelected(iday - 7);
             *                 }
             *                 break;
             *             case KeyEvent.VK_DOWN:
             *                 if (iday <= lastDay - 7) {
             *                     setSelected(iday + 7);
             *                 }
             *                 break;
             *         }
             *     }
             * </pre>
             */
    public void keyPressed(KeyEvent e) {
        int iday = getSelectedDay();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (iday > 1) {
                    setSelected(iday - 1);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (iday < lastDay) {
                    setSelected(iday + 1);
                }
                break;
            case KeyEvent.VK_UP:
                if (iday > 7) {
                    setSelected(iday - 7);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (iday <= lastDay - 7) {
                    setSelected(iday + 7);
                }
                break;
        }
    }

    public void mouseClicked(MouseEvent e) {
        JLabel day = (JLabel) e.getSource();
        if (!day.getText().equals(" ")) {
            setSelected(day);
        }
        daysGrid.requestFocus();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public DateChooser(Dialog owner, String title) {
        super(owner, title, true);
        construct();
    }

    public DateChooser(Dialog owner) {
        super(owner, true);
        construct();
    }

    public DateChooser(Frame owner, String title) {
        super(owner, title, true);
        construct();
    }

    public DateChooser(Frame owner) {
        super(owner, true);
        construct();
        setLocation((screen.width - 800) / 2, ((screen.height - 550) / 2));
    }

    /**
     * Selects a date.
     *
     * @return the selected date
     * @throws SomeException if there is an issue with selecting the date
     *
     * Example:
     * <pre>
     * {@code
     * Date selectedDate = select();
     * System.out.println("Selected date: " + selectedDate);
     * }
     * </pre>
     */
    public Date select(Date date) {
        calendar.setTime(date);
        int _day = calendar.get(Calendar.DATE);
        int _month = calendar.get(Calendar.MONTH);
        int _year = calendar.get(Calendar.YEAR);

        year.setSelectedIndex(_year - FIRST_YEAR);
        month.setSelectedIndex(_month - Calendar.JANUARY);
        setSelected(_day);
        okClicked = false;
        show();
        if (!okClicked) {
            return null;
        }
        calendar.set(Calendar.DATE, getSelectedDay());
        calendar.set(Calendar.MONTH, month.getSelectedIndex() + Calendar.JANUARY);
        calendar.set(Calendar.YEAR, year.getSelectedIndex() + FIRST_YEAR);
        return calendar.getTime();

    }

    /**
     * Selects a date.
     *
     * @return the selected date
     * @throws SomeException if there is an issue with selecting the date
     *
     * Example:
     * <pre>
     * {@code
     * Date selectedDate = select();
     * System.out.println("Selected date: " + selectedDate);
     * }
     * </pre>
     */
    public Date select() {
        return select(new Date());
    }
}

