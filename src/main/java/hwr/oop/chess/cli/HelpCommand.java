package hwr.oop.chess.cli;

import java.io.PrintStream;
import java.util.List;

public final class HelpCommand implements MutableCommand {

  @Override
  public void parse(List<String> arguments) {
    // nothing to do
  }

  @Override
  public boolean isApplicable(List<String> arguments) {
    return arguments.getFirst().equals("help");
  }

  @Override
  public void invoke(PrintStream out) {
    out.println("You can use one of the following commands:");
    out.println("New game: new_game");
    out.println("Game state: on game XX state");
    out.println("Play on a game: on game XX player white/black moves XX to XX");
  }
}
