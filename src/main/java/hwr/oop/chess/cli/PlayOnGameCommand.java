package hwr.oop.chess.cli;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PlayOnGameCommand implements MutableCommand {
  private String gameId;
  private String playerId;
  private List<String> cards;

  public PlayOnGameCommand() {}

  @Override
  public void parse(List<String> arguments) {
    this.gameId = arguments.get(2);
    this.playerId = arguments.get(4);
    final String playType = arguments.get(5);
    if (playType.startsWith("play") || playType.startsWith("lay")) {
      this.cards = parseCardStrings(arguments);
    } else {
      this.cards = Collections.emptyList();
    }
  }

  private static List<String> parseCardStrings(List<String> arguments) {
    return arguments.subList(6, arguments.size()).stream()
        .map(c -> Arrays.stream(c.split(",")).toList())
        .flatMap(Collection::stream)
        .toList();
  }

  @Override
  public boolean isApplicable(List<String> arguments) {
    return arguments.size() >= 7
        && arguments.getFirst().equals("on")
        && arguments.get(1).equals("game")
        && arguments.get(3).equals("player")
        && (arguments.get(5).equals("lays")
            || arguments.get(5).equals("plays")
            || arguments.get(5).equals("picks"));
    // TODO change arguments
  }

  @Override
  public void invoke(PrintStream out) {
    // nothing to do
  }
}
