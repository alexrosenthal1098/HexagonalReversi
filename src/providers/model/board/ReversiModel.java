package providers.model.board;

import providers.controller.ModelListener;

/**
 * The interface for the model to represent a Reversi.
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Construts a board for a Reversi.
   *
   * @param size is the size of each of the sides of the board.
   */
  void makeBoard(int size);


  /**
   * Starts the game.
   */
  void startGame();

  /**
   * passes a players turn to the other player.
   *
   * @param playerColor is the color of the player passing.
   */
  void pass(String playerColor);

  /**
   * Attempts to make a move into a given cell with the player color.
   *
   * @param cell        is the cell trying to be moved to
   * @param playerColor is the color of the player trying to move
   */
  void makeMove(ICell cell, String playerColor);


  void notifyListenerMove(ICell cell);

  /**
   * notofies all listeners of the model that the player color has been changed.
   */
  void notifyListenerChangePlayer();

  /**
   * Adds a listener to the model.
   *
   * @param vl is the listener being added.
   */
  void addListener(ModelListener vl);



}
