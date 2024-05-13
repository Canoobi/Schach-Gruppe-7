package hwr.oop.persistanceTests;

import static org.assertj.core.api.Assertions.assertThat;

import hwr.oop.chess.Board;
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
  void saveGameTest() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));
    Board board = new Board();
    board.initBoard();

    List<String> allMatchID = Arrays.asList("1", "2", "3");

    persistanceHandler.saveGame("1", board);
    persistanceHandler.saveGame("2", board);
    persistanceHandler.saveGame("3", board);

    assertThat(persistanceHandler.getAllMatchId()).isEqualTo(allMatchID);
  }

  @Test
  void getLatestIdTest() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));
    Board board = new Board();
    board.initBoard();

    persistanceHandler.saveGame("1", board);
    persistanceHandler.saveGame("3", board);
    persistanceHandler.saveGame("2", board);

    assertThat(persistanceHandler.getLatestID()).isEqualTo("3");
  }

  @Test
  void getBoardFromIDTest() {
    PersistanceHandler persistanceHandler =
        new PersistanceHandler(Paths.get("src/test/resources/testFile.csv"));
    Board board = new Board();
    board.initBoard();

    Board board2 = new Board();
    board2.setBoardToFen("1k6/8/6r1/8/3q4/8/8/3R4");

    persistanceHandler.saveGame("1", board);
    persistanceHandler.saveGame("2", board2);

    assertThat(persistanceHandler.getBoardFromID("2").getFenOfBoard())
        .isEqualTo("1k6/8/6r1/8/3q4/8/8/3R4");
  }
}
