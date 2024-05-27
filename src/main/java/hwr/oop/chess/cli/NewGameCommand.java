package hwr.oop.chess.cli;

import hwr.oop.chess.Game;
import hwr.oop.chess.persistance.PersistanceHandler;

import java.io.PrintStream;
import java.util.List;

final class NewGameCommand implements MutableCommand {

  PersistanceHandler persistance;

  public NewGameCommand(PersistanceHandler persistance) {
    this.persistance = persistance;
  }

  @Override
  public void parse(List<String> arguments) {
    // nothing to do
  }

  @Override
  public boolean isApplicable(List<String> arguments) {
    return !arguments.isEmpty() && arguments.getFirst().equals("new_game");
  }

  @Override
  public void invoke(PrintStream out) {
    int gameId = Integer.parseInt(persistance.getLatestID()) + 1;
    Game game = new Game(gameId);
    persistance.saveGame(game);
    out.println("Created new game with id: " + gameId);
  }
}
