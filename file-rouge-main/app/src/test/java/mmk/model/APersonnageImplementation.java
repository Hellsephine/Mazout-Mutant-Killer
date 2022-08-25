package mmk.model;

import mmk.model.item.deck.Deck;
import mmk.model.item.equipable.Armor;
import mmk.model.item.equipable.Weapon;
import mmk.model.personnage.hero.Hero;

import java.util.HashMap;

public class APersonnageImplementation extends Hero {


    public APersonnageImplementation() {
        this.setName("paul");
        this.setMaxHp(100);
        this.setHp(100);
        this.setStrength(10);
        this.setPa(1);
        this.setWeapon(new WeaponImplementation());
        this.setArmor(new ArmorImplementation());
        this.deck = new DeckImplementation();
        this.setPngURL(" ");
    }

    @Override
    public int save() {
        return 0;
    }

    private class WeaponImplementation extends Weapon {


        public WeaponImplementation() {
            this.setName("poing");
            this.setCc(0);
            this.setDamage(10);
            this.setRange(1);
            this.setRarity(1);
        }
    }

    private class ArmorImplementation extends Armor {


        public ArmorImplementation() {
            this.setName("paper");
            this.setDefence(5);
            this.setRarity(1);
        }
    }

    private class DeckImplementation extends Deck {

        public DeckImplementation() {
            this.deck = new HashMap<>();
        }
    }
}


