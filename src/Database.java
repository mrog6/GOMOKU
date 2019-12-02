import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Class Database contains all of the necessary methods to save game stats into
 * a database. It can create a table, insert data into a table, delete data from
 * a table, read data from a table, and update the data in a table.
 *
 * @author Meghan Rogers, Nick Chua, Ewan Akins
 * @see "No Borrowed Code"
 *
 */
public class Database {
    static final String DATABASE_NAME = "databaseStats.db";
    static final String CONNECTION_URL = "jdbc:sqlite:databases/" + DATABASE_NAME;
    static final String GAME_STATS = "gameStats";
    static final String ID = "id";

    Connection connection;

    /**
     * The constructor gets the connection to the database and then calls
     * createStatsTable().
     */
//    public Database() {
//        getConnection();
//        createStatsTable();
//    }

    /**
     * Creates a table that holds game stats. The stats include player wins/losses as well as
     * player colors and symbols.
     */
    public void createStatsTable() {
        String sqlCreate = "CREATE TABLE " + GAME_STATS + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ")";
        System.out.println(sqlCreate);
        if (connection != null && !tableExists()) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(sqlCreate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inserts specific game stats into the database.
     * @param gameStats the game stats that are to be saved
     */
    public void insertStats(GameStats gameStats) {
        String sqlInsert = "INSERT INTO " + GAME_STATS + " VALUES(null, '" +
//                gameStats.getName() + "', '" +
                "')";
        System.out.println(sqlInsert);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(sqlInsert);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads all of the game stats from the database.
     * @return ArrayList statsList, a list of the game stats from the database
     */
    public List<GameStats> getAllContactsList() {
        List<GameStats> statsList = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + GAME_STATS;
        System.out.println(sqlSelect);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlSelect);
                while (resultSet.next()) {
                    int id = resultSet.getInt(ID);
                    GameStats gameStats = new GameStats();
                    statsList.add(gameStats);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statsList;
    }

    /**
     * Updates the game stats for each player when necessary.
     * @param id the location of the row to be updated in the database
     * @param newGameStats the game stats that are to be saved
     */
    public void updateGameStats(int id, GameStats newGameStats) {
        // update record with id to have new info stored in newContact
        // UPDATE tableContacts SET name='SPIKE', phoneNumber='208-208-2082' WHERE id=1
        String sqlUpdate = "UPDATE " + GAME_STATS + " SET " +
                "' WHERE " + ID + "=" + id;
        System.out.println(sqlUpdate);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(sqlUpdate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the game stats from the database table.
     */
    public void deleteAllStats() {
        String sqlDelete = "DELETE FROM " + GAME_STATS;
        System.out.println(sqlDelete);
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(sqlDelete);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtains a connection to the database via the connection url.
     */
    public void getConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks for the existence of a database table.
     * @return boolean hasNext, true if the table already exists, false otherwise
     */
    private boolean tableExists() {
        DatabaseMetaData md = null;
        boolean hasNext = false;
        try {
            md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, GAME_STATS, null);
            hasNext = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasNext;
    }
}
