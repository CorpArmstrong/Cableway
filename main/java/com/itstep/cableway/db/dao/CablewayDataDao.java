package com.itstep.cableway.db.dao;

import com.itstep.cableway.db.entities.CablewayData;
import java.util.List;

public interface CablewayDataDao {

    void create(CablewayData cablewayData);

    void save(CablewayData cablewayData);

    void update(CablewayData cablewayData);

    void delete(long cablewayDataId);

    List<CablewayData> findAll();

    CablewayData findById(long id);

    int calculateNumCableways();
}
