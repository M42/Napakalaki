package Game;

import java.util.ArrayList;

/**
 * @date 27/02/2014
 * @author Mario Román
 * @author José Carlos Entrena
 * @author Óscar Bermúdez
 */
public class BadConsequence {
    private final String text;
    private int levels;
    private int nVisibleTreasures;
    private int nHiddenTreasures;
    private boolean death;
    private final ArrayList<TreasureKind> specificHiddenTreasures;
    private final ArrayList<TreasureKind> specificVisibleTreasures;

  
    // Constructores
    public BadConsequence (String text, int levels, int nVisible, int nHidden) {
        this.text = text;
        this.levels = levels;
        this.nVisibleTreasures = nVisible;
        this.nHiddenTreasures = nHidden;
        this.specificHiddenTreasures = new ArrayList();
        this.specificVisibleTreasures = new ArrayList();
    }
    
    public BadConsequence (String text, int levels, ArrayList<TreasureKind> tVisible, ArrayList<TreasureKind> tHidden) {
        this.text = text; 
        this.levels = levels; 
        this.specificHiddenTreasures = (ArrayList<TreasureKind>) tHidden.clone();
        this.specificVisibleTreasures = (ArrayList<TreasureKind>) tVisible.clone(); 
        this.nVisibleTreasures = tVisible.size();
        this.nHiddenTreasures = tHidden.size();         
    }
    
    public BadConsequence (String text, boolean death) {
        this.text = text;
        this.death = death;
        this.specificHiddenTreasures = new ArrayList();
        this.specificVisibleTreasures = new ArrayList();
        this.nHiddenTreasures = 0; 
        this.nVisibleTreasures = 0; 
    }
    
    
    // Consultores
    public boolean isEmpty(){
        return (death == false && levels == 0 && nVisibleTreasures == 0 && nHiddenTreasures == 0); 
    }
    
    public boolean kills(){
        return death; 
    }
    
    public String getText() {
        return text; 
    }
    
    public int getLevels() {
        return levels; 
    }
    
    public int getnHiddenTreasures() {
        return nHiddenTreasures; 
    }
    
    public int getnVisibleTreasures() {
        return nVisibleTreasures; 
    }

    public ArrayList<TreasureKind> getSpecificHiddenTreasures() {
        return specificHiddenTreasures;
    }

    public ArrayList<TreasureKind> getSpecificVisibleTreasures() {
        return specificVisibleTreasures;
    }
    
    
    // Métodos públicos
    // En los siguientes dos métodos, hay que usar que hay dos posibles BadConsequence que trabajan con tesoros, los que conocen los objetos que quitan y los que sólo conocen la cantidad.
    // Para saber si un tesoro está contenido, tenemos que ver si está en la lista de tesoros(conoce los tesoros) o está vacía pero la cantidad de tesoros que quita no es nula(no conoce los tesoros)

    public void substractVisibleTreasure (Treasure treasure) {
        if(specificVisibleTreasures.remove(treasure) || (specificVisibleTreasures.isEmpty() && nVisibleTreasures != 0))
            nVisibleTreasures--;
    }
    
    public void substractHiddenTreasure (Treasure treasure) {
        if(specificHiddenTreasures.remove(treasure) || (specificHiddenTreasures.isEmpty()) && nHiddenTreasures != 0)
            nHiddenTreasures--;
    }
    
    /**
     * @brief En este método, se ajustan los malos rollos, teniendo en cuenta que ya se han restado los niveles. 
     * @param visibles Tesoros visibles del jugador
     * @param hidden Tesoros ocultos del jugador
     * @return Mal rollo ajustado
     */
    public BadConsequence adjustToFitTreasureLists(ArrayList<Treasure> visibles, ArrayList<Treasure> hidden){
            BadConsequence adjustedBC = null;
            
            // Si no conoce los tesoros específicos, trabaja con las cantidades, tomando el menor número
            // de tesoros a eliminar, entre los que indica el mal rollo y los que tiene el jugador. 
            if(specificVisibleTreasures.isEmpty() && specificHiddenTreasures.isEmpty()){
                
                int nVTreasures = (visibles.size() > nVisibleTreasures? nVisibleTreasures : visibles.size());
                int nHTreasures = (hidden.size() > nHiddenTreasures? nHiddenTreasures : hidden.size());
                
                adjustedBC = new BadConsequence(text, 0, nVTreasures, nHTreasures);
            }
            // Si conoce los tesoros, trabaja con los tipos de tesoros. 
            else{
                final ArrayList<TreasureKind> listHiddenTreasures = null;
                final ArrayList<TreasureKind> listVisibleTreasures = null;
                
                for (TreasureKind tKind : TreasureKind.values()) {
                    int min, min1 = 0, min2 = 0; 
                        // Trabajamos con tesoros visibles para cada TreasureKind
                        for(int i = 0; i < specificVisibleTreasures.size(); i++){
                            if(specificVisibleTreasures.get(i) == tKind)
                                min1++; 
                        }
                        for(int i = 0; i < visibles.size(); i++){
                            if(visibles.get(i).getType() == tKind)
                                min2++; 
                        }
                        // Tomamos el mínimo y añadimos ese número de TreasureKind
                        min = (min1 < min2? min1 : min2); 
                        for(int i = 0; i < min; i++){
                            listVisibleTreasures.add(tKind); 
                        }
                        
                        // Trabajamos con tesoros ocultos. 
                        for(int i = 0; i < specificHiddenTreasures.size(); i++){
                            if(specificHiddenTreasures.get(i) == tKind)
                                min1++; 
                        }
                        for(int i = 0; i < hidden.size(); i++){
                            if(hidden.get(i).getType() == tKind)
                                min2++; 
                        }
                        // Tomamos el mínimo y añadimos ese número de TreasureKind
                        min = (min1 < min2? min1 : min2); 
                        for(int i = 0; i < min; i++){
                            listHiddenTreasures.add(tKind); 
                        }
                }
                
                adjustedBC = new BadConsequence(text, levels, listVisibleTreasures, listHiddenTreasures);
            }
        return adjustedBC;
    }
    
    // Métodos auxiliares
    @Override
    public String toString() {
        return text +
               "\n\t" + (death? " DEATH." : (Integer.toString(levels) + " levels, " +
               Integer.toString(nHiddenTreasures) + " hidden treasures, " +
               Integer.toString(nVisibleTreasures) + " visible treasures.")) + "\n"; 
    }
}
    
