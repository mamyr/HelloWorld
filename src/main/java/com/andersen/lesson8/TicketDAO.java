package com.andersen.lesson8;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLExceptionSubclassTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class TicketDAO {
    private static Logger logger = Logger.getLogger(String.valueOf(TicketDAO.class));
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

    public Ticket saveTicket(Ticket ticket){
        Connection conn = null;
        Savepoint savepoint = null;
        PreparedStatement st = null;
        try {
            String[] generatedColumns = { "id" };
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            if(ticket.getId() != 0) {
                st = conn.prepareStatement("INSERT INTO public.ticket (id, user_id, ticket_type) VALUES (?,?,?::ticket_type)", generatedColumns);
                st.setObject(3, ticket.getTicketType().toString());
                st.setObject(2, ticket.getUserId());
                st.setObject(1, ticket.getId());
            } else {
                st = conn.prepareStatement("INSERT INTO public.ticket (user_id, ticket_type) VALUES (?,?::ticket_type)", generatedColumns);
                st.setObject(2, ticket.getTicketType().toString());
                st.setObject(1, ticket.getUserId());
            }

            int rowsSaved = st.executeUpdate();

            if (rowsSaved == 0) {
                throw new SQLException("Creating ticket failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setId(generatedKeys.getInt(1));
                    // Commit transaction
                    conn.commit();

                    st.close();
                    return ticket;
                }
                else {
                    throw new SQLException("Creating ticket failed, no ID obtained.");
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

    public boolean updateTicketType(Ticket ticket){
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            PreparedStatement st = conn.prepareStatement("UPDATE public.ticket SET ticket_type=?::ticket_type where id=?");
            st.setObject(1, ticket.getTicketType().toString());
            st.setObject(2, ticket.getId());
            int rowsUpdated = st.executeUpdate();

            // Commit transaction
            conn.commit();

            st.close();
            return rowsUpdated>0;
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

    public Ticket getTicketById(int id){
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            // Turn use of the cursor on.
            PreparedStatement st = conn.prepareStatement("SELECT * FROM public.ticket WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setUserId(rs.getInt(2));
                ticket.setTicketType(Ticket.TicketType.valueOf(rs.getString(3)));
                ticket.setCreationDate(rs.getTimestamp(4));

                // Commit transaction
                conn.commit();

                rs.close();
                st.close();
                return ticket;
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

    public ArrayList<Ticket> getTicketsByUserId(int id){
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            conn = connectDatabase();

            // Create a savepoint
            savepoint = conn.setSavepoint("Savepoint");

            // Turn use of the cursor on.
            PreparedStatement st = conn.prepareStatement("SELECT * FROM public.ticket WHERE user_id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            ArrayList<Ticket> list = new ArrayList<Ticket>();
            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setUserId(rs.getInt(2));
                ticket.setTicketType(Ticket.TicketType.valueOf(rs.getString(3)));
                ticket.setCreationDate(rs.getTimestamp(4));
                list.add(ticket);

            }
            if(list.size()!=0) {
                // Commit transaction
                conn.commit();

                rs.close();
                st.close();
                return list;
            }
            else return null;
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
