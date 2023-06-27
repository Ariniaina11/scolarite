package classes.models;

import classes.Matiere;
import classes.Note;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class NoteModel extends AbstractTableModel {
    private String[] columns = {"MATIERE", "NOTE", "REF"};
    private List<Note> notes;

    public NoteModel(List<Note> notes){
        this.notes = notes;
    }

    @Override
    public int getRowCount() {
        return notes.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 -> notes.get(rowIndex).getMatiere().getDesignation();
            case 1 -> notes.get(rowIndex).getValeur();
            case 2 -> notes.get(rowIndex).getId();
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
}
