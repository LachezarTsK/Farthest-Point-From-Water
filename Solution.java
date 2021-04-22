import java.util.LinkedList;

public class Solution {

  // Applied instead of implementing a boolean array for 'visited'
  public final int NEW_MARK_FOR_ALL_LAND = 2;

  // Input data: initially all island area is designated with '1' and all water area with '0'.
  public int[][] matrix;
  public int[][] moves = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left
    {0, 1} // right
  };

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int solve(int[][] matrix)' so that the code
  can be run on the website. Even though the name 'solve' does not make
  a lot of sense, it is left as it is, so that the code can be run directly on the website,
  without any modifications.
  */
  public int solve(int[][] matrix) {
    this.matrix = matrix;
    return multiPoint_bfs_findfarthestPointFromWater();
  }
  /*
  Multipoint Breadth First Search: starting from the coastal water points finds the farthest land.
  @return The farthest land from water.
  */
  public int multiPoint_bfs_findfarthestPointFromWater() {

    LinkedList<Point> queue = findCoastalWaterPoints();

    // Matrix constists only of either '1' or '0'.
    if (queue.size() == 0 || queue.size() == matrix.length * matrix[0].length) {
      return -1;
    }

    int farthestPointFromWater = 0;

    while (!queue.isEmpty()) {

      Point current = queue.removeFirst();
      int row = current.row;
      int column = current.column;

      farthestPointFromWater = current.distanceFromWater;

      for (int i = 0; i < moves.length; i++) {
        int new_r = row + moves[i][0];
        int new_c = column + moves[i][1];

        if (isInMatrix(new_r, new_c) && matrix[new_r][new_c] == 1) {
          matrix[new_r][new_c] = NEW_MARK_FOR_ALL_LAND;
          Point p = new Point(new_r, new_c);
          p.distanceFromWater = current.distanceFromWater + 1;
          queue.add(p);
        }
      }
    }
    return farthestPointFromWater;
  }

  /*
  The search for the farthest land has to start from a costal water point.
  @return A list with all water points that are next to a coast.
  */
  public LinkedList<Point> findCoastalWaterPoints() {

    LinkedList<Point> coastalWaterPoints = new LinkedList<Point>();

    for (int r = 0; r < matrix.length; r++) {
      for (int c = 0; c < matrix[0].length; c++) {
        if (matrix[r][c] == 0 && isCoastalWaterPoint(r, c)) {
          coastalWaterPoints.add(new Point(r, c));
        }
      }
    }

    return coastalWaterPoints;
  }

  public boolean isCoastalWaterPoint(int row, int column) {

    for (int i = 0; i < moves.length; i++) {
      int new_r = row + moves[i][0];
      int new_c = column + moves[i][1];

      if (isInMatrix(new_r, new_c) && matrix[new_r][new_c] == 1) {
        return true;
      }
    }
    return false;
  }

  public boolean isInMatrix(int row, int column) {
    if (row < 0 || column < 0 || row > matrix.length - 1 || column > matrix[0].length - 1) {
      return false;
    }
    return true;
  }
}

class Point {
  int row;
  int column;
  int distanceFromWater;

  public Point(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
