package ch.ronoli.tensing.models;

/**
 * Created by nathic on 26.04.2015.
 */
public class Exercise {

    private long id;
    private String name;
    private String text;
    private String description;
    private String thumbnail;
    private String link;
    private Category category;

    public Exercise(){}

    public Exercise(String name, String text, String description, String thumbnail, String link, Category category){
        this.name = name;
        this.text = text;
        this.description = description;
        this.thumbnail = thumbnail;
        this.link = link;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return getName();
    }
}
