package strategy;

import java.awt.*;

import model.HexagonalReversi;
import model.ReadOnlyReversiModel;
import model.ReversiModel;

/**
 * A strategy for determining the next move to play in a game of HexagonalReversi.
 * Finds the move that would capture the most pieces for the current player, and breaks ties
 * by selecting the uppermost-leftmost tile.
 */
public class CaptureMaxPieces implements ReversiStrategy {
  @Override
  public Point choseMove(ReadOnlyReversiModel model) {
    if (model == null) { // check if the given model is null and throw exception if it is.
      throw new IllegalArgumentException("Model cannot be null.");
    }
    // todo
    throw null;
  }


  // return the number of pieces that would be captured if a move was made on the given model
  // at the given q and r position
  int numCapturedPieces(int tileQ, int tileR, HexagonalReversi model) {
    if (model == null) { // check if the model is null and throw an exception if it is
      throw new IllegalArgumentException("Model or tilePoint can't be null.");
    }
    if (!model.isMovePossible(tileQ, tileR)) {
      throw new IllegalStateException("A move is not possible at the point.");
    }
    ReversiModel modelCopy = new HexagonalReversi(model);

    return 0; // todo
  }

  Point upperLeftMostPoint(Point point1, Point point2) {

    return null; // todo
  }
}
