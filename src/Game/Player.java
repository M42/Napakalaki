package Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @date 19/3/14
 * @author José Carlos Entrena
 * @author Mario Román
 * @author Óscar Bermúdez
 */
public class Player {
    private boolean dead;
    private String name;
    private int level;
    private ArrayList<Treasure> hiddenTreasures;
    private ArrayList<Treasure> visibleTreasures;

    
    private void bringToLife(){}
    
    private void incrementLevels(int nlevels){
        level += nlevels; 
    }
    
    private void decrementLevels(int nlevels){
        level -= nlevels;
        
        if(level < 1)
            level = 1;
    }
    
    private void setPendingBadConsequence(BadConsequence bad){}
    
    private void die(){
        level = 1;
        
        hiddenTreasures.clear();
        visibleTreasures.clear();
        
        dead = true;
        
        bringToLife();
    }
    
    private void discardNecklaceIfVisible(){
        if(visibleTreasures.contains(new ArrayList<>(Arrays.asList(TreasureKind.NECKLACE))))
            visibleTreasures.remove(new ArrayList<>(Arrays.asList(TreasureKind.NECKLACE)));
    }
    
    private void dieIfNoTreasures(){}
    
    private int computeGoldCoinsValue(Treasure treasure){
        return treasure.getGoldCoins();
    }
    
    private boolean canIBuyLevels(int levels){
        return false;
    }
    
    public void applyPrize(Prize prize){}
    
    public CombatResult combat(Monster monster){
        return null;
    }
    
    public void applyBadConsequence(BadConsequence bad){
    }
    
    public boolean makeTreasureVisible(Treasure treasure){
        return false;
    }
    
    public boolean canMakeTreasureVisible(Treasure treasure){
        return false;
    }
    
    public void discardVisibleTreasure(Treasure treasure){
        visibleTreasures.remove(new ArrayList<>(Arrays.asList(treasure.getType())));
    }

    public void discardHiddenTreasure(Treasure treasure){
        hiddenTreasures.remove(new ArrayList<>(Arrays.asList(treasure.getType())));
    }
    
    public boolean buyLevels(ArrayList<Treasure> visible, ArrayList<Treasure> hidden){
        return canIBuyLevels();
    }
    
    public Player(String name) {
        this.dead = false;
        this.name = name;
        level = 1;
        
        bringToLife();
    }    
    /*
+buyLevels(visible : Treasure [], hidden : Treasure []) : boolean
+getCombatLevel() : int
+validState() : boolean
+initTreasures() : boolean
+isDead() : boolean
+hasVisibleTreasures() : boolean
*/

    public ArrayList<Treasure> getHiddenTreasures() {
        return hiddenTreasures;
    }

    public ArrayList<Treasure> getVisibleTreasures() {
        return visibleTreasures;
    }

    private boolean canIBuyLevels() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
