package com.banking.cqrs_core.domain;

import com.banking.cqrs_core.events.BaseEvent;
import com.banking.cqrs_core.messages.Message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChanges(BaseEvent event, boolean isNewEvent) {
        try {
            var method = getClass().getMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException methodException) {
            logger.log(Level.WARNING, MessageFormat.format("El metodo apply no fue encontrado para {0}", event.getClass().getName()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erroes aplicando el evento al aggregate", e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        applyChanges(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChanges(event, false));
    }
}
