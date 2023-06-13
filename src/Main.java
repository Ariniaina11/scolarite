import classes.Etudiant;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static int choice;

    public static void main(String[] args) throws SQLException {
        init();

        while (choice != 4){
            choice = menu();

            switch (choice){
                case 1:
                    etudiant_section();
                    break;
                case 2:
                    //matiere_menu();
                    break;
                case 3:
                    //note_menu();
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix inconnu !");
                    break;
            }
        }
    }


    // ------------------------------------------ FONCTIONS ------------------------------------------ //
    static void init(){
        scanner = new Scanner(System.in);
        choice = 0;
    }

    // Menu principale
    static int menu(){
        System.out.println("==========================");
        System.out.println("\tMENU PRINCIPALE");
        System.out.println("==========================");
        System.out.println("1 - Etudiant ");
        System.out.println("2 - Matière ");
        System.out.println("3 - Note ");
        System.out.println("4 - Quitter ");
        System.out.println("==========================");
        System.out.print("Votre choix : ");

        return scanner.nextInt();
    }

    // Séction ETUDIANT
    static void etudiant_section() throws SQLException {
        int choix = 0;
        while(choix != 5){
            choix = etudiant_menu();

            switch (choix){
                case 1:
                    etudiant_liste();
                    break;
                case 2:
                    etudiant_create();
                    break;
                case 3:
                    etudiant_edit();
                    break;
                case 4:
                    etudiant_destroy();
                    break;
                default:
                    System.out.println("Choix inconnu !");
                    break;
            }
        }
    }

    // Menu de la section étudiant
    static int etudiant_menu() {
        System.out.println("==========================");
        System.out.println("\t\tETUDIANT");
        System.out.println("==========================");
        System.out.println("1 - Liste des étudiants ");
        System.out.println("2 - Ajouter ");
        System.out.println("3 - Modifier ");
        System.out.println("4 - Supprimer ");
        System.out.println("5 - Menu principale ");
        System.out.println("==========================");
        System.out.print("Votre choix : ");

        return scanner.nextInt();
    }

    // Liste de tous les étudiants
    static void etudiant_liste() throws SQLException{
        System.out.println("==========================");
        System.out.println("\tListe des étudiants");
        System.out.println("==========================");

        for (Etudiant etd : (new Etudiant().getAllStudents())) {
            System.out.println(etd.getCode() + " - " + etd.getNom() + " " + etd.getPrenom());
        }
    }

    // Ajout d'un nouveau étudiant
    static void etudiant_create() throws SQLException {
        Etudiant etd = new Etudiant();
        String scanStr = "";

        System.out.println("==========================");
        System.out.println("Nouveau étudiant");
        System.out.println("==========================");

        System.out.print("Nom : ");
        scanStr = scanner.nextLine();
        scanStr = scanner.nextLine();
        etd.setNom(scanStr);

        System.out.print("Prénoms :");
        scanStr = scanner.nextLine();
        etd.setPrenom(scanStr);

        System.out.print("Adresse : ");
        scanStr = scanner.nextLine();
        etd.setAdresse(scanStr);

        System.out.print("Téléphone : ");
        scanStr = scanner.nextLine();
        etd.setTelephone(scanStr);

        etd.store();
        System.out.println("Etudiant créé avec succès !");
    }

    // Modification d'un étudiant
    static void etudiant_edit() throws SQLException {
        Etudiant etd;
        Etudiant new_etd = new Etudiant();

        System.out.print("Veuillez entrer le code de l'étudiant : ");
        etd = new Etudiant().getStudentByCode(scanner.nextInt());

        if(etd.getCode() == 0){
            System.out.println("Etudiant non trouvé !");
        }else{
            System.out.println("==========================");
            System.out.println("Modification d'un étudiant");
            System.out.println("==========================");

            System.out.print("Nom (" + etd.getNom() + ") : ");
            new_etd.setNom(scanner.nextLine());
            System.out.print("Prénoms (" + etd.getPrenom() + ") : ");
            new_etd.setPrenom(scanner.nextLine());
            System.out.print("Adresse (" + etd.getAdresse() + ") : ");
            new_etd.setAdresse(scanner.nextLine());
            System.out.print("Téléphone (" + etd.getTelephone() + ") : ");
            new_etd.setTelephone(scanner.nextLine());

            new_etd.setCode(etd.getCode());
            new_etd.update();

            System.out.println("Etudiant modifié avec succès !");
        }
    }

    // Suppression d'un étudiant
    static void etudiant_destroy() throws SQLException {
        Etudiant etd = new Etudiant();

        System.out.print("Veuillez entrer le code de l'étudiant : ");
        etd.setCode(scanner.nextInt());
        etd.destroy();

        System.out.println("Etudiant supprimé avec succès !");
    }
}
