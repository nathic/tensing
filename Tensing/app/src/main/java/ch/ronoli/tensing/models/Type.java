package ch.ronoli.tensing.models;

/**
 * Created by nathic on 26.04.2015.
 */
public class Type {
    private long id;
    private String name;

    public Type(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
