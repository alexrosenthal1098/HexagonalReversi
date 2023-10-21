package Tile;

import java.awt.*;
import java.util.Objects;

import Player.ReversiPlayer;

/**
 * A class that represents a pointy-top shaped hexagon tile for the
 * game Reversi.
 */
public class PointyTopHexagon implements ReversiTile {
  private ReversiPlayer player;

  /**
   * An empty constructor for a PointyTopHexagon. Used to initialize the state
   * of the board in a Reversi game.
   */
  public PointyTopHexagon() { }

  @Override
  public Polygon buildTile(Point center, int sideLength) throws IllegalArgumentException {
    // TODO: add 6 points to a polygon to make hexagon with the given side length
    if (center == null || sideLength <= 0) {
      throw new IllegalArgumentException("Center cannot be null and side length must be positive");
    }
    return null;
  }

  @Override
  public void changePlayer(ReversiPlayer player) {
    this.player = Objects.requireNonNull(player);
  }

  @Override
  public String toString() {
    if (this.player == null) {
      return "_";
    }
    else if (this.player.getColor().equals(Color.BLACK)) {
      return "X";
    }
    else {
      return "O";
    }
  }
}
