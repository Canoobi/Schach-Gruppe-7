package hwr.oop.chess.cli;

import hwr.oop.chess.persistance.PersistanceHandler;
import java.util.List;

final class ArgumentParser {

  private final List<MutableCommand> commands;

  public ArgumentParser(PersistanceHandler persistance) {
    this.commands =
        List.of(
            new HelpCommand(),
            new NewGameCommand(persistance),
            new PlayOnGameCommand(persistance),
            new GameQueryCommand(persistance));
  }

  public Command parse(List<String> arguments) {
    if (arguments.isEmpty()) {
      return new HelpCommand();
    }
    final var candidate =
        commands.stream()
            .filter(c -> c.isApplicable(arguments))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown command: " + arguments));
    candidate.parse(arguments);
    return candidate;
  }
}
