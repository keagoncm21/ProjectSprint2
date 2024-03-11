import javax.swing.*;
import java.awt.*;

//UI for SOS
public class Sprint2UI extends JFrame {
    //UI elements (had private, but switched them all to protected to allow JUnit testing
    protected JComboBox<Integer> sizeComboBox;
    protected JRadioButton blueSButton;
    protected JRadioButton blueOButton;
    protected JRadioButton redSButton;
    protected JRadioButton redOButton;
    protected JLabel blueLabel;
    protected JLabel redLabel;
    protected JPanel boardPanel;
    protected JPanel mainPanel;
    protected JRadioButton simpleGameRadioButton;
    protected JRadioButton generalGameRadioButton;
    protected JButton startButton;
    private Sprint2GameLogic game;

    //helps keep track of the turns
    private boolean isBlueTurn = true;

    //constructor used to initialize UI
    public Sprint2UI() {
        //setting up the JFrame
        setTitle("SOS");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //panel to hold the other components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //panel for game selection
        JPanel gameTypePanel = new JPanel();
        gameTypePanel.setLayout(new FlowLayout());
        ButtonGroup gameTypeGroup = new ButtonGroup();

        //radio buttons for game selection
        simpleGameRadioButton = new JRadioButton("Simple Game");
        simpleGameRadioButton.setSelected(true); //default selection
        generalGameRadioButton = new JRadioButton("General Game");

        //group for the radio buttons
        gameTypeGroup.add(simpleGameRadioButton);
        gameTypeGroup.add(generalGameRadioButton);

        //adding the buttons to the top panel
        gameTypePanel.add(simpleGameRadioButton);
        gameTypePanel.add(generalGameRadioButton);
        mainPanel.add(gameTypePanel, BorderLayout.NORTH);

        //creating panel for player options
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(2, 2));

        //options for blue player
        JPanel bluePlayerPanel = new JPanel();
        bluePlayerPanel.setLayout(new BorderLayout());
        blueLabel = new JLabel("Blue Player (Your Turn)");
        bluePlayerPanel.add(blueLabel, BorderLayout.NORTH);

        //radio buttons for blue player
        blueSButton = new JRadioButton("S");
        blueOButton = new JRadioButton("O");
        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueSButton);
        blueGroup.add(blueOButton);
        bluePlayerPanel.add(blueSButton, BorderLayout.WEST);
        bluePlayerPanel.add(blueOButton, BorderLayout.EAST);

        //options for red player
        JPanel redPlayerPanel = new JPanel();
        redPlayerPanel.setLayout(new BorderLayout());
        redLabel = new JLabel("Red Player");
        redPlayerPanel.add(redLabel, BorderLayout.NORTH);

        //radio buttons for red player (pretty much identical to the blue player)
        redSButton = new JRadioButton("S");
        redOButton = new JRadioButton("O");
        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redSButton);
        redGroup.add(redOButton);
        redPlayerPanel.add(redSButton, BorderLayout.WEST);
        redPlayerPanel.add(redOButton, BorderLayout.EAST);

        //adding both players' panels to the player panel
        playerPanel.add(bluePlayerPanel);
        playerPanel.add(redPlayerPanel);

        //start game button
        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(120, 30));
        startButton.addActionListener(e -> startGame());

        //start button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(startButton, BorderLayout.CENTER);

        //board size panel
        JPanel sizePanel = new JPanel();
        JLabel label = new JLabel("Select Board Size:");
        sizePanel.add(label);

        //basic combo box for board size selection
        //(decided to go with the preset options of 3-8 since it gives a lot of options while still being a reasonable size)
        sizeComboBox = new JComboBox<>();
        sizeComboBox.addItem(3);
        sizeComboBox.addItem(4);
        sizeComboBox.addItem(5);
        sizeComboBox.addItem(6);
        sizeComboBox.addItem(7);
        sizeComboBox.addItem(8);
        sizePanel.add(sizeComboBox);

        //adding all the sub panels to the main panel
        mainPanel.add(sizePanel, BorderLayout.EAST);
        mainPanel.add(playerPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        //once the main panel is put together, it is being added to the frame
        add(mainPanel);
        setVisible(true);
    }

    //start game method
    void startGame() {
        //getting the selected options
        int boardSize = (int) sizeComboBox.getSelectedItem();
        String gameType = ""; //open-ended to allow for more game type logic later on
        String bluePlayerOption = blueSButton.isSelected() ? "S" : "O";
        String redPlayerOption = redSButton.isSelected() ? "S" : "O";

        //starting the game
        game = new Sprint2GameLogic(boardSize, gameType, bluePlayerOption, redPlayerOption);
        displayBoard(boardSize);
    }

    //display method
    private void displayBoard(int size) {
        if (boardPanel != null) {
            mainPanel.remove(boardPanel);
        }
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size * size; i++) {
            JButton button = new JButton();
            button.addActionListener(e -> takeTurn(button));
            boardPanel.add(button);
        }

        //giving the board in the middle a fixed size
        int boardSize = Math.min(300, getSize().height - 100);
        boardPanel.setPreferredSize(new Dimension(boardSize, boardSize));

        //centering the board (grid)
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    //turn method (red v blue)
    protected void takeTurn(JButton button) {
        if (game == null) {
            JOptionPane.showMessageDialog(this, "Please start the game first.");
            return;
        }

        //getting the current symbol (O v S)
        String symbol;
        if (isBlueTurn) {
            symbol = blueSButton.isSelected() ? "S" : "O";
        } else {
            symbol = redSButton.isSelected() ? "S" : "O";
        }

        // Check if the current player has selected their symbol
        if ((isBlueTurn && !blueSButton.isSelected() && !blueOButton.isSelected()) ||
                (!isBlueTurn && !redSButton.isSelected() && !redOButton.isSelected())) {
            JOptionPane.showMessageDialog(this, "Please select 'S' or 'O' for your turn.");
            return;
        }

        //checking to see if a spot is taken on the board/grid
        if (!button.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "This spot is taken. Find a new place.");
            return;
        }


        button.setText(symbol);

        //which turn
        isBlueTurn = !isBlueTurn;

        //calling method to show whose turn it is
        updatePlayerLabels();
    }

    //method which changes the player labels to display whose turn it is
    private void updatePlayerLabels() {
        if (isBlueTurn) {
            blueLabel.setText("Blue Player (Your Turn)");
            redLabel.setText("Red Player");
        } else {
            redLabel.setText("Red Player (Your Turn)");
            blueLabel.setText("Blue Player");
        }
    }


    //method which starts the UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sprint2UI::new);
    }
}
