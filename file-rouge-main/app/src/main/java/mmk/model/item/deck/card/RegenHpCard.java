package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.world.Board;

public class RegenHpCard extends ACard {
    private boolean used = false;

    public RegenHpCard() {
        super(1);
    }

    @Override
    public void effect(Board board) {
        if(!this.used){
            for (Hero hero : board.getHeros()){
                if(hero.getHp()< hero.getMaxHp()*(30/100.0f)){
                    hero.addHp((int) (hero.getMaxHp()*(20/100.0f)));
                }
            }
            this.used = true;
        }
    }

    @Override
    public int save() {
        return 0;
    }
}
