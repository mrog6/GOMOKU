import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller extends JPanel {
    private Model model;
    private View view;
    protected Timer timer;
    static final int DELAY = 5000;
    protected int turn;
    protected String playerOne;
    protected String playerTwo;
    protected Color newColor1;
    protected Color newColor2;
    Color c = Color.WHITE;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
        startOver();

        view.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.statusLabel.setForeground(Color.BLACK);
                model.emptyBoard();
                for (int k = 0; k < 15; k++) {
                    for (int j = 0; j < 15; j++) {
                        view.buttons[k][j].setText(" ");
                        view.buttons[k][j].setForeground(Color.WHITE);
                        view.buttons[k][j].setBackground(Color.WHITE);
                        view.buttons[k][j].setEnabled(true);
                    }
                }
                startOver();
            }
        });

        view.saveExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

    public void chooseColor() {
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

        newColor2 = JColorChooser.showDialog(null,
                playerTwo + ", choose your symbol color!",
                null);
        if (newColor2 == null || newColor2.equals(c)) {
            view.playerTwoName.setForeground(Color.BLACK);
            newColor2 = Color.RED;
        }
        else {
            view.playerTwoName.setForeground(newColor2);
        }
    }

    public void openingMessage() {
        String[] options = {"Friend", "Computer", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null, "The object of the game is to get 5 in a row. " +
                        "Each player will have five seconds to make a move. Would you like to play against the computer or a friend?",
                "WELCOME TO GO MOKU!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options,
                options[0]);
//        if (choice == JOptionPane.YES_OPTION) {
//
//        }
//        if (choice == JOptionPane.NO_OPTION)
//            System.exit(0);
//        else
//            System.exit(0);
    }

    public void startOver() {
        openingMessage();
        playerOne = JOptionPane.showInputDialog("Please enter player 1's name");
        if (playerOne.length() != 0)
            view.playerOneName.setText(playerOne);
        else
            view.playerOneName.setText("Player 1");

        playerTwo = JOptionPane.showInputDialog("Please enter player 2's name");
        if (playerTwo.length() != 0)
            view.playerTwoName.setText(playerTwo);
        else
            view.playerTwoName.setText("Player 2");

        this.turn = 1;
        view.statusLabel.setText(view.playerOneName.getText() + "'s turn");
        chooseColor();
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
                    view.statusLabel.setText(view.playerOneName.getText() + " Wins!");
                }
                else if (turn % 2 == 0) {
                    view.buttons[i][j].setText("O");
                    view.statusLabel.setForeground(newColor2);
                    view.statusLabel.setText(view.playerTwoName.getText() + " Wins!");
                    view.buttons[i][j].setForeground(newColor2);
                }
            }

            turn++;
        }
    }

    public void makeMove() {

    }

    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
    }
}