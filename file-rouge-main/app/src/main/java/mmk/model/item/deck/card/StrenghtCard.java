package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

/**
 *  carte qui augment la force d'une valeur fixe
 */
public class StrenghtCard extends ACard {

    public StrenghtCard() {
        super(5);
    }

    @Override
    public void effect(Board board) {
        int strenghtBonus = 5;
        for (Hero hero : board.getHeros()) {
            hero.addStrength(strenghtBonus);
        }
    }

    @Override
    public int save() {
        return 0;
    }
}
