package providers.model.board;


/**
 * Represents a cell to be part of a Reversi Board.
 */
public class Cell implements ICell {
  private int x;
  private int y;
  Fill.FillColor fill;

  boolean clicked = false;

  /**
   * Constructs a new cell with no x and y so defaults to 0.
   */
  public Cell() {
    fill = Fill.FillColor.EMPTY;
    x = -1;
    y = -1;
  }

  /**
   * Changes the x coord of teh cell.
   *
   * @param x is the new x value
   */
  @Override
  public void changeX(int x) {
    this.x = x;
  }

  /**
   * Changes the y coord of the cell.
   *
   * @param y is the new y value
   */
  @Override
  public void changeY(int y) {
    this.y = y;
  }

  /**
   * gets the fill of the cell.
   *
   * @return fill is the fill value
   */
  @Override
  public Fill.FillColor getFill() {
    return fill;
  }

  /**
   * Changes the fill of the cell.
   *
   * @param fill is the new fill value
   */
  @Override
  public void changeFill(Fill.FillColor fill) {
    this.fill = fill;
  }

  /**
   * Overrides the equals() of the original Cell to compare two cells.
   *
   * @param other is the other cell we are comparing to.
   * @return whether or not the two cells are equal
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Cell)) {
      return false;
    }
    Cell that = (Cell) other;

    if (that.x == this.x && that.y == this.y) {
      if (that.fill == this.fill) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the clicked boolean to true or false.
   *
   * @param ifClicked is the boolean we are setting clicked to
   */
  @Override
  public void setClicked(boolean ifClicked) {
    clicked = ifClicked;
  }

  /**
   * Gets the clicked boolean.
   *
   * @return the clicked boolean
   */
  @Override
  public boolean clicked() {
    return clicked;
  }

  /**
   * ovversides the hashcode method.
   *
   * @return int of teh hashcode for Cell
   */
  @Override
  public int hashCode() {
    return fill.hashCode();
  }

  /**
   * Constructs a new Cell with a given x and y.
   *
   * @param x is the Cell x relative to the furthest left cell on that row of the board
   * @param y is the Cell y relative to the furthest up cell on the board
   */
  public Cell(int x, int y) {
    fill = Fill.FillColor.EMPTY;
    this.x = x;
    this.y = y;
  }

  /**
   * Sets the color of this cell.
   *
   * @param color is the color we are trying to set the cell to
   */
  @Override
  public void setColor(String color) {
    if (color.equals("BLACK")) {
      fill = Fill.FillColor.BLACK;
    } else if (color.equals("WHITE")) {
      fill = Fill.FillColor.WHITE;
    } else {
      fill = Fill.FillColor.EMPTY;
    }
  }

  /**
   * Gets the fill of this cell.
   *
   * @return the fill
   */
  @Override
  public Fill.FillColor getColor() {
    return fill;
  }


  /**
   * Gets the edge length of this cell.
   *
   * @param size      is the size of the cell
   * @param boardSize is the size of the board
   * @return the a value
   */
  @Override
  public double getA(int size, int boardSize) {
    return (double) boardSize / ((size * 2 - 1) * Math.sqrt(3));
  }

  /**
   * Gets the center of this cell.
   *
   * @param size      is the size of the cell
   * @param boardSize is the size of the board
   * @return the center
   */
  @Override
  public Posn getCenter(int size, int boardSize) {
    double a = getA(size, boardSize);
    double howFarFromMiddle = Math.abs(size - 1 - this.y);
    double x = a * Math.sqrt(3) * (howFarFromMiddle / 2.0) + this.x * a * Math.sqrt(3)
            + Math.sqrt(3) * a * .5;
    //(a * Math.sqrt(3)) * (Math.abs(((boardSize - 1) / 2)) / 2.0);
    //    if (howFarFromMiddle % 2 == 0) {
    //      x = this.x * (a * Math.sqrt(3)) + (howFarFromMiddle / 2) *
    //      (a * Math.sqrt(3)) + (a * Math.sqrt(3) / 2);
    //    }
    //    else {
    //      x = this.x * (a * Math.sqrt(3)) + (howFarFromMiddle * ((a *
    //      Math.sqrt(3) / 2))) +  (a * Math.sqrt(3) / 2);
    //    }
    double y = a + (this.y * ((1.5) * a));
    return new Posn(x, y);
  }

  /**
   * Gets the logical position of this cell.
   *
   * @return the logical position
   */
  @Override
  public Posn getLogicalPosn() {
    return new Posn(x, y);
  }

  /**
   * Gets the x of this cell.
   *
   * @return the x
   */
  @Override
  public int getX() {
    return x;
  }

  /**
   * Gets the y of this cell.
   *
   * @return the y
   */
  @Override
  public int getY() {
    return y;
  }
}


