package Game;

/**
 * @date 27/02/2014
 * @author Mario Román
 * @author José Carlos Entrena
 * @author Óscar Bermúdez
 */
public class Prize {
    private final int treasures;
    private final int levels;
    
    // Constructores
    public Prize (int treasures, int levels) {
        this.treasures = treasures;
        this.levels = levels;
    }
    
    
    // Consultores
    public int getTreasures() {
        return treasures;
    }
    
    public int getLevels() {
        return levels;
    }
    
    
    // Métodos auxiliares
    @Override
    public String toString() {
        return Integer.toString(treasures) + " tesoros y "   + 
               Integer.toString(levels)    + " niveles.";
    }
}
