package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Etudiant {
    private int Code;
    private String Nom;
    private String Prenom;
    private String Adresse;
    private String Telephone;
    private Database DB;

    public Etudiant() throws SQLException {
        this.Code = 0;
        this.DB = new Database();
    }

    public Etudiant getStudentByCode(int code) throws SQLException {
        Etudiant etd = new Etudiant();
        String query = "SELECT * FROM etudiant WHERE code = ?";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        statement.setInt(1, code);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            etd.setCode(resultSet.getInt("code"));
            etd.setNom(resultSet.getString("nom"));
            etd.setPrenom(resultSet.getString("prenom"));
            etd.setAdresse(resultSet.getString("adresse"));
            etd.setTelephone(resultSet.getString("telephone"));
        }

        return etd;
    }

    public List<Etudiant> getAllStudents() throws SQLException {
        List<Etudiant> lists = new ArrayList();
        String query = "SELECT * FROM etudiant";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            Etudiant etd = new Etudiant();
            etd.setCode(resultSet.getInt("code"));
            etd.setNom(resultSet.getString("nom"));
            etd.setPrenom(resultSet.getString("prenom"));
            etd.setAdresse(resultSet.getString("adresse"));
            etd.setTelephone(resultSet.getString("telephone"));
            lists.add(etd);
        }

        return lists;
    }

    public List<Etudiant> getCustomStudents(String pattern) throws SQLException {
        List<Etudiant> lists = new ArrayList();
        String query = "SELECT * FROM etudiant WHERE code LIKE '%" + pattern + "%' " +
                            "OR nom LIKE '%" + pattern + "%' " +
                            "OR prenom LIKE '%" + pattern + "%' " +
                            "OR adresse LIKE '%" + pattern + "%' " +
                            "OR telephone LIKE '%" + pattern + "%'";

        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            Etudiant etd = new Etudiant();
            etd.setCode(resultSet.getInt("code"));
            etd.setNom(resultSet.getString("nom"));
            etd.setPrenom(resultSet.getString("prenom"));
            etd.setAdresse(resultSet.getString("adresse"));
            etd.setTelephone(resultSet.getString("telephone"));
            lists.add(etd);
        }

        return lists;
    }

    public void store() throws SQLException {
        String query = "INSERT INTO etudiant(nom, prenom, adresse, telephone) VALUES(?, ?, ?, ?)";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        statement.setString(1, this.Nom);
        statement.setString(2, this.Prenom);
        statement.setString(3, this.Adresse);
        statement.setString(4, this.Telephone);
        statement.executeUpdate();
    }

    public void update() throws SQLException {
        String query = "UPDATE etudiant SET nom = ?, prenom = ?, adresse = ?, telephone = ? WHERE code = ?";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        statement.setString(1, this.Nom);
        statement.setString(2, this.Prenom);
        statement.setString(3, this.Adresse);
        statement.setString(4, this.Telephone);
        statement.setInt(5, this.Code);
        statement.executeUpdate();
    }

    public void destroy() throws SQLException {
        String query = "DELETE FROM etudiant WHERE code = ?";
        PreparedStatement statement = this.DB.getConnection().prepareStatement(query);
        statement.setInt(1, this.Code);
        statement.executeUpdate();
    }

    public int getCode() {
        return this.Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public String getNom() {
        return this.Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public String getPrenom() {
        return this.Prenom;
    }

    public void setPrenom(String prenom) {
        this.Prenom = prenom;
    }

    public String getAdresse() {
        return this.Adresse;
    }

    public void setAdresse(String adresse) {
        this.Adresse = adresse;
    }

    public String getTelephone() {
        return this.Telephone;
    }

    public void setTelephone(String telephone) {
        this.Telephone = telephone;
    }
}
