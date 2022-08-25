package mmk.model.item.deck.card;

import mmk.model.personnage.hero.Hero;
import mmk.model.personnage.monster.Monster;
import mmk.model.world.Board;

public class PaPairImpairCard extends ACard {

    public PaPairImpairCard(){
        super(2);
    }

    @Override
    public void effect(Board board) {
        int paBonus = 1;
        if (this.nbTours % 2 == 0) {
            for (Hero hero : board.getHeros())
                hero.addPa(paBonus);

            if(this.nbTours != 0)
                for(Monster monster: board.getMonsters())
                    monster.removePa(paBonus);

        }
        else{
            for (Monster monster : board.getMonsters())
                monster.addPa(paBonus);

            for(Hero hero: board.getHeros())
                hero.removePa(paBonus);

        }
        this.nbTours++;
    }

    @Override
    public int save() {
        return 0;
    }
}
