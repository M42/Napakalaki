package UserInterface;

import Game.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @date 10/04/14
 * @author Mario Román
 * @author José Carlos Entrena
 * @author Óscar Bermúdez
 */
public class TextUI {
    private static Scanner scanIn;
    private static final Reader reader = new InputStreamReader(System.in);
    private static final Napakalaki NP = Napakalaki.getInstance();
    private static int turn;
    
    private TextUI(){
        turn = 0;
    }
    
    // Método main.
    public static void main(String[] args) {
        play();  
    }

// EJECUCIÓN TEXTUI
    
    public static void play(){
        CombatResult result;
        
        // Presentación del juego
        clearConsole();
        printHeader(); 
        
        // Lee los jugadores
        ArrayList<String> players = readPlayers();
        NP.initGame(players); 
        
        // Bucle principal del juego
        do{
            // Anuncia el nuevo turno
            clearScreen();
            
            // El jugador elige acción
            selectionMenu();
            
            // Combate
            result = NP.combat();
            printCombatResult(result);

            if(result != CombatResult.WINANDWINGAME){
                // Aplica mal rollo si pierde, o bien ofrece la posibilidad de eliminar tesoros.
                adjust();
                
                do{
                    // El jugador elige acción
                    selectionMenu2();
                    
                // Pasa al siguiente turno
                } while(!yesNoQuestion("¿Pasar al siguiente turno?"));
                
                NP.nextTurn();
                turn++;
            }
            else{
                //Fin del juego.
                System.out.println("¡El juego ha terminado! Ganador: " + NP.getCurrentPlayer());
            }
        } while (!NP.endOfGame(result)); 
    }
    
    
// SECCIÓN LECTURA:
    
    /**
     * Lee un número entero introducido por el usuario.
     * @param msg Mensaje mostrado por pantalla para solicitar la entrada.
     * @return Número entero leído.
     */
    public static int readInteger(String msg) {
        int input = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));       
        System.out.print(msg);
        
        try {
            input = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe){
            System.err.println("Invalid Format!");
        }
        catch (IOException ioe){
            System.err.println("IO Error!");            
        }
        return input;
    }
    
    /**
     * @brief Lee una cadena introducida por el usuario.
     * @param msg Mensaje mostrado por pantalla para solicitar la entrada.
     * @return Cadena de texto leída.
     */
    public static String readString(String msg) {
        String input= "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));       
        System.out.print(msg);
        
        try{
            input = br.readLine();
        }
        catch(IOException ioe){
            System.err.println("IO Error!");            
        }
        
        return input;
    }
    
    
    /**
     * @brief Imprime un mensaje y lee los nombres de los jugadores
     * @return Una lista de jugadores 
     */
    private static ArrayList<String> readPlayers() {
        String line = readString("Introduzca los nombres de los jugadores: ");
        String[] players = line.split(" ");
        clearConsole();
        
        return new ArrayList<>(Arrays.asList(players));
    }
    
    /**
     * @brief Imprime un mensaje por pantalla y toma una respuesta a la pregunta. 
     * @param msg Mensaje (pregunta) a imprimir. 
     * @return Valor booleano, respuesta a la pregunta. 
     */
    public static boolean yesNoQuestion(String msg) {
        String input; 
        do{
            input = readString(msg + "(y/n): "); 
        } while(!input.equals("y") && !input.equals("n"));
        
        return input.equals("y"); 
    }
    
    // Limpiar consola.
    public final static void clearConsole() {
        System.out.print("\u001B[H\u001B[2J");
        System.out.flush();
    }

    public final static void clearScreen() {
        clearConsole();
        printHeader();
        System.out.println("Turno: " + turn);
        System.out.println(CardDealer.getInstance().toString());
        System.out.println("Sectarios en juego: " + Game.CultistPlayer.getTotalCultistPlayers());
        printCurrentPlayerStatus();
        printCurrentMonsterStatus();
        
    }

    
