package providers.model.board;

/**
 * Represents a cell to be part of a Reversi Board.
 */
public interface ICell {


  /**
   * changes the x value of a cell.
   *
   * @param x is the new x value
   */
  void changeX(int x);

  /**
   * changes the y value of a cell.
   *
   * @param y is the new y value
   */
  void changeY(int y);

  /**
   * gets the fill of a cell.
   *
   * @return the fill of a cell
   */
  Fill.FillColor getFill();

  /**
   * changes the fill of a cell.
   */
  void changeFill(Fill.FillColor fill);


  /**
   * Determines if two cells are equal.
   *
   * @param other is the other cell.
   * @return true if the cells are equal, false otherwise.
   */
  boolean equals(Object other);

  /**
   * sets the clicked value of a cell.
   */
  void setClicked(boolean ifClicked);

  /**
   * gets the clicked value of a cell.
   *
   * @return the clicked value of a cell.
   */
  boolean clicked();

  /**
   * Gets the hashcode of a cell.
   *
   * @return the hashcode of a cell.
   */
  int hashCode();

  /**
   * Gets the string representation of a cell.
   *
   * @return the string representation of a cell.
   */
  String toString();

  /**
   * sets the color of a cell.
   */
  void setColor(String color);

  /**
   * gets the color of a cell.
   *
   * @return the color of a cell.
   */
  Fill.FillColor getColor();

  /**
   * gets A for a cell.
   *
   * @param size      is the size of the board
   * @param boardSize is the size of the board
   * @return A for a cell.
   */
  double getA(int size, int boardSize);


  /**
   * gets center for a cell.
   *
   * @param size      is the size of the board
   * @param boardSize is the size of the board
   * @return center for a cell.
   */
  Posn getCenter(int size, int boardSize);

  /**
   * gets the logical posn of a cell.
   *
   * @return the logical posn of a cell.
   */
  Posn getLogicalPosn();

  /**
   * gets the x value of a cell.
   *
   * @return the x value of a cell.
   */
  int getX();

  /**
   * gets the y value of a cell.
   *
   * @return the y value of a cell.
   */
  int getY();
}
