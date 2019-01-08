import java.util.*;

class Solution {

  public static void main(String[] args) {
    try {
      Solution s = new Solution();
      s.drawRect('L', 1, 1, 4, 4);
      s.drawRect('R', 2, 1, 4, 4);
      s.print();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // instance variables here

  List<Rectangle> rectangles = new ArrayList<>();;

  /*
   * Solution() { this.rectangles = new ArrayList<>(); }
   */

  void drawRect(char c, int x1, int y1, int x2, int y2) {
    Rectangle newRectangle = new Rectangle(c, x1, y1, x2, y2);
    this.rectangles.add(0, newRectangle);
  }

  void bringRectToFront(int x, int y) {
    for (int i = 0; i < this.rectangles.size(); i++) {
      if (this.rectangles.get(i).containsSquare(x, y)) {
        Rectangle current = this.rectangles.get(i);
        this.rectangles.remove(i);
        this.rectangles.add(0, current);
        break;
      }
    }
  }

  void dragRect(int x, int y, int x1, int y1) {
    for (int i = 0; i < rectangles.size(); i++) {
      if (rectangles.get(i).containsSquare(x, y)) {
        Rectangle current = rectangles.get(i);
        /* Need to modify the current rectangle */
        int xOffset = x - current.x;
        int yOffset = y - current.y;
        current.moveRect(x1 - xOffset, y1 - yOffset);
        break;
      }
    }
  }

  void eraseArea(int x1, int y1, int x2, int y2) {
    for (Rectangle r : rectangles) {
      r.eraseArea(x1, y1, x2, y2);
    }
  }

  void print() {
    char[][] canvas = new char[30][30];
    for (int i = this.rectangles.size() - 1; i >= 0; i--) {
      Rectangle current = this.rectangles.get(i);
      current.drawOnCanvas(canvas);
    }
    // Print the canvas
    for (char[] x : canvas) {
      for (char y : x) {
        System.out.print(y + " ");
      }
      System.out.println();
    }
  }

  class Rectangle {
    int x;
    int y;
    char[][] data;

    Rectangle(char c, int x1, int y1, int x2, int y2) {
      this.x = x1;
      this.y = y1;
      this.data = new char[x2 - x1][y2 - y1];
      for (char[] row : this.data) {
        Arrays.fill(row, c);
      }
    }

    // returns true if this rectangle contains the
    // passed in square
    boolean containsSquare(int x, int y) {
      int x2 = this.x + data.length;
      int y2 = this.y + data[0].length;
      boolean xInBounds = x >= this.x && x < x2;
      boolean yInBounds = y >= this.y && y < y2;
      boolean isValid = xInBounds && yInBounds && this.data[x - this.x][y - this.y] != '.';
      return isValid;
    }

    // moves this rectangle to the x and y coordinate
    // that is passed in
    void moveRect(int x, int y) {
      this.x = x;
      this.y = y;
    }

    void eraseArea(int x1, int y1, int x2, int y2) {
      for (int i = x1; i <= x2; i++) {
        for (int j = y1; j <= y2; j++) {
          this.erasePixel(i, j);
        }
      }
    }

    char[][] drawOnCanvas(char[][] canvas) {
      for (int i = 0; i < this.data.length; i++) {
        for (int j = 0; j < this.data[0].length; j++) {
          int xCanvas = this.x + i;
          int yCanvas = this.y + j;
          if (isPixelOnCanvas(xCanvas, yCanvas, canvas)) {
            canvas[xCanvas][yCanvas] = this.data[i][j];
          }
        }
      }
      return canvas;
    }

    private boolean isPixelOnCanvas(int x, int y, char[][] canvas) {
      int canvasWidth = canvas.length;
      int canvasHeight = canvas[0].length;
      boolean xInBounds = x >= 0 && x < canvasWidth;
      boolean yInBounds = y >= 0 && y < canvasHeight;
      return xInBounds && yInBounds;
    }

    // checks if this square has the included pixel and
    // erases it if it does
    private void erasePixel(int x, int y) {
      if (this.containsSquare(x, y)) {
        this.data[x - this.x][y - this.y] = '.';
      }
    }
  }
}