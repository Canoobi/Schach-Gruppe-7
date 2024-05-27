package hwr.oop.cliTests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
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
    this.cli = new Cli(outputStream, persistance);
  }

  @Test
  void newGame_CreatesNewGame() {
    cli.handle(
        "new_game",
        "id",
        "1337",
        "trump",
        "HEARTS",
        "players",
        "alpha",
        "beta",
        "--file",
        "example.csv");
    final var output = outputStream.toString();
    assertThat(output).contains("Created new game with id: 1337");
  }
}
