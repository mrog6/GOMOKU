import java.awt.*;
import java.util.Random;

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
     * @param i the row of the symbol placement
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
     * @param i the row of the symbol placement
     * @param j the column of the symbol placement
     * @return boolean, true if the spot on the board is empty, false otherwise
     */
    public boolean validMove(int i, int j) {
        try
        {
            return board[i][j] == '-';
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
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
        int rightBound;
    
        int WIN_COUNT = 5;
        if (j - WIN_COUNT - 1 >= 0)
            leftBound = j - WIN_COUNT - 1;
    
        int COLUMNS = 15;
        if (j + WIN_COUNT - 1 <= COLUMNS - 1)
            rightBound = j + WIN_COUNT -1;
        else
            rightBound = COLUMNS - 1;

        int jTraverse = j;

        if (j == 0) {
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
        }
        else {
            while (jTraverse >= leftBound && consecutiveCounter < WIN_COUNT) {
                if (board[i][jTraverse] == playerSymbol) {
                    consecutiveCounter++;
                    if (jTraverse != 0) {
                        jTraverse--;
                    }
                    else {
                        jTraverse = j+1;
                        break;
                    }
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
        }

        //VERTICAL
        consecutiveCounter = 0;
        int smallerBound;
        int largerBound;

        if (i - WIN_COUNT - 1 >= 0)
            smallerBound = i - WIN_COUNT - 1;
        else
            smallerBound = 0;
    
        int ROWS = 15;
        if (i + WIN_COUNT - 1 <= ROWS - 1)
            largerBound = i + WIN_COUNT - 1;
        else
            largerBound = ROWS - 1;

        int iTraverse = i;

        if (iTraverse == 0) {
            while (iTraverse <= largerBound && consecutiveCounter < WIN_COUNT) {
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
        }
        else {
            while (iTraverse >= smallerBound && consecutiveCounter < WIN_COUNT) {
                if (board[iTraverse][j] == playerSymbol) {
                    consecutiveCounter++;
                    if (iTraverse != 0) {
                        iTraverse--;
                    }
                    else {
                        iTraverse = i+1;
                        break;
                    }
                }
                else {
                    iTraverse = i+1;
                    break;
                }
            }

            while (iTraverse <= largerBound && consecutiveCounter < WIN_COUNT) {
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
        }

        //DIAGONAL " / " direction
        consecutiveCounter = 0;
        iTraverse = i;
        jTraverse = j;

        if (jTraverse == 0 || iTraverse == 14) {
            while (iTraverse >= 0 && jTraverse < COLUMNS && consecutiveCounter <= 5) {
                if (board[iTraverse][jTraverse] == playerSymbol) {
                    iTraverse--;
                    jTraverse++;
                    consecutiveCounter++;
                }
                else {
                    break;
                }
            }

            if (consecutiveCounter >= WIN_COUNT) {
                return true;
            }
        }
        else {
            while (iTraverse < ROWS && jTraverse >= 0 && consecutiveCounter <= 5) {
                if (board[iTraverse][jTraverse] == playerSymbol) {
                    consecutiveCounter++;
                    if (iTraverse != 14 && jTraverse != 0) {
                        iTraverse++;
                        jTraverse--;
                    }
                    else {
                        iTraverse = i-1;
                        jTraverse = j+1;
                        break;
                    }
                }
                else {
                    iTraverse = i-1;
                    jTraverse = j+1;
                    break;
                }
            }

            while (iTraverse >= 0 && jTraverse < COLUMNS && consecutiveCounter <= 5) {
                if (board[iTraverse][jTraverse] == playerSymbol) {
                    iTraverse--;
                    jTraverse++;
                    consecutiveCounter++;
                }
                else {
                    break;
                }
            }

            if (consecutiveCounter >= WIN_COUNT) {
                return true;
            }
        }

        //DIAGONAL " \ " direction
        consecutiveCounter = 0;
        iTraverse = i;
        jTraverse = j;

        if (jTraverse == 14 || iTraverse == 14) {
            while (jTraverse < COLUMNS && iTraverse < ROWS && consecutiveCounter <= 5) {
                if (board[iTraverse][jTraverse] == playerSymbol) {
                    jTraverse++;
                    iTraverse++;
                    consecutiveCounter++;
                }
                else {
                    break;
                }
            }

            if (consecutiveCounter >= WIN_COUNT) {
                return true;
            }
        }
        else {
            while (jTraverse >= 0 && iTraverse >= 0 && consecutiveCounter <= 5) {
                if (board[iTraverse][jTraverse] == playerSymbol) {
                    consecutiveCounter++;
                    if (jTraverse != 0 && iTraverse != 0) {
                        jTraverse--;
                        iTraverse--;
                    }
                    else {
                        jTraverse = j+1;
                        iTraverse = i+1;
                        break;
                    }
                }
                else {
                    jTraverse = j+1;
                    iTraverse = i+1;
                    break;
                }
            }

            while (jTraverse < COLUMNS && iTraverse < ROWS && consecutiveCounter <= 5) {
                if (board[iTraverse][jTraverse] == playerSymbol) {
                    jTraverse++;
                    iTraverse++;
                    consecutiveCounter++;
                }
                else {
                    break;
                }
            }

            if (consecutiveCounter >= WIN_COUNT) {
                return true;
            }
        }
        return false;
    }
    
    
    public boolean isWinner(int turn)
    {
        char playerSymbol;
        
        if(turn % 2 == 1)
            playerSymbol = 'X';
        else
            playerSymbol = 'O';
        
        int inRow;
        int inColumn;
        int inDiagOne;
        int inDiagTwo;
        
        for(int i = 0; i < 15; ++i)
        {
            for(int j = 0; j < 15; ++j)
            {
                inDiagOne = 0;
                inDiagTwo = 0;
                inRow = 0;
                inColumn = 0;
                
                if(board[i][j] == playerSymbol)
                {
                    for(int x = 0; x < 5; ++x)
                    {
                        if (i + x < 15 && j + x < 15)
                        {
                            if (board[i][j + x] == playerSymbol)
                                inRow++;
                            if (inRow == 5)
                                return true;
                            if (board[i + x][j] == playerSymbol)
                                inColumn++;
                            if (inColumn == 5)
                                return true;
                        }
                    
                        if(i + x < 15 && j + x < 15 && i - x >= 0)
                        {
                            if (board[i - x][j + x] == playerSymbol)
                                inDiagOne++;
                            if (inDiagOne == 5)
                                return true;
                            if (board[i + x][j + x] == playerSymbol)
                                inDiagTwo++;
                            if (inDiagTwo == 5)
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method generates a random point for the computer to place "O" on the board, based
     * on the user's previous move.
     * @param i the row of the symbol placed by the single player
     * @param j the column of the symbol placed by the single player
     * @return Point, the random point generated by the computer
     */
    public Point computerChoice(int i, int j) {
        Random rand = new Random();

        //Randomizes a spot near the last symbol placed.
        //Format:
        /*
        0 1 2
        7 x 3
        6 5 4
         */

        int randomSpot = rand.nextInt(8);
        
        while(true)
        {
            switch (randomSpot)
            {
                case 0:
                    if (validMove(i - 1, j - 1))
                        return new Point(i - 1, j - 1);
                    break;
                case 1:
                    if(validMove(i, j - 1))
                        return new Point(i, j - 1);
                    break;
                case 2:
                    if(validMove(i + 1, j - 1))
                        return new Point(i + 1, j - 1);
                    break;
                case 3:
                    if(validMove(i + 1, j))
                        return new Point(i + 1, j);
                    break;
                case 4:
                    if(validMove(i + 1, j + 1))
                        return new Point(i + 1, j + 1);
                    break;
                case 5:
                    if(validMove(i, j + 1))
                        return new Point(i, j + 1);
                    break;
                case 6:
                    if(validMove(i - 1, j + 1))
                        return new Point(i - 1, j + 1);
                    break;
                default:
                    if(validMove(i - 1, j))
                        return new Point(i - 1, j);
            }
            randomSpot = rand.nextInt(8);
        }
    }
    
    public boolean findFours(Point p)
    {
        return false;
    }
}

