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
   */
  public ToProviderCell() {
    this.ourTile = new PointyTopHexagon();
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
    return (double) boardSize / ((double) (size * 2 - 1) * Math.sqrt(3));
  }

  @Override
  public Posn getCenter(int size, int boardSize) {
    // using roughly the same formula as in HexagonalBoard.updateBoard()
    double tileSideL = this.getA(size, boardSize);
    double rowDif = Math.abs(size - 1 - this.y);
    double x = (tileSideL * rowDif * Math.sqrt(3) / 2.0 ) + (this.x * 3 * tileSideL);
    double y = tileSideL + (this.y * 1.5 * tileSideL);
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
