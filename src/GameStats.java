import java.awt.*;

/**
 * Class GameStats holds the fields that are inserted into the database, such as the
 * wins/losses and player colors.
 *
 * @author Meghan Rogers, Nick Chua, Ewan Akins
 * @see "No Borrowed Code"
 *
 */
public class GameStats {
    private int id;
    private Color oneColor;
    private Color twoColor;
    private String oneName;
    private String twoName;
    private int oneLosses;
    private int twoLosses;
    private int oneWins;
    private int twoWins;

    /**
     * The constructor, initializes the id to -1.
     */
    public GameStats() {
        id = -1;
    }

    /**
     * The EVC for the GameStats class is used to add game stats to the database.
     * @param oneColor the color chosen by player one
     * @param twoColor the color chosen by player two
     * @param oneName the name of player one
     * @param twoName the name of player two (could be "Computer")
     * @param oneLosses how many losses player one has
     * @param twoLosses how many losses player two has
     * @param oneWins how many wins player one has
     * @param twoWins how many wins player two has
     */
    public GameStats(Color oneColor, Color twoColor, String oneName, String twoName,
                     int oneLosses, int twoLosses, int oneWins, int twoWins) {
        this.oneColor = oneColor;
        this.twoColor = twoColor;
        this.oneName = oneName;
        this.twoName = twoName;
        this.oneLosses = oneLosses;
        this.twoLosses = twoLosses;
        this.oneWins = oneWins;
        this.twoWins = twoWins;
    }

    /**
     * toString() returns the id number.
     */
    @Override
    public String toString() {
        return id + " ";
    }

    public Color getOneColor() {
        return oneColor;
    }

    public void setOneColor(Color oneColor) {
        this.oneColor = oneColor;
    }

    public Color getTwoColor() {
        return twoColor;
    }

    public void setTwoColor(Color twoColor) {
        this.twoColor = twoColor;
    }

    public String getOneName() {
        return oneName;
    }

    public void setOneName(String oneName) {
        this.oneName = oneName;
    }

    public String getTwoName() {
        return twoName;
    }

    public void setTwoName(String twoName) {
        this.twoName = twoName;
    }

    public int getOneLosses() {
        return oneLosses;
    }

    public void setOneLosses(int oneLosses) {
        this.oneLosses = oneLosses;
    }

    public int getTwoLosses() {
        return twoLosses;
    }

    public void setTwoLosses(int twoLosses) {
        this.twoLosses = twoLosses;
    }

    public int getOneWins() {
        return oneWins;
    }

    public void setOneWins(int oneWins) {
        this.oneWins = oneWins;
    }

    public int getTwoWins() {
        return twoWins;
    }

    public void setTwoWins(int twoWins) {
        this.twoWins = twoWins;
    }
}
