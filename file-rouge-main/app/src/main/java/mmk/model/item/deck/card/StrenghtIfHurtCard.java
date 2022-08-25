package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

/**
 * Carte qui ajoute de la force si l'on a moins de 50% de ses Pv Max
 */

public class StrenghtIfHurtCard extends ACard {

    public StrenghtIfHurtCard() {
        super(7);
    }

    @Override
    public void effect(Board board) {
        int strenghtBonus = 5;
        for (Hero hero : board.getHeros())
            if(hero.getHp() < (hero.getMaxHp()/2))
                hero.addStrength(strenghtBonus);
    }

    @Override
    public int save() {
        return 0;
    }
}
