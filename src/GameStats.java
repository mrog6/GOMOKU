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

    @Override
    /**
     * toString() returns the id number.
     */
    public String toString() {
        return id + " ";
    }

}
