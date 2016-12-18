package ua.ck.tars.parsehtml.model;

public class Word {

    private int id;
    private String name;
    private String translation;

    public Word(int id, String name, String translation) {
        this.id = id;
        this.name = name;
        this.translation = translation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

}
