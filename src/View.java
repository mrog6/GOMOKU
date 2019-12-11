import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The View class extends JFrame in order to set up the GUI for Go-Moku, and it
 * implements ActionListener so to obtain the location of a button pressed on the
 * game board.
 *
 * @author Meghan Rogers, Nick Chua, Ewan Akins
 * @see "No Borrowed Code"
 *
 */
public class View extends JFrame implements ActionListener {
    /**
     * An object of class Controller.
     */
    private Controller controller;

    /**
     * Status label used to update the user(s).
     */
    protected JLabel statusLabel;

    /**
     * Label used for the color chooser.
     */
    protected JLabel banner;

    /**
     * Label to store the name of player one.
     */
    protected JLabel playerOneName;

    /**
     * Label to store the name of player two.
     */
    protected JLabel playerTwoName;

    /**
     * Label to store the win count of player one.
     */
    protected JLabel playerOneWins;

    /**
     * Label to store the loss count of player one.
     */
    protected JLabel playerOneLosses;

    /**
     * Label to store the win count of player two.
     */
    protected JLabel playerTwoWins;

    /**
     * Label to store the loss count of player two.
     */
    protected JLabel playerTwoLosses;

    /**
     * JPanel to hold components of the GUI.
     */
    protected JPanel contentPanel;

    /**
     * Button that allows the user to reset the game stats.
     */
    protected JButton resetButton;

    /**
     * Button that allows the user to save their game and exit the app.
     */
    protected JButton saveExitButton;

    /**
     * Button that allows the user to quit the app.
     */
    protected JButton quitButton;

    /**
     * An array of buttons for the game board.
     */
    protected JButton[][] buttons;

    /**
     * Constructor for class View. Calls setupUI() and pack().
     * @param controller a Controller object
     */
    public View(Controller controller) {
        super("Go-Moku");
        this.controller = controller;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 800));
        setupUI();
        pack();
    }

    /**
     * Sets up the User Interface using four separate JPanels.
     * The topPanel contains the info boxes for each player, the gridPanel contains the
     * game board, the statusLabel Panel contains the status label, and the lastPanel
     * contains the buttons.
     */
    public void setupUI() {
        contentPanel = (JPanel) getContentPane();
        banner = new JLabel("Welcome to Color Chooser!");

        // layout for the top half of the app: two gridLayouts for the players inside of a boxLayout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setPreferredSize(new Dimension(150, 200));

        // player 1 info box
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 0));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Player 1 (X)"));
        JLabel playerOneNameLabel = new JLabel("Name:");
        playerOneNameLabel.setFont(new Font(" ", Font.PLAIN, 20));
        playerOneName = new JLabel(" ");
        playerOneName.setFont(new Font(" ", Font.PLAIN, 20));
        JLabel playerOneWinLabel = new JLabel("Wins:");
        playerOneWinLabel.setFont(new Font(" ", Font.PLAIN, 20));
        playerOneWins = new JLabel("0");
        playerOneWins.setFont(new Font(" ", Font.PLAIN, 20));
        JLabel playerOneLossLabel = new JLabel("Losses:");
        playerOneLossLabel.setFont(new Font(" ", Font.PLAIN, 20));
        playerOneLosses = new JLabel("0");
        playerOneLosses.setFont(new Font(" ", Font.PLAIN, 20));
        leftPanel.add(playerOneNameLabel);
        leftPanel.add(playerOneName);
        leftPanel.add(playerOneWinLabel);
        leftPanel.add(playerOneWins);
        leftPanel.add(playerOneLossLabel);
        leftPanel.add(playerOneLosses);

        // player 2 info box
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6, 0));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Player 2 (O)"));
        JLabel playerTwoNameLabel = new JLabel("Name:");
        playerTwoNameLabel.setFont(new Font(" ", Font.PLAIN, 20));
        playerTwoName = new JLabel(" ");
        playerTwoName.setFont(new Font(" ", Font.PLAIN, 20));
        JLabel playerTwoWinLabel = new JLabel("Wins:");
        playerTwoWinLabel.setFont(new Font(" ", Font.PLAIN, 20));
        playerTwoWins = new JLabel("0");
        playerTwoWins.setFont(new Font(" ", Font.PLAIN, 20));
        JLabel playerTwoLossLabel = new JLabel("Losses:");
        playerTwoLossLabel.setFont(new Font(" ", Font.PLAIN, 20));
        playerTwoLosses = new JLabel("0");
        playerTwoLosses.setFont(new Font(" ", Font.PLAIN, 20));
        rightPanel.add(playerTwoNameLabel);
        rightPanel.add(playerTwoName);
        rightPanel.add(playerTwoWinLabel);
        rightPanel.add(playerTwoWins);
        rightPanel.add(playerTwoLossLabel);
        rightPanel.add(playerTwoLosses);

        // encapsulates the player info boxes into the topPanel
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);

        JPanel statusLabelPanel = new JPanel();
        statusLabel = new JLabel("Welcome to GO-MOKU!");
        statusLabel.setFont(new Font(" ", Font.BOLD, 30));
        statusLabelPanel.add(statusLabel);

        // grid for the JButtons
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(15, 15));
        buttons = new JButton[15][15];
        for(int i = 0; i < 15; i++) {
            for(int j=0; j<15; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setText(" ");
                buttons[i][j].setFont(new Font(" ", Font.BOLD, 20));
                buttons[i][j].setPreferredSize(new Dimension(3, 3));
                buttons[i][j].addActionListener(this);
                buttons[i][j].setOpaque(true);
                gridPanel.add(buttons[i][j]);
            }
        }

        JPanel lastPanel = new JPanel();
        resetButton = new JButton("Reset Names and Stats");
        saveExitButton = new JButton("Save and Exit");
        quitButton = new JButton("Quit");
        lastPanel.add(saveExitButton);
        lastPanel.add(resetButton);
        lastPanel.add(quitButton);

        // encapsulates both panels into the app
        contentPanel.add(topPanel, BorderLayout.WEST);
        contentPanel.add(statusLabelPanel, BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);
        contentPanel.add(lastPanel, BorderLayout.SOUTH);
    }

    /**
     * When a button on the game board is pressed, this actionPerformed is called
     * to obtain the location of the button and call pressButton in the Controller class.
     * @param e the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 15; i++) {
            for(int j=0; j<15; j++) {
                if (e.getSource() == buttons[i][j])
                    controller.pressButton(i, j);
            }
        }
    }
}