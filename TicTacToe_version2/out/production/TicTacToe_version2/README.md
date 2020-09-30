TIC TAC TOE VERSION 2 README

Name: Leo Chau
Email: lchau@bu.edu
BU ID: U48783299

Compilation Instructions:
Please place all of the files in the same directory and also include the config.properties file in the directory, as that is where the name of the implemented games, rules and all the default hard coded information about the game is stored. Without this, the program will detect that there are no games to be played. (I remembered that we are not supposed to submit files other than the .java files at the last minute and did not leave myself time to make it such that I can get rid of the use of config.properties file and thus I will be including this file with my submission.)

I personally used intellij when writing this program and thus if opened with an IDE like intellij, one can simply press the "run" button which will start the app. If one were to compile and run through the terminal, then you can use javac *.java in the terminal to compile all the java files (If your javac and java version are different, you may need to change the version of one of them or compile the java files such that they can run on the same version as your java using the following command: javac -source (java version) -target (java version) *.java).



Execution Instructions:
As noted previously, one may simply use an ide such as intellij to run the program.
However if following the compilation instructions through the terminal, please continue reading this section.
Once everything is compiled correctly. You may type in the command: "java App". As the main function for the program exists within the App.java file. Doing so will begin the program. Have fun!



Descriptions For Each Class (Alphabetical order):

App.java -
(App class)

The main driver class to be used to run the program.

Board.java - 
(Board class)

A class representing the board that players will be using to play the game. It stores the board as a 2D array of tiles and can display it to the user.

BoardGame.java -
(BoardGame class)

A class representing a board game. It is created in an abstract way such that one can extend it to any type of board game.

CaptureExit.java -

A class that utilizes a thread to listen for when the program exits. It will print out an ending dialogue.

CreateGame.java -
(CreateGame class)

A class to initialize and run a session of the program until the user wants to quit. Allows for functionality for Order and Chaos and Tic Tac Toe currently

OrderAndChaos.java -
(OrderAndChaos class)

A class representing the game order and chaos which makes use of the BoardGame abstract class. It is possible to play different variations of the game with differing board sizes (The win condition for order will always be a 5 in a row).

Piece.java -
(Piece class)

A class representing a piece in a board game which a user can place down.

Player.java -
(Player class)

A class representing a player participating in a board game with a name to identify them. Duplicates of the same name are allowed.

ScoreBoard.java -
(ScoreBoard class)

A class representing the scoreboard of a board game. The score board will be updated and displayed at the end of each game.

Team.java -
(Team class)

A class representing a team in a board game. One can think of 2 player game as two teams as one and was implemented with that in mind.

TeamManager.java -
(TeamManager class)

A class to keep track and manage the teams. This is used to keep track of whose turn it is and is in charge of changing that between turns.

TicTacToe.java - 
(TicTacToe class)

A class representing the game tic tac toe which makes use of the BoardGame abstract class. It is possible to play different variations of the game with differing board size (the win condition will scale with the board size ex. 5x5 board will need a 5 in a row).

Tile.java -
(Tile class)

A class representing a tile in a board of any board game. 


Potential Bonus:

I am not sure exactly what potential bonus points there are however, I will list what I think is bonus.

- Teams (However for the purpose of Chaos and Order and Tic Tac Toe I limited it to two teams as it would seem weird to have more than or less than 2 and it would be easily extendable to more than 2 players if needed)



Other Notes:

As mentioned in the compilation instructions, when implementing this program I decided that it would probably be a better idea to have the hardcoded information about the games and rules of each board game stored in an external file and have the program read that file instead of having a string or some data structure within the program to store it to keep that information separate from the program. Which is why I have included a config.properties file which stores the information for a game. To extend the program to other games, one would need to add the game and its ruleset to config.properties, implement a class for the game which will be a subclass of BoardGame and also edit the gameToPlay() method in createGame.java. (There is probably a way to get rid of the last step of changing gameToPlay but that will be a future feature). It is also worth noting that while playing the game as a user, it is annoying to remember the positions of on the board and it maybe be better to implement it such that user can ask for the board positions again so that they do not need to scroll to the beginning, count themselves or remember it.
