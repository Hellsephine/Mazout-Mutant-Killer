package mmk.model.util.eventmanagement;

public interface EventListener {

    /**
     * permet d'etre notifier quand un event est lancé
     * @param eventType le type de l'event
     * @param data les données associées
     */
    public void update(EEventType eventType, Object data);
}
