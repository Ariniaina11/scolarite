package formes;

import classes.Etudiant;
import classes.Matiere;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddCourse extends JDialog {
    private JPanel contentPane;
    private JButton enregistrerBtn;
    private JButton annulerBtn;
    private JTextField codeTxt;
    private JTextField designationTxt;
    private JSpinner volumeSp;
    private boolean EDIT;
    private Matiere MATIERE;
    private boolean dialogResult;
    private String OldCode;

    public AddCourse(JFrame parent, boolean edit, Matiere mat) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(enregistrerBtn);

        // Set the size and position of the dialog
        setSize(300, 200);
        setTitle("Enregistrement d'une matière");
        setLocationRelativeTo(parent);

        if(edit) {
            initEdit(mat);
        }else{
            init();
        }

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

    private void initEdit(Matiere mat) {
        codeTxt.setText(mat.getCode());
        designationTxt.setText(mat.getDesignation());
        volumeSp.setValue(mat.getVolume());

        MATIERE = mat;
        EDIT = true;
        OldCode = mat.getCode();
    }

    private void init() { EDIT = false; }

    public boolean showDialog() {
        setVisible(true); // Show the dialog
        return dialogResult; // Return the dialog result
    }

    private void onOK() throws SQLException {
        Matiere mat = new Matiere();
        mat.setCode(codeTxt.getText());
        mat.setDesignation(designationTxt.getText());

        short volume = Short.parseShort(volumeSp.getValue().toString());
        mat.setVolume(volume);

        if(EDIT){
            mat.update(OldCode);
        }else{
            mat.store();
        }

        dialogResult = true;
        dispose();
    }

    private void onCancel() {
        dialogResult = false;
        dispose();
    }
}
