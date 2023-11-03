    OVERVIEW
Welcome to HexagonalReversi! This codebase allows you to play Reversi, also known as Othello.
Reversi is typically played on a square board composed of square tiles, but the version of the
game implemented in this project uses hexagonal tiles arranged on a hexagonal board. It is assumed
that the user of this project is familiar with the rules of the game. It is also important to be
familiar with the axial coordinate system for hexagons which can be found in the "Coordinate System"
section of this website: https://www.redblobgames.com/grids/hexagons/
The model is sufficiently generalized to allow for a variety of different implementations of the
game, not just the hexagonal version. The tiles can be any regular polygon, and the board can take
on any shape that can be described using a two-dimensional coordinate system. This includes boards
that are asymmetrical, have holes, or even separate sections.


    CODE EXAMPLE
Here is an example of instantiating a model, viewing it, and playing a few moves:

ReversiModel hexReversi = new HexagonalReversi(5);
TextView reversiView = new HexReversiTextView(hexReversi);

hexReversi.playMove(1, 1);
hexReversi.playMove(1, 2);
hexReversi.playMove(2, -1);
hexReversi.playMove(1, -2);

The board looks like this to start:
    _ _ _ _ _
   _ _ _ _ _ _
  _ _ _ _ _ _ _
 _ _ _ X O _ _ _
_ _ _ O _ X _ _ _
 _ _ _ X O _ _ _
  _ _ _ _ _ _ _
   _ _ _ _ _ _
    _ _ _ _ _

And after the moves are played, it looks like this:
    _ _ _ _ _
   _ _ _ _ _ _
  _ _ _ O _ _ _
 _ _ _ O O X _ _
_ _ _ O _ O _ _ _
 _ _ _ X X O _ _
  _ _ _ _ _ O _
   _ _ _ _ _ _
    _ _ _ _ _


    KEY COMPONENTS
model - Represents the state of the game by providing the state of the board, current player,
        scores of players, and if the game is over. Also enforces the rules of the game and
        performs player actions on the board. The model is the heart of the program and is what
        performs the logic involved in the game itself.
view - A representation of the board that allows us to see the state of the model visually. The
       view does not further the playing of the game, it simply observes the model and displays
       its current state.
Players - Without players, there would be no way to play the game other than hard coding moves,
          and that's no fun! Players allow moves to be selected according to the current state of
          the model.


    KEY SUBCOMPONENTS
Tiles - The model is represented by an arrangement of ReversiTiles. Each tile can have a disk
        placed on it according to moves performed on the model. Each disk has one color on each
        side, so the disks can be flipped to reveal a different color after each move.


    PROJECT ORGANIZATION
The project is composed of 4 packages:
The model package holds the model interfaces and the classes that implement them.
The player package holds the interface for players along with the player classes.
The tile package holds the tile interface and implementing classes.
The view package holds the interfaces for viewing the model and the classes that implement them.




    CHANGES FOR PART 2
The model from part 1 lacked a default and copy constructor. To add this, we created a constructor
that took no arguments that initialized the board with a side length of 6 tiles. We also added a
constructor that takes in another HexagonalReversi model and copies the information we needed to
instantiate the new model.

The model lacked an observation method that returned the size of the board, so we chose to store
that value and return the side length, which is measure in tiles, of the board using a method
called getBoardSideLength.

We also replaced the getTiles() method, which returned a map of points to tiles, with a method
called getTileAt in order to prevent leaking the implementation details of the model.

Similarly, we removed the buildTile method from the ReversiTile interface and instead implemented
that functionality within the view itself (using a Path2D.Double instead of a Polygon). This
helps to further decouple the tiles from the view, and allows views to represent tiles using
whatever data types they want.