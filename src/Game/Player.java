package Game;

import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @date 19/3/14
 * @author José Carlos Entrena
 * @author Mario Román
 * @author Óscar Bermúdez
 */
public class Player {
    // Atributos.
    private boolean dead;
    private final String name;
    private int level;
    private BadConsequence pendingBadConsequence;
    private ArrayList<Treasure> hiddenTreasures;
    private ArrayList<Treasure> visibleTreasures;

    // Constructor.
    public Player(String name) {
        this.name = name;
        bringToLife();
    }
    
    
    // Métodos privados.
    private void bringToLife(){
        dead = false;
        level = 1;
    }
    
    private void incrementLevels(int nlevels){
        level += nlevels; 
    }
    
    private void decrementLevels(int nlevels){
        level -= nlevels;
        
        if(level < 1)
            level = 1;
    }
    
    private void setPendingBadConsequence(BadConsequence bad){
        pendingBadConsequence = bad;
    }
    
    private void die(){
        for (Treasure treasure : hiddenTreasures){
            CardDealer.getInstance().giveTreasureBack(treasure);
        }
        hiddenTreasures.clear();
        for (Treasure treasure : visibleTreasures){
            CardDealer.getInstance().giveTreasureBack(treasure);
        }
        visibleTreasures.clear();
        dead = true;
    }
    
    private void discardNecklaceIfVisible(){
        int position = 0; 
        int size = visibleTreasures.size();
        boolean found = false;
        
        while (!found || position == size){
           if (visibleTreasures.get(position).getType() != TreasureKind.NECKLACE)
               position++; 
           else 
               found = true; 
        }
        
        if (found){
            CardDealer.getInstance().giveTreasureBack(visibleTreasures.get(position));
            visibleTreasures.remove(position); 		
        }
    }	
    
    private void dieIfNoTreasures(){
        if (visibleTreasures.isEmpty() && hiddenTreasures.isEmpty())
            die();
    }
    
    private int computeGoldCoinsValue(ArrayList<Treasure> treasure_list){
        int gold_coins = 0; 
        for (Treasure t : treasure_list)
            gold_coins += t.getGoldCoins(); 
        return (gold_coins / 1000); 
    }
    
    /**
     * Comprueba si al comprar un número de niveles, no se supera el nivel 10.
     * Hacemos esto porque no podemos alcanzar el nivel 10 en una compra de niveles. 
     */
    private boolean canIBuyLevels(int levels){
        return (this.level + levels < 10); 
    }
    
    
    
    
    // Métodos públicos
    public void applyPrize(Prize prize){
        int nLevels = prize.getLevels(); 
        incrementLevels(nLevels); 
        int nPrize = prize.getTreasures(); 
        for (int i = 0; i < min(nPrize, 4-hiddenTreasures.size()); i++){
            Treasure treasure = CardDealer.getInstance().nextTreasure(); 
            hiddenTreasures.add(treasure); 
        }
    }
    
    public CombatResult combat(Monster monster){
        int total_level = this.getCombatLevel();
        return null;
    }
        
    public void applyBadConsequence(BadConsequence bad){
        int nlevels = bad.getLevels();
        decrementLevels(nlevels); 
        BadConsequence pendingBad = bad.adjustToFitTreasureLists(visibleTreasures, hiddenTreasures); 
        setPendingBadConsequence(pendingBad); 
    }
    
    public boolean makeTreasureVisible(Treasure treasure){
        if (canMakeTreasureVisible(treasure)){
            visibleTreasures.add(treasure); 
            hiddenTreasures.remove(treasure); 
            return true; 
        }
        else
            return false;       
    }
    
    public boolean canMakeTreasureVisible(Treasure treasure){
        TreasureKind type = treasure.getType(); 
        
        if (type == TreasureKind.NECKLACE)
            return true; 
        
        else if (type != TreasureKind.ONEHAND){
            boolean has_item = false; 
            for (Treasure t : visibleTreasures){
                if(t.getType() == type)
                    has_item = true;
            }
            return !has_item; 
        }
        else{
            int number_of_onehands = 0; 
            for (Treasure t : visibleTreasures){
                if(t.getType() == TreasureKind.ONEHAND)
                    number_of_onehands++;
            }
            return (number_of_onehands < 2); 
        }       
    }
    
    public void discardVisibleTreasure(Treasure treasure){
        visibleTreasures.remove(treasure);
        if (pendingBadConsequence!=null && !pendingBadConsequence.isEmpty())
            pendingBadConsequence.substractVisibleTreasure(treasure);
        CardDealer.getInstance().giveTreasureBack(treasure); 
        dieIfNoTreasures(); 
    }

    public void discardHiddenTreasure(Treasure treasure){
        hiddenTreasures.remove(treasure.getType());
        if (pendingBadConsequence!=null && !pendingBadConsequence.isEmpty())
            pendingBadConsequence.substractHiddenTreasure(treasure);
        CardDealer.getInstance().giveTreasureBack(treasure); 
        dieIfNoTreasures(); 
    }
    
    /**
     * Compra niveles a partir de una lista de tesoros, si el juego nos lo permite. 
     * IMPORTANTE: Aunque se llama al método discardVisibleTreasure(), que actúa sobre 
     * pendingBadConsequence, no interfiere en el mal rollo ya que este aun no ha aparecido.
     * @param visible Lista de tesoros visibles. 
     * @param hidden Lista de tesoros ocultos. 
     * @return Compra realizada. 
     */
    public boolean buyLevels(ArrayList<Treasure> visible, ArrayList<Treasure> hidden){
        int levels = computeGoldCoinsValue(visible); 
        levels += computeGoldCoinsValue(hidden); 
        boolean canI = canIBuyLevels(levels); 
        if (canI){
            incrementLevels(levels); 
            for(Treasure treasure : visible)
                discardVisibleTreasure(treasure); 
            for (Treasure treasure : hidden)
                discardHiddenTreasure(treasure); 
        }
        return canI;
    }
    
    public int getCombatLevel(){
        int combat_level = level;
        boolean has_necklace = false; 
        
        // Revisar este bucle: puede haber una mejor forma. 
        for (Treasure treasure : visibleTreasures){
            if(treasure.getType() == TreasureKind.NECKLACE)
                has_necklace = true; 
        }
        for (Treasure treasure : visibleTreasures){
            if (has_necklace)
                combat_level += treasure.getMaxBonus();
            else
                combat_level += treasure.getMinBonus();
            
        }
        return combat_level;
    }
    
    /**
     * Comprueba que el estado del jugador es válido. 
     * Para ello, comprueba que el BadConsequence asociado esté vacío. 
     * @return 
     */
    public boolean validState(){
        return pendingBadConsequence.isEmpty();
    }
    
    public void initTreasures(){
        bringToLife();
        int number = Dice.getInstance().nextNumber(); 
        if (number == 1){
            hiddenTreasures.add(CardDealer.getInstance().nextTreasure());
        }
        else if (number == 6){
            for (int i = 0; i < 3; i++)
                hiddenTreasures.add(CardDealer.getInstance().nextTreasure()); 
        }
        else{
            for (int i = 0; i < 2; i++)
                hiddenTreasures.add(CardDealer.getInstance().nextTreasure());
        }
    }
    
    public boolean isDead(){
        return dead;
    }
    
    public boolean hasVisibleTreasures(){
        return !visibleTreasures.isEmpty();
    }

    public ArrayList<Treasure> getHiddenTreasures() {
        return hiddenTreasures;
    }

    public ArrayList<Treasure> getVisibleTreasures() {
        return visibleTreasures;
    }
}
