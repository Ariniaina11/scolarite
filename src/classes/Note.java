package classes;

public class Note {
    private int Id;
    private Matiere Matiere;
    private Etudiant Etudiant;
    private float Valeur;

    public Note() {
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
