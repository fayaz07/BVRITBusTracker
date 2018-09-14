package fz.bvritbustracker;

public class BugModel {
    private String bug,uid;

    public String getBug() {
        return bug;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BugModel(String bug, String uid) {
        this.bug = bug;
        this.uid = uid;
    }

    public BugModel() {
    }
}
