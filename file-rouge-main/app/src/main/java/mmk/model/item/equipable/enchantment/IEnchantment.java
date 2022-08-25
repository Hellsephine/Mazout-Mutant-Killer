package mmk.model.item.equipable.enchantment;

import mmk.model.personnage.APersonnage;

/**
 * représente un Item enchantable
 */
public interface IEnchantment {

    /**
     * permet d'utiliser l'objet enchanté
     */
    public int use(APersonnage personnage, int degattheorique);

}
