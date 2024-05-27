package hwr.oop.chess.cli;

import hwr.oop.chess.persistance.PersistanceHandler;

import java.util.Arrays;

public class Main {
  private static PersistanceHandler persistance;

  @SuppressWarnings("java:S106")
  public static void main(String[] args) {
    final Cli cli = new Cli(
            System.out,
            persistance
    );
    cli.handle(Arrays.asList(args));
  }
}
