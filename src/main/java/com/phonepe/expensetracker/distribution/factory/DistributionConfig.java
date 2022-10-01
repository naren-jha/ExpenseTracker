package com.phonepe.expensetracker.distribution.factory;

import com.phonepe.expensetracker.Database;
import com.phonepe.expensetracker.distribution.DistributionRepository;
import com.phonepe.expensetracker.distribution.DistributionStrategy;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class DistributionConfig {

    private final List<Distribution> distributions;
    private Database db;

    @Bean
    public Map<DistributionType, Distribution> distributionStrategyMap() {
        Map<DistributionType, Distribution> distributionStrategyMap = new EnumMap<>(DistributionType.class);
        distributions.forEach(distributionStrategy -> distributionStrategyMap.put(distributionStrategy.distributionType(), distributionStrategy));
        return distributionStrategyMap;
    }

    @Bean
    CommandLineRunner commandLineRunner(DistributionRepository distributionRepository) {
        DistributionStrategy equalDS = DistributionStrategy
                .builder()
                .distributionType(DistributionType.EQUAL)
                .order(0)
                .build();
        DistributionStrategy exactDS = DistributionStrategy
                .builder()
                .distributionType(DistributionType.EXACT)
                .order(0)
                .build();
        DistributionStrategy percentageDS = DistributionStrategy
                .builder()
                .distributionType(DistributionType.PERCENTAGE)
                .order(0)
                .build();

        return args -> {
            db.addDistributionStrategies(List.of(equalDS, exactDS, percentageDS));
        };
    }
}