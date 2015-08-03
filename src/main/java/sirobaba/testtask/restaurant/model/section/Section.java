package sirobaba.testtask.restaurant.model.section;

/**
 * Created by sirobaban on 27.07.2015.
 */
public class Section {

    private int id;
    private String title;

    public Section(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
