package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import Player.PlayerCreator;
import Player.PlayerType;
import Player.ReversiPlayer;
import Tile.PointyTopHexagon;
import Tile.ReversiTile;

/**
 * A version of the game Reversi that is played on hexagonal tiles. The tiles are arranged
 * in a grid-like pattern that creates the shape of a larger hexagon.
 */
public class HexagonalReversi implements ReversiModel {
  // The Point uses axial coordinates as described on the page linked in the instructions
  // The x value of the point is the q value of the tile, which is like a diagonal column
  // The y value of the point is the r value of the tile, which is a horizontal row

  // A map that represents the board using each hexagon's axial coordinates
  private final Map<Point, PointyTopHexagon> tiles;
  private final Map<PointyTopHexagon, Color> tileColors; // A map of each tile to its disc color
  private final ReversiPlayer player1; // The player who moves first and uses black discs
  private final ReversiPlayer player2; // The player who moves second and uses white discs
  private ReversiPlayer currentPlayer; // A reference to the player whose move it currently is

  /**
   * A constructor that specifies the type of each player and the
   * side length of the board.
   * @param player1 The player type of the player who moves first and uses the black discs.
   * @param player2 The player type of the player who moves second and uses the white discs.
   * @param sideLength The side length, in hexagons, of each edge of the board.
   */
  public HexagonalReversi(PlayerType player1, PlayerType player2, int sideLength) {
    if (player1 == null || player2 == null) { // check if either player is null
      throw new IllegalArgumentException("Player types cannot be null.");
    }
    if (sideLength < 3) { // check if the side length is at least three
      throw new IllegalArgumentException("The board side length must be at least 3.");
    }

    this.player1 = PlayerCreator.create(player1); // set player 1 using its type
    this.player2 = PlayerCreator.create(player2); // set player 2 using its type
    this.currentPlayer = this.player1; // set the current player to player 1 (they go first)

    this.tiles = new HashMap<Point, PointyTopHexagon>(); // create a hashmap to store the tiles
    this.tileColors = new HashMap<PointyTopHexagon, Color>(); // create a hashmap for tile colors
    this.initBoard(sideLength); // initialize the state of the board
  }

  @Override
  public void moveAt(int row, int col) throws IllegalArgumentException, IllegalStateException {
    // TODO make this actually check if the move is possible
    ReversiTile tile = this.tiles.get(new Point(row, col));
    if (tile == null) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    // check if the move is valid

    // change the tile of the color that was moved at depending on who the current player is
    // using referential equality because currentPlayer simply refers to either player

    // use a helper method and pass in row, col and update every tile by going in all possible directions

    // change to the other player, again checking using referential equality
    this.currentPlayer = currentPlayer == this.player1 ? this.player2 : this.player1;
  }

  @Override
  public void passTurn() {
    // using referential equality because currentPlayer simply points to a ReversiPlayer object
    if (this.currentPlayer == this.player1) { // if the current player is player 1
      this.currentPlayer = this.player2; // switch to player 2
    }
    else { // if the current player is player 2
      this.currentPlayer = this.player1; // switch to player 1
    }
  }

  @Override
  public boolean isMovePossible(int row, int col) throws IllegalArgumentException {

    return false;
  }

  @Override
  public boolean anyMoves() {
    // TODO: implement method

    return false;
  }

  @Override
  public boolean isGameOver() {
    // TODO: implement method

    return false;
  }

  @Override
  public int getPlayer1Score() {
    // use a helper to find tiles with player 1's color (black)
    return this.tilesWithColor(Color.BLACK);
  }

  @Override
  public int getPlayer2Score() {
    // use a helper to find tiles with player 2's color (white)
    return this.tilesWithColor(Color.WHITE);
  }

  @Override
  public ReversiPlayer getCurrentPlayer() {
    return this.currentPlayer; // return a reference to the current player
    // player objects are immutable so no need to make a copy
  }

  @Override
  public Color getColorAt(int row, int col) throws IllegalArgumentException {
    ReversiTile tile = this.tiles.get(new Point(row, col));
    if (tile == null) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    return this.tileColors.get(this.tiles.get(new Point(row, col)));
  }

  /**
   * Returns a map that represents the coordinate location of each hexagonal tile
   * in the board. The location of the tiles are specified using axial coordinates, with
   * the first tile's coordinates being (0, 0).
   * @return A map of Point to ReversiTile.
   */
  @Override
  public Map<Point, ReversiTile> getTiles() {
    // create deep copy of the tiles map
    Map<Point, ReversiTile> clone = new HashMap<>(); // create a new hashmap
    for (Point point: this.tiles.keySet()) { // iterate over all the keys
      // create a copy of the hexagon tile and put it with the corresponding copy of the point
      clone.put(point, new PointyTopHexagon(this.tiles.get(point)));
    }
    return clone; // return the copy of tiles
  }

  @Override
  public Map<ReversiTile, Color> getTileColors() {
    // create deep copy of the tileColors map
    Map<ReversiTile, Color> clone = new HashMap<>(); // create a new hashmap
    for (PointyTopHexagon tile: this.tileColors.keySet()) { // iterate over all the keys
      // create a copy of the color and put it with the corresponding copy of the tile
      clone.put(new PointyTopHexagon(tile), new Color(this.tileColors.get(tile).getRGB()));
    }
    return clone; // return the copy of tiles
  }



  //          HELPER METHODS

  // initializes the state of the board using the given side length of the hexagon
  void initBoard(int side) {
    // this code is adapted from the "Movement Range" section of the page linked in the instructions
    // the "N" value is the number of tiles away from the center a tile can be, which in our case
    // is the side length of the hex grid - 1
    int N = side - 1;
    for (int q = -side + 1; q <= N; q++) { // iterate over all q values
      int rStart = Math.max(-N, -q - N); // calculate starting point of r values for this column
      int rEnd = Math.min( N, -q + N); // calculate ending point of r values for this column
      for (int r = rStart; r <= rEnd; r++) { // loop over r values from start to end point
        // at the point (q, r) create a new hexagon that knows its own coordinates
        this.tiles.put(new Point(q, r), new PointyTopHexagon(new Point(q, r)));
      }
    }
    // at the points surrounding the center of the grid (which are hard coded)
    // change the disk color to white or black
    this.tileColors.put(this.tiles.get(new Point(0, -1)), Color.BLACK);
    this.tileColors.put(this.tiles.get(new Point(1, -1)), Color.WHITE);
    this.tileColors.put(this.tiles.get(new Point(1, 0)), Color.BLACK);
    this.tileColors.put(this.tiles.get(new Point(0, 1)), Color.WHITE);
    this.tileColors.put(this.tiles.get(new Point(-1, 1)), Color.BLACK);
    this.tileColors.put(this.tiles.get(new Point(-1, 0)), Color.WHITE);
  }

  // returns the total number of tiles with the given disc color
  int tilesWithColor(Color color) {
    int total = 0; // initialize total number of tiles with the color
    for (PointyTopHexagon tile : this.tiles.values()) { // iterate over all tiles in the map
      Color discColor = this.tileColors.get(tile); // get the disc color, which could be null
      if (discColor == null) { // if the discColor is null
        continue; // continue to the next tile
      }
      // if the color matches add 1, if not add 0
      total += discColor.equals(color) ? 1 : 0;
    }
    return total; // return the total
  }
}