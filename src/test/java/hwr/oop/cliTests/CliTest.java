package hwr.oop.cliTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;

import hwr.oop.chess.cli.Cli;
import hwr.oop.chess.persistance.PersistanceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CliTest {
  private OutputStream outputStream;
  private Cli cli;
  private PersistanceHandler persistance;

  @BeforeEach
  void setUp() {
    this.outputStream = new ByteArrayOutputStream();
    PersistanceHandler persistance;
    final var file = new File("src/main/resources/savedChessGames.csv");
    Path path = file.toPath();
    persistance = new PersistanceHandler(path);
    this.cli = new Cli(outputStream, persistance);
  }

  @Test
  void newGame_CreatesNewGame() {
    cli.handle("new_game");
    final var output = outputStream.toString();
    assertThat(output).contains("Created new game with id: ");
  }
}
