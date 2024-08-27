package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LimitOrderAgent implements PriceListener {
    private ExecutionClient executionClient;
    public LimitOrderAgent(final ExecutionClient ec) {
        this.executionClient = ec;
    }


    @Override
    public void priceTick(String productId, BigDecimal price) throws ExecutionClient.ExecutionException {
        if(productId.equals("IBM")) {
            this.addOrder(true, productId,price, 100);
        }

    }

    public void addOrder(boolean buyFlag, String productId, BigDecimal price, int limit) throws ExecutionClient.ExecutionException{
        if(buyFlag && price.compareTo(BigDecimal.valueOf(limit)) <  0) {
            this.executionClient.buy(productId,price.multiply(BigDecimal.valueOf(1000)).setScale(0, RoundingMode.HALF_UP).intValue());
        } else {
            throw new ExecutionClient.ExecutionException("Price is More");
        }
    }

    public static void main(String[] args) throws ExecutionClient.ExecutionException {
        ExecutionClient client = new ExecutionClient();
        LimitOrderAgent limitOrderAgent = new LimitOrderAgent(client);
        limitOrderAgent.priceTick("IBM", BigDecimal.valueOf(101));
    }

}
