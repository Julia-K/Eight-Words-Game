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

    @Test
    public void testBoardShouldHavePassword() {
        Board board = new Board(2);
        board.setPassword("password");
        Assert.assertEquals(board.getPassword(), "password");
    }

    @Test
    public void testBoardShouldHaveNullPassword() {
        Board board = new Board(5);
        Assert.assertNull(board.getPassword());
    }

    @Test
    public void testBoardShouldHaveLetters() {
        Board board = new Board(2);
        char[] letters = {'A', 'B'};
        board.setLetters(letters);
        Assert.assertEquals(board.getLetters(), letters);
    }

    @Test
    public void testBoardShouldHaveNullLetters() {
        Board board = new Board(6);
        Assert.assertNull(board.getLetters());
    }

    @Test
    public void testBoardShouldAllowSetPasswordCell() {
        Board board = new Board(2);
        board.setPasswordCell(0,0,true);
        Assert.assertTrue(board.isPasswordCell(0,0));
    }

    @Test
    public void testBoardShouldAllowSetCellToChangePossible() {
        Board board = new Board(2);
        Assert.assertTrue(board.isCellChangePossible(0,0));
        board.setCellChangeToPossible(0,0,false);
        Assert.assertFalse(board.isCellChangePossible(0,0));
    }

    @Test
    public void testBoardShouldHaveProperlyFilledCell() {
        Board board = new Board(2);
        board.setValue(0,0,'p');
        Assert.assertEquals(board.getValue(0,0), 'p');
    }

    @Test
    public void testBoardShouldRestart() {
        Board board = new Board(2);
        board.setValue(0,0,'p');
        board.restart();
        Assert.assertEquals(board.getValue(0,0), ' ');
    }

    @Test
    public void testBoardShouldAllowChangingCellToFilled() {
        Board board = new Board(2);
        board.setValue(0,0,'a');
        Assert.assertTrue(board.isFilledCell(0,0));
    }

    @Test
    public void testBoardShouldHaveEmptyCell() {
        Board board = new Board(2);
        Assert.assertFalse(board.isFilledCell(0,0));
    }

    @Test
    public void testBoardShouldAddValueWithHistory() {
        Board board = new Board(5);
        board.addValueWithHistory(0,0,'p');
        board.undo();
        Assert.assertEquals(board.getValue(0,0),' ');
    }

    @Test
    public void testBoardShouldUndo() {
        Board board = new Board(2);
        board.addValueWithHistory(0,0,'a');
        board.addValueWithHistory(0,0,'p');
        board.addValueWithHistory(0,0,'x');
        board.undo();
        board.undo();
        Assert.assertEquals(board.getValue(0,0),'a');
    }

    @Test
    public void testBoardShouldRedo() {
        Board board = new Board(5);
        board.addValueWithHistory(0, 0, 'q');
        board.addValueWithHistory(0, 0, 'p');
        board.addValueWithHistory(0,0,'t');
        board.undo();
        board.redo();
        Assert.assertEquals(board.getValue(0,0),'t');
    }

    @Test
    public void testBoardShouldUnableSetValue() {
        Board board = new Board(3);
        board.setCellChangeToPossible(0,0,false);
        board.setValue(0, 0, 'x');
        Assert.assertEquals(board.getValue(0,0),' ');
    }
}
