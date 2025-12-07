package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SectionDAO {

    private static final String SQL_FIND =
        "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";

    private final DAOFactory daoFactory;

    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String find(int termid, String subjectid, String num) {

        String jsonOutput = "[]";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND)) {

            if (conn.isValid(0)) {

                stmt.setInt(1, termid);
                stmt.setString(2, subjectid);
                stmt.setString(3, num);

                try (ResultSet rs = stmt.executeQuery()) {

                    // Convert result set → JSON using helper
                    jsonOutput = DAOUtility.getResultSetAsJson(rs);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return jsonOutput;
    }

}
