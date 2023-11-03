package view.gui;

/**
 * An interface that describes the features that a Reversi game view can provide.
 */
public interface ReversiFeatures extends BoardFeatures {
  // currently this interface only includes features of the board

  // if we want to have the features of ONLY the board, we use the BoardFeatures interface,
  // but if we want to display an entire game view (which can include extra information like
  // player score's, the move number, etc.) we use this interface

  // possible future methods: restartGame, setPlayerScore, setMoveNumber, etc.
}
