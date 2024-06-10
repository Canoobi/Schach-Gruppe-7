package hwr.oop.chess.cli;

import hwr.oop.chess.persistance.PersistanceHandler;

import java.io.OutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Cli {

  private final PrintStream out;
  private final ArgumentParser argumentParser;

  public Cli(OutputStream outputStream, PersistanceHandler persistance) {
    this.out = new PrintStream(outputStream);
    this.argumentParser = new ArgumentParser(persistance);
  }

  public void handle(String... arguments) {
    handle(Arrays.asList(arguments));
  }

  public void handle(List<String> arguments) {
    final List<String> mutable = new ArrayList<>(arguments);
    if (mutable.contains("--debug")) {
      out.println("Welcome to Chess!");
      out.println(" Arguments were: " + arguments);
      mutable.remove("--debug");
    }
    final var reduced = Collections.unmodifiableList(mutable);
    final var command = argumentParser.parse(reduced);
    command.invoke(out);
  }
}
