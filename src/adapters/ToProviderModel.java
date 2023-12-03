package adapters;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.HexagonalReversi;
import model.tile.ReversiTile;
import providers.controller.ModelListener;
import providers.model.board.Fill;
import providers.model.board.ICell;
import providers.model.board.ReversiModel;
import util.HexReversiUtils;

/**
 * A class that adapts our model implementation to the providers model interface.
 */
public class ToProviderModel extends HexagonalReversi implements BothModels  {
  private final int sideLength; // the side length, in tiles, of the board

  /**
   * A constructor that specifies the board's side length in number of tiles
   * @param sideLength Side length in number of tiles.
   */
  public ToProviderModel(int sideLength) {
    super(sideLength);
    this.sideLength = sideLength;
  }

  /**
   * A default constructor that uses a board side length of 6.
   */
  public ToProviderModel() {
    this(6);
  }

  @Override
  public int getSize() {
    // return the side length of the board
    return this.sideLength;
  }

  @Override
  public List<List<ICell>> getBoard() {
    List<List<ICell>> board = new ArrayList<>();

    for (int row = 0; row < this.sideLength * 2 - 1; row++) {
      board.add(new ArrayList<>());
      if (row < this.sideLength) {
        for (int col = 0; col < this.sideLength + row; col++) {
          board.get(row).add(null);
        }
      }
      else {
        for (int col = (this.sideLength * 2) - 2; col > row - this.sideLength; col--) {
          board.get(row).add(null);
        }
      }
    }

    Map<Point, ReversiTile> tiles = super.getTiles();
    for (Point tilePoint : tiles.keySet()) {
      Point theirCoords = this.toTheirCoordinates(tilePoint);
      board.get(theirCoords.y).set(theirCoords.x, new ToProviderCell(tiles.get(tilePoint)));
    }

    return board;
  }

  @Override
  public boolean isGameOver() {
    return super.isGameOver();
  }

  @Override
  public boolean anyLegalMovesLeft() {
    try {
      super.anyMoves();
    }
    catch (Exception e) {
      // if an exception was caught then the model hasn't started yet so throw an exceptoin
      throw new IllegalStateException("Game has not started yet.");
    }
    return super.isGameOver();
  }

  @Override
  public boolean ifPlayerHasAnyLegalMoves(String playerColor) {
    return true;
  }

  @Override
  public boolean isValidMove(ICell cell, String playerColor) {
    if (cell == null || playerColor == null) {
      throw new IllegalArgumentException("Cannot give null arguments.");
    }
    Point ourCoords = this.toOurCoordinates(cell);

    if (!this.stringToColor(playerColor).equals(super.currentPlayerColor())) {
      super.passTurn();
      boolean validMove = super.isMovePossible(ourCoords.x, ourCoords.y);
      super.passTurn();
      return validMove;
    }

    return super.isMovePossible(ourCoords.x, ourCoords.y);
  }

  @Override
  public List<ICell> getCellsSurrounding(ICell cell) {
    List<ICell> finalList = new ArrayList<>();
    this.addIfNotNull(finalList, this.getUpperLeftCell(cell));
    this.addIfNotNull(finalList, this.getUpperRightCell(cell));
    this.addIfNotNull(finalList, this.getRightCell(cell));
    this.addIfNotNull(finalList, this.getLowerRightCell(cell));
    this.addIfNotNull(finalList, this.getLowerLeftCell(cell));
    this.addIfNotNull(finalList, this.getLeftCell(cell));
    return finalList;
  }

