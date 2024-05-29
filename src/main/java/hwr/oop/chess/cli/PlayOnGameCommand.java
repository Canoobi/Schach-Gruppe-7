package hwr.oop.chess.cli;

import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import hwr.oop.chess.Position;
import hwr.oop.chess.persistance.PersistanceHandler;
import java.io.PrintStream;
import java.util.List;

public final class PlayOnGameCommand implements MutableCommand {
  private String gameId;
  private Piece.Color playerColor;
  private Position oldPosition;
  private String oldPositionString;
  private Position newPosition;
  private String newPositionString;
  private Game game;
  private final PersistanceHandler persistance;

  public PlayOnGameCommand(PersistanceHandler persistance) {
    this.persistance = persistance;
  }

  @Override
  public void parse(List<String> arguments) {
    this.gameId = arguments.get(2);
    this.playerColor = Piece.Color.valueOf(arguments.get(4).toUpperCase());
    oldPositionString = arguments.get(6).toLowerCase();
    oldPosition = convertToIndices(oldPositionString);
    newPositionString = arguments.get(8).toLowerCase();
    newPosition = convertToIndices(newPositionString);
  }

  public static Position convertToIndices(String chessCoordinate) {
    if (chessCoordinate.length() != 2) {
      throw new IllegalArgumentException("Illegal chessCoordinate: " + chessCoordinate);
    }

    char file = chessCoordinate.charAt(0);
    int column = file - 'a';

    char rank = chessCoordinate.charAt(1);
    int row = Character.getNumericValue(rank) - 1;

    Position indices = new Position(column, row);

    if (indices.getX() > 7 || indices.getX() < 0 || indices.getY() > 7 || indices.getY() < 0) {
      return new Position(-1, -1);
    }
    return indices;
  }

  @Override
  public boolean isApplicable(List<String> arguments) {
    return arguments.get(0).equals("on")
        && arguments.get(1).equals("game")
        && arguments.get(3).equals("player")
        && arguments.get(5).equals("moves")
        && arguments.get(7).equals("to");
  }

  @Override
  public void invoke(PrintStream out) {
    if (Integer.parseInt(gameId) < 0) {
      out.println("Game with ID " + gameId + " not found!");
    } else {
      if (Integer.parseInt(this.gameId) <= Integer.parseInt(persistance.getLatestID())) {
        this.game = persistance.getGameFromID(gameId);
      }
      if (oldPosition.getX() == -1 || newPosition.getY() == -1) {
        out.println("Index is out of range of the board.");
        return;
      }
      if (game.getBoard().getPieceAt(oldPosition.getX(), oldPosition.getY()) == null) {
        out.println("No piece at position " + oldPositionString + " found. Please try again.");
        return;
      }
      if (game.getActivePlayer() == playerColor) {
        moveLegal(out);
      } else {
        out.println("Player " + game.getActivePlayer() + " is playing!");
      }
    }
  }

  private void moveLegal(PrintStream out) {
    boolean boo =
        game.movePiece(
            oldPosition.getX(), oldPosition.getY(), newPosition.getX(), newPosition.getY());

    if (game.getBoard().stalemate(game.getActivePlayer())) {
      out.println("The game is a stalemate! It's a draw!");
      persistance.deleteMatch(gameId);
    } else if (game.getBoard().checkmate(game.getActivePlayer())) {
      game.setWinner(playerColor.toString());
      out.println("Congratulations! Player " + game.getWinner() + " won the game!");
      persistance.deleteMatch(gameId);
    } else if (boo) {
      out.println("Piece " + oldPositionString + " moved to " + newPositionString);
      if (game.getBoard().isCheck(game.getActivePlayer())) {
        out.println("Player " + game.getActivePlayer() + " stands in check.");
      }
      out.println("Now it's player " + game.getActivePlayer() + "'s turn.");
      persistance.deleteMatch(gameId);
      persistance.saveGame(game);
    } else {
      out.println("Move failed! Please try again.");
    }
  }
}
