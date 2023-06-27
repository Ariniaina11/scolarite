package formes;

import classes.Etudiant;
import classes.Matiere;
import classes.Note;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddNote extends JDialog {
    private JPanel contentPane;
    private JButton enregistrerBtn;
    private JButton annulerBtn;
    private JComboBox matiereCb;
    private JSpinner noteSp;
    private JLabel titleLbl;
    //
    private Matiere MATIERE;
    private Etudiant ETUDIANT;
    private boolean dialogResult;

    public AddNote(JFrame parent, Etudiant etd) throws SQLException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(enregistrerBtn);

        // Set the size and position of the dialog
        setSize(300, 200);
        setTitle("Ajout d'une note");
        setLocationRelativeTo(parent);

        init(etd);

        enregistrerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        annulerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void init(Etudiant etd) throws SQLException {
        MATIERE = new Matiere();
        ETUDIANT = etd;

        titleLbl.setText(etd.getNom() + " " + etd.getPrenom() + " [ " + etd.getCode() + " ]");
        getMatieres();
    }

    public boolean showDialog() {
        setVisible(true); // Show the dialog
        return dialogResult; // Return the dialog result
    }

    private void getMatieres() throws SQLException {
        for (Matiere mat : MATIERE.getAllCourses()) {
            matiereCb.addItem(mat.getCode());
        }
    }

    private void onOK() throws SQLException {
        Note nt = new Note();
        nt.setEtudiant(ETUDIANT);
        nt.setMatiere(MATIERE.getCourseByCode(matiereCb.getSelectedItem().toString()));
        nt.setValeur((Integer)noteSp.getValue());
        nt.store();

        // add your code here
        dialogResult = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dialogResult = false;
        dispose();
    }

}
