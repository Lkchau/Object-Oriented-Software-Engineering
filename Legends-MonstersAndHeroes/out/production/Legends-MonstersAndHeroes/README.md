**LEGENDS MONSTERS AND HEROES README**

Name: Leo Chau

Email: lchau@bu.edu

BU ID: U48783299

**Compilation Instructions:**

Please place all the files in the same directory. I personally used intellij to create and run the program. If using such an ide you may simply run it through the ide.

If one were to compile and run through the terminal, then you can use **javac *.java** in the terminal to compile all the java files (If your javac and java version are different, you may need to change the version of one of them or compile the java files such that they can run on the same version as your java using the following command: **javac -source (java version) -target (java version) *.java**).

NOTE: BE SURE TO INCLUDE ALL TEXT FILES AND ALSO THE Legends_Monsters_and_Heroes File in this directory!
The game will not run without them.


**Execution Instructions:**

If you are running through an ide, you may skip this section and simply press run.

If following the compilation instructions through the terminal, please continue reading this section.
Once everything is compiled correctly. You may type in the command **java App** while in the same directory. As the main function for the program exists within the App.java file. Doing so will begin the program. Have fun!

**Descriptions For Each Class:**

There are a lot of classes in this project and thus instead of describing
every single class, I will be describing groups of RELATED classes.

App -

The main driver class to be used to run the program.

Menu - The following classes have to do with the representation of menus and the different kinds of menus that one will be shown while playing the game.
There is also a menu factory to create and a general menu. I most likely could have cut down on the amount of menus.

AbstractMenuFactory
BattleActionMenu
BuyArmorMenu
BuyFireSpellMenu
BuyIceSpellMenu
BuyLightningSpellMenu
BuyPotionMenu
BuyWeaponMenu
CharacterMenuFactory
ChooseCharacterInPartyMenu
ChooseMarketMenu
ControlMenu
CreateHeroMenu
CreatePaladinMenu
CreateWarriorMenu
ConcreteMenuFactory
MarketMainMenu
Menu
MenuFactory
MenuFactoryInterface (interface)
StartMenu

Game - The following classes are to represent the abstract idea of a game and also the concrete implementation (legends monsters and heroes)
In addition to that, I also created a game factory in case if I want to be able to add other games and create other games.

Game
GameFactory
GameFactoryInterface (interface)
GameManager
RolePlayingGame
RolePlayingGameWithMap
LegendsMonstersAndHeroes


Map - the following classes are to represent the physical map, which has a 2d board of tiles and other information!

Board
Map
MapFactory
MapFactoryInterface (interface)
LegendsMap

Tile - the following classes are different tiles in a board and also the factory for them

CommonTile
InaccessibleTile
MarketTile
Enterable (interface)
Tile
TileFactory
TileFactoryInterface

Stats - the following classes are used to represent the idea of stats that a hero can have and to maintain the stats instead
of having to maintain each stat separately within the hero class
NOTE: some of the stats are used by items as well

Agility
HeroStats
Stat
Strength
Dexterity
Damage
Health
Mana
Level
Money
Buff


Items - the following classes are used to represent items that are bought/sold and used throughout the game

Armor
ArmorFactory
FireSpell
IceSpell
Item
ItemAbstractFactory
ItemFactory
ItemFactoryInterface (interface)
LightningSpell
Potion
PotionFactory
Spell
SpellFactory
SpellFactoryInterface (interface)
Consumable (interface)
Castable (interface)
Equippable (interface)
Weapon
WeaponFactory

Character - the following classes are used to represent characters in the game, which could be a hero or a monster
and also there are classes to help store information about the character!

Attackable (interface)
Buffable (interface)
Character
CharacterAbstractFactory
CharacterFactory
CharacterFactoryInterface (interface)
Hero
HeroFactory
HeroFactoryInterface (interface)
Inventory
Debuffable (interface)
Experience
Monster
MonsterFactory
Name
Paladin
Party
Warrior
Sorcerer

Battle - this class is used to represent a battle in legends monsters and heroes. It may have been better to have an abstract battle and then implement a specific kind of battle for certain games instead
Battle

Market - this class is used to represent a Market in legends monsters and heroes. It may have been better to have an abstract Market and then implement a specific kind of Market for certain games instead
The markets are stored within market tiles in the game

Market

The final three classes are all singleton classes!
FileParser - Used for parsing through different text files.
Colors - Used to help color strings to print out nicely in the terminal
UserPrompt - Used to ask for user input to a given prompt!





**Other Notes:**
I added colors to the terminal!
NOTE: Windows users will most likely need to run the following:
reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f
To be able to get the color to show up in the terminal.