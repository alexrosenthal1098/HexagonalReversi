package Model;

import java.awt.*;
import java.util.Arrays;

import Player.PlayerCreator;
import Player.PlayerType;
import Player.ReversiPlayer;
import Tile.PointyTopHexagon;
import Tile.ReversiTile;

public class HexagonalReversi implements ReversiModel {
  // A 2D array representation of a hexagonal grid map
  // Moving across a row behaves normally and moves left to right visually,
  // but moving down a column in the array moves diagonally, down to the right, visually
  private final PointyTopHexagon[][] tiles;
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
    this.tiles = new PointyTopHexagon[sideLength * 2 - 1][sideLength * 2 - 1];
    // TODO: use private method like initTiles() for this ^^^
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
  public ReversiTile[][] getTiles() {
    // create deep copy of the tiles
    ReversiTile[][] tilesCopy = new ReversiTile[this.tiles.length][]; // create new outer array
    for (int i = 0; i < tilesCopy.length; i++) { // iterate over the entries in the outer array
      // copy the inner array at each index
      tilesCopy[i] = Arrays.copyOf(this.tiles[i], this.tiles[i].length);
    }
    return tilesCopy; // return the copied version
  }
}
