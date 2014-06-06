/**
 * TreasureView.java
 * @author Jose Carlos Entrena
 * @author Mario Román 
 * @author Óscar Bermúdez
 */

package GUI;
import Game.Treasure;

/**
 *
 * @author José Carlos Entrena
 * @author Mario Román
 * @author Óscar Bermúdez
 */
public class TreasureView extends javax.swing.JPanel {
    Treasure treasureModel;
    boolean selected = false;
    
    /**
     * Creates new form TreasureView
     */
    public TreasureView() {
        initComponents();
        setOpaque(selected);
    }
    
    public void setTreasure(Treasure t){
        treasureModel = t; 
        maxBonusLabel.setText(Integer.toString(treasureModel.getSpecialValue()));
        minBonusLabel.setText(Integer.toString(treasureModel.getBasicValue()));
        priceLabel.setText(Integer.toString(treasureModel.getGoldCoins()) + " $");
        treasureKindLabel.setText(treasureModel.getType().toString());
        treasureNameLabel.setText(treasureModel.getName()); 
        repaint();    
    }
    
    public Treasure getTreasure(){
        return treasureModel;
    }

    public boolean isSelected(){
        return selected;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        treasureNameLabel = new javax.swing.JLabel();
        treasureKindLabel = new javax.swing.JLabel();
        minBonusLabel = new javax.swing.JLabel();
        maxBonusLabel = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        minBonusTextLabel = new javax.swing.JLabel();
        maxBonusTextLabel = new javax.swing.JLabel();
        priceTextLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(236, 204, 172));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        treasureNameLabel.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        treasureNameLabel.setText("Treasure Name");

        treasureKindLabel.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        treasureKindLabel.setText("Treasure Kind");

        minBonusLabel.setText("0");

        maxBonusLabel.setText("0");

        priceLabel.setText("0 $");

        minBonusTextLabel.setText("Min Bonus:");

        maxBonusTextLabel.setText("Max Bonus:");

        priceTextLabel.setText("Price: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(priceTextLabel)
                                .addGap(18, 18, 18)
                                .addComponent(priceLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(minBonusTextLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(maxBonusTextLabel)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maxBonusLabel)
                            .addComponent(minBonusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(treasureKindLabel)
                            .addComponent(treasureNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(treasureNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(treasureKindLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minBonusLabel)
                    .addComponent(minBonusTextLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxBonusTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxBonusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceLabel)
                    .addComponent(priceTextLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        selected = !selected;
        setOpaque(selected);
        repaint();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel maxBonusLabel;
    private javax.swing.JLabel maxBonusTextLabel;
    private javax.swing.JLabel minBonusLabel;
    private javax.swing.JLabel minBonusTextLabel;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JLabel priceTextLabel;
    private javax.swing.JLabel treasureKindLabel;
    private javax.swing.JLabel treasureNameLabel;
    // End of variables declaration//GEN-END:variables
}
