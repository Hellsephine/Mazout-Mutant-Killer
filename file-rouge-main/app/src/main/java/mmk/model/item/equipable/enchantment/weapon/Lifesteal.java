package mmk.model.item.equipable.enchantment.weapon;

import mmk.model.item.equipable.Weapon;
import mmk.model.item.equipable.enchantment.AEnchantment;
import mmk.model.personnage.APersonnage;

/**
 * représente l'enchangement Life steal, qui permet de récupérer des Pv selon un pourcentage de dégats infligé
 */
public class Lifesteal extends AEnchantment {

    /**
     * constructeur de LifeSteal
     * @param weapon l'arme à enchanter
     */
    public Lifesteal(Weapon weapon) {
        super(weapon);
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        if(degattheorique > 1){
            personnage.addHp((int) (degattheorique*(10/100.0f)));
        }
        return super.use(personnage, degattheorique);
        
    }
}
