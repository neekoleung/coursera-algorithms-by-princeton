/* *****************************************************************************
 *  Name: Neeko Leung
 *  Date: Mar 7th, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> pointSet;
    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("This point is null and cannot be inserted.");
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("This point is null.");
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point: pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        TreeSet<Point2D> rangeSet = new TreeSet<>();
        for (Point2D point: pointSet) {
            if (rect.contains(point)) rangeSet.add(point);
        }
        return rangeSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (pointSet.isEmpty()) return null;
        Point2D nearest = null;
        double distanceMin = Double.POSITIVE_INFINITY;   // The min distance between p and any point

        for (Point2D point: pointSet) {
            double distance = point.distanceSquaredTo(p);   // The distance between p and current point
            if (distance < distanceMin) {
                nearest = point;
                distanceMin = distance;
            }
        }
        return nearest;
    }

    // public static void main(String[] args) {
    //
    // }
}
