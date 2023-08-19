package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Matiere {
    private String Code;
    private String Designation;
    private short Volume;

    public Matiere() {
        this.Code = "-";
        this.Designation = "-";
        this.Volume = 0;
    }

    // Prendre toutes les matières
    public List<Matiere> getAllCourses() throws SQLException {
        List<Matiere> lists = new ArrayList();
        String query = "SELECT * FROM matiere ORDER BY orderClm";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            Matiere mat = new Matiere();
            mat.setCode(resultSet.getString("code"));
            mat.setDesignation(resultSet.getString("designation"));
            mat.setVolume(resultSet.getShort("volume"));
            lists.add(mat);
        }

        return lists;
    }

    // Prendre un matière personnalisé
    public List<Matiere> getCustomCourses(String pattern) throws SQLException {
        List<Matiere> lists = new ArrayList();
        String query = "SELECT * FROM matiere WHERE code LIKE '%" + pattern + "%' " +
                "OR designation LIKE '%" + pattern + "%' " +
                "OR volume LIKE '%" + pattern + "%' ORDER BY orderClm";

        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            Matiere mat = new Matiere();
            mat.setCode(resultSet.getString("code"));
            mat.setDesignation(resultSet.getString("designation"));
            mat.setVolume(resultSet.getShort("volume"));
            lists.add(mat);
        }

        return lists;
    }

    // Prendre une matière sachant son code
    public Matiere getCourseByCode(String code) throws SQLException {
        Matiere mat = new Matiere();
        String query = "SELECT * FROM matiere WHERE code = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, code);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            mat.setCode(resultSet.getString("code"));
            mat.setDesignation(resultSet.getString("designation"));
            mat.setVolume(resultSet.getShort("volume"));
        }

        return mat;
    }

    // Enregistrer un matière
    public void store() throws SQLException {
        String query = "INSERT INTO matiere(code, designation, volume) VALUES(?, ?, ?)";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, this.Code);
        statement.setString(2, this.Designation);
        statement.setInt(3, this.Volume);
        statement.executeUpdate();
    }

    // Mettre à jour un matière
    public void update(String OldCode) throws SQLException {
        String query = "UPDATE matiere SET code = ?, designation = ?, volume = ? WHERE code = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, this.Code);
        statement.setString(2, this.Designation);
        statement.setShort(3, this.Volume);
        statement.setString(4, OldCode);
        statement.executeUpdate();
    }

    // Supprimer un matière
    public void destroy() throws SQLException {
        String query = "DELETE FROM matiere WHERE code = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, this.Code);
        statement.executeUpdate();
    }

    // ================ GETTES & SETTERS ================ //

    public String getCode() {
        return this.Code;
    }

    public void setCode(String code) {
        this.Code = code;
    }

    public String getDesignation() {
        return this.Designation;
    }

    public void setDesignation(String designation) {
        this.Designation = designation;
    }

    public short getVolume() {
        return this.Volume;
    }

    public void setVolume(short volume) {
        this.Volume = volume;
    }
}
