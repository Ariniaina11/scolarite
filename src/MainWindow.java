import classes.Etudiant;
import classes.Matiere;
import classes.components.ButtonEditor;
import classes.components.ButtonRenderer;
import classes.models.EtudiantModel;
import classes.models.MatiereModel;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import formes.AddStudent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MainWindow extends JFrame {
    private JPanel rootPnl;
    private JTabbedPane tabbedPane1;
    private JTextField nomEtdTxt;
    private JTextField prenomEtdTxt;
    private JTextField adresseEtdTxt;
    private JTextField telephoneEtdTxt;
    private JButton enregistrerEtdBtn;
    private JTable etudiantTbl;
    private JPanel etudiantPnl;
    private JLabel countEtdLbl;
    private JPanel matierePnl;
    private JPanel notePnl;
    private JTextField codeMtTxt;
    private JTextField designationMtTxt;
    private JSpinner volumeMtSp;
    private JTable matiereTbl;
    private JLabel countMatLbl;
    private JButton enregistrerMatBtn;
    private JTextField textField1;
    private JButton rechercheButton;
    private JButton METTREAJOURButton;
    private JButton SUPPRIMERButton;
    private JButton nouveauEtdBtn;
    private JButton modifierEtdBtn;
    private JButton supprimerEtdBtn;
    private JTextField rechercheEtdTxt;
    private JButton rechercheEtdBtn;
    private JTable table1;
    private JTable table2;
    private JButton ajouterUneNoteButton;
    private JButton openBtn;
    private JButton editButton;
    private JPanel countLbl;

    //
    static Etudiant ETUDIANT;
    static Matiere MATIERE;
    static boolean EDIT_ETD; // Utilisé pour l'enregistrement d'un étudiant (Nouveau / Mise à jour)
    static int CODE_ETD; // L'étudiant sélectionné
    static MainWindow THIS;

    public MainWindow() throws SQLException {
        init();

        supprimerEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    supprimerEtdAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        rechercheEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rechercheEtdAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Nouveau étudiant
        nouveauEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent nW = new AddStudent(THIS, false, null);
                boolean rs = nW.showDialog();

                if(rs){
                    try {
                        get_etd_data(ETUDIANT.getAllStudents());
                        init_etd_form();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Modifier un étudiant
        modifierEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Etudiant etd = ETUDIANT.getStudentByCode(CODE_ETD);
                    AddStudent nW = new AddStudent(THIS, true, etd);
                    boolean rs = nW.showDialog();

                    if(rs){
                        get_etd_data(ETUDIANT.getAllStudents());
                        init_etd_form();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        try {
            UIManager.setLookAndFeel(new FlatCobalt2IJTheme());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        MainWindow main = new MainWindow();
        main.setContentPane(main.rootPnl);
        main.setTitle("Scolarité");
        main.setSize(600, 600);
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Initialisation
    private void init() throws SQLException {
        THIS = this;
        ETUDIANT = new Etudiant();
        MATIERE = new Matiere();
        //
        get_etd_data(ETUDIANT.getAllStudents());
        get_mat_data();
        //
        get_etd_selection();
        //
    }

    // Initialisation (Form)
    private void init_etd_form() {
        etudiantTbl.clearSelection();

        modifierEtdBtn.setEnabled(false);
        supprimerEtdBtn.setEnabled(false);

        EDIT_ETD = false;
    }

    // Ajouter les étudiants sur une table
    private void get_etd_data(List<Etudiant> lists) throws SQLException {
        EtudiantModel model = new EtudiantModel(lists);
        etudiantTbl.setModel(model);
        etudiantTbl.setAutoCreateRowSorter(true);
        etudiantTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        countEtdLbl.setText(model.getRowCount() + " ETUDIANT(S)");
    }

    // Ajouter les matières sur une table
    private void get_mat_data() throws SQLException {
        List<Matiere> lists = MATIERE.getAllCourses();

        MatiereModel model = new MatiereModel(lists);
        matiereTbl.setModel(model);
        matiereTbl.setAutoCreateRowSorter(true);
        matiereTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        countMatLbl.setText(model.getRowCount() + " matière(s) enregistré(s)");

        //
        matiereTbl.getColumn("ACTION").setCellRenderer(new ButtonRenderer());
        matiereTbl.getColumn("ACTION").setCellEditor(new ButtonEditor("Luda"));
    }

    // Séléction du table
    private void get_etd_selection() {
        ListSelectionModel sM = etudiantTbl.getSelectionModel();
        sM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = etudiantTbl.getSelectedRow();
                    if (selectedRow != -1) {
                        CODE_ETD = (int)etudiantTbl.getValueAt(selectedRow, 0);
                        try {
                            Etudiant etd = ETUDIANT.getStudentByCode(CODE_ETD);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        modifierEtdBtn.setEnabled(true);
                        supprimerEtdBtn.setEnabled(true);
                    }
                }
            }
        });
    }

    // Action sur "supprimer" étudiant
    private void supprimerEtdAction() throws SQLException {
        Etudiant etd = new Etudiant();
        etd.setCode(CODE_ETD);
        int dialog = JOptionPane.showConfirmDialog(
                THIS,
                "Voulez-vous vraiment supprimer cet étudiant [code : " + CODE_ETD + "] ?",
                "Suppréssion",
                JOptionPane.YES_NO_OPTION
        );

        if(dialog == 0){
            etd.destroy();

            System.out.println("Etudiant supprimé avec succès :)");

            init_etd_form();
            get_etd_data(ETUDIANT.getAllStudents());
        }
    }

    // Action sur la recheche d'un étudiant
    private void rechercheEtdAction() throws SQLException {
        get_etd_data(ETUDIANT.getCustomStudents(rechercheEtdTxt.getText()));
    }
}
