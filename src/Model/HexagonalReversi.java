package Model;

import java.awt.*;
import java.util.Arrays;

import Player.PlayerCreator;
import Player.PlayerType;
import Player.ReversiPlayer;
import Tile.PointyTopHexagon;
import Tile.ReversiTile;

public class HexagonalReversi implements ReversiModel {
  private final PointyTopHexagon[][] tiles;
  private final ReversiPlayer player1;
  private final ReversiPlayer player2;
  private ReversiPlayer currentPlayer;

  public HexagonalReversi(PlayerType player1, PlayerType player2, int sideLength) {
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException("Player types cannot be null.");
    }
    if (sideLength < 3) {
      throw new IllegalArgumentException("The board side length must be at least 3.");
    }
    this.player1 = PlayerCreator.create(player1, Color.BLACK);
    this.player2 = PlayerCreator.create(player2, Color.WHITE);
    this.currentPlayer = this.player1;
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
    return this.currentPlayer;
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
    return tilesCopy;
  }
}
