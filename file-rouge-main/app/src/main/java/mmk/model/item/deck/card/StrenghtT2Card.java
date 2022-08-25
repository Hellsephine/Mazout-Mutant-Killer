package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

/**
 * carte qui augment la force pendant 2 tours
 */

public class StrenghtT2Card extends ACard {

    public StrenghtT2Card() {
        super(3);
    }

    @Override
    public void effect(Board board) {
        int strenghtBonus = 5;
        if (this.nbTours == 0) {
            for (Hero hero : board.getHeros()) {
                hero.addStrength(strenghtBonus);
            }
        }
        if (this.nbTours == 2) {
            for (Hero hero : board.getHeros()) {
                hero.removeStrength(strenghtBonus);
            }
        }
        this.nbTours++;
    }

    @Override
    public int save() {
        return 0;
    }
}
