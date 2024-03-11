import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class Sprint2UITest {

    private Sprint2UI ui;

    @Before
    public void setUp() {
        // Set up the UI before each test
        ui = new Sprint2UI();
    }

    @Test
    public void testUIInitialization() {
        //test if UI components initialize
        assertNotNull(ui);
        assertEquals("SOS", ui.getTitle());
        assertEquals(800, ui.getWidth());
        assertEquals(600, ui.getHeight());
        assertTrue(ui.isVisible());

        //test if mainPanel contains components
        assertNotNull(ui.mainPanel);
        assertNotNull(ui.sizeComboBox);
        assertNotNull(ui.blueSButton);
        assertNotNull(ui.blueOButton);
        assertNotNull(ui.redSButton);
        assertNotNull(ui.redOButton);
        assertNotNull(ui.blueLabel);
        assertNotNull(ui.redLabel);
    }

    @Test
    public void testBoardSizeSelection() {
        JComboBox<Integer> sizeComboBox = ui.sizeComboBox;
        assertNotNull(sizeComboBox);

        //testing a few different board sizes
        List<Integer> boardSizes = new ArrayList<>();
        boardSizes.add(3);
        boardSizes.add(4);
        boardSizes.add(5);
        boardSizes.add(6);

        //iterate through board sizes
        for (Integer size : boardSizes) {
            //setting the board size
            sizeComboBox.setSelectedItem(size);

            //calling startGame
            ui.startGame();

            //getting the displayed size
            int displayedSize = ui.boardPanel.getComponentCount();

            //double-checking the number of spaces on the grid
            int expectedGridSquares = size * size;
            assertEquals(expectedGridSquares, displayedSize);
        }
    }

    @Test
    public void testChoosingGameMode() {
        //simple game mode selected (by default)
        assertTrue(ui.simpleGameRadioButton.isSelected());

        //selecting general game
        ui.generalGameRadioButton.doClick();
        assertTrue(ui.generalGameRadioButton.isSelected());

        //selecting simple game
        ui.simpleGameRadioButton.doClick();
        assertTrue(ui.simpleGameRadioButton.isSelected());
    }

    @Test
    public void testStartGame() {
        //board panel should be null
        assertNull(ui.boardPanel);

        //clicking start game
        ui.startButton.doClick();

        //board panel shouldn't be null after starting game
        assertNotNull(ui.boardPanel);
    }

    @Test
    public void testMakingMoveSimpleGame() {
        //starting a simple game
        ui.simpleGameRadioButton.doClick();
        ui.startButton.doClick();

        //choosing s for blue and o for red
        ui.blueSButton.doClick();
        ui.redOButton.doClick();

        //trying making a move
        JButton gridSpace = (JButton) ui.boardPanel.getComponent(0);
        gridSpace.doClick();


        //checking to make sure the move was made
        assertEquals("S", gridSpace.getText());
    }

    @Test
    public void testMakingMoveGeneralGame() {
        //starting a general game
        ui.generalGameRadioButton.doClick();
        ui.startButton.doClick();

        //choosing s for blue and o for red
        ui.blueSButton.doClick();
        ui.redOButton.doClick();

        //trying making a move
        JButton gridSpace = (JButton) ui.boardPanel.getComponent(0);
        gridSpace.doClick();

        //checking to make sure the move was made
        assertEquals("S", gridSpace.getText());
    }
}