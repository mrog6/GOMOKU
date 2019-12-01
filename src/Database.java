import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    static final String DATABASE_NAME = "databaseStats.db";
    static final String CONNECTION_URL = "jdbc:sqlite:databases/" + DATABASE_NAME;
    static final String GAME_STATS = "gameStats";
    static final String ID = "id";

    Connection connection;

//    public Database() {
//        getConnection();
//        createStatsTable();
//    }

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

    public void updateContact(int id, GameStats newGameStats) {
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

    public void getConnection() {
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("Successfully connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
