import classes.Database;
import classes.Etudiant;
import classes.Matiere;
import classes.Note;
import classes.models.EtudiantModel;
import classes.models.MatiereModel;
import classes.models.NoteModel;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkMediumIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatVuesionIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import formes.AddCourse;
import formes.AddNote;
import formes.AddStudent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private JTable matiereTbl;
    private JLabel countMatLbl;
    private JTextField rechercheMatTxt;
    private JButton rechercheMatBtn;
    private JButton nouveauEtdBtn;
    private JButton modifierEtdBtn;
    private JButton supprimerEtdBtn;
    private JTextField rechercheEtdTxt;
    private JButton rechercheEtdBtn;
    private JTable etudiantNtTbl;
    private JTable noteTbl;
    private JButton ajouterBtn;
    private JButton nouvelleMatBtn;
    private JButton modifierMatBtn;
    private JButton supprimerMatBtn;
    private JTextField rechercheNtTxt;
    private JButton rechercheNtBtn;
    private JLabel avgLbl;
    private JLabel titleNtLbl;
    private JPanel averageLbl;
    private JButton openBtn;
    private JButton editButton;
    private JPanel countLbl;

    //
    static Etudiant ETUDIANT;
    static Matiere MATIERE;
    static Note NOTE;
    static int CODE_ETD, CODE_ETD_NT; // L'étudiant sélectionné
    static String CODE_MAT; // Matière sélectionée
    static MainWindow THIS;
    static Database DATABASE;


    public MainWindow() throws SQLException {
        init();

        // Clique sur un bouton "supprimer" un étudiant
        supprimerEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteStudentAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "rechercher" un étudiant
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

        // Clique sur un bouton "nouveau" étudiant
        nouveauEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newStudentAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "modifier" étudiant
        modifierEtdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editStudentAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "nouveau" matière
        nouvelleMatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newCourseAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "modifier" un matière
        modifierMatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editCourseAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "supprimer" un matière
        supprimerMatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteCourseAction();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Clique sur un bouton "rechercher" un matière
        rechercheMatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rechercheMatAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "ajouter" une note
        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addNoteAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Clique sur un bouton "rechercher" un étudiant (Note)
        rechercheNtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rechercheEtdNtAction();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) throws SQLException {
        try {
            UIManager.setLookAndFeel(new FlatVuesionIJTheme());
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
        DATABASE = new Database();
        //
        THIS = this;
        ETUDIANT = new Etudiant();
        MATIERE = new Matiere();
        NOTE = new Note();
        //
        get_etd_data(ETUDIANT.getAllStudents());
        get_mat_data(MATIERE.getAllCourses());
        get_etdNt_data(ETUDIANT.getAllStudents());
        //
        get_etd_selection();
        get_mat_selection();
        get_etdNt_selection();
        //
    }

    // ========================== INITIALISATIONS ========================== //

    // Initialisation (Form de l'étudiant)
    private void init_etd() {
        etudiantTbl.clearSelection();

        modifierEtdBtn.setEnabled(false);
        supprimerEtdBtn.setEnabled(false);
    }

    // Initialisation (Form de la matière)
    private void init_mat() {
        matiereTbl.clearSelection();

        modifierMatBtn.setEnabled(false);
        supprimerMatBtn.setEnabled(false);
    }

    // Initialisation (Form de l'étudiant - Note)
    private void init_etdNt(){
        ajouterBtn.setEnabled(false);
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
    private void get_mat_data(List<Matiere> lists) throws SQLException {
        MatiereModel model = new MatiereModel(lists);
        matiereTbl.setModel(model);
        matiereTbl.setAutoCreateRowSorter(true);
        matiereTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        countMatLbl.setText(model.getRowCount() + " MATIERE(S)");
    }

    // Ajouter les étudiants (note) sur une table
    private void get_etdNt_data(List<Etudiant> lists) throws SQLException {
        EtudiantModel model = new EtudiantModel(lists);
        etudiantNtTbl.setModel(model);
        etudiantTbl.setAutoCreateRowSorter(true);
        etudiantNtTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // Ajouter les notes d'un étudiant sur la table
    private void get_note_data(List<Note> lists) throws SQLException {
        NoteModel model = new NoteModel(lists);
        noteTbl.setModel(model);
        noteTbl.setAutoCreateRowSorter(true);
        noteTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        titleNtLbl.setText(("NOTES DE [ " + NOTE.getEtudiant().getNom() + " " + NOTE.getEtudiant().getPrenom()).toUpperCase() + " ]");
        avgLbl.setText("MOYENNE : " + NOTE.getStudentAverage());
    }

    // ========================== SELECTIONS ========================== //

    // Séléction du table étudiant
    private void get_etd_selection() {
        ListSelectionModel sM = etudiantTbl.getSelectionModel();
        sM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = etudiantTbl.getSelectedRow();
                    if (selectedRow != -1) {
                        CODE_ETD = (int)etudiantTbl.getValueAt(selectedRow, 0);

                        modifierEtdBtn.setEnabled(true);
                        supprimerEtdBtn.setEnabled(true);
                    }
                }
            }
        });
    }

    // Séléction du table matière
    private void get_mat_selection() {
        ListSelectionModel sM = matiereTbl.getSelectionModel();
        sM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = matiereTbl.getSelectedRow();
                    if (selectedRow != -1) {
                        CODE_MAT = matiereTbl.getValueAt(selectedRow, 0).toString();

                        modifierMatBtn.setEnabled(true);
                        supprimerMatBtn.setEnabled(true);
                    }
                }
            }
        });
    }

    // Séléction du table étudiant (Note)
    private void get_etdNt_selection() {
        ListSelectionModel sM = etudiantNtTbl.getSelectionModel();
        sM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = etudiantNtTbl.getSelectedRow();
                    if (selectedRow != -1) {
                        noteTbl.clearSelection();
                        CODE_ETD_NT = (int)etudiantNtTbl.getValueAt(selectedRow, 0);

                        try {
                            Etudiant etd = ETUDIANT.getStudentByCode(CODE_ETD_NT);
                            Note nt = new Note();
                            nt.setEtudiant(etd);

                            NOTE = nt;
                            get_note_data(nt.getStudentNote());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        ajouterBtn.setEnabled(true);
                    }
                }
            }
        });
    }

    // ========================== ACTIONS ========================== //

    // Action sur "nouveau" étudiant
    private void newStudentAction() throws SQLException {
        AddStudent nW = new AddStudent(THIS, false, null);
        boolean rs = nW.showDialog();

        if(rs){
            init_etd();
            init_etdNt();
            get_etd_data(ETUDIANT.getAllStudents());
            get_etdNt_data(ETUDIANT.getAllStudents());
        }
    }

    // Action sur "modifier" étudiant
    private void editStudentAction() throws SQLException {
        Etudiant etd = ETUDIANT.getStudentByCode(CODE_ETD);
        AddStudent nW = new AddStudent(THIS, true, etd);
        boolean rs = nW.showDialog();

        if(rs){
            init_etd();
            init_etdNt();
            get_etd_data(ETUDIANT.getAllStudents());
            get_etdNt_data(ETUDIANT.getAllStudents());
        }
    }

    // Action sur "supprimer" étudiant
    private void deleteStudentAction() throws SQLException {
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

            init_etd();
            init_etdNt();
            get_etd_data(ETUDIANT.getAllStudents());
            get_etdNt_data(ETUDIANT.getAllStudents());
        }
    }

    // Action sur "nouveau" matière
    private void newCourseAction() throws SQLException {
        AddCourse aC = new AddCourse(THIS, false, null);
        boolean rs = aC.showDialog();

        if(rs){
            init_mat();
            get_mat_data(MATIERE.getAllCourses());
        }
    }

    // Action sur "modifier" matière
    private void editCourseAction() throws SQLException {
        Matiere mat = MATIERE.getCourseByCode(CODE_MAT);
        System.out.println(mat.getCode());
        AddCourse aC = new AddCourse(THIS, true, mat);
        boolean rs = aC.showDialog();

        if(rs){
            init_mat();
            get_mat_data(MATIERE.getAllCourses());
        }
    }

    // Action sur "supprimer" matière
    private void deleteCourseAction() throws SQLException {
        Matiere mat = new Matiere();
        mat.setCode(CODE_MAT);
        int dialog = JOptionPane.showConfirmDialog(
                THIS,
                "Voulez-vous vraiment supprimer cette matière [code : " + CODE_MAT + "] ?",
                "Suppréssion",
                JOptionPane.YES_NO_OPTION
        );

        if(dialog == 0){
            mat.destroy();

            init_mat();
            get_mat_data(MATIERE.getAllCourses());
        }
    }

    // Action sur "nouvel" note
    private void addNoteAction() throws SQLException {
        Etudiant etd = ETUDIANT.getStudentByCode(CODE_ETD_NT);

        AddNote aN = new AddNote(THIS, etd);
        boolean rs = aN.showDialog();

        if(rs){
            init_etdNt();
            get_etdNt_data(ETUDIANT.getAllStudents());

            //

            Note nt = new Note();
            nt.setEtudiant(etd);
            get_note_data(nt.getStudentNote());
        }
    }

    // Action sur la recheche d'un étudiant
    private void rechercheEtdAction() throws SQLException {
        get_etd_data(ETUDIANT.getCustomStudents(rechercheEtdTxt.getText(), ETUDIANT.getAllStudents()));
    }

    // Action sur la recheche d'un matière
    private void rechercheMatAction() throws SQLException {
        get_mat_data(MATIERE.getCustomCourses(rechercheMatTxt.getText()));
    }

    // Action sur la recheche d'un étudiant (Note)
    private void rechercheEtdNtAction() throws SQLException {
        get_etdNt_data(ETUDIANT.getCustomStudents(rechercheNtTxt.getText(), ETUDIANT.getAllStudents()));
    }
}
