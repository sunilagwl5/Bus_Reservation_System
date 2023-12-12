import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main implements Runnable {
    private final JFrame frame;

    public Main(JFrame frm) {
        this.frame = frm;
    }//constructor closed

    /**
     * Makes the frame visible.
     *
     * @throws NullPointerException if the frame is null
     *
     * Example:
     * <pre>
     * {@code
     *     run();
     * }
     * </pre>
     */
    public void run() {
        frame.setVisible(true);
    }//run method closed

    /**
     * The main method of the application.
     *
     * @param args the command-line arguments
     * @throws SomeException if something goes wrong
     * @throws AnotherException if another thing goes wrong
     *
     * Example:
     * <pre>
     * public static void main(String[] args) {
     *     new frmSplash(3000);
     *     EventQueue.invokeLater(new Main(new LoginScreen()));
     * }
     * </pre>
     */
    public static void main(String args[]) {
        new frmSplash(3000);
        EventQueue.invokeLater(new Main(new LoginScreen()));
    }//main method closed

}//class closed
