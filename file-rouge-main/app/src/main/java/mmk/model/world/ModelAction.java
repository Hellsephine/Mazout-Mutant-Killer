package mmk.model.world;

import mmk.model.action.EActionState;
import mmk.model.personnage.APersonnage;
import mmk.model.personnage.hero.Hero;
import mmk.model.personnage.monster.Monster;
import mmk.model.util.eventmanagement.EEventType;
import mmk.model.util.eventmanagement.EventManager;
import mmk.model.util.Vector2;

public class ModelAction {

    private final Board board;

    public ModelAction() {
        this.board = new Board(1);
        EventManager.EVENT_MANAGER.notify(EEventType.MODIFY_APERSONNAGE_BOARD, this.board.apersonnageBoard);
        EventManager.EVENT_MANAGER.notify(EEventType.MODIFY_BACKGROUND_BOARD, this.board.backgroundBoard);
    }

    public void play() {

        for (Hero hero : this.board.getHeros()) {
            if (!hero.isDead()) {
                EActionState[] actionStates = hero.getActionState();
                int pa = hero.getPa();
                for (int i = 0; i < pa; i++) {
                    actionStates[i].action.doo(hero, board, actionStates[i].getArguments());
                    EventManager.EVENT_MANAGER.notify(EEventType.MODIFY_APERSONNAGE_BOARD, this.board.apersonnageBoard);
                }
                hero.resetActionState();
            } else {
                throw new RuntimeException("Hero dead");
            }
        }


        for (Monster monster : this.board.getMonsters()) {
            if (!monster.isDead()) {
                int pa = monster.getPa();
                for (int i = 0; i < pa; i++) {
                    monster.setActionState(this.board);
                    EActionState[] actionStates = monster.getActionState();
                    actionStates[0].action.doo(monster, board, actionStates[0].getArguments());
                    EventManager.EVENT_MANAGER.notify(EEventType.MODIFY_APERSONNAGE_BOARD, this.board.apersonnageBoard);
                }
            } else {
                monster.IsMonsterDead();
                this.board.removeAPersonnage(monster);
                EventManager.EVENT_MANAGER.notify(EEventType.MODIFY_APERSONNAGE_BOARD, this.board.apersonnageBoard);
            }
        }
    }

    public Hero getHero() {
        return this.board.getHeros()[0];
    }

    public int addPersonnageActionState(APersonnage target, EActionState state) {
        return target.addActionState(state);
    }

    public void addPersonnage(APersonnage personnage, Vector2 position) {
        board.addPersonnage(personnage, position);
    }

    public void save() {
        this.board.save();
    }

    public void notifyNew() {
        EventManager.EVENT_MANAGER.notify(EEventType.MODIFY_APERSONNAGE_BOARD, this.board.apersonnageBoard);
    }

}
