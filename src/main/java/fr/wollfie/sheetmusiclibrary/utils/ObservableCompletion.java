package fr.wollfie.sheetmusiclibrary.utils;

import javafx.beans.property.*;

public class ObservableCompletion {
    
    private final ObjectProperty<Status> completed = new SimpleObjectProperty<>(Status.PENDING);
    public ReadOnlyObjectProperty<Status> statusProperty() { return completed; }
    public void setSuccess() { this.completed.set(Status.SUCCESS); }
    public void setFailed() { this.completed.set(Status.FAILURE); }
    public void setFromBool(boolean success) { this.completed.set(success ? Status.SUCCESS : Status.FAILURE); }
    
    public final IntegerProperty completion = new SimpleIntegerProperty(0);
    public ReadOnlyIntegerProperty completionProperty() { return completion; }
    public void setCompletion(int completion) { this.completion.set(completion); }

    public ObservableCompletion() { }
    
    private enum Status {
        PENDING, SUCCESS, FAILURE
    }
}
