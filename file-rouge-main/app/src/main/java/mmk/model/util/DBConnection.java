package mmk.model.util;

import java.sql.*;

public class DBConnection {

    private static Connection conn = null;

    public static void init() {
        String user = "mmk";
        String password = "!9skSJ6@2a";
        String database = "mmk_test";
        int port = 3306;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://51.68.227.19:"+port+"/"+database, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static PreparedStatement createPreperedStatment(String sql) {
        try {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static int executeInsert(PreparedStatement stmt) {
        try {
            if (stmt.executeUpdate() > 0) {
                ResultSet set = stmt.getGeneratedKeys();
                if (set.next())
                    return set.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState" + ex.getSQLState());
            System.out.println("VendorError" + ex.getErrorCode());
            throw new RuntimeException("Probleme avec la requete");
        }
        return -1;
    }

    public static int executeUpdate(PreparedStatement stmt) {
        try {
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState" + ex.getSQLState());
            System.out.println("VendorError"+ ex.getErrorCode());
            throw new RuntimeException("Probleme avec la requete");
        }
    }

    public static ResultSet executeSelect(String sql) {
        try {
            Statement statement;
            statement = conn.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("SQLException" + ex.getMessage());
            System.out.println("SQLState" + ex.getSQLState());
            System.out.println("VendorError"+ ex.getErrorCode());
            throw new RuntimeException("Probleme avec la requete");
        }
    }

    public static void beginTransaction() {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollBack() {
        try {
            conn.rollback();
            conn.setAutoCommit(true);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
