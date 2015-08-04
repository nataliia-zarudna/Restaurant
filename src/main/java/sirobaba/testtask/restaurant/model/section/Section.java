package sirobaba.testtask.restaurant.model.section;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sirobaban on 27.07.2015.
 */
public class Section {

    private int id;

    @NotEmpty(message = "Section title must not be empty")
    private String title;

    public Section() {}

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
