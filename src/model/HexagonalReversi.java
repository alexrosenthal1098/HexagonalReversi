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
 * board is at the point (0, 0).
 */
public class HexagonalReversi implements ReversiModel {
  //          FIELDS
  //////////////////////////////////////////
  // a map of a player listener and a boolean representing if they listen to player one or two
  // (true for player one, false for player two)
  private Map<ModelListener, Boolean> playerListeners;
  private List<ModelListener> readOnlyListeners; // a list of read-only model listeners
  private boolean gameStarted; // true if the game has started, false if it has not
  protected final Color PLAYER_1_COLOR; // The disk color of player one
  protected final Color PLAYER_2_COLOR; // The disk color of player two

  // The board uses axial coordinates as described on the "Coordinate Systems" section of the
  // website linked in the README.
  // The x value of a point is the q value of the tile, which is like a diagonal column
  // The y value of a point is the r value of the tile, which is a horizontal row

  // A map that represents the board using each hexagon's axial coordinates
  private final Map<Point, ReversiTile> tiles;
  protected Color currentPlayer; // The disk color of the current player

  // INVARIANT: currentPlayer equals PLAYER_1_COLOR or PLAYER_2_COLOR

  // The color and currentPlayer fields are declared as protected so that subclasses can change
  // each player's color along with how the turn is decided (maybe each player goes twice in a row)

  // The tiles and sideLength fields are kept private because we want to ensure that the shape of
  // the board is not changed after the model is instantiated. Subclasses can, however, override
  // the makeBoard helper if they want to change the board shape.



  //          CONSTRUCTORS
  //////////////////////////////////////////
  /**
   * A constructor that takes in no arguments and initializes the board with a default
   * side length of 6.
   */
  public HexagonalReversi() {
    this(6);
  }

  /**
   * A constructor that specifies the side length of the board, in tiles.
   * The side length must be at least 3.
   * @param sideLength The side length, in hexagons, of each edge of the board.
   */
  public HexagonalReversi(int sideLength) {
    if (sideLength < 3) { // check if the side length is at least three, guaranteeing the invariant
      throw new IllegalArgumentException("The board side length must be at least 3.");
    }
    this.playerListeners = new HashMap<>(); // initialize empty map of player listeners
    this.readOnlyListeners = new ArrayList<>(); // initialize empty list of read-only listeners
    this.gameStarted = false; // initialize game started to false

    // initialize the player colors as black and white
    this.PLAYER_1_COLOR = Color.BLACK;
    this.PLAYER_2_COLOR = Color.WHITE;

    // the currentPlayer invariant is guaranteed by the constructor because it is
    // initialized as player 1 color.
    this.currentPlayer = this.PLAYER_1_COLOR; // set the current player to player 1 (they go first)
    this.tiles = this.initBoard(sideLength); // initialize the state of the board
  }

  /**
   * A constructor that initializes this model to be a copy of the given model. The given model
   * does not have to be hexagonal because the constructor simply copies the shape of the board
   * and the disks on the tiles; it does not care about the shape of the individual tiles. This
   * constructor also ensures that this model starts on the same player that the given model is
   * currently on.
   * @param modelToCopy The model to create a copy of.
   */
  public HexagonalReversi(ReadOnlyReversiModel modelToCopy) {
    if (modelToCopy == null) { // check if the given model is null
      throw new IllegalArgumentException("Given model cannot be null"); // if it is, throw exception
    }

    // initialize listeners to be empty because we do not want listeners of the model to copy to
    // receives notifications for this new model
    this.playerListeners = new HashMap<>(); // initialize empty map of player listeners
    this.readOnlyListeners = new ArrayList<>(); // initialize empty list of read-only listeners

    this.gameStarted = false; // initialize gameStarted to false

    // set our player colors to the given model's colors, setting the current player as player 1
    this.PLAYER_1_COLOR = modelToCopy.currentPlayerColor();
    this.PLAYER_2_COLOR = modelToCopy.otherPlayerColor();

    this.currentPlayer = this.PLAYER_1_COLOR; // set current player to player 1

    this.tiles = modelToCopy.getTiles();
  }

