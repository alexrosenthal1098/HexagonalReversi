package view.gui;

import java.awt.Point;

/**
 * A class that represents an empty point that does not exist.
 */
public class EmptyPoint extends Point {
  // no fields, constructors, or methods needed to represent an empty point

  // we only need to override equals so that only other empty points are equal
  @Override
  public boolean equals(Object other) {
    return other instanceof EmptyPoint;
  }

  @Override
  public int hashCode() {
    return this.x * 31 + this.y;
  }
}
