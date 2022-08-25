package mmk.model.util;

import mmk.model.item.consumable.AConsumable;
import mmk.model.item.deck.card.ACard;
import mmk.model.item.deck.card.PaPairImpairCard;
import mmk.model.item.deck.card.RegenHpCard;
import mmk.model.item.deck.card.StrenghtBonusAttackCard;
import mmk.model.item.deck.card.StrenghtCard;
import mmk.model.item.deck.card.StrenghtForHealingCard;
import mmk.model.item.deck.card.StrenghtIfHurtCard;
import mmk.model.item.deck.card.StrenghtPairCard;
import mmk.model.item.deck.card.StrenghtT2Card;
import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.item.equipable.enchantment.armor.Thorns;
import mmk.model.item.equipable.enchantment.weapon.FireAspect;
import mmk.model.item.equipable.enchantment.weapon.Freezing;

public class Manager {
    
    public static IEnchantment setDecoration(int id, IEnchantment item){

        return switch (id) {
            case 1 -> new FireAspect(item);
            case 2 -> new Freezing(item);
            case 101 -> new Thorns(item);
            default -> throw new RuntimeException("pas d'enchantmenet correspondant");
        };

    }

    public static ACard createCard(int id) {
        return switch (id) {
            case 1 -> new RegenHpCard();
            case 2 -> new PaPairImpairCard();
            case 3 -> new StrenghtT2Card();
            case 4 -> new StrenghtPairCard();
            case 5 -> new StrenghtCard();
            case 6 -> new StrenghtForHealingCard();
            case 7 -> new StrenghtIfHurtCard();
            case 8 -> new StrenghtBonusAttackCard();
            default -> throw new RuntimeException("pas de carte correspondant");
        };
    }

    public static AConsumable createConsumable(int id) {
        return switch (id) {
            case 1 -> AConsumable.POTION_VIE;
            case 2 -> AConsumable.POTION_PA;
            case 3 -> AConsumable.POTION_CLEANSE;
            default -> throw new RuntimeException("pas de consomable correspondant");
        };
    }
}
