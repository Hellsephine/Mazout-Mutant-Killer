package mmk.model.item.consumable;

import mmk.model.personnage.hero.Hero;

/**
 * représente la Potion de point d'action, qui permet d'avoir plus de point d'action
 */
public class PotionPA extends AConsumable {

    protected PotionPA() {
        super(2);
    }


    @Override
    public int save() {
        return 0;
    }

    @Override
    public void consume(Hero hero) {
        int paBonus = 1;
        hero.addPa(paBonus);
    }
}


    