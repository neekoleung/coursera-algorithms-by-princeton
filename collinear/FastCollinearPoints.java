/* *****************************************************************************
 *  Name: Neeko
 *  Date: Jan 27th, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final Point[] copyPoints;
    private final ArrayList<LineSegment> lines;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        // deep copy the array to avoid change the input array to avoid mutate the constructor argument
        copyPoints = new Point[points.length];
        for (int i = 0; i < copyPoints.length; i++) {
            copyPoints[i] = points[i];
        }

        // sort the elements in array in a natural order
        Arrays.sort(copyPoints);
        checkDuplicate(copyPoints);
        lines = new ArrayList<LineSegment>();

        for (int i = 0; i < copyPoints.length; i++) {
            Point p = copyPoints[i];
            Point[] pointsBySlope = copyPoints.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            int n = 1;
            while (n < pointsBySlope.length) {
                LinkedList<Point> candidates = new LinkedList<>();
                final double SLOPE_OF_P = p.slopeTo(pointsBySlope[n]);
                do {
                    candidates.add(pointsBySlope[n++]);
                } while (n < pointsBySlope.length && p.slopeTo(pointsBySlope[n]) == SLOPE_OF_P);


                if (candidates.size() >= 3 && p.compareTo(candidates.peek()) < 0) {
                    Point min = p;
                    Point max = candidates.removeLast();
                    lines.add(new LineSegment(min, max));
                }
            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    private void checkNull(Point[] points) {
        // if the array is null
        if (points == null) throw new IllegalArgumentException("The input array is null!");
        // if the element is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("There are null points in the array!");
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                throw new IllegalArgumentException("There are duplicate elements in the array!");
            }
        }
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
