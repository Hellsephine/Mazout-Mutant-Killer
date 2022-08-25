package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

/**
 *  carte qui augment la force pendant les tours paires
 */

public class StrenghtPairCard extends ACard {

    public StrenghtPairCard(){
        super(4);
    }

    @Override
    public void effect(Board board) {
        int strenghtBonus = 5;
        if (this.nbTours % 2 == 0) {
            for (Hero hero : board.getHeros()) {
                hero.addStrength(strenghtBonus);
            }
        }
        else{
            for(Hero hero: board.getHeros()){
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