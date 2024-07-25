package com.cqrs.core.domain;

import com.cqrs.core.events.BaseEvent;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.ArrayList;
import java.util.List;

import java.text.MessageFormat;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    // #region Getters and Setters

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public List<BaseEvent> getUncommitedChanges() {
        return changes;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void markChangesAsCommited() {
        changes.clear();
    }

    // #endregion
    // #region Api

    public void riseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }

    protected void applyChange(BaseEvent event, Boolean isNewEvent) {
        try {
            var method = this.getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            logger.log(
                    Level.WARNING,
                    MessageFormat.format("No apply method found in the aggregate for {0}", event.getClass().getName()));
        } catch (Exception e) {
            logger.log(
                    Level.SEVERE,
                    MessageFormat.format("Error applying event {0} to aggregate {1}", event.getClass().getName(),
                            this.getClass().getName()));
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    // #endregion

}
