package pl.jnowacki.dao;

import pl.jnowacki.model.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class UserDaoImpl implements UserDao {

    @Override
    public User getUser(String userLogin) {

        Context ctx = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyBlogDB");

            con = ds.getConnection();

            stmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            stmt.setString(1, userLogin);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("id");
                String userName = rs.getString("login");
                String password = rs.getString("password");

                return new User(id, userName, password);
            }

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
                if (ctx != null) {
                    ctx.close();
                }
            } catch (SQLException e) {
                System.out.println("Exception in closing DB resources");
            } catch (NamingException e) {
                System.out.println("Exception in closing Context");
            }
        }
        return null;
    }
}
