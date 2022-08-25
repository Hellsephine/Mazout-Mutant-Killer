package mmk.model.item.equipable.enchantment;

import mmk.model.personnage.APersonnage;
import mmk.model.util.ASavable;

/**
 * classe représentant un enchantement (fait partie du design pattern decorator)
 */
public abstract class AEnchantment extends ASavable implements IEnchantment {
    private IEnchantment equipable;

    /**
     * constructeur de AEnchantment
     * @param equipable l'objet à enchanter
     */
    public AEnchantment(IEnchantment equipable) {
        this.equipable = equipable;
    }

    public IEnchantment getEquipable() {
        return this.equipable;
    }

    @Override
    public int use(APersonnage personnage, int degattheorique) {
        return equipable.use(personnage,degattheorique);
    }

    public void setEquipable(IEnchantment equipable){
        this.equipable= equipable;
    }

    @Override
    public int save() {
        return ((ASavable)equipable).save();
    }

}
