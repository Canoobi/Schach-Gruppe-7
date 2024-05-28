package hwr.oop.chess.cli;

import java.util.List;

public interface MutableCommand extends Command {
  void parse(List<String> arguments);

  boolean isApplicable(List<String> arguments);
}
