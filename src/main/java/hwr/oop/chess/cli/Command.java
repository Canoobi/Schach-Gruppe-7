package hwr.oop.chess.cli;

import java.io.PrintStream;

interface Command {
  void invoke(PrintStream out);
}
