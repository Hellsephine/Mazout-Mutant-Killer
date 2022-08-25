package mmk.model.personnage;

import mmk.model.action.EActionState;
import mmk.model.item.equipable.Armor;
import mmk.model.item.equipable.Weapon;
import mmk.model.item.equipable.enchantment.AEnchantment;
import mmk.model.item.equipable.enchantment.IEnchantment;
import mmk.model.util.ASavable;
import mmk.model.personnage.hero.Hero;
import mmk.model.personnage.monster.Monster;
import mmk.model.util.Vector2;
import mmk.model.world.Board;

import java.util.Arrays;

/**
 * classe abstraite représantant la base d'un personnage
 */
public abstract class APersonnage extends ASavable {

    //#region attributs
    protected int pa;
    protected int hp;
    protected String name;
    protected int strength;
    protected int maxHp;
    protected IEnchantment weapon;
    protected IEnchantment armor;
    protected String pngURL;
    protected EActionState[] actionState;
    protected int personnageState = EPersonnageState.NEUTRAL.value;
    protected int id=0;
    protected Vector2 direction= new Vector2();

    //#endregion


    public APersonnage() {
        this.actionState = new EActionState[10];
    }

    /**
     * renvoie vrai si le personnage est mort, faux sinon
     * @return vrai si le personnage est mort, faux sinon
     */
    public boolean isDead() {
        return this.getHp() <= 0;
    }

    /**
     * permet au personnage d'attaquer
     * @param board le plateau dans le quel le personnage evolue
     */
    public void attack(Board board) {
        APersonnage target = getInRange(board);
        if(target == null){
            return ;
        }

        int degat =this.weapon.use(target, 0);
        degat +=strength;

        target.takeDomage(this, degat);

    }

    /**
     * permet au personnage de prendre des degats
     * @param amount la quantité de dégats entrant
     */
    public void takeDomage(APersonnage attacker, int amount) {
        int degat =this.armor.use(attacker, amount);
        if((this.personnageState & EPersonnageState.BLOCK.value) >0){
            degat*=0.8;
        }
        if(degat>0){
            removeHp(degat);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * renvoie le premier personnage dans la range d'attaque, null si personne dans la range
     * @param board lel plateau dans le quel le personnage evolue
     * @return le permier personnage dans la range d'attaque, null si personne dans la range
     */

    public APersonnage getInRange(Board board){
        int range = this.getWeapon().getRange();
        Vector2 posTarget = board.getApersonnagePosition(this).add(this.direction);

        for(int j =1; j<=range; j++){
            APersonnage target = board.getAPersonnage(posTarget);
            if (target != null)
                return target;
            posTarget.add(this.direction);
        }

        return null;
    }

    public int getType() {
        if (this instanceof Hero)
            return 1;
        else if (this instanceof Monster)
            return 2;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof APersonnage personnage))
            return false;

        if (personnage.getHp() != this.getHp())
            return false;
        if (personnage.getPa() != this.getPa())
            return false;
        if (personnage.getMaxHp() != this.getMaxHp())
            return false;
        if (!personnage.getArmor().equals(this.getArmor()))
            return false;
        if (!personnage.getWeapon().equals(this.getWeapon()))
            return false;/*
        if (personnage.getActionState(). this.getActionState())
            return false;*/
        if (!personnage.getPngURL().equals(this.getPngURL()))
            return false;
        if (!personnage.getName().equals(this.getName()))
            return false;
        return true;
    }

    //#region adder/remover
    public void addPa(int amount) {
        this.pa = this.getPa()+amount;
    }

    public void removePa(int amount) {
        if (this.getPa()-amount > 0)
            this.pa = this.getPa()-amount;
    }

    public void addStrength(int amount) {
        this.setStrength(this.getStrength() + amount);
    }

    public void removeStrength(int amount) {
        this.setStrength(this.getStrength() - amount);
    }

    public void addHp(int amount) {
        this.hp = Math.min(this.getHp() + amount, this.getMaxHp());
    }

    public void removeHp(int amount) {
        this.hp = this.getHp()-amount;
    }

    public int addActionState(EActionState actionState) {
        for (int i=0;i<this.getPa();i++) {
            if (this.actionState[i] == null) {
                this.actionState[i] = actionState;
                return this.getPa()-i-1;
            }
        }
        return 0;
    }

    public void resetActionState() {
        Arrays.fill(this.actionState, null);
    }
    //endregion

    //#region getter/setter


    protected void setPa(int pa) {
        this.pa = pa;
    }


    protected void setPersonnageState(int personnageState) {
        this.personnageState = personnageState;
    }


    public int getPa() {
        return pa;
    }

    public int getHp() {
        return hp;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public Weapon getWeapon() {
        if (this.weapon instanceof Weapon)
            return (Weapon) this.weapon;
        AEnchantment enchantment = (AEnchantment) this.weapon;
        return (Weapon) enchantment.getEquipable();
    }

    public void setWeapon(IEnchantment weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        if (this.armor instanceof Armor)
            return (Armor) this.armor;
        AEnchantment enchantment = (AEnchantment) this.armor;
        return (Armor) enchantment.getEquipable();
    }

    public void setArmor(IEnchantment armor) {
        this.armor=armor;
    }

    protected String getPngURL() {
        return pngURL;
    }

    protected void setPngURL(String pngURL) {
        this.pngURL = pngURL;
    }

    public EActionState[] getActionState() {
        return this.actionState;
    }

    public int getStrength() {
        return strength;
    }

    protected void setStrength(int strength) {
        this.strength = strength;
    }

    public int getPersonnageState() {
        return personnageState;
    }

    public void addPersonnageState(EPersonnageState personnageState) {
        this.personnageState |= personnageState.value;
    }

    public void removePersonnageState(EPersonnageState personnageState) {
        this.personnageState ^= personnageState.value;
    }

    public boolean isPersonnageState(EPersonnageState personnageState) {
        return (personnageState == EPersonnageState.NEUTRAL && this.getPersonnageState() == 0)
                || ((personnageState.value&this.getPersonnageState())>0);
    }

    public void removeAllPersonnageState() {
        this.personnageState = 0;
    }
    public IEnchantment getIEnchantmentWeapon(){
        return this.weapon;
    }
    public IEnchantment getIEnchantmentArmor(){
        return this.armor;
    }
    public Vector2 getDirection(){
        return this.direction;
    }
    public void setDirection(Vector2 v){
        this.direction.setX(v.getX());
    }
    public void setHp(int hp){
        this.hp=hp;
    }
    //#endregion
}