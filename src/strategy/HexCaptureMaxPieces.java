package strategy;

import java.awt.*;

import model.HexagonalReversi;
import model.ReadOnlyReversiModel;

/**
 * A strategy for determining the next move to play in a game of HexagonalReversi.
 * Finds the move that would capture the most pieces for the current player, and breaks ties
 * by selecting the uppermost-leftmost tile.
 */
public class HexCaptureMaxPieces implements ReversiStrategy {

  @Override
  public Point chooseMove(ReadOnlyReversiModel model) {
    if (model == null) { // check if the given model is null and throw exception if it is.
      throw new IllegalArgumentException("Model cannot be null.");
    }

    int maxCaptured = -1; // initialize a var to hold the maximum number of captured pieces
    Point bestMove = null; // initialize the best move

    for (Point point : model.getTiles().keySet()) { // iterate over all tile points on the board
      if (model.isMovePossible(point.x, point.y)) { // if this point is a valid tile to move at
        // get the number of pieces that would be captured for this move
        int capturedPieces = this.numCapturedPieces(point, model);
        if (capturedPieces > maxCaptured) { // if the captured pieces is greater than the max
          maxCaptured = capturedPieces; // update the maximum captured pieces
          bestMove = point; // set the bestMove to this point;
        }
        else if (capturedPieces == maxCaptured) { // if the captured pieces is equal to the max
          if (bestMove == null) { // if there is no best move currently
            bestMove = point; // set it to be the current point
          }
          // if there is a best move, we must break the tie by choosing the uppermost-leftmost tile
          bestMove = this.upperLeftMostPoint(bestMove, point);
        }
      }
    }

    if (bestMove == null) {
      throw new IllegalStateException("No moves left for the current player.");
    }

    return bestMove;
  }


  // return the number of pieces that would be captured if a move was made on the given model
  // at the given q and r position
  int numCapturedPieces(Point tilePoint, ReadOnlyReversiModel model) {
    // check if the model or tilePoint is null and throw an exception if it is
    if (model == null || tilePoint == null) {
      throw new IllegalArgumentException("Model or tilePoint can't be null.");
    }
    if (!model.isMovePossible(tilePoint.x, tilePoint.y)) {
      throw new IllegalStateException("A move is not possible at the point.");
    }

    // make a copy of the model so that we can make the move and let the model handle the logic
    // of how many pieces were captured
    HexagonalReversi modelCopy = new HexagonalReversi(model);

    int scoreBefore = modelCopy.getCurrentPlayerScore(); // get the current player's score before
    modelCopy.moveAt(tilePoint.x, tilePoint.y); // make the move
    int scoreAfter = modelCopy.getOtherPlayerScore(); // get the score after making the move

    // return the difference in score - 1, which is equal to the number of
    // pieces captured during that turn
    return scoreAfter - scoreBefore - 1;


  }

  // decide which of the two given points is more
  Point upperLeftMostPoint(Point point1, Point point2) {
    int delQ = point2.x - point1.x; // get difference in q value
    int delR  = point2.y - point1.y; // get difference in r value

    if (delR > 0) { // if delR r is positive, then point1 is more upper
      return point1; // so return point1
    }
    else if (delR < 0) { // if delR is negative, then point2 is more upper
      return point2; // so return point2
    }
    else { // if delR is 0, then both points are on the same row
      if (delQ > 0) { // if delQ is positive, then point1 is more left
        return point1; // so return point1
      }
      else { // if delQ is negative, then point2 is more left
        // or if delQ is 0, then they are the same point, so it doesn't matter which one is returned
        return point2; // so return point2
      }
    }
  }
}
