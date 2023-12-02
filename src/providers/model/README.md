### Overview:
The view is solving the issues of providing a visualization for the game reversi, in this case a hexagonal version
of the game reversi. This code is reliant on two different prerequisites, a model of the overall game that is being
displayed and a cell class that has representations of different states that a cell can be in. In the case of this
game the cells can be either occupied by the black player, occupied by the white player or empty. However, this code
is not reliant on the direct actions of the players themselves, meaning that you do not need to have a player class or
interface to have this code working.

### Quick Start:
Here is an example indicating how the end result o this code would be used:
public void testCreation() {<br>
ReversiModel model = new ABSTReversi();<br>
model.makeBoard(4);<br>
System.out.println();<br>
TextualView view = new TextualView(model);<br>
System.out.print(view.toString());<br>
}<br>
- as you can see here the model has to be instantiated first and then have the board be created before
  calling the render method from view that will then produce a string that shows a basic state of how the
  game board is being represented in that current state

### Key Components:
Player Interface- This is the interface that is used to describe what attributes Players in the game
can have. Whether it be a human player or an AI playing it describes what rules they have to follow
when playing the game<br>
Player Class- This is the class that implements the Player interface and is used to describe the attributes that a
player has. In this case it is used to describe the attributes that a human player or Computer based player has when
playing the game. This class drives the control flow of the model becasue it utalizes the model in whichever manner
it wants as long as the moves players make are valid <br>
ReversiModel Interface- This is the interface that is used to describe what rules the model of the game has to
follow when game functions are being called. For exmaple for the act of making a move the interface lays oit
wheat methods are necessary to make that happen in a valid way<br>
ABSTReversi Class- This class provides the basic rules and methods that every model for the game reversi in a
hexagonal manner has to follow. This class is driving the control flow of the model as it is controling the
subcompnanents of teh board(ie. Cells and there many attributes) and how they can be manipitlated and used
within certain paramaters<br>
Cell class- used to describe the sub compnaonent of the board themselves. The cell class describes how each
cell act as the subcomponents of the board and how they can be interacted with. This is being driven by th emodel
and players later on as this class represents the subcomponents of the board<br>
Fill class - this class is use dto descrive the different states that a cell can be in. In this case the
diffrent states can be either Black, which mean it is occupied by a chip from the black player, White, which
means it is occupied by a chip from the white player, or Empty, which means that the cell is not occupied by any chip
from either player. This class is being driven by the cell class as it is the class that is being used to describe the
different states that a cell can be in<br>
TextualView Class- This class is used to describe how the game board is being represented in a textual manner. This
class is driving the control flow as it is using the model to get the data that it needs to represent the game board
<br>
Class BasicReversi- This class is used to describe the basic rules of the game reversi. This class is driving the
control flow as it is using the model to get the data that it needs to represent the game board. However, is is also
being driven by ABSTReversi becasue Basic reversi extends ABSTReversi and does not add any more methods past that<br>




### Key Subcomponents:
#### ABSTReversi -
1. board - This is the data structure that is used to represent the game board. It is a 2D array of cells that
   represents the game board. This is being driven by the model as it is the data structure that is being used to
   represent the game board<br>
2. size - This is the variable that is used to represent the size of the game board.
3. cellsThatNeedToBeChanged - this is a Arraylist that is used to represent the cells that need to be changed
   evefytime th emove method is called and the conditions are valid
4. isStarted - This is the variable that is used to represent whether or not the game has started. This is being
   driven by the model as it is the variable that is being used to represent whether or not the game has started<br>
5. blackPlayedLast - This is the variable that is used to represent whether or not the black player played last.
   This is being driven by the model as it is the variable that is being used to represent whether or not the black
   player played last<br>
6. whitePlayedLast - This is the variable that is used to represent whether or not the white player played last.
   This is being driven by the model as it is the variable that is being used to represent whether or not the white
   player played last<br>
7. passes - This is the variable that is used to represent the number of passes that have occured in the game.
   This is being driven by the model as it is the variable that is being used to represent the number of passes that
   have occured in the game<br>




#### Cell -
1. fill - This is the variable that is used to represent the state that a cell can be in.
2. X - This is the variable that is used to represent the x coordinate of the cell on the game board.
3. Y - This is the variable that is used to represent the y coordinate of the cell on the game board.




#### Fill -
1. BLACK - This is the variable that is used to represent the state that a cell can be in when it is
   occupied by a chip from the Black Player
2. WHITE - This is the variable that is used to represent the state that a cell can be in when it is
   occupied by a chip from the White Player
3. EMPTY - This is the variable that is used to represent the state that a cell can be in when it is
   not occupied by a chip from either player




#### Player -
1. name - This is the variable that is used to represent the name of the player
2. color - This is the variable that is used to represent the color of the player
3. score - This is the variable that is used to represent the score of the player

- A player is a class that is used to represent the attributes that a player has. In this case it is used to
  describe the attributes that a human player or Computer based player has when playing the game. This class drives
  the control flow of the model becasue it utalizes the model in whichever manner it wants as long as the moves
  players make are valid. A player can be either a human player or a computer player. A human player is a player 
can make either a move or pass its turn. 




#### TextualView -
1. model - This is the variable that is used to represent the game board that is being represnted within textual view
2. a - the appendable stringbuiler that is being used to create a string based display of the gameboard




### Source Organization:
The sorce code is split up into two sections, the model and the view. The model is split up into two sections, the
board and the players. Thr board is made up of the Reversi Intrface, the Cell Class the BasicReversi class, the
ABSTreversi class and the fill class. The player section is made up of the player interface and the player class.
The other section, the view, is made up of the textualView class.


### Class Invariant:
#### The board:
In the assignment we have declared the board to be the primary class invariant. The reason behind this would be that
the board does not change in size or its contents. Throughout th entire program the board will always be the same
size, it does not change, and it will have the same number of cells in the same x y coordinate plane regardless of
what actions occur. The only thing that changes is the state of the cells themselves, but the cells themselves do
not change or the board itself does not change.

