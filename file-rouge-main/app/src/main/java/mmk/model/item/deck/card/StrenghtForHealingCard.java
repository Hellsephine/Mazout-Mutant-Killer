package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;
/**
 * Carte qui donne une regeneration de pv si la force est suffisante
 */
public class StrenghtForHealingCard extends ACard{



    public StrenghtForHealingCard() {
        super(6);
    }

    @Override
    public void effect(Board board) {
        if (this.nbTours != 0) {
            for (Hero hero : board.getHeros()) {
                if(hero.getStrength() > 20 && hero.getHp() < hero.getMaxHp()){
                    hero.addHp(2);
                }
            }
        }
        this.nbTours++;
    }

    @Override
    public int save() {
        return 0;
    }
}
