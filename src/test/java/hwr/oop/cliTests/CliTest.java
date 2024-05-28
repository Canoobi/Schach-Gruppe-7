package hwr.oop.cliTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;
import java.nio.file.Path;

import hwr.oop.chess.Game;
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
          softly.assertThat(output).contains("...");
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
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Piece b2 moved to b3");
          softly.assertThat(output).contains("Now it's player BLACK's turn.");
        });
  }

  @Test
  void playOnGameCommandFailTest() {
    cli.handle("on", "game", "-3", "player", "white", "moves", "b2", "to", "b3");
    final var output = outputStream.toString();
    assertSoftly(
        softly -> {
          softly.assertThat(output).contains("Game with ID -3 not found!");
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
    void exceptionTests() {
        IllegalArgumentException exception1 =
                assertThrows(IllegalArgumentException.class, () -> cli.handle("not a command"));

        IllegalArgumentException exception2 =
                assertThrows(IllegalArgumentException.class, () -> cli.handle("on", "game", "0", "player", "black", "moves", "be2", "to", "b3"));
    }
}
