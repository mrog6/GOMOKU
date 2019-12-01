import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller extends JPanel {
    private Database database;
    private Model model;
    private View view;
//    protected Timer timer;
//    static final int DELAY = 5000;
    protected int turn;
    protected String playerOne;
    protected String playerTwo;
    protected Color newColor1;
    protected Color newColor2;
    Color c = Color.WHITE;
    protected int playerOneWins = 0;
    protected int playerTwoWins = 0;
    protected int playerOneLosses = 0;
    protected int playerTwoLosses = 0;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
        openingMessage();

        view.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //database.deleteAllStats();
                view.statusLabel.setForeground(Color.BLACK);
                model.emptyBoard();
                for (int k = 0; k < 15; k++) {
                    for (int j = 0; j < 15; j++) {
                        view.buttons[k][j].setText(" ");
                        view.buttons[k][j].setForeground(Color.WHITE);
                        view.buttons[k][j].setBackground(Color.WHITE);
                        view.buttons[k][j].setEnabled(true);
                        playerOneLosses = 0;
                        playerTwoLosses = 0;
                        playerOneWins = 0;
                        playerTwoWins = 0;
                        view.playerTwoWins.setText(playerTwoWins + " ");
                        view.playerOneLosses.setText(playerOneLosses + " ");
                        view.playerOneWins.setText(playerOneWins + " ");
                        view.playerTwoLosses.setText(playerTwoLosses + " ");
                    }
                }
                openingMessage();
            }
        });

        view.saveExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        view.quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //database.deleteAllStats();
                System.exit(0);
            }
        });

