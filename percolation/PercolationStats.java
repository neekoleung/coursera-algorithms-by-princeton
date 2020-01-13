/* *****************************************************************************
 *  Name: Neeko
 *  Date: Dec 13th, 2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid n or trials!");
        }

        double[] resultSet = new double[trials];

        for (int i = 0; i < trials; i++) {
            resultSet[i] = generateRandomPercolation(n);
        }

        this.mean = StdStats.mean(resultSet);
        this.stddev = StdStats.stddev(resultSet);
        this.confidenceLo = mean - (1.96 * stddev / Math.sqrt(trials));
        this.confidenceHi = mean + (1.96 * stddev / Math.sqrt(trials));
    }

    private double generateRandomPercolation(int n) {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int s = StdRandom.uniform(1, n*n+1);
            int row = (int) Math.ceil((double) s / n);
            int col = (s % n == 0) ? n : s % n;
            p.open(row, col);
        }
        double threshold = (double) p.numberOfOpenSites()/(n*n);
        return threshold;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(64, 150);
        System.out.println(String.format("%-23s", "mean")+" = "+p.mean);
        System.out.println(String.format("%-23s", "stddev")+" = "+p.stddev);
        System.out.println("95% confidence interval = [" + p.confidenceLo + "," + p.confidenceHi + "]");

    }
}
