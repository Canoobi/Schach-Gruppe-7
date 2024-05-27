package hwr.oop.chess.cli;

import java.io.PrintStream;
import java.util.List;

final class NewGameCommand implements MutableCommand {

  private String board;

  public NewGameCommand() {}

  @Override
  public void parse(List<String> arguments) {
    this.board = arguments.get(4);
    // TODO ^
  }

  @Override
  public boolean isApplicable(List<String> arguments) {
    return arguments.size() >= 8
        && arguments.get(1).equals("id")
        && arguments.get(3).equals("trump")
        && arguments.get(5).equals("players");
  }

  @Override
  public void invoke(PrintStream out) {
    // nothing to do
  }
}