  //          INTERFACE METHODS
  ///////////////////////////////////////////////
  @Override
  public void moveAt(int q, int r) throws IllegalArgumentException, IllegalStateException {
    this.checkGameStarted(); // check if the game has started

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
  public void passTurn() throws IllegalStateException {
    this.checkGameStarted(); // check if the game has started

    // the currentPlayer is only modified in the passTurn method
    // the invariant is enforced because the otherPlayerColor helper can only return
    // either player 1's color or player 2's color.
    this.currentPlayer = this.otherPlayerColor(); // set the current player to the other player

    this.notifyModelChanged(); // notify read-only listeners that the model changed
    this.notifyTurnStarted(); // notify player listeners that their turn started
  }

  @Override
  public boolean isMovePossible(int q, int r) throws IllegalArgumentException,
          IllegalStateException {
    this.checkGameStarted(); // check if the game has started

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
  public boolean anyMoves() throws IllegalStateException {
    this.checkGameStarted(); // check if the game has started

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
  public boolean isGameOver() throws IllegalStateException {
    this.checkGameStarted(); // check if the game has started

    boolean curPlayerHasMoves = this.anyMoves(); // check if the current player has any moves
    // manually change the turn to the next player to avoid sending model changed event
    this.currentPlayer = this.otherPlayerColor();

    boolean nextPlayerHasMoves = this.anyMoves(); // check if the other player has any moves
    // change the turn back to the first player to avoid messing up turns
    this.currentPlayer = this.otherPlayerColor();

    // if neither player has any moves, return true
    return !curPlayerHasMoves && !nextPlayerHasMoves;
  }

  @Override
  public int getCurrentPlayerScore() throws IllegalStateException {
    this.checkGameStarted(); // check if the game has started

    // use a helper to find tiles with player one's disk color
    return this.tilesWithColor(this.currentPlayer);
  }

  @Override
  public int getOtherPlayerScore() throws IllegalStateException {
    this.checkGameStarted(); // check if the game has started

    // use a helper to find tiles with player two's disk color
    return this.tilesWithColor(this.otherPlayerColor());
  }

  @Override
  public Color currentPlayerColor() {
    return this.currentPlayer; // return the current player's color
    // we don't need a copy because the color class is immutable
  }

  @Override
  public Color otherPlayerColor() {
    // using referential equality because currentPlayer is simply a reference to either
    // the player 1 or player 2 field, not a whole new object
    if (this.currentPlayer == this.PLAYER_1_COLOR) { // if current player is player 1
      return this.PLAYER_2_COLOR; // player 2 color
    }
    else { // if current player is player 2
      return this.PLAYER_1_COLOR; // player 1 color
    }

    // this method can only return either player 1's or player 2's color,
    // which enforces the invariant
  }

  @Override
  public ReversiTile getTileAt(int q, int r) throws IllegalArgumentException {
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(q, r))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    ReversiTile tileToCopy = this.tiles.get(new Point(q, r)); // get the tile from the board

    if (tileToCopy.hasDisk()) { // if the tile has a disk
      Color topColor = tileToCopy.getTopColor(); // get the top color of the tile
      tileToCopy.flipDisk(); // flip it so we see the bottom color
      Color bottomColor = tileToCopy.getTopColor(); // get the bottom color
      tileToCopy.flipDisk(); // flip the disk back over to its original state
      // return a new tile that has a copy of the disk
      return new PointyTopHexagon(topColor, bottomColor);
    }

    // if the tile does not have a disk
    return new PointyTopHexagon(); // return a new tile with no disk
  }

  /**
   * Returns a map that represents the coordinate location of each hexagonal tile
   * in the board. The location of the tiles are specified using axial coordinates, with
   * the center tile's coordinates being (0, 0).
   * @return A map of Point to ReversiTile.
   */
  @Override
  public Map<Point, ReversiTile> getTiles() {
    // create deep copy of the tiles map
    Map<Point, ReversiTile> clone = new HashMap<>(); // create a new hashmap
    for (Point point: this.tiles.keySet()) { // iterate over all the keys
      //use getTileAt to create a copy of the hexagon tile and put it with the corresponding point
      clone.put(new Point(point), this.getTileAt(point.x, point.y));
    }
    return clone; // return the copy of tiles
  }

  @Override
  public ReversiModel copyModel() {
    return new HexagonalReversi(this);
  }

  @Override
  public void addReadOnlyListener(ModelListener listener) throws IllegalArgumentException {
    if (listener == null) { // check if the listener is null and throw exception if it is
      throw new IllegalArgumentException("Cannot register a null listener.");
    }
    // if the list of listeners does not already contain this listener, add it to the list
    // this avoids notifying a listener more than one time for the same event
    if (!this.readOnlyListeners.contains(listener)) {
      this.readOnlyListeners.add(listener);
    }
  }

  @Override
  public void addListener(ModelListener listener, boolean firstPlayer)
          throws IllegalArgumentException, IllegalStateException {
    if (this.gameStarted) { // check if the game has already started and throw error
      throw new IllegalStateException("Cannot add a listener once the game has started.");
    }
    if (listener == null) { // check if the listener is null and throw exception if it is
      throw new IllegalArgumentException("Cannot register a null listener.");
    }

    // if the listener is not already in the map, put it and their firstPlayer boolean
    // in the listeners map
    if (!playerListeners.containsKey(listener)) {
      this.playerListeners.put(listener, firstPlayer);
    }
  }

  @Override
  public void startGame() throws IllegalStateException {
    if (this.gameStarted) { // check if the game has already started and throw error
      throw new IllegalStateException("The game has already started.");
    }

    this.gameStarted = true; // set gameStarted to true
    this.notifyTurnStarted(); // notify listeners that their turn started
  }

  //          HELPER METHODS
  ////////////////////////////////////////////

  // throws an exception if the game has not started
  final void checkGameStarted() {
    if (!this.gameStarted) {
      throw new IllegalStateException("Game has not started.");
    }
  }

  // notifies the listeners of the current player that their turn has started
  final void notifyTurnStarted() {
    // iterate through all player listeners
    for (ModelListener listener : this.playerListeners.keySet()) {
      boolean player1 = this.currentPlayer == this.PLAYER_1_COLOR; // is it player 1's turn

      // if the player they listen to is the current turn
      if (this.playerListeners.get(listener) == player1) {
        listener.yourTurn(); // notify them it's their turn.
      }
    }
  }

  final void notifyModelChanged() {
    // iterate through all readOnly listeners and notify them that the model has changed
    for (ModelListener listener : this.readOnlyListeners) {
      listener.modelChanged();
    }
  }


  // initializes the state of the board using the given side length of the hexagon
  // this method has the protected modifier in case a subclass wants to use a different
  // board shape
  protected Map<Point, ReversiTile> initBoard(int side) {
    Map<Point, ReversiTile> board = new HashMap<>(); // create the board

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
  // if you reach the end of the board or an empty tile before finding a tile with the
  // current  player's color, return an empty list.
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
