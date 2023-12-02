package providers.players;

import providers.model.strategies.IStrategies;

/**
 * used to describe the actions that a player can do.
 */
public interface PlayerActions extends IPlayer {

  /**
   * make a move for the player.
   */
  void makeMove();


  /**
   * adds a strategy to the player.
   *
   * @param strategy the strategy to be added
   */
  void addStrategy(IStrategies strategy);

  /**
   * gets the strategy of the player.
   *
   * @return the strategy of the player
   */
  IStrategies getStrat();
}
