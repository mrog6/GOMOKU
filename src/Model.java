/**
 * Class Model contains all of the functionality for the game Go-Moku. It is used
 * by class Controller to empty the board, check for a winner, and check for valid moves.
 *
 * @author Meghan Rogers, Nick Chua, Ewan Akins
 * @see "No Borrowed Code"
 *
 */
public class Model {
    private char[][] board;
    public static int WIN_COUNT = 5;
    public static int COLUMNS = 15;
    public static int ROWS = 15;

    /**
     * Constructor, creates 15x15 board of dashes.
     */
    public Model() {
        board = new char[15][15];
        emptyBoard();
    }

    /**
     * Empties the board and initializes the board to dashes.
     */
    public void emptyBoard() {
        for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
                board[i][j] = '-';
            }
        }
    }

    /**
     * Inserts the symbol onto the board.
     * @param i the row symbol placement
     * @param j the column of the symbol placement
     * @param turn the turn variable (int) used to indicate who's turn it is
     */
    public void insertSymbol(int i, int j, int turn) {
        if (turn % 2 == 1) {
            board[i][j] = 'X';
        }
        else if (turn % 2 == 0) {
            board[i][j] = 'O';
        }
    }

    /**
     * Checks whether or not the move made is valid.
     * @param i the row symbol placement
     * @param j the column of the symbol placement
     * @return boolean, true if the spot on the board is empty, false otherwise
     */
    public boolean validMove(int i, int j) {
        if (board[i][j] != '-') {
            return false;
        }
        return true;
    }

    /**
     * Checks for a winner of the game.
     * @param i the row symbol placement
     * @param j the column of the symbol placement
     * @param turn the turn variable (int) used to indicate who's turn it is
     * @return boolean, true if a winner is found, false otherwise
     */
    public boolean checkWin(int i, int j, int turn) {

        char playerSymbol;

        if (turn % 2 == 1) {
            playerSymbol = 'X';
        }
        else {
            playerSymbol = 'O';
        }

        int consecutiveCounter = 0;

        //HORIZONTAL
        int leftBound = 0;
        int rightBound = 0;
        if (j - WIN_COUNT >= 0)
            leftBound = j - WIN_COUNT;
        else
            leftBound = 0;

        if (j + WIN_COUNT <= COLUMNS - 1)
            rightBound = j + WIN_COUNT;
        else
            rightBound = COLUMNS - 1;

        int jTraverse = j;

        while (jTraverse >= leftBound && consecutiveCounter < WIN_COUNT) {
            if (board[i][jTraverse] == playerSymbol) {
                consecutiveCounter++;
                jTraverse--;
            }
            else {
                jTraverse = j+1;
                break;
            }
        }

        while (jTraverse <= rightBound && consecutiveCounter < WIN_COUNT) {
            if (board[i][jTraverse] == playerSymbol) {
                consecutiveCounter++;
                jTraverse++;
            }
            else {
                break;
            }
        }

        if (consecutiveCounter >= WIN_COUNT) {
            return true;
        }

        //VERTICAL

        consecutiveCounter = 0;
        int upperBound = 0;
        int lowerBound = 0;

        if (i - WIN_COUNT >= 0)
            upperBound = i - WIN_COUNT;
        else
            upperBound = 0;

        if (i + WIN_COUNT <= ROWS - 1)
            lowerBound = i + WIN_COUNT;
        else
            lowerBound = ROWS - 1;

        int iTraverse = i;

        while (iTraverse >= upperBound && consecutiveCounter < WIN_COUNT) {
            if (board[iTraverse][j] == playerSymbol) {
                consecutiveCounter++;
                iTraverse--;
            }
            else {
                iTraverse = i + 1;
                break;
            }
        }

        while (iTraverse <= lowerBound && consecutiveCounter < WIN_COUNT) {
            if (board[iTraverse][j] == playerSymbol) {
                consecutiveCounter++;
                iTraverse++;
            }
            else {
                break;
            }
        }

        if (consecutiveCounter >= WIN_COUNT) {
            return true;
        }

        //DIAGONAL


        return false;
    }
}

