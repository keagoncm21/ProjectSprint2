//game logic for SOS game
//once the project progresses, this will be a more active file
public class Sprint2GameLogic {
    //setting up game attributes
    private int boardSize;
    private String gameType;
    private String bluePlayerOption;
    private String redPlayerOption;

    //initializing the game attributes
    public Sprint2GameLogic(int boardSize, String gameType, String bluePlayerOption, String redPlayerOption) {
        this.boardSize = boardSize;
        this.gameType = gameType;
        this.bluePlayerOption = bluePlayerOption;
        this.redPlayerOption = redPlayerOption;
    }

    //methods regarding game attributes (not really using these atm)
    public String getBluePlayerSymbol() {
        return bluePlayerOption;
    }

    public String getRedPlayerSymbol() {
        return redPlayerOption;
    }
}