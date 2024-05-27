package hwr.oop.chess.cli;

import java.io.PrintStream;
import java.util.List;

public final class GameQueryCommand implements MutableCommand {

  private String gameId;

  public GameQueryCommand() {}

  @Override
  public void parse(List<String> arguments) {
    this.gameId = arguments.get(2);
  }

  @Override
  public boolean isApplicable(List<String> arguments) {
    return arguments.size() >= 4
        && arguments.get(0).equals("on")
        && arguments.get(1).equals("game")
        && arguments.get(3).equals("state");
  }

  @Override
  public void invoke(PrintStream out) {
    // TODO print out game info here
  }
}
