package hwr.oop.chess.cli;

import hwr.oop.chess.Game;
import hwr.oop.chess.persistance.PersistanceHandler;

import java.io.PrintStream;
import java.util.List;

public final class GameQueryCommand implements MutableCommand {

  private String gameId;
  private Game game;
  private final PersistanceHandler persistance;

  public GameQueryCommand(PersistanceHandler persistance) {
    this.persistance = persistance;
  }

  @Override
  public void parse(List<String> arguments) {
    this.gameId = arguments.get(2);
    if (Integer.parseInt(this.gameId) <= Integer.parseInt(persistance.getLatestID())) {
      game = persistance.getGameFromID(gameId);
    }
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
    if (game == null) {
      out.println("Game with ID " + gameId + " not found!");
    } else {
      out.println("The chess game with the ID " + gameId + " looks like this:");
      out.println(game.getBoard().getFenOfBoard());
      //TODO print board
    }
  }
}
