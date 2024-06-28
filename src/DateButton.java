import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class DateButton extends JButton {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateChooser DATE_CHOOSER = new DateChooser((JFrame) null, "Select Date");
    private Date date;

    /**
     * Transforms the sign-up request data to match the backend's expected format.
     *
     * @param {SignUpRequest} signUpData - The original sign-up request data.
     *
     * @returns {Object} The transformed sign-up request data with the following changes:
     * - `firstName` is mapped to `first_name`
     * - `lastName` is mapped to `last_name`
     * - `email` is mapped to `username`
     * - All other properties remain unchanged.
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
     * Transforms the sign-up request data to match the backend's expected format.
     *
     * @param {SignUpRequest} signUpData - The original sign-up request data.
     *
     * @returns {Object} The transformed sign-up request data with the following changes:
     * - `firstName` is mapped to `first_name`
     * - `lastName` is mapped to `last_name`
     * - `email` is mapped to `username`
     * - All other properties remain unchanged.
     */
    public Date getDate() {

        return date;
    }

    /**
     * Transforms the sign-up request data to match the backend's expected format.
     *
     * @param {SignUpRequest} signUpData - The original sign-up request data.
     *
     * @returns {Object} The transformed sign-up request data with the following changes:
     * - `firstName` is mapped to `first_name`
     * - `lastName` is mapped to `last_name`
     * - `email` is mapped to `username`
     * - All other properties remain unchanged.
     */
    public void setDate(Date date) {

        Date old = this.date;
        this.date = date;
        setText(DATE_FORMAT.format(date));
        firePropertyChange("date", old, date);
    }
}
