package org.afob.limit;

import org.afob.execution.ExecutionClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LimitOrder extends LimitOrderAgent {
    private ExecutionClient executionClient;
    public LimitOrder(ExecutionClient ec) {
        super(ec);
        this.executionClient = ec;
    }

    @Override
    public void addOrder(boolean buyFlag, String productId, BigDecimal price, int limit) throws ExecutionClient.ExecutionException {
        if(buyFlag && price.compareTo(BigDecimal.valueOf(limit)) <  0) {
            this.executionClient.buy(productId,price.multiply(BigDecimal.valueOf(1000)).setScale(0, RoundingMode.HALF_UP).intValue());
        } else {
            throw new ExecutionClient.ExecutionException("Price is More");
        }
    }
}
