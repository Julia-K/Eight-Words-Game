package test.com.jkozlowska.eightwords;

import main.com.jkozlowska.eightwords.model.OneCell;
import org.junit.Assert;
import org.junit.Test;

public class OneCellTest {

    @Test
    public void testOneCellValueShouldHaveProperValue() {
        OneCell oneCell = new OneCell();
        Assert.assertEquals(oneCell.getValue(),' ');
        oneCell.setValue('X');
        Assert.assertEquals(oneCell.getValue(), 'X');
    }

    @Test
    public void testOneCellShouldBeFilled() {
        OneCell oneCell = new OneCell();
        oneCell.setFilled(true);
        Assert.assertTrue(oneCell.isFilled());
    }

    @Test
    public void testOneCellShouldNotBeFilled() {
        OneCell oneCell = new OneCell();
        Assert.assertFalse(oneCell.isFilled());
    }

    @Test
    public void testOneCellShouldBeChangeable() {
        OneCell oneCell = new OneCell();
        Assert.assertTrue(oneCell.getChangePossibility());
    }

    @Test
    public void testOneCellShouldNotBeChangeable() {
        OneCell oneCell = new OneCell();
        oneCell.setChangePossibility(false);
        Assert.assertFalse(oneCell.getChangePossibility());
    }

    @Test
    public void testOneCellIsPasswordNeeded() {
        OneCell oneCell = new OneCell();
        oneCell.setPasswordNeeded(true);
        Assert.assertTrue(oneCell.isPasswordNeeded());
    }

    @Test
    public void testOneCellShouldBeCleared() {
        OneCell oneCell = new OneCell();
        oneCell.setValue('X');
        oneCell.clear();
        Assert.assertEquals(oneCell.getValue(),' ');
        Assert.assertFalse(oneCell.isFilled());
    }
}
