package classes.models;

import classes.Etudiant;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EtudiantModel extends AbstractTableModel {
    // Les en-têtes
    private String[] columns = {"CODE", "NOM", "PRENOMS", "ADRESSE", "TELEPHONE"};
    private List<Etudiant> etudiants;

    public EtudiantModel(List<Etudiant> etudiants){
        this.etudiants = etudiants;
    }

    @Override
    public int getRowCount() {
        return etudiants.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 -> etudiants.get(rowIndex).getCode();
            case 1 -> etudiants.get(rowIndex).getNom();
            case 2 -> etudiants.get(rowIndex).getPrenom();
            case 3 -> etudiants.get(rowIndex).getAdresse();
            case 4 -> etudiants.get(rowIndex).getTelephone();
            default -> "-";
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(getValueAt(0, columnIndex) != null){
            return getValueAt(0, columnIndex).getClass();
        }else{
            return Object.class;
        }
    }
}
