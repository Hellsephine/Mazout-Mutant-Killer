package mmk.model.item.consumable;

import mmk.model.personnage.hero.Hero;

/**
 * représente la Potion de vie, qui permet de regagner des Hp
 */
public class PotionVie extends AConsumable {
    
    protected PotionVie() {
        super(1);
    }

    @Override
    public int save() {
        return 0;
    }

    @Override
    public void consume(Hero hero) {
        hero.addHp(10);
    }
    
}
