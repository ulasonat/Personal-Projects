package yl9i70;

import java.sql.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/** This class will store the highscore in a mySQL database.*/
public class Database {

    private Map<String, Integer> highscores;
    private Connection connection;
    private Statement statement;

    public Database() {
        highscores = new HashMap<>();
        try {
            // Connecting to the database
            connect();

            // Creating a statement
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Inserts data to the database. */
    public void insertData(String name, int score) {
        try {
            String sql = "insert into highscores "
                    + " (name, score)"
                    + " values ('" + name + "', " + score + ")";

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Exception occurred.");
        }
    }

    /** Updates the HashMap object that this class have, also provides that it can contain maximum 10 data and
     * they will be in reverse order. */
    public void loadData() {
        try {
            HashMap<String, Integer> tmp = new HashMap<>();
            ResultSet resultSet = statement.executeQuery("select * from highscores");

            // Processing the result set
            while (resultSet.next()) {
                tmp.put(resultSet.getString("name"), resultSet.getInt("score"));
            }

            highscores = tmp.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .limit(10)
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        } catch (SQLException e) {
            System.out.println("Exception occurred.");
        }
    }

    /** Connects to the database. */
    public void connect() throws Exception {

        if(connection != null)
            return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("Driver not found!");
        }

        String url = "jdbc:mysql://127.0.0.1:3306/data?autoReconnect=true&useSSL=false";
        connection = DriverManager.getConnection(url, "root", "pass1234");

        System.out.println("Connected!" + connection);
    }

    /** Disconnects from the database. */
    public void disconnect() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error while closing the connection");
            }
        }
    }

    public Map<String, Integer> getHighscores() {
        return highscores;
    }
}
