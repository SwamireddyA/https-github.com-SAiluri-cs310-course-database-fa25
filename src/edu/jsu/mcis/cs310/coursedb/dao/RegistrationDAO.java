package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class RegistrationDAO {

    private final DAOFactory daoFactory;

    /* SQL statements used for the registration operations */
    private static final String SQL_INSERT =
        "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";

    private static final String SQL_DELETE_SINGLE =
        "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";

    private static final String SQL_DELETE_ALL =
        "DELETE FROM registration WHERE studentid = ? AND termid = ?";

    private static final String SQL_LIST =
        "SELECT studentid, termid, crn FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";

    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /* -------------------------------------------------------------
       Create one registration row
       ------------------------------------------------------------- */
    public boolean create(int studentid, int termid, int crn) {

        boolean success = false;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            if (conn.isValid(0)) {

                stmt.setInt(1, studentid);
                stmt.setInt(2, termid);
                stmt.setInt(3, crn);

                success = (stmt.executeUpdate() == 1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /* -------------------------------------------------------------
       Delete one specific registration
       ------------------------------------------------------------- */
    public boolean delete(int studentid, int termid, int crn) {

        boolean success = false;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_SINGLE)) {

            if (conn.isValid(0)) {

                stmt.setInt(1, studentid);
                stmt.setInt(2, termid);
                stmt.setInt(3, crn);

                success = (stmt.executeUpdate() > 0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /* -------------------------------------------------------------
       Delete ALL registrations for student+term
       ------------------------------------------------------------- */
    public boolean delete(int studentid, int termid) {

        boolean success = false;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_ALL)) {

            if (conn.isValid(0)) {

                stmt.setInt(1, studentid);
                stmt.setInt(2, termid);

                success = (stmt.executeUpdate() > 0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    /* -------------------------------------------------------------
       List all registrations as JSON
       ------------------------------------------------------------- */
    public String list(int studentid, int termid) {

        String jsonResult = "[]";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LIST)) {

            if (conn.isValid(0)) {

                stmt.setInt(1, studentid);
                stmt.setInt(2, termid);

                try (ResultSet rs = stmt.executeQuery()) {

                    // Convert result set → JSON array
                    jsonResult = DAOUtility.getResultSetAsJson(rs);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResult;
    }

}
