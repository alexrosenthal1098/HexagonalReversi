package adapters;

import java.util.List;

import providers.controller.ModelListener;
import providers.model.board.ICell;
import providers.model.board.ReversiModel;
import util.HexReversiUtils;

/**
 * A class that adapts our model implementation to the providers model interface.
 */
public class ToProviderModel implements ReversiModel {
  private final model.ReversiModel ourModel; // the adaptee model
  private final int sideLength;

  /**
   * A constructor that takes in our model implementation to adapt to the provider interface.
   * @param ourModel An implementation of our ReversiModel interface.
   */
  public ToProviderModel(model.ReversiModel ourModel) {
    if (ourModel == null) {
      throw new IllegalArgumentException("Given model cannot be null.");
    }

    this.ourModel = ourModel;

    // get the side length of the board and save it in the field
    this.sideLength = HexReversiUtils.getBoardSideLength(this.ourModel.getTiles());
  }

  @Override
  public int getSize() {
    // return the side length of the board
    return this.sideLength;
  }

  @Override
  public List<List<ICell>> getBoard() {

    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean anyLegalMovesLeft() {
    return false;
  }

  @Override
  public boolean ifPlayerHasAnyLegalMoves(String playerColor) {
    return false;
  }

  @Override
  public boolean isValidMove(ICell cell, String playerColor) {
    return false;
  }

  @Override
  public List<ICell> getCellsSurrounding(ICell cell) {
    return null;
  }

  @Override
  public ICell getUpperLeftCell(ICell cell) {
    return null;
  }

  @Override
  public ICell getUpperRightCell(ICell cell) {
    return null;
  }

  @Override
  public ICell getRightCell(ICell cell) {
    return null;
  }

  @Override
  public ICell getLowerRightCell(ICell cell) {
    return null;
  }

  @Override
  public ICell getLowerLeftCell(ICell cell) {
    return null;
  }

  @Override
  public ICell getLeftCell(ICell cell) {
    return null;
  }

  @Override
  public int howManyCellsAreBeingChangedByMove(ICell cell, String playerColor) {
    return 0;
  }

  @Override
  public int getScore(List<List<ICell>> board, String playerColor) {
    return 0;
  }

  @Override
  public String oppositePlayerColor(String playerColor) {
    return null;
  }

  @Override
  public String getColor() {
    return null;
  }

  @Override
  public void makeBoard(int size) {

  }

  @Override
  public void startGame() {

  }

  @Override
  public void pass(String playerColor) {

  }

  @Override
  public void makeMove(ICell cell, String playerColor) {

  }

  @Override
  public void notifyListenerMove(ICell cell) {

  }

  @Override
  public void notifyListenerChangePlayer() {

  }

  @Override
  public void addListener(ModelListener vl) {

  }
}
