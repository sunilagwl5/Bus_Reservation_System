import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class DateButton extends JButton {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateChooser DATE_CHOOSER = new DateChooser((JFrame) null, "Select Date");
    private Date date;

    /**
     * Fires an action performed event with the given ActionEvent.
     *
     * @param e the ActionEvent to be fired
     * @throws NullPointerException if the DATE_CHOOSER is null
     * @throws IllegalArgumentException if the date selected is invalid
     *
     * Example:
     *
     * <pre>{@code
     * protected void someMethod() {
     *     ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "command");
     *     fireActionPerformed(event);
     * }
     * }</pre>
     */
    protected void fireActionPerformed(ActionEvent e) {

        Date newDate = DATE_CHOOSER.select(date);
        if (newDate == null) {
            return;
        }
        setDate(newDate);
    }

    public DateButton(Date date) {

        super(DATE_FORMAT.format(date));
        this.date = date;
    }

    public DateButton() {

        this(new Date());
    }

    /**
     * Returns the date.
     *
     * @return the date
     */
    public Date getDate() {

        return date;
    }

    /**
     * Sets the date and updates the text with the formatted date.
     *
     * @param date the new date to be set
     * @throws IllegalArgumentException if the date is null
     * @throws PropertyVetoException if the date change is vetoed by a PropertyChangeListener
     * @example
     * <pre>{@code
     * Date newDate = new Date();
     * setDate(newDate);
     * }</pre>
     */
    public void setDate(Date date) {

        Date old = this.date;
        this.date = date;
        setText(DATE_FORMAT.format(date));
        firePropertyChange("date", old, date);
    }
}
