package Player;

import java.awt.*;
import java.util.Objects;

/**
 * A human player for the game Reversi.
 */
public class HumanPlayer implements ReversiPlayer{
  private final Color color; // the color of the disc this player uses

  /**
   * A constructor that accepts this human player's disk color.
   * @param color The color disc this player uses.
   */
  public HumanPlayer(Color color) {
    this.color = Objects.requireNonNull(color); // ensure the color is not null and set it
  }

  @Override
  public Color getColor() {
    // create a copy of the color using its int rgb value and the corresponding constructor
    // prevents mutation of this player's color
    return new Color(this.color.getRGB());
  }
}
