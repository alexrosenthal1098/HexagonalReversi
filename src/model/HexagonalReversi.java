package model;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import tile.PointyTopHexagon;
import tile.ReversiTile;

/**
 * A version of the game Reversi that is played on hexagonal tiles using black and white disks.
 * The tiles are arranged in a grid-like pattern that creates the shape of a larger hexagon.
 * The game has two players. Player one moves first and uses the black side of the disks while
 * player two, who moves second, uses the white side. The location of each tile is described
 * using axial coordinates (q and r in place of x and y respectively) where the center of the
 * board is at the point (0, 0).
 */
public class HexagonalReversi implements ReversiModel {
  //          FIELDS
  //////////////////////////////////////////
  protected final Color PLAYER_1_COLOR = Color.BLACK; // The disk color of player one
  protected final Color PLAYER_2_COLOR = Color.WHITE; // The disk color of player two

  // The board uses axial coordinates as described on the "Coordinate Systems" section of the
  // website linked in the README.
  // The x value of a point is the q value of the tile, which is like a diagonal column
  // The y value of a point is the r value of the tile, which is a horizontal row

  // A map that represents the board using each hexagon's axial coordinates
  // notice how the tiles can only be represented using hexagons!
  private final Map<Point, PointyTopHexagon> tiles;

  protected Color currentPlayer; // The disk color of the current player.

  // INVARIANT: currentPlayer equals PLAYER_1_COLOR or PLAYER_2_COLOR

  // All fields EXCEPT for tiles are declared as protected so that subclasses have the
  // opportunity to change the color of players or how the currentPlayer is decided.
  // The tiles field is kept private because we want to ensure that the shape of the board is not
  // changed after the model is instantiated. Subclasses can, however, override the makeBoard
  // helper if they want to change the board shape.



  /**
   * A constructor that specifies the side length of the board, in tiles.
   * The side length must be at least 3.
   * @param sideLength The side length, in hexagons, of each edge of the board.
   */
  public HexagonalReversi(int sideLength) {
    if (sideLength < 3) { // check if the side length is at least three
      throw new IllegalArgumentException("The board side length must be at least 3.");
    }
    // the currentPlayer invariant is guaranteed by the constructor because it is
    // initialized as player 1 color.
    this.currentPlayer = this.PLAYER_1_COLOR; // set the current player to player 1 (they go first)
    this.tiles = this.makeBoard(sideLength); // initialize the state of the board
  }



  //          INTERFACE METHODS
  ///////////////////////////////////////////////
  @Override
  public void moveAt(int q, int r) throws IllegalArgumentException, IllegalStateException {
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(q, r))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    if (!this.isMovePossible(q, r)) { // if the move is not possible
      throw new IllegalStateException("This move is not possible.");
    }

    // place a disk on the tile that was moved at with the current player's color face up
    this.tiles.get(new Point(q, r)).placeDisk(this.currentPlayer, this.otherPlayerColor());

