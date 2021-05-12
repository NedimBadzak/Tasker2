package ba.unsa.etf.rpr;

import static ba.unsa.etf.rpr.TerminalUtils.*;

public class Task {
    String text;
    String grupa;
    Boolean completed;
    int id;

    public Task(String text, String grupa, Boolean completed, int id) {
        this.text = text;
        this.grupa = grupa;
        if(completed != null) this.completed = completed;
        else this.completed = false;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String returner = "";
        returner += ANSI_BLUE + id + ".\t" + ANSI_RESET;
        returner += Boolean.TRUE.equals(completed) ? ANSI_CHECKMARK : ANSI_CROSS;
        returner += "\t" + text;
        if(grupa != null) {
            returner += "\t" + ANSI_RED + "@" + grupa + ANSI_RESET;
        }
        return returner;
    }
}
