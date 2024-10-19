package data.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {
    private int id;
    private String name;
    public static final String COLUMN_ID = "genre_id";
    public static final String COLUMN_NAME = "name";

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private Genre() {
    }

    public static Genre fromResultSet(ResultSet rs) throws SQLException {
        return new Genre()
                .setId(rs.getInt(COLUMN_ID))
                .setName(rs.getString(COLUMN_NAME));

    }

    public int getId() {
        return id;
    }

    public Genre setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }
}