//SECCIÓN PRINT'S:
    
    // Imprime una cabecera para el juego
    public static void printHeader(){
        System.out.println("------------------------------"); 
        System.out.println("\tNapakalaki");
        System.out.println("------------------------------"); 
    }
    
    // Imprime el estado del jugador actual. 
    public static void printCurrentPlayerStatus(){
        System.out.println("\nJugador actual: " + NP.getCurrentPlayer().toString() + "\n");
        printVisibleTreasures();
        printHiddenTreasures();
        printCurrentPlayerCombatStatus();
    }
    
    // Imprime el nivel de combate del jugador actual. 
    public static void printCurrentPlayerCombatStatus(){
        System.out.println("Nivel de combate: " + NP.getCurrentPlayer().getCombatLevel() + "\n");
    }

    // Imprime una lista de tesoros
    public static void printTreasures(ArrayList<Treasure> treasures){
        for(int i = 0; i < treasures.size(); i++){
            System.out.println("\t(" + i + "): " + treasures.get(i));
        }
    }
    
    // Imprime los tesoros visibles del jugador actual. 
    public static void printVisibleTreasures(){
        System.out.println("Tesoros visibles:");
        printTreasures(NP.getVisibleTreasures());
    }

    // Imprime los tesoros ocultos del jugador actual. 
    public static void printHiddenTreasures(){
        System.out.println("Tesoros ocultos:");
        printTreasures(NP.getHiddenTreasures());
    } 
    
    // Imprime el estado del monstruo actual.
    public static void printCurrentMonsterStatus(){
        System.out.println("\nMonstruo actual: " + NP.getCurrentMonster().toString() + "\n");
    }
    
    /**
     * @brief Imprime un mensaje asociado al resultado de un combate 
     * @param result Resultado del combate
     */
    public static void printCombatResult(CombatResult result){
        clearScreen();
        
        System.out.println("Combate contra " + NP.getCurrentMonster().getName() + ":");        
        
        switch (result) {
            case WIN:  System.out.println("Has derrotado al monstruo.");
                break;
            case WINANDWINGAME:  System.out.println("Has ganado el combate y el juego. ¡Enhorabuena!");
                break;
            case LOSE:  System.out.println("Has sido derrotado. Ahora se te aplicará el mal rollo del monstruo.");
                break;
            case LOSEANDDIE:  System.out.println("Has sido derrotado y has muerto.");
                break;
            case LOSEANDESCAPE:  System.out.println("Has logrado escapar del combate a salvo.");
                break;
            case LOSEANDCONVERT: System.out.println("Has sido derrotado. ¡Ahora eres sectario!"); 
                break;
            default: System.out.println("Error en el combate.");
                break;
        }
        
    }
    
    
