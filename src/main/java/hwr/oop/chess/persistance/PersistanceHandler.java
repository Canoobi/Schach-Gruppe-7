package hwr.oop.chess.persistance;

import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PersistanceHandler {

  private final Path csvFilePath;
  private final IOExceptionBomb ioExceptionBomb;
  private Map<String, Piece.Color> intToEnum;

  private static boolean isNumeric(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private void createHashMap() {
    intToEnum = new HashMap<>();
    intToEnum.put("BLACK", Piece.Color.BLACK);
    intToEnum.put("WHITE", Piece.Color.WHITE);
  }

  public PersistanceHandler(Path csvFilePath) {
    this(csvFilePath, IOExceptionBomb.DONT);
    createHashMap();
  }

  public PersistanceHandler(Path csvFilePath, IOExceptionBomb ioExceptionBomb) {
    this.csvFilePath = csvFilePath;
    this.ioExceptionBomb = ioExceptionBomb;
  }

  public void saveGame(Game game) {

    String csvString =
        game.getId() + "," + game.getBoard().getFenOfBoard() + "," + game.getActivePlayer() + "\n";

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
              .orElse(-1));
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read CSV file", e);
    }
  }

  public void deleteMatch(String id) {
    List<String> result;
    StringBuilder newSaveFile = new StringBuilder();
    try (var stuff = Files.newBufferedReader(csvFilePath)) {
      result = stuff.lines().toList();

    } catch (IOException e) {
      throw new IllegalStateException();
    }

    for (String match : result) {
      if (!match.startsWith(id)) {
        newSaveFile.append(match).append("\n");
      }
    }

    try (final var writer = Files.newBufferedWriter(csvFilePath)) {
      writer.write(String.valueOf(newSaveFile));
    } catch (IOException e) {
      throw new IllegalStateException("Could not save game", e);
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

  public Game getGameFromID(String id) {
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

    return new Game(Integer.parseInt(id), result.get(1), intToEnum.get(result.get(2)));
  }
}
