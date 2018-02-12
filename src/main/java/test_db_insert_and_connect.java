import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class test_db_insert_and_connect implements test_db_insert_and_connect_DAO {
    private String dbUsername = "";
    private int dbPassword;

    private String posts = "svett";

    /* Set the username as whatever the user types in */
    public void DbUsername(String username) {
        dbUsername = username;
    }

    /* Get the username */
    public String getDbUsername() {
        return dbUsername;
    }

    public void DbPassword(int password) {
        dbPassword = password;
    }

    public int getDbPassword() {
        return dbPassword;
    }

    public String getPosts() {
        return posts;
    }

    private List<test_db_insert_and_connect> fullPost;

    private List<String> fullPostText;

    public test_db_insert_and_connect() { fullPostText = new ArrayList<>();
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
    public void insert(String name, int password) {
        String sql = "INSERT INTO Users(username,password) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertPost(String category, String text) {
        String sql = "insert into Posts(category,text) values (?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, text);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<String> test(){
        String sql = "SELECT Text from Posts";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {

                fullPostText.add(rs.getString("Text"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return fullPostText;
    }
    /**
     * Get a user row from the users table
     *
     * @param name
     * @param password
     */
    public void getUsernameAndPassword(String name, int password) {

        String sql = "SELECT username,password FROM Users WHERE username= ? AND password = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, name);
            pstmt.setInt(2, password);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                DbUsername(rs.getString("username"));
                DbPassword(rs.getInt("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get a user row from the users table
     *
     * @param uName
     */
    public boolean getUsername(String uName) {
        boolean exist = false;
        String sql = "SELECT username FROM Users WHERE username= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, uName);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            if (!rs.next()) {
                exist = false;
            } else {
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
    }


    public List<List<String>> getAllPosts(String kategori) {
        List<List<String>> outer = new ArrayList<List<String>>();
        List<String> inner1 = new ArrayList<String>();
        List<String> inner2 = new ArrayList<String>();

        String sql = "SELECT Category, Text FROM Posts WHERE Category= ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the value
            pstmt.setString(1, kategori);
            //
            ResultSet rs = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                inner1.add(rs.getString("Category"));
                inner2.add(rs.getString("Text"));
            }
            outer.add(inner1);
            outer.add(inner2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return outer;
    }

    @Override
    public boolean add(test_db_insert_and_connect post) {
        return fullPost.add(post);
    }

    @Override
    public List<test_db_insert_and_connect> findAll() {
        return new ArrayList<>(fullPost);
    }
    public List<String> findIt() {
        return new ArrayList<>(fullPostText);
    }
}
