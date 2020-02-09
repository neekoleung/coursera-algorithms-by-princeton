/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;
    private int rowOfBlank;
    private int colOfBlank;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("The input Board is null!");
        this.tiles = copyOf(tiles);
        n = tiles.length;
        rowOfBlank = -1;
        rowOfBlank = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    rowOfBlank = i;
                    colOfBlank = j;
                    return;
                }
            }
        }

    }

    // Deep copy the board
    private int[][] copyOf(int[][] matrix) {
        int[][] clone = new int[matrix.length][];
        for (int row = 0; row < matrix.length; row++) {
            clone[row] = matrix[row].clone();
        }
        return clone;
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        // first line contains the board size n
        str.append(n).append("\n");
        // the remaining n lines contains the n-by-n grid of tiles in row-major order, using 0 to designate the blank square
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(String.format("%2d ", tiles[i][j]));
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place (the value is wrong)
    public int hamming() {
        int number = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skip when encounter the blank
                if (i == rowOfBlank && j == colOfBlank) continue;
                if (manhattanHelper(i,j) != 0) number++;
            }
        }
        return number;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int number = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skip when encounter the blank
                if (i == rowOfBlank && j == colOfBlank) continue;
                number += manhattanHelper(i, j);
            }
        }
        return number;
    }

    private int manhattanHelper(int row, int col) {
        int desValue = tiles[row][col] - 1;
        int rowOfDes = desValue / n;
        int colOfDes = desValue % n;
        return Math.abs(row-rowOfDes) + Math.abs(col-colOfDes);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    // two boards are equal if they are have the same size and their corresponding tiles are in the same positions
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        // compare the size and the position of the blank
        if (that.rowOfBlank != rowOfBlank || that.colOfBlank != colOfBlank || that.n != n) return false;
        // compare the corresponding tiles
        for (int i = 0; i < that.tiles.length; i++) {
            for (int j = 0; j < that.tiles[i].length; j++) {
                if (that.tiles[i][j] != tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        // if the blank is not in the first row, definitely can swap the blank and tile at its top
        if (rowOfBlank > 0) {
            int[][] neighbor = copyOf(this.tiles);
            swap(neighbor, rowOfBlank, colOfBlank, rowOfBlank - 1, colOfBlank);
            neighbors.add(new Board(neighbor));
        }
        // if the blank is not in the last row, definitely can swap the blank and tile at its bottom
        if (rowOfBlank < n - 1) {
            int[][] neighbor = copyOf(this.tiles);
            swap(neighbor, rowOfBlank, colOfBlank, rowOfBlank + 1, colOfBlank);
            neighbors.add(new Board(neighbor));
        }
        // if the blank is not in the first column, definitely can swap the blank and tile at its left
        if (colOfBlank > 0) {
            int[][] neighbor = copyOf(this.tiles);
            swap(neighbor, rowOfBlank, colOfBlank, rowOfBlank, colOfBlank - 1);
            neighbors.add(new Board(neighbor));
        }
        // if the blank is in the last column, definitely can swap the blank and tile at its right
        if (colOfBlank < n - 1) {
            int[][] neighbor = copyOf(this.tiles);
            swap(neighbor, rowOfBlank, colOfBlank, rowOfBlank, colOfBlank + 1);
            neighbors.add(new Board(neighbor));
        }
        return neighbors;
    }

    // swap two tiles in the board
    private void swap(int[][] currTiles, int row1, int col1, int row2, int col2) {
        int tmp = currTiles[row2][col2];
        currTiles[row2][col2] = currTiles[row1][col1];
        currTiles[row1][col1] = tmp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = copyOf(tiles);
        if (rowOfBlank != 0) swap(copy, 0, 0, 0, 1);
            else swap(copy, 1, 0, 1, 1);
        return new Board(copy);
    }

    // unit testing (not graded)
    // public static void main(String[] args) {
    //
    // }

}
