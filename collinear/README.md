# Collinear Points
* [Point.java](https://github.com/neekoleung/coursera-algorithms-by-princeton/blob/master/collinear/Point.java): Creates a data type representing a point in the *xy* coordinate system. This class provides methods to compare two points, calculate the slope of a point, compare the slopes between two line segments, visualize the point and line segment.

* [BruteCollinearPoints.java](https://github.com/neekoleung/coursera-algorithms-by-princeton/blob/master/collinear/BruteCollinearPoints.java): __A Brute force solution.__ This program examines four points at a time and checks whether they all lie on the same line segment, returning all such line segments. The time complexity is __*O(n<sup>4</sup>)*__.

* [FastCollinearPoints.java](https://github.com/neekoleung/coursera-algorithms-by-princeton/blob/master/collinear/FastCollinearPoints.java): __A faster, sorting-based solution.__ Combining with *Merge Sort* algorithm, this program solves the Collinear Points problem that contains more than four collinear points with time complexity of __*O(n<sup>2</sup>logn)*__.



For more information, please refer to the [Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php).
