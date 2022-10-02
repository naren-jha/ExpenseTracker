package com.phonepe.expensetracker.distribution;

import com.phonepe.expensetracker.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DistributionRepository {

    @Autowired
    private Database db;

    public List<DistributionStrategy> getDistributionStrategies() {
        return new ArrayList<>(db.getDistributionStrategies().values());
    }
}
