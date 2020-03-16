// minesweeper by Mihir Kolli

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board extends JPanel
{
    private final int numImages = 13;
    // there are 13 images I use in this game, representing a mine, the 8 possible cells
    // around a mine, the empty cell, the covered cell, the marked cell, and the wrongly marked cell
    private final int cellSize = 15;
    // The size of each cell is 15 x 15 pixels

    private final int markedCell = 10;
    private final int coveredCell = 10;
    private final int mineCell = 9;
    private final int hiddenMine = mineCell + coveredCell;
    private final int MmarkedMine = hiddenMine + markedCell;
    private final int emptyCell = 0;
    // I treat the minefield as an array of numbers, and define what game element corresponds to what number

    private final int drawMine = 9;
    private final int drawCover = 10;
    private final int drawMark = 11;
    private final int drawWrongMark = 12;
    // These constants are used to draw mines, coveres, and marks

    private final int numMines = 40;
    private final int numRows = 16;
    private final int numCols = 16;
    // These constants define values concerning the structure of the game board

    private final int boardWidth = numCols * cellSize + 1;
    private final int boardHeight = numRows * cellSize + 1;

    private int[] field;
    // this field of integers is the minefield
    private boolean inGame;
    // This boolean stores if the game is over or not
    private int minesLeft;
    // This integer counts the number of unmarked mines on the field
    private Image[] img;
    // This image array is used to load the images from the resource directory into the JPanel

    private int numCells;
    // This integer counts the nummber of cells on the board
    private final JLabel statusbar;
    // This status bar conveys information to the player

    public Board(JLabel statusbar)
    {
        this.statusbar = statusbar;
        initBoard();
        // Status bar is initialized
    }

    private void initBoard()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        img = new Image[numImages];

        for (int i = 0; i < numImages; i++) {

            var path = "Java/Minesweeper/resources/" + i + ".png";
            // Change this path to the location of the src directory in eclipse, where the images should be placed
            img[i] = (new ImageIcon(path)).getImage();
        }
        // Images are loaded into the image array from the resource folder for easy reference and use

        addMouseListener(new MinesAdapter());
        // Mouse listener checks for clicks so the player can interact with the game board
        newGame();
        // A new game is begun
    }

    private void newGame()
    {
    int cell;

    var random = new Random();
    // A random is created for use in making a random board
        inGame = true;
        minesLeft = numMines;

        numCells = numRows * numCols;
        field = new int[numCells];

        for (int i = 1; i <= numCells; i++)
        {
            field[i] = coveredCell;
        }
        // The entire minefield is covered at the start of the game

        statusbar.setText(Integer.toString(minesLeft));
        // The statusbar informs the player how many mines they have unmarked

        int i = 0;

        while (i < numMines)
        {
            int position = (int) (numCells * random.nextDouble());
            // This while loop randomly selects one of the cells in the minefield, ensuring no cell is selected twice


            if ((position < numCells)
                    && (field[position] != hiddenMine)) {

                int current_col = position % numCols;
                field[position] = hiddenMine;
                // If there is no mine in the randomy selected cell, that cell has a mine placed there
                i++;

                if (current_col > 0) {
                    cell = position - 1 - numCols;
                    if (cell >= 0) {
                        if (field[cell] != hiddenMine) {
                            field[cell] += 1;
                    // This if statement along with the rest of the if statements in this method
                    // increase the value of the tiles surrounding the mine, provided that one of
                    // the surrounding cells doesn't contain a mine. This will indicate to the player there
                    // are n mines adjacent to the cell
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != hiddenMine) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + numCols - 1;
                    if (cell < numCells) {
                        if (field[cell] != hiddenMine) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position + numCols;
                if (cell < numCells) {
                    if (field[cell] != hiddenMine) {
                        field[cell] += 1;
                    }
                }

                cell = position - numCols;
                if (cell >= 0) {
                    if (field[cell] != hiddenMine) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (numCols - 1)) {
                    cell = position - numCols + 1;
                    if (cell >= 0) {
                        if (field[cell] != hiddenMine) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < numCells) {
                        if (field[cell] != hiddenMine) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + numCols + 1;
                    if (cell < numCells) {
                        if (field[cell] != hiddenMine) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_emptyCells(int j)
    // The purpose of this find empty cells method is to find all empty cells, so if the player
    // clicks on an empty cell, all other adjacent empty and numbered cells are uncovered
    {
        int current_col = j % numCols;
        int cell;

        if (current_col > 0) {
            cell = j - numCols - 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= coveredCell;
                    if (field[cell] == emptyCell) {
                        find_emptyCells(cell);
                    }
                }
            }
            // This first if statement checks the cell to the left of the empty cell clicked on,
            // if this left-adjacent cell is not empty, it is immediatly uncovered, if it is empty,
            // the process is recursively repeated by calling the method again

            // This process repeats in the following if statements for any of the 8 cells
            // adjacent to an empty cell

            cell = j + numCols - 1;
            if (cell < numCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= coveredCell;
                    if (field[cell] == emptyCell) {
                        find_emptyCells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= coveredCell;
                    if (field[cell] == emptyCell) {
                        find_emptyCells(cell);
                    }
                }
            }
        }

        cell = j + numCols;
        if (cell < numCells) {
            if (field[cell] > mineCell) {
                field[cell] -= coveredCell;
                if (field[cell] == emptyCell) {
                    find_emptyCells(cell);
                }
            }
        }

        cell = j - numCols;
        if (cell >= 0) {
            if (field[cell] > mineCell) {
                field[cell] -= coveredCell;
                if (field[cell] == emptyCell) {
                    find_emptyCells(cell);
                }
            }
        }

        if (current_col < (numCols - 1)) {
            cell = j - numCols + 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= coveredCell;
                    if (field[cell] == emptyCell) {
                        find_emptyCells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < numCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= coveredCell;
                    if (field[cell] == emptyCell) {
                        find_emptyCells(cell);
                    }
                }
            }

            cell = j + numCols + 1;
            if (cell < numCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= coveredCell;
                    if (field[cell] == emptyCell) {
                        find_emptyCells(cell);
                    }
                }
            }
        }

    }

    public void paintComponent(Graphics g)
    // The purpose of this paint components method is to turn the numbers in the field array into images
    {
        int uncover = 0;

        for (int i = 0; i < numRows; i++) {

            for (int j = 0; j < numCols; j++) {

                int cell = field[(i * numCols) + j];

                if (inGame && cell == mineCell) {

                    inGame = false;
                    // if a cell that would be uncovered is a mine cell, the game is ended
                }

                if (!inGame) {

                    if (cell == hiddenMine) {
                        cell = drawMine;
                    } else if (cell == MmarkedMine) {
                        cell = drawMark;
                    } else if (cell > hiddenMine) {
                        cell = drawWrongMark;
                    } else if (cell > mineCell) {
                        cell = drawCover;
                    }
                    // If the game is over, all uncovered mines are shown and all wrongly marked mines are also shown

                } else {

                    if (cell > hiddenMine) {
                        cell = drawMark;
                    } else if (cell > mineCell) {
                        cell = drawCover;
                        uncover++;
                        // If a cell isn't a mine and the player hasn't lost, the cell is uncovered or
                        // marked, depending on the player's choice. A counter keeps track of the
                        // number of unmasked cells
                    }
                }

                g.drawImage(img[cell], (j * cellSize),
                        (i * cellSize), this);
                // this function draws all of the cells in the game into the JPanel
            }
        }

        if (uncover == 0 && inGame) {

            inGame = false;
            statusbar.setText("YTou Won!");
            // If every empty cell is clicked on, the player has won

        } else if (!inGame) {
            statusbar.setText("You Lost...");
            // If the player clicked on a mine, they have lost
        }
    }

    private class MinesAdapter extends MouseAdapter
    {
        public void mousePressed(MouseEvent e) {
            // The mouse pressed method reacts to player input

            int x = e.getX();
            int y = e.getY();
            // the x and y coordinates of the mouse are determined and stored

            int cCol = x / cellSize;
            int cRow = y / cellSize;
            // the column and row corresponding to the mouse's position are determined

            boolean doRepaint = false;

            if (!inGame) {

                newGame();
                repaint();
                // If a game is lost, a new one is started
            }

            if ((x < numCols * cellSize) && (y < numRows * cellSize)) {
                // ensure the mouse is on the field

                if (e.getButton() == MouseEvent.BUTTON3) {
                    // if the player presses the right mouse button, tile is uncovered

                    if (field[(cRow * numCols) + cCol] > mineCell) {

                        doRepaint = true;

                        if (field[(cRow * numCols) + cCol] <= hiddenMine) {

                            if (minesLeft > 0) {
                                field[(cRow * numCols) + cCol] += markedCell;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                                // if an unmarked cell is right clicked, markedCell is added to the
                                // number in the cell, causing the paint method to covere the
                                // cell with a mark

                            } else {
                                statusbar.setText("No marks left");
                                // if the player has marked 40 cells, they are informed they can't
                                // mark any more ceells
                            }
                        } else {

                            field[(cRow * numCols) + cCol] -= markedCell;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                            // if a cell with a mark is right clicked, the mark is removed and the
                            // number of marks remaining is increased by one
                        }
                    }

                } else {

                    if (field[(cRow * numCols) + cCol] > hiddenMine) {

                        return;
                        // nothing is done if the player left clicks on a marked cell, the mark must
                        // first be removed by a right click
                    }

                    if ((field[(cRow * numCols) + cCol] > mineCell)
                            && (field[(cRow * numCols) + cCol] < MmarkedMine)) {

                        field[(cRow * numCols) + cCol] -= coveredCell;
                        // left clicking removes the cover from a cell
                        doRepaint = true;

                        if (field[(cRow * numCols) + cCol] == mineCell) {
                            inGame = false;
                            // if a mine cell is clicked, the game is lost
                        }

                        if (field[(cRow * numCols) + cCol] == emptyCell) {
                            find_emptyCells((cRow * numCols) + cCol);
                            // if an empty cell is clicked, the find empty cell method is called to
                            // uncover all other adjacent non-mine cells
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                    // if the player makes any change to the board, it is repainted
                }
            }
        }
    }
}

