package sirobaba.testtask.restaurant.model.dao;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Section;

import java.util.List;

/**
 * Created by sirobaban on 27.07.2015.
 */
public interface SectionDAO {

    Section create(String title) throws ModelException;

    Section update(int id, String title) throws ModelException;

    void delete(int id) throws ModelException;

    Section findByID(int id) throws ModelException;

    List<Section> findAll() throws ModelException;

}
