package hwr.oop.chess.cli;

import hwr.oop.chess.Board;
import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
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
      this.game = persistance.getGameFromID(gameId);
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
      printBoard(game, out);
    }
  }

  public void printBoard(Game game, PrintStream out) {
    StringBuilder outString = new StringBuilder();
    int row = 8;

    Board board = game.getBoard();

    for (List<Piece> l : board.getPlayBoard().reversed()) {
      outString.append(row).append("  ");
      row--;
      for (Piece p : l) {
        if (p == null) {
          outString.append("   ");
        } else {
          if (p.getColor() == Piece.Color.WHITE) {
            outString.append(Character.toUpperCase(p.getAbbreviation())).append("  ");
          } else {
            outString.append(p.getAbbreviation()).append("  ");
          }
        }
      }
      outString.append("\n");
    }

    outString.append("   ");

    for (int i = 65; i < 65 + 8; i++) {
      outString.append(Character.toString(i)).append("  ");
    }

    out.println(outString);
  }
}
