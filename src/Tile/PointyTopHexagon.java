package Tile;

import java.awt.*;
import java.util.Objects;

import Player.ReversiPlayer;

/**
 * A class that represents a pointy-top shaped hexagon tile for the
 * game Reversi.
 */
public class PointyTopHexagon implements ReversiTile {
  private ReversiPlayer player; // the player that occupies this tile
  // if this field is null, then no player occupies this tile and it has no disc on it

  /**
   * An empty constructor for a PointyTopHexagon. Used to initialize the state
   * of the board in a Reversi game before players have made moves.
   */
  public PointyTopHexagon() { }

  @Override
  public Polygon buildTile(Point center, int sideLength) throws IllegalArgumentException {
    // TODO: add 6 points to a polygon to make hexagon with the given side length
    // ensure the center point is not null and side length is positive
    if (center == null || sideLength <= 0) {
      throw new IllegalArgumentException("Center cannot be null and side length must be positive");
    }
    return new Polygon();
  }

  @Override
  public void changePlayer(ReversiPlayer player) {
    // ensure given player is not null and set this player
    this.player = Objects.requireNonNull(player);
  }

  @Override
  public String toString() {
    if (this.player == null) { // if this tile is unoccupied (player is null)
      return "_";
    }
    else if (this.player.getColor().equals(Color.BLACK)) { // if the players color is black
      return "X";
    }
    else if (this.player.getColor().equals(Color.WHITE)) { // if the players color is white
      return "O";
    }
    else { // if the color is not null, black, or white
      throw new IllegalStateException("The player that occupies this tile" +
              "has an unrecognized color"); // throw an error
    }
  }
}
