package by.krukouski;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class FirstConnection {

    public static final String QUERY = " SELECT * FROM cab WHERE capacity = ?";

    public static void main(String[] args) {

        Logger log = LogManager.getRootLogger();

        try (Connection conn = getConnection(); CallableStatement stmt = conn.prepareCall("CALL getAllShifts")) {

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    log.info(rs.getString("id"));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try(InputStream inputStream = new FileInputStream("src/main/resources/jdbc.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(props.getProperty("jdbc.url"),
                props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
    }

}
