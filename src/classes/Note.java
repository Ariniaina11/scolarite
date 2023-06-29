package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Note {
    private int Id;
    private Matiere Matiere;
    private Etudiant Etudiant;
    private float Valeur;

    public Note() {
        this.Id = 0;
        this.Matiere = new Matiere();
        this.Etudiant = new Etudiant();
        this.Valeur = 0;
    }

    public List<Note> getStudentNote() throws SQLException {
        List<Note> lists = new ArrayList<>();
        String query = "SELECT * FROM note WHERE code_etudiant = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setInt(1, this.Etudiant.getCode());

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Note nt = new Note();
            Etudiant etd = (new Etudiant()).getStudentByCode(resultSet.getInt("code_etudiant"));
            Matiere mat = (new Matiere()).getCourseByCode(resultSet.getString("code_matiere"));

            nt.setId(resultSet.getInt("id"));
            nt.setEtudiant(etd);
            nt.setMatiere(mat);
            nt.setValeur(resultSet.getFloat("valeur"));

            lists.add(nt);
        }

        return lists;
    }

    public float getStudentAverage() throws SQLException {
        float moyenne = 0;
        String query = "SELECT avg(valeur) AS moyenne FROM note WHERE code_etudiant = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setInt(1, this.Etudiant.getCode());

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            moyenne = resultSet.getFloat("moyenne");
        }

        return moyenne;
    }

    public void store() throws SQLException {
        String query = "INSERT INTO note(code_matiere, code_etudiant, valeur) VALUES(?, ?, ?)";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, this.Matiere.getCode());
        statement.setInt(2, this.Etudiant.getCode());
        statement.setFloat(3, this.Valeur);
        statement.executeUpdate();
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Matiere getMatiere() {
        return this.Matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.Matiere = matiere;
    }

    public Etudiant getEtudiant() {
        return this.Etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.Etudiant = etudiant;
    }

    public float getValeur() {
        return this.Valeur;
    }

    public void setValeur(float valeur) {
        this.Valeur = valeur;
    }
}
