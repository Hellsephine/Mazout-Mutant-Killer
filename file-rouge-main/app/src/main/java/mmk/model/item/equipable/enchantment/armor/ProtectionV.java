package mmk.model.item.equipable.enchantment.armor;

import mmk.model.item.equipable.enchantment.AEnchantment;
import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.personnage.APersonnage;

/**
 * représente l'enchantement Protection , qui réduit les dégat de 5 points
 */
public class ProtectionV extends AEnchantment {

    /**
     * constructeur de ProtectionV
     * @param armor l'armure à enchanter
     */
    public ProtectionV(IEnchantment armor) {
        super(armor);
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        degattheorique = degattheorique - 5;
        return super.use(personnage, degattheorique);
    }
}