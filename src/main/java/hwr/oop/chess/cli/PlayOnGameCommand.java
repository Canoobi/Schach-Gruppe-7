package hwr.oop.chess.cli;

import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import hwr.oop.chess.persistance.PersistanceHandler;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public final class PlayOnGameCommand implements MutableCommand {
  private String gameId;
  private Piece.Color playerColor;
  private List<Integer> oldPositionInteger;
  private String oldPositionString;
  private List<Integer> newPositionInteger;
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
    oldPositionInteger = convertToIndices(oldPositionString);
    newPositionString = arguments.get(8).toLowerCase();
    newPositionInteger = convertToIndices(newPositionString);
  }

  public static List<Integer> convertToIndices(String chessCoordinate) {
    if (chessCoordinate.length() != 2) {
      throw new IllegalArgumentException("Illegal chessCoordinate: " + chessCoordinate);
    }

    char file = chessCoordinate.charAt(0);
    int column = file - 'a';

    char rank = chessCoordinate.charAt(1);
    int row = Character.getNumericValue(rank) - 1;

    List<Integer> indices = new ArrayList<>();
    indices.add(column);
    indices.add(row);

    if (indices.get(0) > 7 || indices.get(0) < 0 || indices.get(1) > 7 || indices.get(1) < 0) {
      return new ArrayList<>();
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
      if (oldPositionInteger.isEmpty() || newPositionInteger.isEmpty()) {
        out.println("Index is out of range of the board.");
        return;
      }
      if (game.getActivePlayer() == playerColor) {
        game.movePiece(
            oldPositionInteger.getFirst(),
            oldPositionInteger.get(1),
            newPositionInteger.getFirst(),
            newPositionInteger.get(1));
        out.println("Piece " + oldPositionString + " moved to " + newPositionString);

        if (game.getBoard().stalemate(game.getActivePlayer())) {
          out.println("The game is a stalemate! It's a draw!");
          // TODO delete game
        } else if (game.getBoard().checkmate(game.getActivePlayer())) {
          game.setWinner(playerColor.toString());
          out.println("Congratulations! Player " + game.getWinner() + " won the game!");
          // TODO delete game
        } else {
          out.println("Now it's player " + game.getActivePlayer() + "'s turn.");
        }
      } else {
        out.println("Player " + game.getActivePlayer() + " is playing!");
      }
    }
  }
}
