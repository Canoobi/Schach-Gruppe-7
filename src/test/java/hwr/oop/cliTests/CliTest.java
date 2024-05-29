package hwr.oop.cliTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import java.nio.file.Path;

import hwr.oop.chess.FENString;
import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import hwr.oop.chess.cli.Cli;
import hwr.oop.chess.persistance.PersistanceHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CliTest {
  private OutputStream outputStream;
  private Cli cli;
  private PersistanceHandler persistance;
  private File file;

  @BeforeEach
  void setUp() {
    this.outputStream = new ByteArrayOutputStream();
    file = new File("src/test/resources/csvFileForTesting.csv");
    Path path = file.toPath();
    try {
      new BufferedWriter(new FileWriter(file));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    persistance = new PersistanceHandler(path);
    this.cli = new Cli(outputStream, persistance);
  }

  @AfterEach
  void tearDown() {
    if (file.delete()) {
      System.out.println("File deleted successfully");
    }
  }

  @Test
  void newGameCommandTest() {
    cli.handle("new_game");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Created new game with id: ");
          softly.assertThat(persistance.getLatestID()).isEqualTo("0");
        });
  }

  @Test
  void helpCommandTest() {
    cli.handle("help", "--debug");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Welcome to Chess!");
          softly.assertThat(output).contains("Arguments were: [help, --debug]");
          softly.assertThat(output).contains("You can use one of the following commands:");
          softly.assertThat(output).contains("New game: new_game");
          softly.assertThat(output).contains("Game state: on game XX state");
          softly
              .assertThat(output)
              .contains("Play on a game: on game XX player white/black moves XX to XX");
        });
  }

  @Test
  void gameQueryCommandTest() {
    Game game = new Game(0);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "state");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("The chess game with the ID 0 looks like this:");
          softly.assertThat(output).contains("8  t  s  l  d  k  l  s  t");
          softly.assertThat(output).contains("7  b  b  b  b  b  b  b  b");
          softly.assertThat(output).contains("6                        ");
          softly.assertThat(output).contains("5                        ");
          softly.assertThat(output).contains("4                        ");
          softly.assertThat(output).contains("3                        ");
          softly.assertThat(output).contains("2  B  B  B  B  B  B  B  B");
          softly.assertThat(output).contains("1  T  S  L  D  K  L  S  T");
          softly.assertThat(output).contains("   A  B  C  D  E  F  G  H");
          softly.assertThat(output).doesNotContain("  I");
        });
  }

  @Test
  void gameQueryCommandFailTest() {
    cli.handle("on", "game", "1", "state");
    final var output = outputStream.toString();
    assertThat(output).contains("Game with ID 1 not found!");
  }

  @Test
  void playOnGameCommandTest() {
    Game game = new Game(0);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "white", "moves", "b2", "to", "b3");
    final var output = outputStream.toString();
    Game finalGame = persistance.getGameFromID("0");
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Piece b2 moved to b3");
          softly.assertThat(output).contains("Now it's player BLACK's turn.");
          softly
              .assertThat(finalGame.getBoard().getFenOfBoard())
              .isNotEqualTo(new FENString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
          softly
              .assertThat(finalGame.getBoard().getFenOfBoard())
              .isEqualTo(new FENString("rnbqkbnr/pppppppp/8/8/8/1P6/P1PPPPPP/RNBQKBNR"));
        });
  }

  @Test
  void playOnGameCommandFail1Test() {
    cli.handle("on", "game", "-3", "player", "white", "moves", "b2", "to", "b3");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Game with ID -3 not found!");
        });
  }

  @Test
  void playOnGameCommandFail2Test() {
    Game game = new Game(0);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "white", "moves", "a1", "to", "c7");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Move failed! Please try again.");
        });
  }

    @Test
    void playOnGameCommandFail3Test() {
        Game game = new Game(0);
        persistance.saveGame(game);
        cli.handle("on", "game", "0", "player", "white", "moves", "d4", "to", "d5");
        final var output = outputStream.toString();
        assertSoftly(
                softly -> {
                    softly.assertThat(output).contains("No piece at position d4 found. Please try again.");
                });
    }

  @Test
  void playOnGameCommandWrongPlayerTest() {
    Game game = new Game(0);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "black", "moves", "b2", "to", "b3");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Player WHITE is playing!");
        });
  }

    @Test
    void playOnGameCommandCheckTest() {
        Game game = new Game(0, new FENString("4k3/p1ppp3/8/5Q2/q7/8/PP3PPP/R3K2R"), Piece.Color.WHITE);
        persistance.saveGame(game);
        cli.handle("on", "game", "0", "player", "white", "moves", "f5", "to", "h5");
        final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Piece f5 moved to h5");
          softly
              .assertThat(output)
              .contains("Player BLACK stands in check.");
          softly.assertThat(output).contains("Now it's player BLACK's turn.");
        });
    }

  @Test
  void playOnGameCommandStalemateTest() {
    Game game = new Game(0, new FENString("k7/8/8/8/8/r7/8/r5PK"), Piece.Color.BLACK);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "black", "moves", "a3", "to", "a2");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("The game is a stalemate! It's a draw!");
          softly.assertThat(persistance.getGameFromID("0")).isNull();
        });
  }

  @Test
  void playOnGameCommandCheckmateTest() {
    Game game = new Game(0, new FENString("k7/8/8/8/8/r7/8/r6K"), Piece.Color.BLACK);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "black", "moves", "a3", "to", "a2");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Congratulations! Player BLACK won the game!");
          softly.assertThat(persistance.getGameFromID("0")).isNull();
        });
  }

  @Test
  void playOnGameCommandIndexOutOfBoundTest() {
    Game game = new Game(0);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "white", "moves", "b9", "to", "b3");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Index is out of range of the board.");
        });
  }

  @Test
  void playOnGameCommandIndexOutOfBound1Test() {
    Game game = new Game(0, new FENString("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR"), Piece.Color.WHITE);
    persistance.saveGame(game);
    cli.handle("on", "game", "0", "player", "white", "moves", "h8", "to", "h1");
    var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Piece h8 moved to h1");
          softly.assertThat(output).contains("Now it's player BLACK's turn.");
        });
  }

  @Test
  void playOnGameCommandIndexOutOfBound2Test() {
    Game game = new Game(1, new FENString("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR"), Piece.Color.WHITE);
    persistance.saveGame(game);
    cli.handle("on", "game", "1", "player", "white", "moves", "a1", "to", "a8");
    var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Piece a1 moved to a8");
          softly.assertThat(output).contains("Now it's player BLACK's turn.");
        });
  }

  @Test
  void exceptionTests() {
    assertThrows(IllegalArgumentException.class, () -> cli.handle("not a command"));

    assertThrows(
        IllegalArgumentException.class,
        () -> cli.handle("on", "game", "0", "player", "black", "moves", "be2", "to", "b3"));
  }
}
