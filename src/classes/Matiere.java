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
    private Database DB;

    public Matiere() throws SQLException {
        this.DB = new Database();
    }

    // Prendre toutes les matières
    public List<Matiere> getAllCourses() throws SQLException {
        List<Matiere> lists = new ArrayList();
        String query = "SELECT * FROM matiere";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
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

    public void store() throws SQLException {
        String query = "INSERT INTO matiere(code, designation, volume) VALUES(?, ?, ?)";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        statement.setString(1, this.Code);
        statement.setString(2, this.Designation);
        statement.setInt(3, this.Volume);
        statement.executeUpdate();
    }


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
