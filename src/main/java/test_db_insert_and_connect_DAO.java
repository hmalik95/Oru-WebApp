import java.util.List;

public interface test_db_insert_and_connect_DAO {
    boolean add(test_db_insert_and_connect post);

    List<test_db_insert_and_connect> findAll();
}