//SECCIÓN MENÚ'S:
    
    /**
     * @brief Crea un menú
     * @param msg Mensaje inicial del menú
     * @param options Opciones del menú
     */
    public static void menu(String msg, String... options){
        System.out.println(msg);
        
        int i = 1;
        
        for(String o : options){
            System.out.println("[" + i + "]: " + o);
            
            i++;
        }
    }
    
    /**
     * @brief Menú de antes de la batalla
     */
    public static void selectionMenu(){
        menu("Elegir acción:",
                "Comprar niveles",
                "Combatir");
        char respuesta = readString("").charAt(0);        
        clearScreen();
        
        // Controla las opciones
        switch (respuesta) {
            case '1': {
                clearScreen();
                buyLevels();
                selectionMenu();
                break;
            }
            case '2': {
                clearScreen();
                break;
            }
            default: {
                clearScreen();
                selectionMenu();
                break;
            }
        }
    }

    /**
     * @brief Menú de después de la batalla
     */
    public static void selectionMenu2(){
        menu("Elegir acción:",
             "Equipar tesoros",
             "Pasar de turno");
        char respuesta = readString("").charAt(0);
        
        clearScreen();
        
        // Controla las opciones
        switch (respuesta) {
            case '1': {
                clearScreen();
                equip();
                selectionMenu2();
                break;
            }
            case '2': {
                clearScreen();
                break;
            }
            default: {
                clearScreen();
                selectionMenu2();
                break;
            }
        }
    }
    
    public static void adjust(){
        do{
            discardVisibleTreasures();
            discardHiddenTreasures();
        } while(!NP.nextTurnAllowed());
    }
    
    /**
     * @brief Elimina tesoros visibles del jugador, y ajusta el mal rollo. 
     */
    public static void discardVisibleTreasures(){
        char read_index;
        
        do {
            System.out.println("Descarta tesoros visibles:\n");
            printVisibleTreasures();
            System.out.println("Dime el índice del tesoro visible a descartar (x para terminar): ");
            
            // Lee al usuario
            read_index = readString("").charAt(0);
            
            // Si el índice es válido lo elimina de los tesoros visibles.
            if (read_index != 'x') {
                int index = Character.getNumericValue(read_index);
                if (index >= 0 && index < NP.getVisibleTreasures().size()) {
                    NP.discardVisibleTreasure(NP.getVisibleTreasures().get(index));
                    clearScreen();
                    System.out.println("Tesoro eliminado.\n");
                }
                else {
                    clearScreen();
                }
            }
            else {
                clearScreen();
            }
        } while (read_index != 'x');
    }
    
    /**
     * @brief Elimina tesoros ocultos del jugador, y ajusta el mal rollo. 
     */
    public static void discardHiddenTreasures(){
        char read_index;
        
        do {
            System.out.println("Descarta tesoros ocultos:\n");
            printHiddenTreasures();
            System.out.println("Dime el índice del tesoro oculto a descartar (x para terminar): ");
            
            // Lee al usuario
            read_index = readString("").charAt(0);
            
            // Si el índice es válido lo elimina de los tesoros visibles.
            if (read_index != 'x') {
                int index = Character.getNumericValue(read_index);
                if (index >= 0 && index < NP.getHiddenTreasures().size()) {
                    NP.discardHiddenTreasure(NP.getHiddenTreasures().get(index));
                    clearScreen();
                    System.out.println("Tesoro eliminado.\n");
                }
                else {
                    clearScreen();
                }
            }
            else {
                clearScreen();
            }
        } while (read_index != 'x');
    }
    
    public static void buyLevels(){
        // Contienen los tesoros visibles y ocultos a vender.
        ArrayList<Game.Treasure> svisibles = new ArrayList<>();
        ArrayList<Game.Treasure> shidden = new ArrayList<>();
        
        // Indican si el tesoro en esa posición se ha vendido ya.
        ArrayList<Boolean> index_visibles = new ArrayList<>();
        ArrayList<Boolean> index_hidden = new ArrayList<>();
        int visibles_size = NP.getVisibleTreasures().size();
        int hiddens_size = NP.getHiddenTreasures().size();
        for (int i=0; i<visibles_size; ++i)
            index_visibles.add(true);
        for (int i=0; i<hiddens_size; ++i)
            index_hidden.add(true);
        
        
        char read_index;
        int index;
        
        // Tesoros visibles.
        do {
            // Muestra las posibles ventas.
            System.out.println("Vendiendo tesoros visibles:");
            System.out.println("Tesoros visibles que se venderán:");
            printTreasures(svisibles);
            printVisibleTreasures();
            System.out.println("\t(x): Salir");
            
            // Lee el índice de la venta
            read_index = readString("").charAt(0);
            
            // Vende el índice indicado si es correcto.
            if (read_index != 'x') {
                index = Character.getNumericValue(read_index);
                
                if (index >= 0 && index < visibles_size && index_visibles.get(index)) {
                    svisibles.add(NP.getVisibleTreasures().get(index));
                    index_visibles.set(index, false);
                }
            }
            
            clearScreen();
        } while (read_index != 'x');
        
        // Tesoros ocultos.
        do {
            // Muestra las posibles ventas.
            System.out.println("Vendiendo tesoros ocultos:");
            System.out.println("Tesoros ocultos que se venderán:");
            printTreasures(shidden);
            printHiddenTreasures();
            System.out.println("\t(x): Salir");
            
            // Lee el índice de la venta
            read_index = readString("").charAt(0);
            
            // Vende el índice indicado si es correcto.
            if (read_index != 'x') {
                index = Character.getNumericValue(read_index);
                
                if (index >= 0 && index < hiddens_size && index_hidden.get(index)) {
                    shidden.add(NP.getHiddenTreasures().get(index));
                    index_hidden.set(index, false);
                }
            }
            
            clearScreen();
        } while (read_index != 'x');
        

        
        // Comprobante de venta.
        System.out.println("Se venderán los siguientes tesoros:\n");
        System.out.println("Tesoros visibles:");
        printTreasures(svisibles);
        
        int sumavisibles = 0;
        
        for(Treasure t : svisibles){
            sumavisibles += t.getGoldCoins();
        }
        
        if (NP.getCurrentPlayer() instanceof CultistPlayer) {
            sumavisibles *= 2;
        }
        
        System.out.println("\tSuma total: " + sumavisibles + "\n");
        System.out.println("Tesoros ocultos:\n");
        printTreasures(shidden);
        
        int sumahidden = 0;
        
        for(Treasure t : shidden){
            sumahidden += t.getGoldCoins();
        }
        
        if (NP.getCurrentPlayer() instanceof CultistPlayer) {
            sumahidden *= 2;
        }
        
        System.out.println("\tSuma total: " + sumahidden + "\n");
        System.out.println("Aumentarías " + (sumavisibles/1000 + sumahidden/1000) + " niveles\n");
                
        if(yesNoQuestion("¿Realizar la compra?")){
            if (!NP.buyLevels(svisibles, shidden)) {
                clearScreen();
                System.out.println("No puedes vender los tesoros.\n");
            }
            else {
                clearScreen();
                System.out.println("Compra realizada.\n");
            }
        }
        else{
            clearScreen();
            System.out.println("Compra anulada.");
        }
    }
    
    
    public static void equip () {
        char read_index;
        
        do {
            System.out.println("Equipación de objetos.");
            printVisibleTreasures();
            printHiddenTreasures();
            System.out.println("\t(x): Salir");
            System.out.println("Dime qué tesoro oculto te quieres equipar:");
            
            // Pasamos el índice del tesoro que queremos equipar.
            read_index = readString("").charAt(0);
            
            if (read_index != 'x') {
                int index = Character.getNumericValue(read_index);
                clearScreen();
                
                // Comprueba que el índice sea válido y si puede equiparse.
                if (index < NP.getHiddenTreasures().size() && index >= 0) {
                    if (NP.canMakeTreasureVisible(NP.getHiddenTreasures().get(index))) {
                        System.out.println("Tesoro " + NP.getHiddenTreasures().get(index).getName() + " equipado.");
                        NP.makeTreasureVisible(NP.getHiddenTreasures().get(index));
                    }
                    else {
                        System.out.println("No puedes equiparte " + NP.getHiddenTreasures().get(index));
                    }
                }
                else {
                    System.out.println("Índice inválido.");
                }
            }
        } while (read_index != 'x');
    }
}
