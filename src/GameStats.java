/**
 * Class GameStats holds the fields that are inserted into the database.
 * Fields include...
 *
 * @author Meghan Rogers, Nick Chua, Ewan Akins
 * @see "No Borrowed Code"
 *
 */
public class GameStats {
    private int id;

    /**
     * The constructor, initializes the id to -1.
     */
    public GameStats() {
        id = -1;
    }

    /**
     * toString() returns the id number.
     */
    @Override
    public String toString() {
        return id + " ";
    }

}
