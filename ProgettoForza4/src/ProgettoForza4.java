import java.util.Objects; //metodo per gli oggetti
import java.util.Scanner; //metodo che assume input dall'utente

public class ProgettoForza4 {

    String[][] campo;   //variabili globali
    Boolean vincitore;
    Boolean parità;
    int giocatoreVincitore;
    int turnoGiocatore;

    public ProgettoForza4() {   //Costruttore
        giocatoreVincitore = 0;
        parità = false;
        turnoGiocatore = 1;
        campo = new String[6][7];
        nuovoCampo();
        scritturaTitolo();
    }

    private void nuovoCampo() {     //costruzione campo
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                campo[i][j] = " - ";
            }
        }
    }

    private void scritturaTitolo() {    //scrittura del titolo
        System.out.println(" ");
        System.out.println("    !! FORZA 4 !!    ");
        System.out.println(" ");
        for(int i=0; i<6; i++) {
            for(int j=0; j<7; j++) {
                System.out.print(campo[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean numeroColonne(String colonne) {     //numero delle colonne
        return ((Objects.equals(colonne, "1") ||
                Objects.equals(colonne, "2") ||
                Objects.equals(colonne, "3") ||
                Objects.equals(colonne, "4") ||
                Objects.equals(colonne, "5") ||
                Objects.equals(colonne, "6") ||
                Objects.equals(colonne, "7")));
    }

    private boolean colonnaPiena(int colonna) {     //la colonna è piena o no
        return (campo[0][colonna-1] == " X " || campo[0][colonna-1] == " 0 ");
    }

    private int getNuovaColonna(int colonna) {      //cambia riga in caso fosse già occupata in quella precisa colonna
        int posizione = 5;
        boolean trovato = false;
        while(posizione >=0 && !trovato) {
            if(!Objects.equals(campo[posizione][colonna], " X") && !Objects.equals(campo[posizione][colonna], " 0 ")) {
                trovato = true;
            } else {
                posizione--;
            }
        }
        return posizione;
    }


    private void scambiaTurnoGiocatore() {      //decide chi è il turno
        if(turnoGiocatore == 1) {
            turnoGiocatore = 2;
        } else {
            turnoGiocatore = 1;
        }
    }

    private void scriviSimbolo() {      //scrivi il simbolo X o 0 a seconda del turno + controlli se il valore inserito non dovesse essere tra 1 e 7
        Scanner sc = new Scanner(System.in);
        System.out.println("Giocatore " + turnoGiocatore + "Seleziona il numero della colonna in cui vuoi mettere il simbolo(1-7):");
        String colonne = sc.nextLine();

        while(!numeroColonne(colonne) || colonnaPiena(Integer.parseInt(colonne))) {
            while(!numeroColonne(colonne)) {
                System.out.println("Colonna non esistente, ritenta con un numero da 1-7");
                colonne = sc.nextLine();
            }
            while(colonnaPiena(Integer.parseInt(colonne))) {
                System.out.println("Colonna piena, scegli un altra colonna");
                colonne = sc.nextLine();
                if (!numeroColonne(colonne)) {
                    break;
                }
            }
        }
        int scegliColonna = Integer.parseInt(colonne) -1;


        String simboloDaMettere;    //se il turno del giocatore è 1 allora il simbolo sarà X, altrimenti 0
        if(turnoGiocatore == 1) {
            simboloDaMettere = " X ";
        } else {
            simboloDaMettere = " 0 ";
        }
        campo[getNuovaColonna(scegliColonna)][scegliColonna] = simboloDaMettere;
        scritturaTitolo();
        scambiaTurnoGiocatore();
    }

    private boolean campoPieno() {      //vero se il campo è pieno, falso se non è pieno
        boolean pieno = true;
        for(int i=0; i<1; i++) {
            for(int j=0; j<7; j++) {
                if(campo[i][j] != " X " && campo[i][j] != " 0 ") {
                    pieno = false;
                }
            }
        }
        return pieno;
    }

    private String controlloVincitoreVerticale() {      //controlla se c'è un vincitore che ha fatto 4 simboli uguali di seguito in veritcale
        String simbolo = null;
        for(int i=0; i<3; i++) {
            for(int j=0; j<7; j++) {
                if((campo[i][j] == campo[i+1][j]) && (campo[i][j] == campo[i+2][j]) && (campo[i][j] == campo[i+3][j])) {
                    if(campo[i][j] == " X " || campo[i][j] == " 0 ") {
                        simbolo = campo[i][j];
                    }
                }
            }
        }
        return simbolo;
    }

    private String controlloVincitoreOrizzontale() {    //controlla se c'è un vincitore che ha fatto 4 simboli uguali di seguito in orizzontale
        String simbolo = null;
        for(int i=0; i<6; i++) {
            for(int j=0; j<4; j++) {
                if((campo[i][j] == campo[i][j+1]) && (campo[i][j] == campo[i][j+2]) && (campo[i][j] == campo[i][j+3])) {
                    if(campo[i][j] == " X " || campo[i][j] == " 0 ") {
                        simbolo = campo[i][j];
                    }
                }
            }
        }
        return simbolo;
    }

    private String controlloVincitoreDiagonaleSinistra() {      //controlla se c'è un vincitore che ha fatto 4 simboli uguali di seguito in diagonale verso sinistra
        String simbolo = null;
        for(int i=0; i<3; i++) {
            for(int j=0; j<4; j++) {
                if((campo[i][j] == campo[i+1][j+1]) && (campo[i][j] == campo[i+2][j+2]) && (campo[i][j] == campo[i+3][j+3])) {
                    if(campo[i][j] == " X " || campo[i][j] == " 0 ") {
                        simbolo = campo[i][j];
                    }
                }
            }
        }
        return simbolo;
    }

    /*
    private String controlloVincitoreDiagonaleDestra() {        //controlla se c'è un vincitore che ha fatto 4 simboli uguali di seguito in diagonale verso destra
        String simbolo = null;
        for(int i=0; i<3; i++) {
            for(int j=0; j<4; j++) {
                if((campo[i][j] == campo[i+1][j-1]) && (campo[i][j] == campo[i+2][j-2]) && (campo[i][j] == campo[i+3][j-3])) {
                    if(campo[i][j] == " X " || campo[i][j] == " 0 ") {
                        simbolo = campo[i][j];
                    }
                }
            }
        }
        return simbolo;
    }
    */


    private int controllaVincitore() {      //controlla chi è il vincitore tra X o 0
        int vincitore = 0;
        String simbolo = " ";

        if(controlloVincitoreVerticale() == " X " || controlloVincitoreVerticale() == " 0 ") {
            simbolo = controlloVincitoreVerticale();
        } else if(controlloVincitoreOrizzontale() == " X " || controlloVincitoreOrizzontale() == " 0 ") {
            simbolo = controlloVincitoreOrizzontale();
        } else if(controlloVincitoreDiagonaleSinistra() == " X " || controlloVincitoreDiagonaleSinistra() == " 0 ") {
            simbolo = controlloVincitoreDiagonaleSinistra();
        } /*else if(controlloVincitoreDiagonaleDestra() == " X " || controlloVincitoreDiagonaleDestra() == " 0 ") {
            simbolo = controlloVincitoreDiagonaleDestra();
        }*/

        if(simbolo == " X ") {
            vincitore = 1;
        } else if(simbolo == " 0 ") {
            vincitore = 2;
        }
        return vincitore;
    }

    private boolean controllaParita() {     //vero se la partita è finita in parità
        return(campoPieno() == true && (controllaVincitore() != 1 && controllaVincitore() != 2));
    }

    private void mostraVincitore() {        //stampa il giocatore vincitore
        System.out.println(" GIOCATORE " + giocatoreVincitore + " HA VINTO !!! ");
    }

    public void gioca() {       //partita di forza4
        while(giocatoreVincitore == 0) {
            giocatoreVincitore = controllaVincitore();
            parità = controllaParita();
            if(giocatoreVincitore == 1) {
                mostraVincitore();
                break;
            } else if(giocatoreVincitore == 2) {
                mostraVincitore();
                break;
            } else if(parità == true) {
                System.out.println("Parità!");
                break;
            }
            scriviSimbolo();
        }
    }

    public static void main(String[] args) {        //main
        ProgettoForza4 f4 = new ProgettoForza4();
        f4.gioca();
    }
}