//        timer = new Timer(DELAY, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("timer");
//            }
//        });
//        timer.start();

    }

    public void chooseColorTwoPlayer() {
        newColor1 = JColorChooser.showDialog(null,
                playerOne + ", choose your symbol color!",
                null);
        if (newColor1 == null || newColor1.equals(c)) {
            newColor1 = Color.BLUE;
            view.playerOneName.setForeground(newColor1);
        }
        else {
            view.playerOneName.setForeground(newColor1);
        }

        newColor2 = JColorChooser.showDialog(null,
                playerTwo + ", choose your symbol color!",
                null);
        if (newColor2 == null || newColor2.equals(c)) {
            newColor2 = Color.RED;
            view.playerTwoName.setForeground(newColor2);
        }
        else {
            view.playerTwoName.setForeground(newColor2);
        }
    }

    public void chooseColorOnePlayer() {
        newColor1 = JColorChooser.showDialog(null,
                playerOne + ", choose your symbol color!",
                null);
        if (newColor1 == null || newColor1.equals(c)) {
            view.playerOneName.setForeground(Color.BLACK);
            newColor1 = Color.BLUE;
        }
        else {
            view.playerOneName.setForeground(newColor1);
        }
        newColor2 = Color.BLACK;
        view.playerTwoName.setForeground(newColor2);
    }

    public void openingMessage() {
        String[] options = {"Friend", "Computer", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null, "The object of the game is to get 5 in a row. " +
                        "Each player will have five seconds to make a move. Would you like to play against the computer or a friend?",
                "WELCOME TO GO MOKU!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options,
                options[0]);
        if (choice == JOptionPane.YES_OPTION) {
            twoPlayerGame();
        }
        else if (choice == JOptionPane.NO_OPTION)
            onePlayerGame();
        else
            System.exit(0);
    }

    public void twoPlayerGame() {
        playerOne = JOptionPane.showInputDialog("Please enter player 1's name");
        if (playerOne.length() != 0)
            view.playerOneName.setText(playerOne);
        else {
            view.playerOneName.setText("Player 1");
            playerOne = "Player 1";
        }

        playerTwo = JOptionPane.showInputDialog("Please enter player 2's name");
        if (playerTwo.length() != 0)
            view.playerTwoName.setText(playerTwo);
        else {
            view.playerTwoName.setText("Player 2");
            playerTwo = "Player 2";
        }
        this.turn = 1;
        view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
        chooseColorTwoPlayer();
    }

    public void onePlayerGame() {
        playerOne = JOptionPane.showInputDialog("Please enter your name");
        if (playerOne.length() != 0)
            view.playerOneName.setText(playerOne);
        else {
            view.playerOneName.setText("Player 1");
            playerOne = "Player 1";
        }
        playerTwo = "Computer";
        view.playerTwoName.setText(playerTwo);
        this.turn = 1;
        view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
        chooseColorOnePlayer();
    }

    public void pressButton(int i, int j) {
        if (!model.validMove(i, j)) {
            if (turn % 2 == 1) {
                view.statusLabel.setText("Invalid Move. " + view.playerOneName.getText() + "'s turn");
            }
            else if (turn % 2 == 0) {
                view.statusLabel.setText("Invalid Move. " + view.playerTwoName.getText() + "'s turn");
            }
            return;
        }
        else {
            model.insertSymbol(i, j, turn);

            if (turn % 2 == 1) {
                view.buttons[i][j].setText("X");
                view.statusLabel.setForeground(newColor2);
                view.statusLabel.setText(view.playerTwoName.getText() + "'s turn");
                view.buttons[i][j].setForeground(newColor1);
                view.buttons[i][j].setBackground(newColor1);
            }
            else if (turn % 2 == 0) {
                view.buttons[i][j].setText("O");
                view.statusLabel.setForeground(newColor1);
                view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
                view.buttons[i][j].setForeground(newColor2);
                view.buttons[i][j].setBackground(newColor2);
            }

            if (model.checkWin(i, j, turn)) {
                System.out.println("WINNER");
                for (int x = 0; x<15; x++) {
                    for (int y = 0; y<15; y++) {
                        view.buttons[x][y].setEnabled(false);
                    }
                }

                if (turn % 2 == 1) {
                    view.statusLabel.setForeground(newColor1);
                    playerOneWins++;
                    playerTwoLosses++;
                    view.statusLabel.setText(view.playerOneName.getText() + " Wins!");
                    view.playerOneWins.setText(playerOneWins + " ");
                    view.playerTwoLosses.setText(playerTwoLosses + " ");
                }
                else if (turn % 2 == 0) {
                    view.buttons[i][j].setText("O");
                    view.statusLabel.setForeground(newColor2);
                    playerTwoWins++;
                    playerOneLosses++;
                    view.statusLabel.setText(view.playerTwoName.getText() + " Wins!");
                    view.buttons[i][j].setForeground(newColor2);
                    view.playerTwoWins.setText(playerTwoWins + " ");
                    view.playerOneLosses.setText(playerOneLosses + " ");
                }
                int newGame = JOptionPane.showConfirmDialog(null, "Do you want to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
                if (newGame == JOptionPane.YES_OPTION) {
                    model.emptyBoard();
                    for (int k = 0; k < 15; k++) {
                        for (int l = 0; l < 15; l++) {
                            view.buttons[k][l].setText(" ");
                            view.buttons[k][l].setForeground(Color.WHITE);
                            view.buttons[k][l].setBackground(Color.WHITE);
                            view.buttons[k][l].setEnabled(true);
                        }
                    }
                    if (turn % 2 == 1) {
                        view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
                        view.statusLabel.setForeground(newColor1);
                    }
                    else if (turn % 2 == 0) {
                        view.statusLabel.setText(view.playerTwoName.getText() + "'s turn");
                        view.statusLabel.setForeground(newColor1);
                    }
                }
                else {
                    int saveGame = JOptionPane.showConfirmDialog(null, "Do you want to save your game stats?",
                            "Save and Close", JOptionPane.YES_NO_OPTION);
                    if (saveGame == JOptionPane.YES_OPTION) {
                        System.exit(0); // this will change
                    }
                    else {
                        database.deleteAllStats();
                        System.exit(0);
                    }
                }

            }
            turn++;
        }
    }

    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
    }
}