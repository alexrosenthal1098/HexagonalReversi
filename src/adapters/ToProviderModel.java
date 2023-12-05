package adapters;

import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.HexagonalReversi;
import model.ReversiModel;
import model.tile.ReversiTile;
import providers.controller.ModelListener;
import providers.model.board.Fill;
import providers.model.board.ICell;

/**
 * A class that adapts our model implementation to the providers model interface.
 */
public class ToProviderModel extends HexagonalReversi implements BothModels  {
  private final int sideLength; // the side length, in tiles, of the board
  private final List<List<ICell>> board; // a 2d list of cells on the board
  // this field is needed so that only one new cell is created for each position and the references
  // to the cells are passed around, not copies

  /**
   * A constructor that specifies the board's side length in number of tiles.
   * @param sideLength Side length in number of tiles.
   */
  public ToProviderModel(int sideLength) {
    super(sideLength);
    this.sideLength = sideLength;
    this.board = this.initBoard();
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
    return this.board;
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
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    ReversiModel modelCopy = super.copyModel();
    modelCopy.startGame();
    if (!this.stringToColor(playerColor).equals(modelCopy.currentPlayerColor())) {
      modelCopy.passTurn();
      return modelCopy.isMovePossible(ourCoords.x, ourCoords.y);
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
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);
    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x, ourCoords.y - 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getUpperRightCell(ICell cell) {
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x + 1, ourCoords.y - 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getRightCell(ICell cell) {
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x + 1, ourCoords.y));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getLowerRightCell(ICell cell) {
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x, ourCoords.y + 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getLowerLeftCell(ICell cell) {
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x - 1, ourCoords.y + 1));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public ICell getLeftCell(ICell cell) {
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    try {
      return new ToProviderCell(super.getTileAt(ourCoords.x - 1, ourCoords.y));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public int howManyCellsAreBeingChangedByMove(ICell cell, String playerColor) {
    model.ReversiModel modelCopy = super.copyModel(); // copy the model
    modelCopy.startGame(); // start the game for the copy

    // if the playerColor string doesn't correspond to the current player of the copy, then
    // pass the turn to the right player
    if (!modelCopy.currentPlayerColor().equals(this.stringToColor(playerColor))) {
      modelCopy.passTurn();
    }

    // convert their coordinates to ours
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);

    // if the move is not possible return 0
    if (!modelCopy.isMovePossible(ourCoords.x, ourCoords.y)) {
      return 0;
    }

    // get the score before and after a move at the given cell is made then return the difference
    int scoreBefore = modelCopy.getCurrentPlayerScore();
    modelCopy.moveAt(ourCoords.x, ourCoords.y);
    int scoreAfter = modelCopy.getOtherPlayerScore();
    return scoreAfter - scoreBefore;
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
        if (iCell.getFill().equals(this.stringToFill(playerColor))) {
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
  public void moveAt(int x, int y) {
    super.moveAt(x, y);
    this.updateCells();
  }

  @Override
  public void makeMove(ICell cell, String playerColor) {
    if (!this.stringToColor(playerColor).equals(super.currentPlayerColor())) {
      throw new IllegalArgumentException("Given player color is not the current player.");
    }
    Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);
    super.moveAt(ourCoords.x, ourCoords.y);
  }

  @Override
  public void notifyListenerMove(ICell cell) {
    throw new UnsupportedOperationException("Listeners will automatically be notified");
  }

  @Override
  public void notifyListenerChangePlayer() {
    throw new UnsupportedOperationException("Listeners will automatically be notified");
  }

  @Override
  public void addListener(ModelListener vl) {
    throw new UnsupportedOperationException("Only listeners using the original controller" +
            "are supported");
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

  // returns the string associated with the given color
  private String colorToString(Color color) {
    if (color.equals(Color.BLACK)) {
      return "BLACK";
    }
    else if (color.equals(Color.WHITE)) {
      return "WHITE";
    }
    else {
      throw new IllegalArgumentException("Color must be black or white.");
    }
  }

  private Fill.FillColor stringToFill(String color) {
    if (color.equalsIgnoreCase("BLACK")) {
      return Fill.FillColor.BLACK;
    }
    else if (color.equalsIgnoreCase("WHITE")) {
      return Fill.FillColor.WHITE;
    }
    else {
      throw new IllegalArgumentException("Color must be black or white");
    }
  }

  // adds the given cell to the list if it is not null
  private void addIfNotNull(List<ICell> list, ICell cell) {
    if (cell == null) {
      return;
    }
    list.add(cell);
  }

  // initializes the cells of the board
  private List<List<ICell>> initBoard() {
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
      Point theirCoords = AdapterUtils.toTheirCoordinates(tilePoint, this.sideLength);
      ICell cell = new ToProviderCell(tiles.get(tilePoint));
      cell.changeX(theirCoords.x);
      cell.changeY(theirCoords.y);
      board.get(theirCoords.y).set(theirCoords.x, cell);
    }

    return board;
  }

  // checks the tiles of our reversi model and ensures that the cells held in this.board are
  // updated
  private void updateCells() {
    for (Point tilePoint : super.getTiles().keySet()) {
      Point theirCoords = AdapterUtils.toTheirCoordinates(tilePoint, this.sideLength);
      ReversiTile tile = super.getTileAt(tilePoint.x, tilePoint.y);
      if (!tile.hasDisk()) {
        this.board.get(theirCoords.y).get(theirCoords.x).changeFill(Fill.FillColor.EMPTY);
        continue;
      }
      Color newColor = tile.getTopColor();
      this.board.get(theirCoords.y).get(theirCoords.x).setColor(this.colorToString(newColor));
    }
  }
}
