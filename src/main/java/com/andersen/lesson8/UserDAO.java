package com.andersen.lesson8;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLExceptionSubclassTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Logger;

public class UserDAO {
    private static Logger logger = Logger.getLogger(String.valueOf(UserDAO.class));
    private SQLExceptionTranslator sqlExceptionTranslator = new SQLExceptionSubclassTranslator();

    public Connection connectDatabase(){
        String url = "jdbc:postgresql://localhost:5432/my_ticket_service_db?user=postgres&password=postgres";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e){
            logger.severe(e.getMessage());
            throw Objects.requireNonNull(sqlExceptionTranslator.translate("<WHICH_TASK>", "<WHICH_SQL_QUERY>", e));
        }
    }

    public User saveUser(User user){
        Connection conn = null;
        Savepoint savepoint = null;
        PreparedStatement st = null;
        try {
            String[] generatedColumns = { "id" };
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            if(user.getId()!=0) {
                st = conn.prepareStatement("INSERT INTO public.user (id, name) VALUES (?, ?);", generatedColumns);
                st.setObject(1, user.getId());
                st.setObject(2, user.getName());
            } else {
                st = conn.prepareStatement("INSERT INTO public.user (name) VALUES (?);", generatedColumns);
                st.setObject(1, user.getName());
            }
            int rowsSaved = st.executeUpdate();
            if (rowsSaved == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    // Commit transaction
                    conn.commit();

                    st.close();
                    return user;
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e){
            logger.severe(e.getMessage());
            try {
                if (conn != null && savepoint != null)
                    conn.rollback(savepoint); // Rollback to savepoint
                conn.commit();
                st.close();
                return null;
            } catch (SQLException ex) {
                //Rollback exception
                logger.severe(ex.getMessage());
            }
            throw Objects.requireNonNull(sqlExceptionTranslator.translate("<WHICH_TASK>", "<WHICH_SQL_QUERY>", e));
        }
    }

    public boolean deleteUser(User user){
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            PreparedStatement st = conn.prepareStatement("DELETE FROM public.ticket WHERE user_id = ?; DELETE FROM public.user WHERE id = ?;");
            st.setObject(1, user.getId());
            st.setObject(2, user.getId());
            int rowsDeleted = st.executeUpdate();

            // Commit transaction
            conn.commit();

            st.close();
            return rowsDeleted > 0;
        } catch (SQLException e){
            try {
                if (conn != null && savepoint != null)
                    conn.rollback(savepoint); // Rollback to savepoint
                conn.commit();
            } catch (SQLException ex) {
                //Rollback exception
                logger.severe(ex.getMessage());
            }
            logger.severe(e.getMessage());
            throw Objects.requireNonNull(sqlExceptionTranslator.translate("<WHICH_TASK>", "<WHICH_SQL_QUERY>", e));
        }
    }

    public User getUserById(int id){
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            // Turn use of the cursor on.
            PreparedStatement st = conn.prepareStatement("SELECT * FROM public.user WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(3));
                user.setCreationDate(rs.getTimestamp(2));

                // Commit transaction
                conn.commit();

                rs.close();
                st.close();
                return user;
            } else return null;
        } catch (SQLException e){
            try {
                if (conn != null && savepoint != null)
                    conn.rollback(savepoint); // Rollback to savepoint
                conn.commit();
            } catch (SQLException ex) {
                //Rollback exception
                logger.severe(ex.getMessage());
            }
            logger.severe(e.getMessage());
            throw Objects.requireNonNull(sqlExceptionTranslator.translate("<WHICH_TASK>", "<WHICH_SQL_QUERY>", e));
        }
    }
}
