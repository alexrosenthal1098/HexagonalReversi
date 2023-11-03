package model;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.tile.PointyTopHexagon;
import model.tile.ReversiTile;

/**
 * A version of the game Reversi that is played on hexagonal tiles using black and white disks.
 * The tiles are arranged in a grid-like pattern that creates the shape of a larger hexagon.
 * The game has two players. Player one moves first and uses the black side of the disks while
 * player two, who moves second, uses the white side. The location of each tile is described
 * using axial coordinates (q and r in place of x and y respectively) where the center of the
 * HexagonalBoard is at the point (0, 0).
 */
public class HexagonalReversi implements ReversiModel {
  //          FIELDS
  //////////////////////////////////////////
  protected final Color PLAYER_1_COLOR = Color.BLACK; // The disk color of player one
  protected final Color PLAYER_2_COLOR = Color.WHITE; // The disk color of player two

  // The HexagonalBoard uses axial coordinates as described on the "Coordinate Systems" section of the
  // website linked in the README.
  // The x value of a point is the q value of the tile, which is like a diagonal column
  // The y value of a point is the r value of the tile, which is a horizontal row

  // A map that represents the HexagonalBoard using each hexagon's axial coordinates
  private final Map<Point, ReversiTile> tiles;
  protected Color currentPlayer; // The disk color of the current player.
  protected final int sideLength;

  // INVARIANT: currentPlayer equals PLAYER_1_COLOR or PLAYER_2_COLOR

  // All fields EXCEPT for tiles are declared as protected so that subclasses have the
  // opportunity to view the other fields
  // The tiles field is kept private because we want to ensure that the shape of the HexagonalBoard is not
  // changed after the model is instantiated. Subclasses can, however, override the makeBoard
  // helper if they want to change the HexagonalBoard shape.



  //          CONSTRUCTORS
  //////////////////////////////////////////
  /**
   * A constructor that takes in no arguments and initializes the HexagonalBoard with a default
   * side length of 6.
   */
  public HexagonalReversi() {
    this(6);
  }

  /**
   * A constructor that specifies the side length of the HexagonalBoard, in tiles.
   * The side length must be at least 3.
   * @param sideLength The side length, in hexagons, of each edge of the HexagonalBoard.
   */
  public HexagonalReversi(int sideLength) {
    if (sideLength < 3) { // check if the side length is at least three, guaranteeing the invariant
      throw new IllegalArgumentException("The HexagonalBoard side length must be at least 3.");
    }
    this.sideLength = sideLength;
    // the currentPlayer invariant is guaranteed by the constructor because it is
    // initialized as player 1 color.
    this.currentPlayer = this.PLAYER_1_COLOR; // set the current player to player 1 (they go first)
    this.tiles = this.makeBoard(sideLength); // initialize the state of the HexagonalBoard
  }

  /**
   * A copy constructor that takes in another HexagonalReversi model and initializes this
   * model to be a copy of it.
   * @param modelToCopy The model that is used to make a copy of.
   */
  public HexagonalReversi(HexagonalReversi modelToCopy) {
    if (modelToCopy == null) { // check if the given model is null
      throw new IllegalArgumentException("Given model cannot be null"); // if it is, throw exception
    }
    this.sideLength = modelToCopy.getBoardSideLength(); // copy the side length
    Color modelCopyPlayer = modelToCopy.getCurrentPlayer(); // get the current player from copy
    if (modelCopyPlayer.equals(this.PLAYER_1_COLOR)) { // if the color is equal to our player 1
      this.currentPlayer = this.PLAYER_1_COLOR; // set our current player to player 1
    }
    else if (modelCopyPlayer.equals(this.PLAYER_2_COLOR)) { // if the color is equal to our player 2
      this.currentPlayer = this.PLAYER_2_COLOR; // set our current player to player 2
    }
    else { // if it is not equal to either, throw an exception
      throw new IllegalStateException("Given model has unrecognized player color.");
    }

    this.tiles = this.copyBoard(modelToCopy);
  }



  //          INTERFACE METHODS
  ///////////////////////////////////////////////
  @Override
  public void moveAt(int q, int r) throws IllegalArgumentException, IllegalStateException {
    // if the HexagonalBoard does not contain the given coordinate
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
    // if the HexagonalBoard does not contain the given coordinate
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
    // if the HexagonalBoard does not contain the given coordinate
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

  @Override
  public ReversiTile getTileAt(int q, int r) throws IllegalArgumentException {
    // if the HexagonalBoard does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(q, r))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    ReversiTile tileToCopy = this.tiles.get(new Point(q, r)); // get the tile to copy
    return new PointyTopHexagon(tileToCopy); // return a copy of the tile
  }

  @Override
  public int getBoardSideLength() {
    return this.sideLength; // return the side length
  }



  //          HELPER METHODS
  ////////////////////////////////////////////
  // initializes the state of the HexagonalBoard using the given side length of the hexagon
  // this method has the protected modifier in case a subclass wants to use a different HexagonalBoard shape
  protected Map<Point, ReversiTile> makeBoard(int side) {
    Map<Point, ReversiTile> board = new HashMap<>(); // create the HexagonalBoard

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

    return board; // return the HexagonalBoard
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
    for (ReversiTile tile : this.tiles.values()) { // iterate over all tiles in the map
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
  // if you reach the end of the HexagonalBoard or an empty tile before finding a tile with the current
  // player's color, return an empty list.
  List<ReversiTile> tilesToFlip(Point start, int delQ, int delR) {
    Objects.requireNonNull(start);
    Point curPoint = new Point(start); // copy the starting point to avoid mutating the argument
    curPoint.translate(delQ, delR); // move the current point in the given direction

    List<ReversiTile> foundTiles = new ArrayList<>(); // initialize list to hold recorded tiles

    while (this.tiles.containsKey(curPoint)) { // while the current point is still on the HexagonalBoard
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
    // if we left the while loop that means we reached a point that is off the HexagonalBoard
    return new ArrayList<>(); // so return an empty list
  }

  // get a list representing all 6 directions that you can move in on the HexagonalBoard
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

  // creates and returns a copy of the HexagonalBoard from the given model
  Map<Point, ReversiTile> copyBoard(HexagonalReversi model) {
    Objects.requireNonNull(model);
    Map<Point, ReversiTile> newBoard = new HashMap<>(); // initialize HexagonalBoard to hold the copy

    for (Point point : model.tiles.keySet()) { // iterate over the point in the given model's tile
      // at each point, put a copy of the tile into our copied HexagonalBoard
      newBoard.put(point, new PointyTopHexagon(model.tiles.get(point)));
    }

    return newBoard; // return the copied HexagonalBoard
  }
}