  @Override
  public ICell getUpperLeftCell(ICell cell) {
    Point ourCoords = this.toOurCoordinates(cell);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x, ourCoords.y - 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getUpperRightCell(ICell cell) {
    Point ourCoords = this.toOurCoordinates(cell);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x + 1, ourCoords.y - 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getRightCell(ICell cell) {
    Point ourCoords = this.toOurCoordinates(cell);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x + 1, ourCoords.y));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getLowerRightCell(ICell cell) {
    Point ourCoords = this.toOurCoordinates(cell);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x, ourCoords.y + 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getLowerLeftCell(ICell cell) {
    Point ourCoords = this.toOurCoordinates(cell);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x - 1, ourCoords.y + 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getLeftCell(ICell cell) {
    Point ourCoords = this.toOurCoordinates(cell);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x - 1, ourCoords.y));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public int howManyCellsAreBeingChangedByMove(ICell cell, String playerColor) {
    return 0;
  }

  @Override
  public int getScore(List<List<ICell>> board, String playerColor) {
    try {
      super.getCurrentPlayerScore();
    }
    catch (IllegalStateException e) {
      // if an error was caught then the model is not started yet so throw error
      throw new IllegalStateException("Game has not started yet.");
    }

    int score = 0;
    for (List<ICell> iCells : board) {
      for (ICell iCell : iCells) {
        if (iCell.getFill().equals(Fill.FillColor.EMPTY)) {
          score++;
        }
      }
    }
    return score;
  }

  @Override
  public String oppositePlayerColor(String playerColor) {
    if (playerColor.equalsIgnoreCase("BLACK")) {
      return "WHITE";
    }
    else if (playerColor.equalsIgnoreCase("WHITE")) {
      return "BLACK";
    }
    else {
      throw new IllegalArgumentException("Neither player has the given color");
    }
  }

  @Override
  public String getColor() {
    Color currentColor = super.currentPlayerColor();
    if (currentColor.equals(Color.BLACK)) {
      return "BLACK";
    }
    else {
      return "WHITE";
    }
  }

  @Override
  public void makeBoard(int size) {
    throw new UnsupportedOperationException("Board size should be " +
            "specified using the adaptee model");
  }

  @Override
  public void startGame() {
    super.startGame();
  }

  @Override
  public void pass(String playerColor) {
    if (!this.stringToColor(playerColor).equals(super.currentPlayerColor())) {
      throw new IllegalArgumentException("Given player color is not the current player.");
    }
    super.passTurn();
  }

  @Override
  public void makeMove(ICell cell, String playerColor) {
    if (!this.stringToColor(playerColor).equals(super.currentPlayerColor())) {
      throw new IllegalArgumentException("Given player color is not the current player.");
    }
    Point ourCoords = this.toOurCoordinates(cell);
    super.moveAt(ourCoords.x, ourCoords.y);
  }

  @Override
  public void notifyListenerMove(ICell cell) {
    throw new UnsupportedOperationException("Listeners will automatically be called");
  }

  @Override
  public void notifyListenerChangePlayer() {
    throw new UnsupportedOperationException("Listeners will automatically be called");
  }

  @Override
  public void addListener(ModelListener vl) {
    throw new UnsupportedOperationException("Only listeners using our controller are supported");
  }

  // returns the color associated with the given string, either black or white
  // throws an error if the string is not black or white
  private Color stringToColor(String playerColor) {
    if (playerColor.equalsIgnoreCase("BLACK")) {
      return Color.BLACK;
    }
    else if (playerColor.equalsIgnoreCase("WHITE")) {
      return Color.WHITE;
    }
    else {
      throw new IllegalArgumentException("Given player color is not valid.");
    }
  }

  // converts the given cell's coordinates to the ones used for our model and
  // returns it as a point
  private Point toOurCoordinates(ICell cell) {
    int ourX;
    if (cell.getY() < this.sideLength) {
      ourX = cell.getX() - cell.getY();
    }
    else {
      ourX = cell.getX() - this.sideLength + 1;

    }
    int ourY = cell.getY() - this.sideLength + 1;
    return new Point(ourX, ourY);
  }

  // converts the given point's coordinates to the ones used for their model and
  // returns it as a point
  private Point toTheirCoordinates(Point tilePoint) {
    int theirY = tilePoint.y + this.sideLength - 1;
    int theirX;
    if (tilePoint.y > 0) {
      theirX = tilePoint.x + this.sideLength - 1;
    }
    else {
      theirX = tilePoint.x + theirY;
    }

    return new Point(theirX, theirY);
  }

  // adds the given cell to the list if it is not null
  private void addIfNotNull(List<ICell> list, ICell cell) {
    if (cell == null) {
      return;
    }
    list.add(cell);
  }
}
