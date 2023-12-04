package adapters;

import java.awt.*;

import model.tile.PointyTopHexagon;
import model.tile.ReversiTile;
import providers.model.board.Fill;
import providers.model.board.ICell;
import providers.model.board.Posn;

/**
 * A class that adapts our implementation of a ReversiTile to the providers ICell.
 */
public class ToProviderCell implements ICell {
  private final ReversiTile ourTile; // the adaptee tile
  private int x;
  private int y;
  private boolean clicked;

  /**
   * A constructor for ToProviderCell that uses a ReversiTile as an adaptee.
   * @param ourTile A ReversiTile to adapt to an ICell.
   */
  public ToProviderCell(ReversiTile ourTile) {
    if (ourTile == null) {
      throw new IllegalArgumentException("Given tile cannot be null.");
    }
    this.ourTile = ourTile;
    this.clicked = false;
  }

  @Override
  public void changeX(int x) {
    this.x = x;
  }

  @Override
  public void changeY(int y) {
    this.y = y;
  }

  @Override
  public Fill.FillColor getFill() {
    if (!this.ourTile.hasDisk()) {
      return Fill.FillColor.EMPTY;
    }

    return this.colorToFill(this.ourTile.getTopColor());
  }

  @Override
  public void changeFill(Fill.FillColor fill) {
    if (fill == null) {
      throw new IllegalArgumentException("Given fill color cannot be null.");
    }
    if (!this.ourTile.hasDisk()) {
      switch(fill) {
        case BLACK:
          this.ourTile.placeDisk(Color.BLACK, Color.WHITE);
          break;
        case WHITE:
          this.ourTile.placeDisk(Color.WHITE, Color.BLACK);
          break;
        default:
          break;
      }
    }
    else {
      switch(fill) {
        case BLACK:
          if (this.ourTile.getTopColor().equals(Color.BLACK)) {
            return;
          }
          this.ourTile.flipDisk();
          break;
        case WHITE:
          if (this.ourTile.getTopColor().equals(Color.WHITE)) {
            return;
          }
          this.ourTile.flipDisk();
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void setClicked(boolean ifClicked) {
    this.clicked = ifClicked;
  }

  @Override
  public boolean clicked() {
    return this.clicked;
  }

  @Override
  public void setColor(String color) {
    if (color == null) {
      throw new IllegalArgumentException("Given string cannot be null.");
    }
    if (color.equalsIgnoreCase("BLACK")) {
      this.changeFill(this.colorToFill(Color.BLACK));
    }
    else if (color.equalsIgnoreCase("WHITE")) {
      this.changeFill(this.colorToFill(Color.WHITE));
    }
  }

  @Override
  public Fill.FillColor getColor() {
    return this.getFill();
  }

  @Override
  public double getA(int size, int boardSize) {
    // using the same formula as in HexagonalBoard.getTileSideLength()
    return (double) boardSize / ((size * 2 - 1) * Math.sqrt(3));
  }

  @Override
  public Posn getCenter(int size, int boardSize) {
    // using roughly the same formula as in HexagonalBoard.updateBoard()
    double tileSide = getA(size, boardSize);
    double rowDif = Math.abs(size - 1 - this.y);
    double x = tileSide * Math.sqrt(3) * (rowDif / 2.0) + this.x * tileSide * Math.sqrt(3)
            + Math.sqrt(3) * tileSide * .5;
    double y = tileSide + (this.y * ((1.5) * tileSide));
    return new Posn(x, y);



  }

  @Override
  public Posn getLogicalPosn() {
    return new Posn(x, y);
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ToProviderCell) {
      ToProviderCell that = (ToProviderCell) other;
      return this.x == that.x & this.y == that.y && this.clicked == that.clicked &&
              this.ourTile == that.ourTile;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return (this.x * 37 + this.y * 37 + Boolean.hashCode(this.clicked) + this.ourTile.hashCode());
  }

  @Override
  public String toString() {
    return String.format("Clicked: %b Color: %s Location: (%d, %d)\n",
            this.clicked, this.getFill(), this.x, this.y);
  }


  // returns the Fill.FillColor that corresponds to the given color
  // returns empty if the color is not black or white
  private Fill.FillColor colorToFill(Color color) {
    if (color.equals(Color.BLACK)) {
      return Fill.FillColor.BLACK;
    }
    else if (color.equals(Color.WHITE)) {
      return Fill.FillColor.WHITE;
    }
    else {
      return Fill.FillColor.EMPTY;
    }
  }
}
