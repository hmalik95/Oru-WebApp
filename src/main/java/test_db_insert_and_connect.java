import java.sql.*;

public class test_db_insert_and_connect {
    private String dbUsername = "";
    private String dbPassword = "";

    /* Set the username as whatever the user types in */
    public void DbUsername(String username)
    {
        dbUsername = username;
    }
    /* Get the username */
    public String getDbUsername()
    {
        return dbUsername;
    }
    public void DbPassword(String password)
    {
        dbPassword = password;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/svettsvett.sqlite";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the warehouses table
     *
     * @param name
     * @param password
     */
    public void insert(String name, String password) {
        String sql = "INSERT INTO Users(username,password) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get a user row from the users table
     *
     * @param name
     * @param password
     */
    public void getUsernameAndPassword(String name, String password)
    {

         String sql = "SELECT username,password FROM Users WHERE username= ? AND password = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                DbUsername(rs.getString("username"));
                DbPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Get a user row from the users table
     *
     * @param uName
     *
     */
    public boolean getUsername(String uName)
    {
        boolean exist = false;
        String sql = "SELECT username FROM Users WHERE username= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, uName);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            if(!rs.next())
            {
                exist = false;
            }
            else
            {
                exist = true;
            }
            /*while (rs.next()) {
                DbUsername(rs.getString("username"));
            }*/


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exist;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       // test_db_insert_and_connect app = new test_db_insert_and_connect();
        // insert three new rows
        /*app.insert("Raw-Materials", "gunnar");
        app.insert("Semifinished-Goods", "Svett123");
        app.insert("Finished-Goods", "Pooping123");
        */
    }
}
