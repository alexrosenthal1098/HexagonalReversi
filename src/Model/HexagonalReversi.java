package Model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import Player.PlayerCreator;
import Player.PlayerType;
import Player.ReversiPlayer;
import Tile.PointyTopHexagon;
import Tile.ReversiTile;

public class HexagonalReversi implements ReversiModel {
  // this design was sourced from the link provided on the instructions page
  // The Point uses axial coordinates as described on the linked page
  // The x value of the point is the q value of the tile, which is like a diagonal column
  // The y value of the point is the r value of the tile, which is a horizontal row
  private final Map<Point, PointyTopHexagon> tiles; // A map representation of a hexagonal grid
  private final ReversiPlayer player1; // The player who moves first and uses black discs
  private final ReversiPlayer player2; // The player who moves second and uses white discs
  private ReversiPlayer currentPlayer; // A reference to the player whose move it currently is

  public HexagonalReversi(PlayerType player1, PlayerType player2, int sideLength) {
    if (player1 == null || player2 == null) { // check if either player is null
      throw new IllegalArgumentException("Player types cannot be null.");
    }
    if (sideLength < 3) { // check if the side length is at least three
      throw new IllegalArgumentException("The board side length must be at least 3.");
    }
    // set player 1 using its type and the color black
    this.player1 = PlayerCreator.create(player1, Color.BLACK);

    // set player 2 using its type and the color black
    this.player2 = PlayerCreator.create(player2, Color.WHITE);

    this.currentPlayer = this.player1; // set the current player to player 1 (they go first)

    // initialize the size of the board using simple equation for number of rows and columns
    // in a hex grid
    this.tiles = new HashMap<Point, PointyTopHexagon>();

    this.initTiles(sideLength); // initialize the state of the board
  }

  @Override
  public void moveAt(int row, int col) throws IllegalArgumentException, IllegalStateException {
    // TODO: implement method
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
    // TODO: implement method

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
    // TODO: implement method
    // can probably use a helper that you pass in the current player
    return 0;
  }

  @Override
  public int getPlayer2Score() {
    // TODO: implement method
    // can probably use a helper that you pass in the current player
    return 0;
  }

  @Override
  public ReversiPlayer getCurrentPlayer() {
    return this.currentPlayer; // return a reference to the current player
    // player objects are immutable so no need to make a copy
  }

  @Override
  public ReversiPlayer getPlayerAt(int row, int col) throws IllegalArgumentException {
    // TODO: implement method
    return null;
  }

  @Override
  public HashMap<Point, ReversiTile> getTiles() {
    // create deep copy of the tiles
    HashMap<Point, ReversiTile> clone = new HashMap<>(); // create a new hashmap
    for (Point point: this.tiles.keySet()) { // iterate over all the keys
      // create a new hexagon tile and put it with the corresponding key
      clone.put(point, new PointyTopHexagon(this.tiles.get(point)));
    }
    return clone; // return the copy of tiles
  }



  //          HELPER METHODS

  // initializes the state of the board using the given side length of the hexagon
  void initTiles(int side) {
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
    // place 3 black and white disks
    this.tiles.get(new Point(0, -1)).changeColor(Color.BLACK);
    this.tiles.get(new Point(1, -1)).changeColor(Color.WHITE);
    this.tiles.get(new Point(1, 0)).changeColor(Color.BLACK);
    this.tiles.get(new Point(0, 1)).changeColor(Color.WHITE);
    this.tiles.get(new Point(-1, 1)).changeColor(Color.BLACK);
    this.tiles.get(new Point(-1, 0)).changeColor(Color.WHITE);
  }
}
