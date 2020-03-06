/* *****************************************************************************
 *  Name: Neeko Leung
 *  Date: Mar 7th, 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private TreeNode root;
    private int size;
    private final RectHV CONTAINER = new RectHV(0, 0, 1, 1);   // the 1 * 1 base container
    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    private class TreeNode {
        private final double x;
        private final double y;
        private TreeNode left;
        private TreeNode right;
        private final boolean isVertical;

        public TreeNode(double x, double y, TreeNode left, TreeNode right, boolean isVertical) {
            this.x = x;
            this.y = y;
            this.left = left;
            this.right = right;
            this.isVertical = isVertical;
        }

    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, true);
    }

    private TreeNode insert(TreeNode node, Point2D p, boolean isVertical) {
        // creates a root if the node is null (corner case)
        if (node == null) {
            size++;
            return new TreeNode(p.x(), p.y(), null, null, isVertical);
        }

        // directly returns when the point is in the tree (base case)
        if (node.x == p.x() && node.y == p.y()) return node;

        // put the node as the left child if (is in the vertical line and have smaller x / is in the horizontal line and have smaller y)
        // put the node as the right child if (is in the vertical line and have bigger x / is in the horizontal line and have bigger y)
        if (node.isVertical && p.x() < node.x || !node.isVertical && p.y() < node.y) {
            node.left = insert(node.left, p, !node.isVertical);
        } else {
            node.right = insert(node.right, p, !node.isVertical);
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p.x(), p.y());
    }

    private boolean contains(TreeNode node, double x, double y) {
        // corner case
        if (node == null) return false;

        // the node has the same x & y with the current node (base case)
        if (node.x == x && node.y == y) return true;

        // continue compare with the left child if (is in the vertical line and have smaller x / is in the horizontal line and have smaller y)
        // continue compare with the right child if (is in the vertical line and have bigger x / is in the horizontal line and have bigger y)
        if (node.isVertical && x < node.x || !node.isVertical && y < node.y) {
            return contains(node.left, x, y);
        } else {
            return contains(node.right, x, y);
        }
    }

    // draw all points to standard draw
    public void draw() {
        // draw the 1 * 1 container
        StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        CONTAINER.draw();

        // draw the points and division lines
        draw(root, CONTAINER);
    }

    private void draw(TreeNode node, RectHV rect) {
        // corner case
        if (node == null) return;

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.x, node.y).draw();

        // get the maximum and minimum point of the division line
        Point2D min, max;
        // draw the vertical line in red and horizontal line in blue
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.x, rect.ymin());
            max = new Point2D(node.x, rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.y);
            max = new Point2D(rect.xmax(), node.y);
        }
        // draw the line from minimum point to maximum
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children (resize the rect)
        draw(node.left, leftRect(rect, node));
        draw(node.right, rightRect(rect, node));
    }

    private RectHV leftRect(RectHV rect, TreeNode node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
        }
    }

    private RectHV rightRect(final RectHV rect, TreeNode node) {
        if (node.isVertical) {
            return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
        }
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> rangeSet = new ArrayList<>();
        range(root, CONTAINER, rect, rangeSet);
        return rangeSet;
    }

    private void range(TreeNode node, RectHV nrect, RectHV rect, List<Point2D> rangeSet) {
        // corner case
        if (node == null) return;

        if (rect.intersects(nrect)) {
            final Point2D p = new Point2D(node.x, node.y);
            if (rect.contains(p)) rangeSet.add(p);
            range(node.left, leftRect(nrect, node), rect, rangeSet);
            range(node.right, rightRect(nrect, node), rect, rangeSet);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return nearest(root, CONTAINER, p.x(), p.y(), null);
    }

    private Point2D nearest(TreeNode node, RectHV rect, double x, double y, Point2D curr) {
        // corner case
        if (node == null) {
            return curr;
        }

        double dqn = 0.0;
        double drq = 0.0;
        RectHV left = null;
        RectHV rigt = null;
        final Point2D query = new Point2D(x, y);
        Point2D nearest = curr;

        if (nearest != null) {
            dqn = query.distanceSquaredTo(nearest);
            drq = rect.distanceSquaredTo(query);
        }

        if (nearest == null || dqn > drq) {
            final Point2D point = new Point2D(node.x, node.y);
            if (nearest == null || dqn > query.distanceSquaredTo(point))
                nearest = point;

            if (node.isVertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
                rigt = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());

                if (x < node.x) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                rigt = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());

                if (y < node.y) {
                    nearest = nearest(node.left, left, x, y, nearest);
                    nearest = nearest(node.right, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.right, rigt, x, y, nearest);
                    nearest = nearest(node.left, left, x, y, nearest);
                }
            }
        }
        return nearest;
    }

    // public static void main(String[] args) {
    //
    // }
}
