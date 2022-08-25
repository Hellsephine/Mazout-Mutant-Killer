package mmk.controller;

import mmk.model.action.EActionState;
import mmk.model.personnage.APersonnage;
import mmk.model.personnage.hero.Hero;
import mmk.model.util.eventmanagement.EEventType;
import mmk.model.util.eventmanagement.EventManager;
import mmk.model.world.ModelAction;
import mmk.view.TerminalView;

public class TerminalController {

    private final ModelAction modelAction;
    private final Hero hero;

    public TerminalController(ModelAction modelAction) {
        this.modelAction = modelAction;
        this.hero = this.modelAction.getHero();
    }

    public void action(String input) {
        String[] split = input.split(" ");

        int[] inputs = new int[2];
        inputs[0] = Integer.parseInt(split[0]);
        if (split.length > 1) {
            inputs[1] = Integer.parseInt(split[1]);
        }

        int paLeft = 0;

        switch (inputs[0]) {
            case 1 :
                paLeft = modelAction.addPersonnageActionState(this.hero, EActionState.MOVE.setArguments(inputs[1]));
                break;
            case 2 :
                paLeft = modelAction.addPersonnageActionState(this.hero, EActionState.ATTACK);
                break;
            case 3 :
                paLeft = modelAction.addPersonnageActionState(this.hero, EActionState.CONSUME.setArguments(inputs[1]));
                break;
            case 4 :
                paLeft = modelAction.addPersonnageActionState(this.hero, EActionState.BLOCK);
                break;
        };

        if (paLeft <= 0)
            this.modelAction.play();

        EventManager.EVENT_MANAGER.notify(EEventType.ASK_TERMINAL_INPUT, null);
    }

}
