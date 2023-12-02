package providers.model.board;

/**
 * Represents a position on the board.
 */
public class Posn {
  double x;
  double y;

  /**
   * Constructs a Posn.
   *
   * @param x is the x coordinate
   * @param y is the y coordinate
   */
  public Posn(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * gets the X value of a posn.
   *
   * @return the x value
   */
  public double getX() {
    return x;
  }

  /**
   * gets the Y value of a posn.
   *
   * @return the y value
   */
  public double getY() {
    return y;
  }

  /**
   * Changes the x value of a posn.
   *
   * @param x is the new x value
   * @return the new x value
   */
  public double changeX(double x) {
    return this.x - x;
  }

  /**
   * Changes the y value of a posn.
   *
   * @param y is the new y value
   * @return the new y value
   */
  public double changeY(double y) {
    return this.y - y;
  }

  /**
   * detremines is tow posns are equal.
   *
   * @param other is the other posn.
   * @return true if they are equal.
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Posn)) {
      return false;
    }

    Posn that = (Posn) other;

    return (that.x == this.x && that.y == this.y);
  }

  /**
   * overrides the hashCode method.
   *
   * @return the hashcode of a posn
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }


  /**
   * overrides the toString method.
   *
   * @return the string representation of a posn
   */
  @Override
  public String toString() {
    return String.format("(%f,%f)", x, y);
  }
}
