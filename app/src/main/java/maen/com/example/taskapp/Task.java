package maen.com.example.taskapp;

public class Task {

    private String Name;
    private String Explane;

    private boolean DoneORNot;

    public Task(String name, String explane, boolean doneORNot) {
        Name = name;
        Explane = explane;
        DoneORNot = doneORNot;
    }



    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }


    public String getExplane() {
        return Explane;
    }

    public void setExplane(String explane) {
        Explane = explane;
    }

    public boolean isDoneORNot() {
        return DoneORNot;
    }

    public void setDoneORNot(boolean doneORNot) {
        DoneORNot = doneORNot;
    }

    @Override
    public String toString() {
        return  Name  ;


    }
}
