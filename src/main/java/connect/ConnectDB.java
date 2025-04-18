package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static ConnectDB instance;
    private static Connection connection;

    public static ConnectDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectDB();
        }
            return instance;

    }
        public void connectDataBase() throws SQLException{
            try{
                String url = "jdbc:sqlserver://localhost:1433;databasename=QLyNhaThuoc;encrypt=false";
                String userName = "sa";
                String passWord = "sapassword";
                connection = DriverManager.getConnection(url, userName,passWord );
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    public static  Connection getConnection(){
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
