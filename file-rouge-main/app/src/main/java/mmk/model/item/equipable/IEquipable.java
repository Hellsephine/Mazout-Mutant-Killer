package mmk.model.item.equipable;

import mmk.model.personnage.APersonnage;

/**
 * représente les objets qu'un personnage peut equiper
 */
public interface IEquipable {

    /**
     * permet d'equiper l'objet sur un personnage
     * @param target le personnage à equiper
     */
    void equip(APersonnage target);

    /**
     * permet de desequiper l'obget du personnage
     * @param target le personnage à desequiper
     */
    void unequip(APersonnage target);
}
