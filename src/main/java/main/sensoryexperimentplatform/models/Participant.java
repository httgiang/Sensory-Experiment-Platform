package main.sensoryexperimentplatform.models;

public class Participant {
    String uid;
    String created;
    public Participant(String uid, String created){
        this.uid = uid;
        this.created = created;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}