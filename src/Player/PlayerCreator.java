package Player;

import java.awt.*;

/**
 * Creates a Reversi player with an associated color.
 */
public final class PlayerCreator {

  /**
   * Creates a Reversi player with the given type and color.
   * @param playerType The type of player to create.
   * @param color The color of disks the player uses.
   * @return The ReversiPlayer object.
   * @throws IllegalArgumentException If any argument is null or the player type is unrecognized.
   */
  public static ReversiPlayer create(PlayerType playerType, Color color)
          throws IllegalArgumentException {
    if (playerType == null || color == null) { // check if either argument is null and throw error
      throw new IllegalArgumentException("Player type or color cannot be null.");
    }
    switch (playerType) { // switch over the player type
      case PLAYER_HUMAN: // if the player type is human
        return new HumanPlayer(color); // create a human player with the given color
      case PLAYER_AI: // TO BE IMPLEMENTED LATER
      default: // if the default case is reached, the player type provided is not supported
        throw new IllegalArgumentException("Player type is not supported.");
    }
  }
}
