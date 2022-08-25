package mmk.model.item.consumable;

import mmk.model.personnage.hero.Hero;

/**
 * représente la Potion de purge d'effet, qui permet d'enlever les effet appliquer sur le héro
 */
public class PotionCleanse extends AConsumable{

    protected PotionCleanse() {
        super(3);
    }
 
    @Override
    public int save() {
        return 0;
    }

    @Override
    public void consume(Hero hero) {
        hero.removeAllPersonnageState();
    }
    
}
