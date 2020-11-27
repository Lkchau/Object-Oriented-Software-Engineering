**BLACK JACK VERSION 1 README**

Name: Leo Chau

Email: lchau@bu.edu

BU ID: U48783299

**Compilation Instructions:**

Please place all the files in the same directory. I personally used intellij to create and run the program. If using such an ide you may simply run it through the ide.

If one were to compile and run through the terminal, then you can use **javac *.java** in the terminal to compile all the java files (If your javac and java version are different, you may need to change the version of one of them or compile the java files such that they can run on the same version as your java using the following command: **javac -source (java version) -target (java version) *.java**).



**Execution Instructions:**

If you are running through an ide, you may skip this section.

If following the compilation instructions through the terminal, please continue reading this section.
Once everything is compiled correctly. You may type in the command **java App** while in the same directory. As the main function for the program exists within the App.java file. Doing so will begin the program. Have fun!

**Descriptions For Each Class (Alphabetical order):**

App.java -
(App class)

The main driver class to be used to run the program.

BlackJack.java - 
(BlackJack class)

A class representing the blackjack card game. It implements the card game abstract class.
 
BlackJackPlayer.java -
(BlackJackPlayer class) 

A class representing a specific player for blackjack, it implements the abstract class player.

Card.java -
(Card class)

A class representing a card in a card game, it is made up of a suit and a rank.

CardGame.java -
(CardGame abstract class)

A class representing the abstract idea of a card game.

Dealer.java -
(Dealer class)

A class representing a dealer in a card game, it is another type of player and thus implements the abstract class player.

Deck.java - 
(Deck class)

A class representing a physical deck which is essentially a bunch of cards.

DeckManager.java -
(DeckManager class)

A class whose main function is to connect and execute functions between players, the game and the deck

GameManager.java -
(GameManager class)

A class whose main purpose is to provide functionality to set up games and then run the game.

Hand.java -
(Hand class)

A class representing a player's hand, which is a list of cards that a player has in a game.

Money.java -
(Money class)

A class representing physical money, with a value and currency. In the future I could add a currency class and functionality to exchange currencies.

MoneyManager.java - 
(MoneyManager class)

A class whose main function is to handle the money between all the players in a game.

Playable.java -
(Playable interface)

Interface for playable objects (in this case, games are playable).

Player.java
(Player abstract class)

Abstract class for the idea of a player in a game.

PlayerManager.java
(PlayerManager class)

A class whose main function is to manage all the players that are currently in the game and remove them if necessary.

Quit.java
(Quit class)

Essentially a class that aids in check for when the program ends and executes code before it ends. In this implementation, it only prints out an ending message.

Rank.java
(Rank class)

A class to represent the ranks of a card.

Runnable.java
(Runnable interface)

An interface for runnable objects. In this case, a gameManager can be ran, which will start the game.

Suit.java
(Suit class)

A class to represent the suits of a card.

**Other Notes:**
Nothing.