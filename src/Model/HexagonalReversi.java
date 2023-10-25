package Model;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    this.initBoard(sideLength); // initialize the state of the board
  }

  @Override
  public void moveAt(int row, int col) throws IllegalArgumentException, IllegalStateException {
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(row, col))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    if (!this.isMovePossible(row, col)) { // if the move is not possible
      throw new IllegalStateException("This move is not possible.");
    }

    // get the current player's color
    Color curPlayerColor = this.currentPlayer == this.player1 ? Color.BLACK : Color.WHITE;

    // change the color of the tile that was moved at
    this.tiles.get(new Point(row, col)).changeDiscColor(curPlayerColor);

    for (Point direction : this.getDirections()) {
      List<ReversiTile> tilesToChange = this.moveHelper(new Point(row, col),
              direction.x, direction.y); // get the tiles that we need to change
      for (ReversiTile tile : tilesToChange) { // iterate over all tiles to change
        tile.changeDiscColor(curPlayerColor); // change the disc color to the current player's color
      }
    }
    this.passTurn(); // change the turn to the other player;
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
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(row, col))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    for (Point delPoint : this.getDirections()) { // iterate over all directions to move in
      // use moveHelper in all directions starting at the given point
      List<ReversiTile> otherTiles = this.moveHelper(new Point(row, col), delPoint.x, delPoint.y);
      if (!otherTiles.isEmpty()) { // if the returned list from moveHelper IS NOT empty
        return true; // then the move is possible, so return true
      }
    }
    // if moveHelper was empty in all directions
    return false; // the move is not possible, return false
  }

  @Override
  public boolean anyMoves() {
    for (Point tilePoint : this.tiles.keySet()) { // iterate over all tile locations
      if (!this.tiles.get(tilePoint).isOccupied()) { // if the tile is empty/not occupied
        // check if a move is possible at that tile
        if (this.isMovePossible(tilePoint.x, tilePoint.y)) {
          return true; // the move is possible so return true
        }
        // if a move is not possible, continue on trying moves
      }
      // if the tile has a color, then it is occupied and you can't move there, so keep trying
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
  public Color getColorAt(int row, int col) throws IllegalArgumentException, IllegalStateException {
    // if the board does not contain the given coordinate
    if (!this.tiles.containsKey(new Point(row, col))) {
      throw new IllegalArgumentException("Invalid coordinates"); // throw an exception
    }

    ReversiTile tile = this.tiles.get(new Point(row, col)); // get the tile at that point
    if (!tile.isOccupied()) { // if the tile is not occupied
      // throw an exception
      throw new IllegalStateException("Cannot get a color from an unoccupied tile.");
    }

    // if the tile is occupied, return its disc color
    return tile.getDiscColor();
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
        this.tiles.put(new Point(q, r), new PointyTopHexagon());
      }
    }
    // at the points surrounding the center of the grid (which are hard coded)
    // change the disk color to white or black
    this.tiles.get(new Point(0, -1)).changeDiscColor(Color.BLACK);
    this.tiles.get(new Point(1, -1)).changeDiscColor(Color.WHITE);
    this.tiles.get(new Point(1, 0)).changeDiscColor(Color.BLACK);
    this.tiles.get(new Point(0, 1)).changeDiscColor(Color.WHITE);
    this.tiles.get(new Point(-1, 1)).changeDiscColor(Color.BLACK);
    this.tiles.get(new Point(-1, 0)).changeDiscColor(Color.WHITE);
  }

  // returns the total number of tiles with the given disc color
  int tilesWithColor(Color color) {
    int total = 0; // initialize total number of tiles with the color
    for (PointyTopHexagon tile : this.tiles.values()) { // iterate over all tiles in the map
      if (!tile.isOccupied()) { // if the tile is not occupied
        continue; // continue to the next tile
      }
      // if the color matches add 1, if not add 0
      total += tile.getDiscColor().equals(color) ? 1 : 0;
    }
    return total; // return the total
  }

  // begin at the given starting point and move in the direction specified
  // by the change in q and change in r for each step.
  // record and return all tiles with a disc of the player that is not the current turn
  // stop once you've reached a tile that has the current player's disc, but don't return it
  // if you reach the end of the board or an empty tile before finding a tile with the current
  // player's color, return an empty list.
  List<ReversiTile> moveHelper(Point start, int delQ, int delR) {
    Point curPoint = new Point(start); // copy the starting point to avoid mutating the argument
    curPoint.move(delQ, delR); // move the current point in the given direction
    // get the other player's color
    Color otherPlayerColor = this.currentPlayer == this.player1 ? Color.WHITE : Color.BLACK;
    List<ReversiTile> foundTiles = new ArrayList<>(); // initialize list to hold recorded tiles

    while (this.tiles.containsKey(curPoint)) { // while the current point is still on the board
      ReversiTile tileAtPoint = this.tiles.get(curPoint); // get the tile at the current point
      if (!tileAtPoint.isOccupied()) { // if the tile is not occupied
        return new ArrayList<>(); // return an empty list
      }
      // if the disc color at the current tile is equal to the other player's color
      else if (tileAtPoint.getDiscColor().equals(otherPlayerColor)) {
        foundTiles.add(tileAtPoint); // record the tile at the current point
        curPoint.move(delQ, delR); // and move the current point
      }
      // if the current tile is occupied but does not have the other player's color,
      // then it must have the current player's color
      return foundTiles; // so return the tiles we've found

    }
    // if we left the while loop that means we reached a point that is off the board
    return new ArrayList<>(); // so return an empty list
  }

  // get a list representing all 6 directions that you can move in on the board
  // a direction is represented by a point holding the change in q and change in r
  // for a step in that direction
  List<Point> getDirections() {
    return new ArrayList<Point>(Arrays.asList(
            new Point(-1, 0), // left
            new Point(0, -1), // up and left
            new Point(1, -1), // up and right
            new Point(1, 0), // right
            new Point(0, -1), // down and right
            new Point(-1, -1) // down and left
    ));
  }

}
