package providers.players;

import providers.model.board.ReversiModel;

/**
 * Represents a player in a game of Reversi.
 */
public interface IPlayer {

  /**
   * gets the score of the player.
   */
  int getPlayerScore(ReversiModel model);

  /**
   * Gets the name of the player.
   *
   * @return the name of the player
   */
  String getName();


  /**
   * Gets the color of the player.
   *
   * @return the color of the player
   */
  String getColor();
}
