import java.util.LinkedList;
import java.util.Queue;

/**
 * Simulate percolation thresholds for a grid-base system using
 *  * BFS techniques for determining if the top of
 *  * a grid is connected to the bottom of a grid.
 *
 * @author Amber Potter and Gautam Sirdeshmukh
 */

public class PercolationBFS extends PercolationDFSFast{

    /**
     * Constructor method
     *
     * @param n
     */

    public PercolationBFS(int n) {
        super(n);
    }

    /**
     *
     * @param row ,the row coordinate of the cell being checked/marked
     * @param col ,the column coordinate of the cell being checked/marked
     *
     */

    @Override
    protected void dfs(int row, int col) {
        if (!inBounds(row, col)) return;
        myGrid[row][col] = FULL;
        Queue<Integer> queue = new LinkedList<>();
        queue.add((row * myGrid.length) + col);
        while(queue.size() != 0) {
            int guy = queue.remove();
            int rcord = guy / myGrid.length;
            int ccord = guy % myGrid.length;

            if(inBounds(rcord, ccord + 1) && isOpen(rcord, ccord + 1) && !isFull(rcord, ccord +1)) {
                myGrid[rcord][ccord + 1] = FULL;
                queue.add(rcord * myGrid.length + ccord + 1);
            }
            if(inBounds(rcord, ccord - 1) &&  isOpen(rcord, ccord - 1) && !isFull(rcord, ccord - 1)) {
                myGrid[rcord][ccord - 1] = FULL;
                queue.add(rcord * myGrid.length + ccord - 1);
            }
            if(inBounds(rcord + 1, ccord) && isOpen(rcord + 1, ccord) && !isFull(rcord + 1, ccord)) {
                myGrid[rcord + 1][ccord] = FULL;
                queue.add((rcord+1)*myGrid.length + ccord);
            }
            if(inBounds(rcord - 1, ccord) && isOpen(rcord - 1, ccord) && !isFull(rcord - 1, ccord) ) {
                myGrid[rcord - 1][ccord] = FULL;
                queue.add((rcord - 1) * myGrid.length + ccord);
            }
        }
    }

}
