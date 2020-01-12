package test.com.jkozlowska.eightwords;

import main.com.jkozlowska.eightwords.model.Board;
import org.junit.Assert;
import org.junit.Test;

public class BoardTest {

    @Test
    public void testBoardShouldBeNotNull() {
        Board board = new Board(8);
        Assert.assertNotNull(board);
    }

    @Test
    public void testBoardShouldHaveProperSize() {
        Board board = new Board(8);
        Assert.assertEquals(board.getSize(),8);
    }

    @Test
    public void testBoardShouldBeNotFilled() {
        Board board = new Board(8);
        Assert.assertFalse(board.isFilled());
    }

    @Test
    public void testBoardShouldBeFilled() {
        Board board = new Board(2);
        board.setValue(0,0,'x');
        board.setValue(0,1,'x');
        board.setValue(1,0,'x');
        board.setValue(1,1,'x');
        Assert.assertTrue(board.isFilled());
    }


}
