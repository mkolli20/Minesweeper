// minesweeper by Mihir Kolli

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.EventQueue;
// imports the necessary libraries to make a minesweeper game appear in a seperate JPanel


public class Minesweeper extends JFrame {
    // The minesweeper class is a type of JFrame, the foundation for Java graphical applications

    private JLabel statusbar;
    // This JLabel conveys information to the player

    public Minesweeper() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        // Initiates the statusbar

        add(new Board(statusbar));

        setResizable(false);
        pack();

        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // The default statusbar simply says "Minesweeper"
    }

    public static void main(String[] args) {
        // The main function

        EventQueue.invokeLater(() -> {
            // EventQueue.invokeLater is standard in JavaSwing, Java's widget toolkit, to make Swing components Thread Safe
            // The lambda operator creates a function that does not belong to any class

            var ex = new Minesweeper();
            ex.setVisible(true);
        });
    }
}
