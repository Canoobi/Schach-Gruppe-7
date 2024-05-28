package hwr.oop.persistanceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import hwr.oop.chess.persistance.IOExceptionBomb;
import hwr.oop.chess.persistance.PersistanceHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersistanceTest {

  private final String filePathString = "src/test/resources/testFile.csv";
  private Path path;

  @BeforeEach
  void CreateFile() throws IOException {
    final var file = new File(filePathString);
    file.createNewFile();
    this.path = file.toPath();
  }

  @AfterEach
  void deleteFile() throws IOException {
    Files.delete(path);
  }

  @Test
  void deleteMatch() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));

    Game game1 = new Game(1, "", Piece.Color.BLACK);
    Game game2 = new Game(2, "", Piece.Color.BLACK);
    Game game3 = new Game(3, "", Piece.Color.BLACK);
    persistanceHandler.saveGame(game1);
    persistanceHandler.saveGame(game2);
    persistanceHandler.saveGame(game3);

    persistanceHandler.deleteMatch("2");

    assertThat(persistanceHandler.getAllMatchId()).doesNotContain("2");
  }

  @Test
  void saveGameTest() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));
    Game game1 = new Game(1, "", Piece.Color.BLACK);
    Game game2 = new Game(2, "", Piece.Color.BLACK);
    Game game3 = new Game(3, "", Piece.Color.BLACK);

    List<String> allMatchID = Arrays.asList("1", "2", "3");

    persistanceHandler.saveGame(game1);
    persistanceHandler.saveGame(game2);
    persistanceHandler.saveGame(game3);

    assertThat(persistanceHandler.getAllMatchId()).isEqualTo(allMatchID);
  }

  @Test
  void getLatestIdTest() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));

    Game game1 = new Game(1, "", Piece.Color.BLACK);
    Game game2 = new Game(2, "", Piece.Color.BLACK);
    Game game3 = new Game(3, "", Piece.Color.BLACK);

    persistanceHandler.saveGame(game1);
    persistanceHandler.saveGame(game2);
    persistanceHandler.saveGame(game3);

    assertThat(persistanceHandler.getLatestID()).isEqualTo("3");
  }

  @Test
  void getGameFromIDTest() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));

    Game game1 = new Game(1, "8/8/6r1/8/3q4/8/8/8", Piece.Color.BLACK);
    Game game2 = new Game(2, "1k6/8/6r1/8/3q4/8/8/3R4", Piece.Color.WHITE);

    persistanceHandler.saveGame(game1);
    persistanceHandler.saveGame(game2);

    assertThat(persistanceHandler.getGameFromID("2").getBoard().getFenOfBoard())
        .isEqualTo("1k6/8/6r1/8/3q4/8/8/3R4");
    assertThat(persistanceHandler.getGameFromID("2").getActivePlayer())
        .isEqualTo(Piece.Color.WHITE);
  }

  @Test
  void exceptionTests() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"), IOExceptionBomb.DO);
    Game game = new Game(1, "8/8/6r1/8/3q4/8/8/8", Piece.Color.BLACK);

    IllegalStateException exception1 =
        assertThrows(IllegalStateException.class, () -> persistanceHandler.saveGame(game));
    assertThat(exception1.getMessage()).isEqualTo("Could not save game");

    IllegalStateException exception2 =
        assertThrows(IllegalStateException.class, persistanceHandler::getLatestID);
    assertThat(exception2.getMessage()).isEqualTo("Failed to read CSV file");

    IllegalStateException exception3 =
        assertThrows(IllegalStateException.class, persistanceHandler::getAllMatchId);

    IllegalStateException exception4 =
        assertThrows(IllegalStateException.class, () -> persistanceHandler.getGameFromID("2"));
  }
}
