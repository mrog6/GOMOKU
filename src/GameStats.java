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
    private String color;
    private String name;
    private int losses;
    private int wins;

    /**
     * The constructor, initializes the id to -1.
     */
    public GameStats() {
        id = -1;
    }

    /**
     * The EVC for the GameStats class is used to add game stats to the database.
     * @param color the color chosen by the player
     * @param name the name of the player
     * @param losses how many losses the player has
     * @param wins how many wins the player has
     */
    public GameStats(String name, String color, int losses, int wins) {
        this.color = color;
        this.name = name;
        this.losses = losses;
        this.wins = wins;
    }

    /**
     * toString() returns the id number.
     */
    @Override
    public String toString() {
        return id + " ";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