    for (Point direction : this.getDirections()) {
      List<ReversiTile> tilesToChange = this.tilesToFlip(new Point(q, r),
              direction.x, direction.y); // get the tiles that we need to change
      for (ReversiTile tile : tilesToChange) { // iterate over all tiles to change
        tile.flipDisk(); // flip the disk on the tile to the current player's color
      }
    }
    this.passTurn(); // change the turn to the other player;
  }

  @Override
  public void passTurn() {
    // the currentPlayer is only modified in the passTurn method
    // the invariant is enforced because the otherPlayerColor helper can only return
    // either player 1's color or player 2's color.
    this.currentPlayer = this.otherPlayerColor(); // set the current player to the other player
  }

  @Override
  public boolean isMovePossible(int q, int r) throws IllegalArgumentException {
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(q, r))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    if (this.tiles.get(new Point(q, r)).hasDisk()) { // if the tile already has a disk
      return false; // then the move is not possible, so return false
    }

    for (Point delPoint : this.getDirections()) { // iterate over all directions to move in
      // find the tiles to flip in all directions starting at the given point
      List<ReversiTile> otherTiles = this.tilesToFlip(new Point(q, r), delPoint.x, delPoint.y);
      if (!otherTiles.isEmpty()) { // if the list of tiles to flip IS NOT empty
        return true; // then the move is possible, so return true
      }
    }
    // if there are no tiles to flip in all directions
    return false; // the move is not possible, return false
  }

  @Override
  public boolean anyMoves() {
    for (Point tilePoint : this.tiles.keySet()) { // iterate over all tile locations
      if (!this.tiles.get(tilePoint).hasDisk()) { // if the tile does not have a disk
        // check if a move is possible at that tile
        if (this.isMovePossible(tilePoint.x, tilePoint.y)) { // if the move is possible
          return true; // return true
        }
        // if a move is not possible, continue on trying moves
      }
      // if the tile has a disk then you can't move there, so keep trying
    }
    return false; // if all the moves are not possible, there are no moves so return false
  }

  @Override
  public boolean isGameOver() {
    boolean curPlayerHasMoves = this.anyMoves(); // check if the current player has any moves
    this.passTurn(); // pass the turn to the next player
    boolean nextPlayerHasMoves = this.anyMoves(); // check if they have any moves
    this.passTurn(); // pass the turn back to the first player to avoid messing up turns

    // if neither player has any moves, return true
    return !curPlayerHasMoves && !nextPlayerHasMoves;
  }

  @Override
  public int getPlayer1Score() {
    // use a helper to find tiles with player one's disk color
    return this.tilesWithColor(this.PLAYER_1_COLOR);
  }

  @Override
  public int getPlayer2Score() {
    // use a helper to find tiles with player two's disk color
    return this.tilesWithColor(this.PLAYER_2_COLOR);
  }

  @Override
  public Color getCurrentPlayer() {
    return new Color(this.currentPlayer.getRGB()); // copy and return the current player's color
  }

  @Override
  public Color getColorAt(int q, int r) throws IllegalArgumentException, IllegalStateException {
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(q, r))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    ReversiTile tile = this.tiles.get(new Point(q, r)); // get the tile at that point
    if (!tile.hasDisk()) { // if the tile does not have a disk
      // throw an exception
      throw new IllegalStateException("Cannot get a color from tile without a disk.");
    }

    // if the tile does have a disk, return its disk color
    return tile.getTopColor();
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
      clone.put(new Point(point), new PointyTopHexagon(this.tiles.get(point)));
    }
    return clone; // return the copy of tiles
  }



  //          HELPER METHODS
  ////////////////////////////////////////////
  // initializes the state of the board using the given side length of the hexagon
  // this method has the protected modifier in case a subclass wants to use a different board shape
  protected Map<Point, PointyTopHexagon> makeBoard(int side) {
    Map<Point, PointyTopHexagon> board = new HashMap<>(); // create the board

    // this code is adapted from the "Movement Range" section of the website linked in the README
    // the "n" value is the number of tiles away from the center a tile can be, which in our case
    // is the side length of the hex grid - 1
    int n = side - 1;
    for (int q = -side + 1; q <= n; q++) { // iterate over all q values
      int rStart = Math.max(-n, -q - n); // calculate starting point of r values for this q column
      int rEnd = Math.min( n, -q + n); // calculate ending point of r values for this q column
      for (int r = rStart; r <= rEnd; r++) { // loop over r values from start to end point
        // at the point (q, r) create a new hexagon
        board.put(new Point(q, r), new PointyTopHexagon());
      }
    }
    // at the points surrounding the center of the grid (which are hard coded)
    // place a disk, alternating which side is face up
    board.get(new Point(0, -1)).placeDisk(this.PLAYER_1_COLOR, this.PLAYER_2_COLOR);
    board.get(new Point(1, -1)).placeDisk(this.PLAYER_2_COLOR, this.PLAYER_1_COLOR);
    board.get(new Point(1, 0)).placeDisk(this.PLAYER_1_COLOR, this.PLAYER_2_COLOR);
    board.get(new Point(0, 1)).placeDisk(this.PLAYER_2_COLOR, this.PLAYER_1_COLOR);
    board.get(new Point(-1, 1)).placeDisk(this.PLAYER_1_COLOR, this.PLAYER_2_COLOR);
    board.get(new Point(-1, 0)).placeDisk(this.PLAYER_2_COLOR, this.PLAYER_1_COLOR);

    return board; // return the board
  }

  // returns the color of the player whose turn it currently is not
  Color otherPlayerColor() {
    // using referential equality because currentPlayer is simply a reference to either
    // the player 1 or player 2 field, not a whole new object
    if (this.currentPlayer == this.PLAYER_1_COLOR) { // if current player is player 1
      return this.PLAYER_2_COLOR; // return player 2 color
    }
    else { // if current player is player 2
      return this.PLAYER_1_COLOR; // return player 1 color
    }

    // this method can only return either player 1's or player 2's color,
    // which enforces the invariant
  }

  // returns the total number of tiles with the given disk color
  int tilesWithColor(Color color) {
    Objects.requireNonNull(color);
    int total = 0; // initialize total number of tiles with the color
    for (PointyTopHexagon tile : this.tiles.values()) { // iterate over all tiles in the map
      if (!tile.hasDisk()) { // if the tile does not have a disk
        continue; // continue to the next tile
      }
      // if the top color matches add 1, if not add 0
      total += tile.getTopColor().equals(color) ? 1 : 0;
    }
    return total; // return the total
  }

  // begin at the given starting point and move in the direction specified
  // by the change in q and change in r for each step.
  // record and return all tiles with a disk color of the player that is not the current turn
  // stop once you've reached a tile that has the current player's disk, and return the list
  // (NOT including the tile with the current player's color)
  // if you reach the end of the board or an empty tile before finding a tile with the current
  // player's color, return an empty list.
  List<ReversiTile> tilesToFlip(Point start, int delQ, int delR) {
    Objects.requireNonNull(start);
    Point curPoint = new Point(start); // copy the starting point to avoid mutating the argument
    curPoint.translate(delQ, delR); // move the current point in the given direction

    List<ReversiTile> foundTiles = new ArrayList<>(); // initialize list to hold recorded tiles

    while (this.tiles.containsKey(curPoint)) { // while the current point is still on the board
      ReversiTile tileAtPoint = this.tiles.get(curPoint); // get the tile at the current point
      if (!tileAtPoint.hasDisk()) { // if the tile does not have a disk
        return new ArrayList<>(); // return an empty list
      }
      // if the top disk color at the current tile is equal to the other player's color
      else if (tileAtPoint.getTopColor().equals(this.otherPlayerColor())) {
        foundTiles.add(tileAtPoint); // record the tile at the current point
        curPoint.translate(delQ, delR); // and move the current point
        continue;
      }
      // if the current tile has a disk, but it isn't flipped to the other player's color,
      // then it must be the current player's color
      return foundTiles; // so return the tiles we've found

    }
    // if we left the while loop that means we reached a point that is off the board
    return new ArrayList<>(); // so return an empty list
  }

  // get a list representing all 6 directions that you can move in on the board
  // a direction is represented by a point holding the change in q and change in r
  // for a step in that direction
  List<Point> getDirections() {
    return new ArrayList<>(Arrays.asList(
            new Point(-1, 0), // left
            new Point(0, -1), // up and left
            new Point(1, -1), // up and right
            new Point(1, 0), // right
            new Point(0, 1), // down and right
            new Point(-1, 1) // down and left
    ));
  }

}
