package main.org.example.daoBasic;

import main.org.example.exception.OwnException;
import main.org.example.jdbcUtil.JdbcUtil;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcExample {

    private static final String CREATE_TABLE_SQL = "create table data(name varchar(255));";
    private static final String INSERT_SQL = "insert into data(name) values(?)";
    private static final String SELECT_ALL_FROM_DB = "select * from data";

    private static DataSource dataSource;

    public static void main(String[] args) {
        init();
        createNewTable();
        insertDataIntoDB();
        printDataFromDB();
    }

    private static void init(){
        dataSource = JdbcUtil.createDefaultInMemoryH2DataSource();
    }

    private static void createNewTable(){
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new OwnException("can't create a table into DB");
        }
    }

    private static void insertDataIntoDB(){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            insertValue(preparedStatement);
        } catch (SQLException e) {
            throw new OwnException("can't insert data into DB");
        }
    }

    private static void insertValue(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, "Alexandr");
        preparedStatement.executeUpdate();
    }

    private static void printDataFromDB(){
        try(Connection connection = dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_FROM_DB);
            printAllData(resultSet);
        } catch (SQLException e) {
            throw new OwnException("can't print data from DB");
        }
    }

    private static void printAllData(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            String text = resultSet.getString(1);
            System.out.println("\"" + text + "\"");
        }
    }
}
