/**
 * Simulate percolation thresholds for a grid-base system using
 *  * union find techniques for determining if the top of
 *  * a grid is connected to the bottom of a grid.
 *
 * @author Amber Potter and Gautam Sirdeshmukh
 */


public class PercolationUF implements IPercolate{
    public IUnionFind myFinder;
    public boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    public int myOpenCount;

    public PercolationUF (IUnionFind finder, int size) {
        myGrid = new boolean[size][size];
        myFinder = finder;
        myFinder.initialize((size * size + 2) );
        VTOP = size * size;
        VBOTTOM = size * size + 1;
        myOpenCount = 0;
    }

    /**
     * Open site (row, col) if it is not already open. By convention, (0, 0)
     * is the upper-left site
     *
     * The method modifies internal state so that determining if percolation
     * occurs could change after taking a step in the simulation.
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     */
    @Override
    public void open(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        if (!isOpen(row, col))
        {
            myOpenCount ++;
            myGrid[row][col] = true;
            if (inBounds(row + 1, col)) {
                if (isOpen(row + 1, col)) {
                    myFinder.union(row * myGrid.length + col,
                            (row + 1) * myGrid.length + col);
                }
            }
            if (inBounds(row - 1, col))
            {
                if (isOpen(row - 1, col))
                {
                    myFinder.union(row * myGrid.length + col,
                            (row - 1) * myGrid.length + col);
                }
            }
            if (inBounds(row, col + 1)) {
                if (isOpen(row, col + 1)) {
                    myFinder.union(row * myGrid.length + col,
                            row * myGrid.length + col + 1);
                }
            }
            if (inBounds(row, col - 1)) {
                if (isOpen(row, col - 1)) {
                    myFinder.union(row * myGrid.length + col,
                            row * myGrid.length + col - 1);
                }
            }

            if (row == 0)
            {
                myFinder.union(row*myGrid.length + col, VTOP);
            }
            if (row + 1 == myGrid.length) {
                myFinder.union(row*myGrid.length + col, VBOTTOM);
            }
        }
    }

    /**
     * Returns true if and only if site (row, col) is OPEN
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     */
    @Override
    public boolean isOpen(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myGrid[row][col];
    }

    /**
     * Returns true if and only if site (row, col) is FULL
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     *            column index in range [0,N-1]
     */
    @Override
    public boolean isFull(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myFinder.connected(row*myGrid.length + col, VTOP);
    }

    /**
     * Returns true if the simulated percolation actually percolates. What it
     * means to percolate could depend on the system being simulated, but
     * returning true typically means there's a connected path from
     * top-to-bottom.
     *
     * @return true if the simulated system percolates
     */
    @Override
    public boolean percolates() {
        if (numberOfOpenSites() < myGrid[0].length)
        {
            return false;
        }
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * Returns the number of distinct sites that have been opened in this
     * simulation
     *
     * @return number of open sites
     */
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    /**
    * Determine if (row,col) is valid for given grid
     * @param row specifies row
     * @param col specifies column
     * @return true if (row,col) on grid, false otherwise
     */
    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
