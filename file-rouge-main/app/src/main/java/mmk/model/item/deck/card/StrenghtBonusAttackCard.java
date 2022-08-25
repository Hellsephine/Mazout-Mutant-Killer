package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

/**
 * Carte qui donne un Pa si nous avons suffisament de Force
 */

public class StrenghtBonusAttackCard extends ACard {
    

    public StrenghtBonusAttackCard() {
        super(8);
    }

    @Override
    public void effect(Board board) {
        for (Hero hero : board.getHeros()) {
            if(hero.getStrength() > 20){
                hero.addPa(1);
            }
        }
    }

    @Override
    public int save() {
        return 0;
    }
}
