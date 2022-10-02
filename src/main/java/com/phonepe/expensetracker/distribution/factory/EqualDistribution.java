package com.phonepe.expensetracker.distribution.factory;

import com.phonepe.expensetracker.distribution.dto.DistributeExpenseInput;
import com.phonepe.expensetracker.distribution.dto.DistributionTransaction;
import com.phonepe.expensetracker.distribution.dto.DistributionTypeResponse;
import com.phonepe.expensetracker.distribution.dto.UserSpend;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Data
public class EqualDistribution implements Distribution {

    @Override
    public DistributionTypeResponse distributeExpenses(DistributeExpenseInput distributeExpenseInput) {

        double amount = distributeExpenseInput.getTotalAmount();
        log.debug("Distributing amount {} by {} method", amount, this.distributionType());
        int numUsers = distributeExpenseInput.getUsers().size();
        double perUserShare = amount / numUsers;
        log.debug("Per user share is {}", perUserShare);

        List<UserSpend> userSpends = distributeExpenseInput.getUserSpends();
        Collections.sort(userSpends, (a, b) -> Double.compare(a.getSpend(), b.getSpend()));

        List<DistributionTransaction> distributionTransactions = new ArrayList<>();
        int i = 0, j = userSpends.size() - 1;
        while (i < j) {
            UserSpend fromUser = userSpends.get(i);
            UserSpend toUser = userSpends.get(j);
            if (fromUser.getSpend() == perUserShare) {
                ++i;
                log.debug("User {} spend is balanced. Moving to next user", fromUser.getUser());
                continue;
            }
            if (toUser.getSpend() == perUserShare) {
                --j;
                log.debug("User {} spend is balanced. Moving to next user", toUser.getUser());
                continue;
            }

            double maxTargetUserCanReceive = toUser.getSpend() - perUserShare;
            double maxSrcUserCanPay = perUserShare - fromUser.getSpend();
            double exchangeAmount = Math.min(maxSrcUserCanPay, maxTargetUserCanReceive);
            fromUser.setSpend(fromUser.getSpend() + exchangeAmount);
            toUser.setSpend(toUser.getSpend() - exchangeAmount);

            DistributionTransaction distributionTransaction = DistributionTransaction
                    .builder()
                    .fromUser(fromUser.getUser())
                    .toUser(toUser.getUser())
                    .amount(exchangeAmount)
                    .build();
            log.debug("Amount {} to be exchanged from user {} to user {}", exchangeAmount, fromUser.getUser(), toUser.getUser());
            distributionTransactions.add(distributionTransaction);
        }

        return DistributionTypeResponse
                .builder()
                .distributionTransactions(distributionTransactions)
                .amountDivided(amount)
                .build();
    }

    @Override
    public DistributionType distributionType() {
        return DistributionType.EQUAL;
    }
}
