import classes.Etudiant;
import classes.Matiere;
import classes.components.ButtonEditor;
import classes.components.ButtonRenderer;
import classes.models.EtudiantModel;
import classes.models.MatiereModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
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
    private JTextField volumeMtTxt;
    private JTable matiereTbl;
    private JLabel countMatLbl;
    private JButton enregistrerMatBtn;
    private JTextField textField1;
    private JButton rechercheButton;
    private JButton METTREAJOURButton;
    private JButton SUPPRIMERButton;
    private JButton nouveauBtn;
    private JButton modifierBtn;
    private JButton supprimerBtn;
    private JTextField rechercheEtdTxt;
    private JButton rechercheEtdBtn;
    private JPanel countLbl;

    //
    static Etudiant ETUDIANT;
    static Matiere MATIERE;
    static boolean EDIT_ETD; // Utilisé pour l'enregistrement d'un étudiant (Nouveau / Mise à jour)
    static int CODE_ETD; // L'étudiant sélectionné

    public MainWindow() throws SQLException {
        init();

        // Clique sur "enregistrer" (Etudiant)
        enregistrerEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enregistrerEtdAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur "enregistrer" (Matière)
        enregistrerMatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enregistrerMatAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        nouveauBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nouveauEtdAction();
            }
        });
        modifierBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierEtdAction();
            }
        });
        supprimerBtn.addActionListener(new ActionListener() {
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
    }

    public static void main(String[] args) throws SQLException {
        MainWindow main = new MainWindow();
        main.setContentPane(main.rootPnl);
        main.setTitle("Scolarité");
        main.setSize(600, 600);
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Initialisation
    private void init() throws SQLException {
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
        nomEtdTxt.setText("");
        prenomEtdTxt.setText("");
        adresseEtdTxt.setText("");
        telephoneEtdTxt.setText("");
        etudiantTbl.clearSelection();

        enregistrerEtdBtn.setEnabled(false);
        modifierBtn.setEnabled(false);
        supprimerBtn.setEnabled(false);

        EDIT_ETD = false;
    }
    private void init_mat_form() {
        codeMtTxt.setText("");
        designationMtTxt.setText("");
        volumeMtTxt.setText("");
        matiereTbl.clearSelection();
    }

    // Ajouter les étudiants sur une table
    private void get_etd_data(List<Etudiant> lists) throws SQLException {
        EtudiantModel model = new EtudiantModel(lists);
        etudiantTbl.setModel(model);
        etudiantTbl.setAutoCreateRowSorter(true);
        etudiantTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        countEtdLbl.setText(model.getRowCount() + " étudiant(s) enregistré(s)");
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

                            nomEtdTxt.setText(etd.getNom());
                            prenomEtdTxt.setText(etd.getPrenom());
                            adresseEtdTxt.setText(etd.getAdresse());
                            telephoneEtdTxt.setText(etd.getTelephone());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        modifierBtn.setEnabled(true);
                        supprimerBtn.setEnabled(true);
                    }
                }
            }
        });
    }

    // Action sur l'enregistrement d'un étudiant
    private void enregistrerEtdAction() throws SQLException {
        Etudiant etd = new Etudiant();
        etd.setNom(nomEtdTxt.getText());
        etd.setPrenom(prenomEtdTxt.getText());
        etd.setAdresse(adresseEtdTxt.getText());
        etd.setTelephone(telephoneEtdTxt.getText());

        if(!EDIT_ETD){
            etd.store();
        }
        else{
            etd.setCode(CODE_ETD);
            etd.update();
        }

        System.out.println("Etudiant enregistré avec succès !");
        init_etd_form();
        get_etd_data(ETUDIANT.getAllStudents());
    }

    // Action sur l'enregistrement d'une matière
    private void enregistrerMatAction() throws SQLException {
        Matiere mat = new Matiere();
        mat.setCode(codeMtTxt.getText());
        mat.setDesignation(designationMtTxt.getText());
        mat.setVolume(Short.parseShort(volumeMtTxt.getText()));
        mat.store();

        System.out.println("Matière ajoutée avec succès !");
        init_mat_form();
        get_mat_data();
    }

    // Action sur "nouveau" étudiant
    private void nouveauEtdAction() {
        etudiantTbl.clearSelection();
        init_etd_form();
        modifierBtn.setEnabled(false);
        supprimerBtn.setEnabled(false);

        EDIT_ETD = false;
        enregistrerEtdBtn.setEnabled(true);
        enregistrerEtdBtn.setText("AJOUTER");
    }

    // Action sur "modifier" étudiant
    private void modifierEtdAction(){
        EDIT_ETD = true;
        enregistrerEtdBtn.setEnabled(true);
        enregistrerEtdBtn.setText("METTRE A JOUR");
    }

    // Action sur "supprimer" étudiant
    private void supprimerEtdAction() throws SQLException {
        Etudiant etd = new Etudiant();
        etd.setCode(CODE_ETD);
        etd.destroy();

        System.out.println("Etudiant supprimé avec succès :)");

        init_etd_form();
        get_etd_data(ETUDIANT.getAllStudents());
    }

    // Action sur la recheche d'un étudiant
    private void rechercheEtdAction() throws SQLException {
        get_etd_data(ETUDIANT.getCustomStudents(rechercheEtdTxt.getText()));
    }
}
