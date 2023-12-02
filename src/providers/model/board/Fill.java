package providers.model.board;

/**
 * Holds an enum that represents different types of Fills.
 */
public class Fill {

  /**
   * Represents different types of Fills.
   */
  public enum FillColor {
    EMPTY,
    BLACK,
    WHITE,
  }

  /**
   * overrides the .equals function
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Fill)) {
      return false;
    }
    Fill that = (Fill) other;
    return this == that;
  }

  /**
   * overrides the .hashCode function
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
