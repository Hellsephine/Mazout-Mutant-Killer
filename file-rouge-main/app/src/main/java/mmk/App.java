package mmk;


import mmk.controller.TerminalController;
import mmk.model.util.DBConnection;
import mmk.model.util.eventmanagement.EEventType;
import mmk.model.util.eventmanagement.EventManager;
import mmk.model.world.ModelAction;
import mmk.view.TerminalView;

public class App {

    public static void main(String[] args) {
        DBConnection.init();
        DBConnection.beginTransaction();

        ModelAction modelAction = new ModelAction();
        TerminalController controller = new TerminalController(modelAction);
        TerminalView view = new TerminalView(controller);

        EventManager.EVENT_MANAGER.subscribe(EEventType.MODIFY_APERSONNAGE_BOARD, view);
        EventManager.EVENT_MANAGER.subscribe(EEventType.ASK_TERMINAL_INPUT, view);

        modelAction.notifyNew();
        view.getInput();

    }
}
