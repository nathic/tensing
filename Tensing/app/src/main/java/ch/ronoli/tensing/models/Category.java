package ch.ronoli.tensing.models;

/**
 * Created by nathic on 26.04.2015.
 */
public class Category {
    private long id;
    private String name;
    private Type type;

    public Category(){}

    public Category(String name, Type type){
        this.name = name;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
