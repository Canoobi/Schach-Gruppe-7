package hwr.oop.chess.cli;

import hwr.oop.chess.persistance.PersistanceHandler;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

  @SuppressWarnings("java:S106")
  public static void main(String[] args) {
    PersistanceHandler persistance;
    final var file = new File("src/main/resources/savedChessGames.csv");
    Path path = file.toPath();
    persistance = new PersistanceHandler(path);
    final Cli cli = new Cli(System.out, persistance);
    cli.handle(Arrays.asList(args));
  }
}
