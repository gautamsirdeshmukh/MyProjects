/**
 * Simulate percolation thresholds for a grid-base system using
 * union find techniques for determining if the top of
 * a grid is connected to the bottom of a grid.
 *  * subclass of PercolatesDFSFast with different implementation
 *  * of updateOnOpen to make implementation faster
 *
 * @author Amber Potter and Gautam Sirdeshmukh
 **/


public class PercolationDFSFast extends PercolationDFS {
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */

    /**
     * Constructor method
     * @param n
     */
    public PercolationDFSFast(int n) {
        super(n);
    }

    /**
     * Updates slots to open/closed when a new slot is opened
     * @param row, the index of the row
     * @param col, the index of the column
     */

    @Override
    public void updateOnOpen(int row, int col) {
        int perc = 0;
        if (row == 0) perc = 1;
        if (row != 0 && myGrid[row - 1][col] == FULL) perc = 1;
        if (row != myGrid[row].length - 1 && myGrid[row+1][col] == FULL) perc = 1;
        if (col != 0 && myGrid[row][col - 1] == FULL) perc = 1;
        if (col != myGrid[col].length - 1 && myGrid[row][col + 1] == FULL) perc = 1;
        if (perc == 1) dfs(row, col);
    }

}
