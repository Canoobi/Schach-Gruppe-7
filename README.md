# OOP-Projekt Gruppe 7 (Schach)

[TODO]: # (Change README.md Headline to better fit to your project!)

This repository contains a student project created for an ongoing lecture on object-oriented
programming with Java/Kotlin at HWR Berlin (summer term 2024).

> :warning: This code is for educational purposes only. Do not rely on it!

## Prerequisites

Installed:

1. IDE of your choice (e.g. IntelliJ IDEA)
2. JDK of choice installed (e.g. through IntelliJ IDEA)
3. Maven installed (e.g. through IntelliJ IDEA)
4. Git installed

## Local Development

This project uses [Apache Maven][maven] as build tool.

To build from your shell (without an additional local installation of Maven), ensure that `./mvnw`
is executable:

```
chmod +x ./mvnw
```

I recommend not to dive into details about Maven at the beginning.
Instead, you can use [just][just] to build the project.
It reads the repositories `justfile` which maps simplified commands to corresponding sensible Maven
calls.

With _just_ installed, you can simply run this command to perform a build of this project and run
all of its tests:

```
just build
```

## Abstract

[TODO]: # (Write a short description of your project.)

[TODO]: # (State most important features.)

[TODO]: # (State the most interesting problems you encountered during the project.)

## Feature List

[TODO]: # (For each feature implemented, add a row to the table!)

| Number | Feature                | Tests                                                                                                                                                                                                   |
|--------|------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1      | setActPosition()       | setActPositionTest()                                                                                                                                                                                    |
| 2      | getColor()             | getFenOfBoardTest()                                                                                                                                                                                     |
| 3      | getAbbr()              | getFenOfBoardTest()                                                                                                                                                                                     |
| 4      | getActPosition()       | getActPositionTest()                                                                                                                                                                                    |
| 5      | getPosMoves()          | isValidMoveTest()                                                                                                                                                                                       |
| 6      | isMoveRepeatable()     | isMoveRepeatableTest()                                                                                                                                                                                  |
| 7      | charToPieceType()      | setBoardToFenTest()                                                                                                                                                                                     |
| 8      | initBoard()            | initBoardTest()                                                                                                                                                                                         |
| 9      | changePos()            | changePosTest()                                                                                                                                                                                         |
| 10     | getPieceAt()           | getPieceAtTest()                                                                                                                                                                                        |
| 11     | getPlayBoard()         | testBoard()^                                                                                                                                                                                            |
| 12     | setPieceAt()           | setPieceAtTest()                                                                                                                                                                                        |
| 13     | setBoardToFen()        | setBoardToFenTest()                                                                                                                                                                                     |
| 14     | printBoard()           | printBoardTest()                                                                                                                                                                                        |
| 15     | isValidMove()          | isValidMoveTest()                                                                                                                                                                                       |
| 16     | isBlocked()            | isBlockedTest()/isBlockedTestFullBoard()/isBlockedTestEmptyBoard()/isBlockedTestPieceOnEdgeOfMovement()/isBlockedTestHalfFilledBoard1()/isBlockedTestHalfFilledBoard2()/isBlockedTestHalfFilledBoard3() |
| 17     | getFenOfBoard()        | getFenOfBoardTest()                                                                                                                                                                                     |
| 18     | abbrToFenChar()        | Any test that creates a new board                                                                                                                                                                       |
| 19     | getKing()              | getKingTest()/getKingNullTest()                                                                                                                                                                         |
| 20     | isCheck()              | isCheckTest()                                                                                                                                                                                           |
| 21     | isValidMoveRepeat()    | isValidMoveTest()                                                                                                                                                                                       |
| 22     | isValidMoveNonRepeat() | isValidMoveTest()                                                                                                                                                                                       |
| 23     | isValidMovePawn()      | isValidMoveTest()                                                                                                                                                                                       |
| 24     | isCorrectColor()       | isCorrectColorTest()                                                                                                                                                                                    |
| 25     | getId()                | newGameTest()/loadGameTest()                                                                                                                                                                            |
| 26     | getActivePlayer()      | newGameTest()/loadGameTest()                                                                                                                                                                            |
| 27     | setActivePlayer()      | loadGameTest()                                                                                                                                                                                          |
| 28     | getBoard()             | newGameTest()/loadGameTest()                                                                                                                                                                            |
| 29     | movePiece()            | movePieceWhiteTest()/movePieceBlackTest()                                                                                                                                                               |
| 30     | getWinner()            | getWinnerTest()                                                                                                                                                                                         |
| 31     | setWinner()            | getWinnerTest()                                                                                                                                                                                         |
| 31     | saveGame()             | saveGameTest()                                                                                                                                                                                          |
| 31     | getLatestId()          | getLatestIdTest()                                                                                                                                                                                       |
| 31     | getAllMatchId()        | saveGameTest()                                                                                                                                                                                          |
| 31     | getBoardFromId()       | getBoardFromIDTest()                                                                                                                                                                                    |

## Additional Dependencies

[TODO]: # (For each additional dependency your project requires- Add an additional row to the table!)

| Number | Dependency Name | Dependency Description | Why is it necessary? |
|--------|-----------------|------------------------|----------------------|
| 1      | /               | /                      | /                    |

### Multiple remote repositories

Your local repository should have a reference to both the fork (your own remote repository)
and the original remote repository.
To configure your git remote repositories, use the `git remote` command set.

1. Clone your fork and go enter the repository.

```
git clone <fork-url>
cd <created-folder>
```

2. Now your fork is configured as primary remote repository (origin).
   Next to origin, you should add the original repository as a second remote repository (upstream).

```
git remote add upstream <repository-url>
```

3. Verify that both remotes are configured correctly.
   The following command should list both remotes: origin and upstream.

```
git remote -v
```

4. To fetch changes from all remote repositories, use:

```
git fetch --all
```

5. If there are interesting changes (in e.g. the `main` branch) to merge into your branch, use:

```
git pull upstream main
```

[maven]: https://maven.apache.org/

[just]: https://github.com/casey/just
