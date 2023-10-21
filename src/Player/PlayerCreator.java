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
    if (playerType == null || color == null) {
      throw new IllegalArgumentException("Player type or color cannot be null.");
    }
    switch (playerType) {
      case PLAYER_HUMAN:
        return new HumanPlayer(color);
      case PLAYER_AI: // TO BE IMPLEMENTED LATER
      default:
        throw new IllegalArgumentException("Player type is unrecognized.");
    }
  }
}
