package formes;

import classes.Etudiant;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nomTxt;
    private JTextField prenomTxt;
    private JTextField adresseTxt;
    private JTextField telephoneTxt;

    //
    private boolean dialogResult;
    private boolean EDIT;
    private Etudiant ETUDIANT;

    public AddStudent(JFrame parent, boolean edit, Etudiant etd) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Set the size and position of the dialog
        setSize(500, 200);
        setTitle("Enregistrement d'un étudiant");
        setLocationRelativeTo(parent);

        if(edit) {
            initEdit(etd);
        }else{
            init();
        }

        // Clique sur 'Enregistrer'
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur 'Annuler'
        buttonCancel.addActionListener(new ActionListener() {
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

    // Initialisation
    private void initEdit(Etudiant etd){
        nomTxt.setText(etd.getNom());
        prenomTxt.setText(etd.getPrenom());
        adresseTxt.setText(etd.getAdresse());
        telephoneTxt.setText(etd.getTelephone());

        ETUDIANT = etd;
        EDIT = true;
    }

    private void init(){
        EDIT = false;
    }

    public boolean showDialog() {
        setVisible(true); // Show the dialog
        return dialogResult; // Return the dialog result
    }

    private void onOK() throws SQLException {
        Etudiant etd = new Etudiant();
        etd.setNom(nomTxt.getText());
        etd.setPrenom(prenomTxt.getText());
        etd.setAdresse(adresseTxt.getText());
        etd.setTelephone(telephoneTxt.getText());

        if(EDIT){
            etd.setCode(ETUDIANT.getCode());
            etd.update();
        }else{
            etd.store();
        }

        dialogResult = true;
        dispose();
    }

    private void onCancel() {
        dialogResult = false;
        dispose();
    }
}
