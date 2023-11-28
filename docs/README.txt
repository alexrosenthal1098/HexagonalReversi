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

While we already had methods to determine each player's score, the notion of having a Player 1
and Player 2 was confusing, especially since there was no way of knowing if the current player
is 1 or 2 unless it is the first move. To fix this issue, we replaced the getPlayer1Score and
getPlayer2Score methods with getCurrentPlayerScore and getOtherPlayerScore. This removes the
notion of a player 1 and 2 and makes it easier for the client to determine the color associated
with each score.

Similarly, we added a otherPlayerColor method so that at any given moment, a client knows the color
of both players of the game instead of only knowing the color of the player whose turn it currently
is.

We removed the buildTile method from the ReversiTile interface and instead implemented
that functionality within the view itself (using a Path2D.Double instead of a Polygon). This
helps to further decouple the tiles from the view, and allows views to represent tiles using
whatever data types they want.

    STRATEGIES
We added the ReversiStrategy interface which requires an implementing class to choose a move to
play on the given model. This will allow players to implement various strategies when choosing the
move. Not allowing the strategies to modify the model and instead making them return a move helps
to decouple the strategies/player's and the model.

    VIEW
The board supports selecting a tile by clicking on it and deselecting by clicking on the same tile
or by clicking off of the board. Only one tile can be selected at a time, and only tiles that do
not have a disk can be selected. The board also supports pressing the key 'm' to signify that the
player wants to make a move at the given tile or the key 'p' to signify they want to pass the turn.
These events do not directly mutate the model, rather they notify a listener of the board of the
event so that they can act accordingly. For example, if a player selects a tile that is not a valid
move but they press 'm' anyway, it is up to the controller to handle that error.



    CHANGES FOR PART 3
Added a startGame method to the model so that initial setup, like registering listeners, can occur
before the game starts and the model can be mutated.

Added a label to the ReversiFrame and changed the constructor to take in a title that is displayed
at the top of the frame via the label.

    MODEL LISTENERS
Added two ways for classes to register as a listener for the model. If the listener wants to be
notified when a specific player's turn has started, then they can use the addListener method along
with a boolean to represent whether they are listening to player 1 (true) or player 2 (false). All
player listeners must be registered before the game starts. If the listener wants to be notified
when the model changes, they can use the addReadOnlyListener method. These listeners can be added
at any point.

    PLAYER-ACTION LISTENERS
Updated the ReversiPlayer interface to allow for player action events and to ensure that the
players are interacting with the view in which they are registered to by the controller if they
need to. This allows the controller to handle both synchronous player moves, like for an AI player,
and asynchronous moves, like for a human player by listening for player action events.

    CONTROLLER
The controller works as a mediator between the model and the players plus their view's. It does this
by listening to events from the model, asking the player for a move when necessary, and then acting
on the model depending on the player's chosen move. It also is capable of handling exceptions
without letting the program crash by displaying error messages to the view and re-prompting player's
to make a move.

    MAIN METHOD
The main method takes in 2 string arguments from the command line that represent the strategy that
each of the two player's will use. Currently, the only supported players are "human", for a human
player that interacts with the view to move, and "capture-max", which is an AI player that uses
the strategy of capturing the most pieces every turn.