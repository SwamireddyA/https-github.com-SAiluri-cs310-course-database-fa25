package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {

    public static final int TERMID_FA24 = 1;

    @SuppressWarnings("unchecked")
    public static String getResultSetAsJson(ResultSet rs) {

        JsonArray resultArray = new JsonArray();

        try {

            if (rs == null) {
                return Jsoner.serialize(resultArray);
            }

            ResultSetMetaData meta = rs.getMetaData();
            int columnTotal = meta.getColumnCount();

            // Move through each row in the ResultSet
            while (rs.next()) {

                JsonObject rowObject = new JsonObject();

                // Loop through each column for the row
                for (int col = 1; col <= columnTotal; col++) {

                    String key = meta.getColumnLabel(col);  // safer than getColumnName()
                    Object value = rs.getObject(col);

                    // Store values as JSON-safe strings or null
                    rowObject.put(key, (value != null ? value.toString() : null));
                }

                // Add row JSON to final array
                resultArray.add(rowObject);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return Jsoner.serialize(resultArray);
    }
}
