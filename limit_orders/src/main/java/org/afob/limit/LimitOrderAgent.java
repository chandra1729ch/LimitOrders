package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;

public class LimitOrderAgent implements PriceListener {
    private ExecutionClient executionClient;
    public LimitOrderAgent(final ExecutionClient ec) {
        this.executionClient = ec;
    }


    @Override
    public void priceTick(String productId, BigDecimal price) throws ExecutionClient.ExecutionException {
        if(productId.equals("IBM")) {
            LimitOrder limitOrder = new LimitOrder(executionClient);
            limitOrder.addOrder(true, productId,price, 100);
        }

    }

    public static void main(String[] args) throws ExecutionClient.ExecutionException {
        ExecutionClient client = new ExecutionClient();
        LimitOrderAgent limitOrderAgent = new LimitOrderAgent(client);
        limitOrderAgent.priceTick("IBM", BigDecimal.valueOf(101));
    }

}
