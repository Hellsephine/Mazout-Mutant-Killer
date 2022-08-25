package mmk.model.util.eventmanagement;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {

    public static final EventManager EVENT_MANAGER = new EventManager();

    private HashMap<EEventType, ArrayList<EventListener>> listeners;

    public EventManager() {
        this.listeners = new HashMap<>();
    }

    /**
     * permet d'ajouter un listener
     * @param eventType le type d'event
     * @param listener le listener
     */
    public void subscribe(EEventType eventType, EventListener listener) {
        this.listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        this.listeners.get(eventType).add(listener);
    }

    /**
     * permet d'enlever un listener
     * @param eventType le type d'event
     * @param listener le listener
     */
    public void unsubscribe(EEventType eventType, EventListener listener) {
        this.listeners.get(eventType).remove(listener);
    }

    /**
     * permet de notifier les listeners
     * @param eventType le type d'evenet
     * @param data les données associées
     */
    public void notify(EEventType eventType, Object data) {
        if (this.listeners.get(eventType) != null)
            for (EventListener listener : this.listeners.get(eventType)) {
                listener.update(eventType, data);
            }
    }
}
