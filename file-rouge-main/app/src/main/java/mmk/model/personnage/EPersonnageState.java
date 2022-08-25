package mmk.model.personnage;

/**
 * represente l'etat du joueur
 */
public enum EPersonnageState {

    NEUTRAL(0b0),
    POISON(0b1),
    BURN(0b1<<1),
    SLOW(0b1<<2),
    BLEEDING(0b1<<3),
    BLOCK(0b1<<4),
    FREEZING(0b1<<5);

    private int duration;
    public int value;

    EPersonnageState(int value){
        this.value = value;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
