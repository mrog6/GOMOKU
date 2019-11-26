import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    protected JLabel statusLabel;
    protected JLabel banner;
    protected JLabel playerOneName;
    protected JLabel playerTwoName;
    protected JLabel playerOneWins;
    protected JLabel playerOneLosses;
    protected JLabel playerTwoWins;
    protected JLabel playerTwoLosses;
    protected JPanel contentPanel;
    protected JButton resetButton;
    protected JButton saveExitButton;
    protected JButton[][] buttons;
    protected final String PLAYER_ONE_SYMBOL = "X";
    protected final String PLAYER_TWO_SYMBOL = "O";


    public View(Controller controller) {
        super("Go-Moku");
        this.controller = controller;
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 800));
        setupUI();
        pack();
    }

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
        lastPanel.add(saveExitButton);
        lastPanel.add(resetButton);

        // encapsulates both panels into the app
        contentPanel.add(topPanel, BorderLayout.WEST);
        contentPanel.add(statusLabelPanel, BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);
        contentPanel.add(lastPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 15; i++) {
            for(int j=0; j<15; j++) {
                if (e.getSource() == buttons[i][j])
                    controller.pressButton(i, j);
            }
        }
    }
}