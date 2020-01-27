/* *****************************************************************************
 *  Name: Neeko
 *  Date: Jan 27th, 2020
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] copyPoints;
    private final ArrayList<LineSegment> lines;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // corner cases check
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

        // O(n^4): worst case time complexity
        for (int k1 = 0; k1 < copyPoints.length; k1++) {
            for (int k2 = k1 + 1; k2 < copyPoints.length; k2++) {
                for (int k3 = k2 + 1; k3 < copyPoints.length; k3++) {
                    if (copyPoints[k1].slopeTo(copyPoints[k2]) == copyPoints[k1].slopeTo(copyPoints[k3])) {
                        for (int k4 = k3 + 1; k4 < copyPoints.length; k4++) {
                            if (copyPoints[k1].slopeTo(copyPoints[k3]) == copyPoints[k1].slopeTo(copyPoints[k4])) {
                                lines.add(new LineSegment(copyPoints[k1], copyPoints[k4]));
                            }
                        }
                    }
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
}
