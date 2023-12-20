package pokemon;


import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            afficherMenu();

            // Lire le choix de l'utilisateur
            int choix = scanner.nextInt();

            // Effectuer une action en fonction du choix
            switch (choix) {
                case 1:
                    System.out.println("Vous avez choisi l'option 1");
                    break;
                case 2:
                    System.out.println("Vous avez choisi l'option 2");
                    break;
                case 3:
                    System.out.println("Vous avez choisi l'option 3");
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("Menu :");
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("3. Option 3");
        System.out.println("4. Quitter");
        System.out.print("Faites votre choix : ");
    }
}
