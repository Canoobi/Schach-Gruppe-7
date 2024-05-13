package hwr.oop.chess.persistance;

import hwr.oop.chess.Board;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class PersistanceHandler {

  private final Path csvFilePath;
  private final IOExceptionBomb ioExceptionBomb;

  private static boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public PersistanceHandler(Path csvFilePath) {
    this(csvFilePath, hwr.oop.chess.persistance.IOExceptionBomb.DONT);
  }

  public PersistanceHandler(
      Path csvFilePath, hwr.oop.chess.persistance.IOExceptionBomb ioExceptionBomb) {
    this.csvFilePath = csvFilePath;
    this.ioExceptionBomb = ioExceptionBomb;
  }

  public void saveGame(String id, Board board) {

    String csvString = id + "," + board.getFenOfBoard() + "\n";

    try (final var writer = Files.newBufferedWriter(csvFilePath, StandardOpenOption.APPEND)) {
      ioExceptionBomb.fire();
      writer.append(csvString);
    } catch (IOException e) {
      throw new IllegalStateException("Could not save game", e);
    }
  }

  public String getLatestID() {
    try (var reader = Files.newBufferedReader(csvFilePath)) {
      ioExceptionBomb.fire();
      return Integer.toString(
          reader
              .lines()
              .flatMap(line -> Stream.of(line.split(",")))
              .filter(PersistanceHandler::isNumeric)
              .mapToInt(Integer::parseInt)
              .max()
              .orElseThrow());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read CSV file", e);
    }
  }

  public List<String> getAllMatchId() {
    List<String> result;
    try (var stuff = Files.newBufferedReader(csvFilePath)) {
      ioExceptionBomb.fire();
      result =
          stuff
              .lines()
              .flatMap(line -> Stream.of(line.split(",")))
              .filter(PersistanceHandler::isNumeric)
              .toList();
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    return result;
  }

  public Board getBoardFromID(String id) {
    Board board = new Board();

    List<String> result;
    try (var stuff = Files.newBufferedReader(csvFilePath)) {
      ioExceptionBomb.fire();
      result =
          stuff
              .lines()
              .filter(l -> l.startsWith(id))
              .flatMap(line -> Stream.of(line.split(",")))
              .toList();
    } catch (IOException e) {
      throw new IllegalStateException();
    }

    board.setBoardToFen(result.get(1));

    return board;
  }
}
