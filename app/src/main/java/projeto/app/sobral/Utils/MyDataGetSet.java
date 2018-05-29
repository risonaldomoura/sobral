package projeto.app.sobral.Utils;

/**
 * Created by Daniel on 09/01/2018.
 */

public class MyDataGetSet {
    public String x, uid;

    private boolean b;

    public MyDataGetSet(){

    }

    public MyDataGetSet(String x, String uid){
        this.x = x;
        this.uid = uid;

    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

}

