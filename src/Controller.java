import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The Controller class handles the action Listeners for all JButtons and
 * controls what the View class displays on the GUI. It contains methods to
 * play with one player or two players as well as methods to choose colors
 * and display dialogs.
 *
 * @author Meghan Rogers, Nick Chua, Ewan Akins
 * @see "No Borrowed Code"
 *
 */
public class Controller extends JPanel {
    private Database database;
    private Model model;
    private View view;
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
    protected boolean computerPlaying = false;
    protected boolean isWinner = false;

    /**
     * The constructor creates a View object to begin the game and displays
     * the opening message. It also holds the action listeners for three JButtons.
     * @param model a Model object
     */
    public Controller(Model model) {
        this.model = model;
        this.database = new Database();
        this.view = new View(this);
        List<GameStats> statsList = database.getAllGameStatsList();
        if (statsList.isEmpty()) {
            openingMessage();
        }
        else {
            int counter = 0;
            for (GameStats stats : statsList) {
                if (counter == 0) {
                    playerOne = stats.getName();
                    playerOneLosses = stats.getLosses();
                    playerOneWins = stats.getWins();
                    newColor1 = new Color(Integer.parseInt(stats.getColor()));
                }
                else {
                    playerTwo = stats.getName();
                    playerTwoLosses = stats.getLosses();
                    playerTwoWins = stats.getWins();
                    newColor2 = new Color(Integer.parseInt(stats.getColor()));
                    if(stats.getName().equals("Computer"))
                        computerPlaying = true;
                }
                counter++;
            }
            view.playerTwoWins.setText(playerTwoWins + " ");
            view.playerOneLosses.setText(playerOneLosses + " ");
            view.playerOneWins.setText(playerOneWins + " ");
            view.playerTwoLosses.setText(playerTwoLosses + " ");
            view.playerOneName.setText(playerOne);
            view.playerTwoName.setText(playerTwo);
            view.playerOneName.setForeground(newColor1);
            view.playerTwoName.setForeground(newColor2);

            int continueGame = JOptionPane.showConfirmDialog(null, "Would you like to continue with the previous " +
                    "game stats?", "Resume Game", JOptionPane.YES_NO_OPTION);
            if(continueGame == JOptionPane.NO_OPTION) {
                database.deleteAllStats();
                resetStats();
                openingMessage();
            }
        }

        view.resetButton.addActionListener(new ActionListener() {
            /**
             * Called when the Reset Names and Stats button is pressed, this action listener
             * empties the board and initializes all player stats to zero. Then, it deletes the
             * current player names and re-prompts the openingMessage().
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                database.deleteAllStats();
                view.statusLabel.setForeground(Color.BLACK);
                model.emptyBoard();
                for (int k = 0; k < 15; k++) {
                    for (int j = 0; j < 15; j++) {
                        view.buttons[k][j].setText(" ");
                        view.buttons[k][j].setForeground(Color.WHITE);
                        view.buttons[k][j].setBackground(Color.WHITE);
                        view.buttons[k][j].setEnabled(true);
                        resetStats();
                    }
                }
                openingMessage();
            }
        });

        view.saveExitButton.addActionListener(new ActionListener() {
            /**
             * Called when the Save and Exit button is pressed, this action listener
             * saves the current game stats for each player.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addStatsToDatabase();
                System.exit(0);
            }
        });

        view.quitButton.addActionListener(new ActionListener() {
            /**
             * Called when the Quit button is pressed, this action listener
             * deletes the current stats from the database and exits the program.
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                database.deleteAllStats();
                System.exit(0);
            }
        });
    }

    /**
     * Allows each of the two players to choose their symbol color.
     */
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

    /**
     * Allows the single user, who is playing against the computer, to choose
     * their symbol color.
     */
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

    /**
     * Displays the game instructions and allows the user to either play with a friend
     * or play against the computer.
     */
    public void openingMessage() {
        String[] options = {"Friend", "Computer", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null, "The object of the game is to get 5 in a row. " +
                        "Would you like to play against the computer or a friend?",
                "WELCOME TO GO MOKU!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options,
                options[0]);
        if (choice == JOptionPane.YES_OPTION) {
            computerPlaying = false;
            twoPlayerGame();
        }
        else if (choice == JOptionPane.NO_OPTION) {
            computerPlaying = true;
            view.statusLabel.setText("Player vs Computer");
            onePlayerGame();
        }
        else
            System.exit(0);
    }

    /**
     * Allows each of two players to input their names.
     */
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

    /**
     * Allows the single user playing against the computer to input his/her name.
     */
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
        view.statusLabel.setText("Player vs Computer");
        chooseColorOnePlayer();
    }

    /**
     * pressButton is called from class View's action listener when a JButton on the game
     * board is pressed. It utilizes Model to check for a valid move, insert the game symbol,
     * and check for a winner.
     * @param i the row of the button that was pressed
     * @param j the column of the button that was pressed
     */
    public void pressButton(int i, int j) {
        if (computerPlaying) {
            if (!model.validMove(i, j)) {
                if (turn % 2 == 1) {
                    view.statusLabel.setText("Invalid Move. Try again");
                }
            }
            else {
                model.insertSymbol(i, j, turn);
                view.buttons[i][j].setText("X");
                view.buttons[i][j].setForeground(newColor1);
                view.buttons[i][j].setBackground(newColor1);
                checkWin(i, j, turn);
                turn++;
                if (!isWinner) {
                    int x;
                    int y;
                    do {
                        Point temp = model.computerChoice(i, j);
                        x = (int) temp.getX();
                        y = (int) temp.getY();
                    } while (!model.validMove(x, y));
                    view.buttons[x][y].setText("O");
                    view.buttons[x][y].setForeground(newColor2);
                    view.buttons[x][y].setBackground(newColor2);
                    model.insertSymbol(x, y, turn);
                    checkWin(x, y, turn);
                }
            }
        }
        else {
            if (!model.validMove(i, j)) {
                if (turn % 2 == 1) {
                    view.statusLabel.setText("Invalid Move. " + view.playerOneName.getText() + "'s turn");
                } else if (turn % 2 == 0) {
                    view.statusLabel.setText("Invalid Move. " + view.playerTwoName.getText() + "'s turn");
                }
            } else {
                model.insertSymbol(i, j, turn);

                if (turn % 2 == 1) {
                    view.buttons[i][j].setText("X");
                    view.statusLabel.setText(view.playerTwoName.getText() + "'s turn");
                    view.buttons[i][j].setForeground(newColor1);
                    view.buttons[i][j].setBackground(newColor1);
                } else if (turn % 2 == 0) {
                    view.buttons[i][j].setText("O");
                    view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
                    view.buttons[i][j].setForeground(newColor2);
                    view.buttons[i][j].setBackground(newColor2);
                }
            }
            checkWin(i, j, turn);
        }
        turn++;
    }

    /**
     * Checks the board for a winner.
     * @param i the row of the button that was pressed
     * @param j the column of the button that was pressed
     * @param turn an integer that keeps track of who's turn it is
     */
    public void checkWin(int i, int j, int turn) {
        if (model.checkWin(i, j, turn)) {
            isWinner = true;
            System.out.println("WINNER");
            for (int x = 0; x < 15; x++) {
                for (int y = 0; y < 15; y++) {
                    view.buttons[x][y].setEnabled(false);
                }
            }
            if (turn % 2 == 1) {
                view.buttons[i][j].setText("X");
                view.statusLabel.setForeground(newColor1);
                view.buttons[i][j].setForeground(newColor1);
                playerOneWins++;
                playerTwoLosses++;
                view.statusLabel.setText(view.playerOneName.getText() + " Wins!");
                view.playerOneWins.setText(playerOneWins + " ");
                view.playerTwoLosses.setText(playerTwoLosses + " ");
            } else if (turn % 2 == 0) {
                view.buttons[i][j].setText("O");
                view.statusLabel.setForeground(newColor2);
                view.buttons[i][j].setForeground(newColor2);
                playerTwoWins++;
                playerOneLosses++;
                view.statusLabel.setText(view.playerTwoName.getText() + " Wins!");
                view.playerTwoWins.setText(playerTwoWins + " ");
                view.playerOneLosses.setText(playerOneLosses + " ");
            }

            int newGame = JOptionPane.showConfirmDialog(null, "Do you want to play again?",
                    "Game Over", JOptionPane.YES_NO_OPTION);
            if (newGame == JOptionPane.YES_OPTION) {
                model.emptyBoard();
                view.statusLabel.setForeground(Color.BLACK);
                for (int k = 0; k < 15; k++) {
                    for (int l = 0; l < 15; l++) {
                        view.buttons[k][l].setText(" ");
                        view.buttons[k][l].setForeground(Color.WHITE);
                        view.buttons[k][l].setBackground(Color.WHITE);
                        view.buttons[k][l].setEnabled(true);
                    }
                }
                if (computerPlaying) {
                    view.statusLabel.setText("Player vs Computer");
                    newColor2 = Color.BLACK;
                }
                else {
                    if (turn % 2 == 1) {
                        view.statusLabel.setText(view.playerTwoName.getText() + "'s turn");
                    } else if (turn % 2 == 0) {
                        view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
                    }
                }
            } else {
                int saveGame = JOptionPane.showConfirmDialog(null, "Do you want to save your game stats?",
                        "Save and Close", JOptionPane.YES_NO_OPTION);
                if (saveGame == JOptionPane.YES_OPTION) {
                    addStatsToDatabase();
                    System.exit(0);
                } else {
                    database.deleteAllStats();
                    System.exit(0);
                }
            }
        }
        else {
            isWinner = false;
        }
    }

    /**
     * Resets the game stats.
     */
    public void resetStats() {
        playerOneLosses = 0;
        playerTwoLosses = 0;
        playerOneWins = 0;
        playerTwoWins = 0;
        view.playerTwoWins.setText(playerTwoWins + " ");
        view.playerOneLosses.setText(playerOneLosses + " ");
        view.playerOneWins.setText(playerOneWins + " ");
        view.playerTwoLosses.setText(playerTwoLosses + " ");
        view.playerOneName.setText(" ");
        view.playerTwoName.setText(" ");
    }

    /**
     * Adds the current game stats to the database.
     */
    public void addStatsToDatabase() {
        String color1 = String.valueOf(newColor1.getRGB());
        String color2 = String.valueOf(newColor2.getRGB());
        GameStats newInsert1 = new GameStats(playerOne, color1, playerOneLosses, playerOneWins);
        GameStats newInsert2 = new GameStats(playerTwo, color2, playerTwoLosses, playerTwoWins);
        database.insertStats(newInsert1);
        database.insertStats(newInsert2);
    }

/**
 * The main() method for the program. Creates a Model object and a Controller object.
 */
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
    }
}