/* *****************************************************************************
 *  Name: Neeko
 *  Date: Dec 13th, 2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF site;
    private final WeightedQuickUnionUF siteWithoutEnding;
    private boolean [][] isOpen;
    private int opennum;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid input!");

        isOpen = new boolean[n][n];  // the array that stores the open status of sites
        this.n = n;
        site = new WeightedQuickUnionUF(n*n+2);  // initialize a QuickUnionUF with an opening node and an ending node
        siteWithoutEnding = new WeightedQuickUnionUF(n*n+1);  // initialize a QuickUnionUF only with an opening node to assist isFull()
        // site.find(0);
        // site.find(n*n+1);

        // connect the nodes in the first line with the opening node
        for (int i = 1; i < 1+n; i++) {
            union(0, i);
        }

        // connect the nodes in the last line with the ending node
        for (int i = n*(n-1)+1; i < n*n+1; i++) {
            site.union(i, n*n+1);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid input!");
        }

        if (n == 1) {
            isOpen[row-1][col-1] = true;
            opennum++;
        } else if (!isOpen[row-1][col-1]) {
            int index = (row-1) * n + col;

            // in the first row
            if (index <= n) {
                if (isOpen(index+n)) {
                    union(index, index+n);
                }
                // except the (1,n), open the block on the right
                if (index != n) {
                    if (isOpen(index+1)) {
                        union(index, index+1);
                    }
                }
                // except the (1,1), open the block on the left
                if (index != 1) {
                    if (isOpen(index-1)) {
                        union(index, index-1);
                    }
                }
            } else if (index > n*(n-1)) {   // in the last row
                if (isOpen(index-n)) {
                    union(index, index-n);
                }
                // except the (n,1), open the block on the left
                if (index != (n*(n-1) + 1)){
                    if (isOpen(index-1)) {
                        union(index, index-1);
                    }
                }
                // except the (n,n), open the block on the right
                if (index != n*n) {
                    if (isOpen(index+1)) {
                        union(index, index+1);
                    }
                }
            } else if (index % n == 1) {   // in the first colummn
                if (isOpen(index+1)) {
                    union(index, index+1);
                }
                if (isOpen(index-n)) {
                    union(index, index-n);
                }
                if (isOpen(index+n)) {
                    union(index, index+n);
                }
            } else if (index % n == 0) {   // in the last column
                if (isOpen(index-1)) {
                    union(index, index-1);
                }
                if (isOpen(index-n)) {
                    union(index, index-n);
                }
                if (isOpen(index+n)) {
                    union(index, index+n);
                }
            } else {
                if (isOpen(index-1)) {
                    union(index, index-1);
                }
                if (isOpen(index+1)) {
                    union(index, index+1);
                }
                if (isOpen(index-n)) {
                    union(index, index-n);
                }
                if (isOpen(index+n)) {
                    union(index, index+n);
                }
            }
            isOpen[row-1][col-1] = true;
            opennum++;
        }

    }

    // unions the nodes in both site and siteWithoutEnding
    private void union(int p, int q) {
        site.union(p, q);
        siteWithoutEnding.union(p, q);
    }

    private boolean isOpen(int p) {
        int row = (int) Math.ceil((double) p / n);
        int col = ((p % n == 0) ? n : (p % n));
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid input!");
        }
        return isOpen[row-1][col-1];
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid input!");
        }
        return isOpen[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > n || col > n || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid input!");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        return siteWithoutEnding.connected(0, (row-1)*n+col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opennum;
    }

    // does the system percolate?
    public boolean percolates() {
        // corner case
        if (n == 1 && !isOpen(1,1)) {
            return false;
        }
        return site.connected(0, n*n+1);
    }

    // test client (optional)
    //  public static void main(String[] args) {
    //     // test the corner case
    //     Percolation p = new Percolation(1);
    //     p.open(1,1);
    //     System.out.println(p.percolates());
    //  }
}
