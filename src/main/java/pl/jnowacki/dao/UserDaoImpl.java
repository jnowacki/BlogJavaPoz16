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
                String token = rs.getString("token");
                boolean active = rs.getBoolean("active");

                return new User(id, userName, password, token, active);
            }

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUpDBConnection(ctx, con, rs, stmt);
        }
        return null;
    }

    @Override
    public boolean createUser(String userLogin, String password, String activationToken) {
        Context ctx = null;
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyBlogDB");

            con = ds.getConnection();

            stmt = con.prepareStatement("INSERT INTO users(login, password, token) VALUES (?, ?, ?)");
            stmt.setString(1, userLogin);
            stmt.setString(2, password);
            stmt.setString(3, activationToken);

            return stmt.executeUpdate() == 1;

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUpDBConnection(ctx, con, null, stmt);
        }

        return false;
    }

    @Override
    public boolean activateUser(String activationToken) {
        Context ctx = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyBlogDB");

            con = ds.getConnection();

            stmt = con.prepareStatement("UPDATE users SET active = true WHERE token = ?");
            stmt.setString(1, activationToken);

            return stmt.executeUpdate() == 1;

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUpDBConnection(ctx, con, rs, stmt);
        }

        return false;
    }

    private void cleanUpDBConnection(Context ctx, Connection con, ResultSet rs, PreparedStatement stmt) {
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
}
