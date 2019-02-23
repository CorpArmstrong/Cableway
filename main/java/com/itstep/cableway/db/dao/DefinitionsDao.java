package com.itstep.cableway.db.dao;

import com.itstep.cableway.db.entities.Definitions;
import java.util.List;

public interface DefinitionsDao {

    void create(Definitions definitions);

    void save(Definitions definitions);

    void update(Definitions definitions);

    void delete(long definitionId);

    List<Definitions> findAll();

    Definitions findById(long id);
}
