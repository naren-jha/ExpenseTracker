package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.SortedMap;

@Repository
public class DistributionRepository {

    @Autowired
    private Database db;


    public SortedMap<Integer, DistributionStrategy> getDistributionStrategies() {
        return db.getDistributionStrategies();
    }
}
