
import dao.UserDao;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // We create the DAO manually (this is what Spring's @Autowired does later)
        UserDao userDao = new UserDao();

        try {
            // 1. Create
            User newUser = new User("Alice");
            int id = userDao.insert(newUser);
            System.out.println("Inserted user with id=" + id);

            // 2. Read one
            userDao.findById(id).ifPresentOrElse(
                    u -> System.out.println("Found: " + u),
                    () -> System.out.println("Not found")
            );

            // 3. Read all
            List<User> all = userDao.findAll();
            System.out.println("All users:");
            all.forEach(u -> System.out.println("  " + u));

            // 4. Update
            newUser.setName("Alice Updated");
            if (userDao.update(newUser)) {
                System.out.println("Updated: " + userDao.findById(id).orElseThrow());
            }

        } catch (SQLException e) {
            System.err.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}