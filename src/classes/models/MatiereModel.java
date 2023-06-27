package classes.models;

import classes.Matiere;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MatiereModel extends AbstractTableModel {
    private String[] columns = {"CODE", "DESIGNATION", "VOLUME"};
    private List<Matiere> matieres;

    public MatiereModel(List<Matiere> matieres){
        this.matieres = matieres;
    }

    @Override
    public int getRowCount() {
        return matieres.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 -> matieres.get(rowIndex).getCode();
            case 1 -> matieres.get(rowIndex).getDesignation();
            case 2 -> matieres.get(rowIndex).getVolume();
            default -> "-";
        };
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(getRowCount() > 0){
            if(getValueAt(0, columnIndex) != null){
                return getValueAt(0, columnIndex).getClass();
            }else{
                return Object.class;
            }
        }else{
            return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }
}
