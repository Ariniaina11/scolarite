import classes.Etudiant;
import classes.Matiere;
import classes.components.ButtonEditor;
import classes.components.ButtonRenderer;
import classes.models.EtudiantModel;
import classes.models.MatiereModel;

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
    private JTextField volumeMtTxt;
    private JTable matiereTbl;
    private JLabel countMatLbl;
    private JButton enregistrerMatBtn;
    private JTextField textField1;
    private JButton rechercheButton;
    private JButton METTREAJOURButton;
    private JButton SUPPRIMERButton;
    private JPanel countLbl;

    //
    static Etudiant ETUDIANT;
    static Matiere MATIERE;

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
        get_etd_data();
        get_mat_data();
        //
        get_etd_selection();
    }

    // Initialisation (Form)
    private void init_etd_form() {
        nomEtdTxt.setText("");
        prenomEtdTxt.setText("");
        adresseEtdTxt.setText("");
        telephoneEtdTxt.setText("");
        etudiantTbl.clearSelection();
    }
    private void init_mat_form() {
        codeMtTxt.setText("");
        designationMtTxt.setText("");
        volumeMtTxt.setText("");
        matiereTbl.clearSelection();
    }

    // Ajouter les étudiants sur une table
    private void get_etd_data() throws SQLException {
        List<Etudiant> lists = ETUDIANT.getAllStudents();

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
                        int code = (int)etudiantTbl.getValueAt(selectedRow, 0);
                        try {
                            Etudiant etd = ETUDIANT.getStudentByCode(code);

                            nomEtdTxt.setText(etd.getNom());
                            prenomEtdTxt.setText(etd.getPrenom());
                            adresseEtdTxt.setText(etd.getAdresse());
                            telephoneEtdTxt.setText(etd.getTelephone());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
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
        etd.store();

        System.out.println("Etudiant ajouté avec succès !");
        init_etd_form();
        get_etd_data();
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
}
