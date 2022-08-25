package mmk.model.item.equipable.enchantment.armor;

import mmk.model.item.equipable.enchantment.AEnchantment;
import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.personnage.APersonnage;

/**
 * représente l'enchantement Berserk, qui fait gagner de la force si l'on prend un coup supérieur à la moitier de notre vie
 */

public class Berserk extends AEnchantment {

    /**
     * constructeur de Berserk
     * @param armor l'armure à enchanter
     */
    public Berserk(IEnchantment armor) {
        super(armor);
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        if(degattheorique >= personnage.getMaxHp()*(50/100.0f))
            personnage.addStrength(1);

        return super.use(personnage, degattheorique);
    }
    
}
