package test.com.jkozlowska.eightwords;

import main.com.jkozlowska.eightwords.model.Board;
import main.com.jkozlowska.eightwords.model.Conditions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConditionsTest {
    private Board board;
    private char[] letters = {'A', 'B', 'C', 'D'};


    @Before
    public void init() {
        board = new Board( 4);
        board.setLetters(letters);
        board.setValue(0, 1, 'D');
        board.setValue(0, 2, 'A');
        board.setValue(0, 3, 'B');
        board.setValue(3, 0, 'A');
        board.setValue(3, 1, 'C');
        board.setValue(3, 2, 'B');
    }

    @Test
    public void testConditionsShouldNotPutInRow() {
        board.addValueWithHistory(0,0,'D');
        boolean condition = Conditions.checkRows(board,letters,board.getSize());
        board.undo();
        Assert.assertFalse(condition);
    }

    @Test
    public void testConditionsShouldPutInRow() {
        board.addValueWithHistory(0, 0, 'C');
        boolean condition = Conditions.checkRows(board,letters,board.getSize());
        board.undo();
        Assert.assertTrue(condition);
    }

    @Test
    public void testConditionsShouldContainsStringInLetter() {
        Assert.assertTrue(Conditions.containsString("ABBA",letters));
    }

    @Test
    public void testConditionsShouldNotContainsStringInLetter() {
        Assert.assertFalse(Conditions.containsString("ABBAF",letters));
    }

    @Test
    public void testConditionsShouldNotPutInColumn() {
        board.addValueWithHistory(1,2,'B');
        boolean condition = Conditions.checkColumns(board,letters,board.getSize());
        board.undo();
        Assert.assertFalse(condition);
    }

    @Test
    public void testConditionsShouldPutInColumn() {
        board.addValueWithHistory(1,2,'C');
        boolean condition = Conditions.checkColumns(board,letters,board.getSize());
        board.undo();
        Assert.assertTrue(condition);
    }

    @Test
    public void testConditionsShouldNotPulInFirstDiagonal() {
        board.addValueWithHistory(0, 0, 'C');
        board.addValueWithHistory(2, 2, 'C');
        char[] firstDiagonal = Conditions.firstDiagonal(board,board.getSize());
        boolean condition = Conditions.checkDiagonal(firstDiagonal,letters,board.getSize());
        board.undo();
        board.undo();
        Assert.assertFalse(condition);
    }

    @Test
    public void testConditionsShouldPulInFirstDiagonal() {
        board.addValueWithHistory(0, 0, 'C');
        board.addValueWithHistory(3, 3, 'A');
        char[] firstDiagonal = Conditions.firstDiagonal(board,board.getSize());
        boolean condition = Conditions.checkDiagonal(firstDiagonal,letters,board.getSize());
        board.undo();
        board.undo();
        Assert.assertTrue(condition);
    }

    @Test
    public void testConditionsShouldNotPulInSecondDiagonal() {
        board.addValueWithHistory(2, 1, 'B');
        char[] secondDiagonal = Conditions.secondDiagonal(board,board.getSize());
        boolean condition = Conditions.checkDiagonal(secondDiagonal,letters,board.getSize());
        board.undo();
        Assert.assertFalse(condition);
    }

    @Test
    public void testConditionsShouldPulInSecondDiagonal() {
        board.addValueWithHistory(1,2, 'C');
        char[] secondDiagonal = Conditions.firstDiagonal(board,board.getSize());
        boolean condition = Conditions.checkDiagonal(secondDiagonal,letters,board.getSize());
        board.undo();
        Assert.assertTrue(condition);
    }

    @Test
    public void testConditionsShouldNotAllowCharacterBeApproved() {
        char p = 'p';
        Assert.assertFalse(Conditions.checkLetter(p, letters));
    }

    @Test
    public void testConditionsShouldAllowCharacterBeApproved() {
        char p = 'A';
        Assert.assertTrue(Conditions.checkLetter(p, letters));
    }

    @Test
    public void testConditionsShouldAllowForMove() {
        board.addValueWithHistory(0, 0, 'C');
        board.addValueWithHistory(1, 2, 'C');
        boolean condition = Conditions.isValidMove(board,letters,board.getValue(1,2),board.getSize());
        Assert.assertTrue(condition);
        board.undo();
        board.undo();
    }

    @Test
    public void testConditionsShouldNotAllowForMove() {
        board.addValueWithHistory(2,1, 'B');
        boolean condition = Conditions.isValidMove(board,letters,board.getValue(2,1),board.getSize());
        Assert.assertFalse(condition);
        board.undo();
    }
}
