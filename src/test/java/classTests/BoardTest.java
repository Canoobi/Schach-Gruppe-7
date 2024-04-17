package classTests;

import classes.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void get_isBlocked(){
        Board board = new Board();
        board.initBoard();
        Assertions.assertThat(board.isBlocked(board.getBoard()[0][0], 0,6,0,0)).isTrue();
        Assertions.assertThat(board.isBlocked(board.getBoard()[2][0], 4,2,0,2)).isTrue();
    }
}
