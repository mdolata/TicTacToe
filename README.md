# Tic Tac Toe App

It is implementation of TicTacToe game with simple AI opponent.
I followed instuctions from JetBrains Academy https://hyperskill.org/projects/48

## How to Build

You can build this app executable jar with gradle wrapper unix executable with this command. Make sure you have jdk 8 installed in your computer.

```sh
./gradlew jar
```

Or if you're on windows you can use this command, this will run the batch file

```sh
gradlew jar
```

You can find the executable jar file in `build/libs/TicTacToe-1.0-SNAPSHOT.jar`

## How to Run

After building the executable jar, you can run the jar with this command

```sh
java -jar build/libs/TicTacToe-1.0-SNAPSHOT.jar
```

## How to start

First you need to enter 3 parameter to start the game,

- 1st parameter is game menu action, you can choose between:
  - `start` to start the game
  - `exit` to quit the game
- 2nd parameter is player 1 with X symbol
- 3rd parameter is player 2 with O symbol

You can choose the player 1 or player 2 between these

- `user` for human player
- `easy` for easy difficulty bot
- `medium` for medium difficulty bot
- `hard` for hard difficulty bot

So, for example if you want to play against easy you can type these

```sh
Input command: start user easy
```

To make bot play first, you can type user as 3rd parameter, like this.

```sh
Input command: start medium user
```

You can also let bot play against each other, like this.

```sh
Input command: start hard hard
```

## Playing the game

The rule is simple:

1. The game requires two players, X and O.
2. The game board is a set 3x3 grid in which players will place their symbol to claim that segment.
3. X typically players first, then players alternate turns.
4. The goal is to claim three segments of the grid in a row, either horizontally, vertically, or diagonally.
No additional sides can be added to the grid.
5. The game is over either when one player achieves three segments in a row, or when the grid is filled without anyone achieving three segments in a row.

In this game, after you select `user` (human player) to play the game, you can type in the coordinates to play the game. You'll be receiving this input command.

```sh
Enter the coordinates:
```

You need to type in the coordinate between for the X and Y position separated by space.

The **X** is the **horizontal** coordinate and the **Y** is the **vertical** coordinate.

The game board ideally look like this:

|Y \ X|1|2|3|
|-|-|-|-|
|3|X| | |
|2| | | |
|1| | | |

So if you want to put your piece at the top left of the board like this

 ‌‌ |1|2|3|
|-|-|-|-|
|3|X| | |
|2| | | |
|1| | | |

You'll need to type in.

```sh
Enter the coordinates: 1 3
```

The 2nd player can choose anything to block the 1st player. For example choosing the middle tile, you can type in.

```sh
Enter the coordinates: 2 2
```

 ‌‌ |1|2|3|
|-|-|-|-|
|3|X| | |
|2| |O| |
|1| | | |

The game will continue and so on.

### Wrong input

If the tile is already occupied, the game will tell you so.

```sh
Enter the coordinates: 1 3
This cell is occupied! Choose another one!
```

If you type in wrong coordinate, the game will also tell you.

```sh
Enter the coordinates: 33
You should two enter numbers with one space!
```

```sh
Enter the coordinates: 1 4
Coordinate should be from 1 to 3!
```

### Ending the game

You'll continue until one of the players win like this.

```sh
---------
| X O X |
| O O O |
| X X   |
---------
O_WINS
```

```sh
---------
| X O X |
| O X O |
| X     |
---------
X_WINS
```

Or until board is full and no one win, it will be `DRAW`

```sh
---------
| X O X |
| O O X |
| X X O |
---------
DRAW
```
