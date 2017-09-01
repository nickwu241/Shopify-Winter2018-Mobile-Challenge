package com.nwu.shopifywinter2018challenge;

import com.nwu.shopifywinter2018challenge.shopifyapi.Order;
import com.nwu.shopifywinter2018challenge.shopifyapi.OrdersResponse;
import com.nwu.shopifywinter2018challenge.shopifyapi.SimpleShopifyAPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class SimpleShopifyAPITest {
    private SimpleShopifyAPI shopify;

    @Before
    public void setup() {
        shopify = App.instance().getShopifyAPI();
    }

    @Test
    public void simple() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(SimpleShopifyAPI.KEY_ACCESS_TOKEN, App.ACCESS_TOKEN);

        Call<OrdersResponse> ordersJsonCall = shopify.orders(params);
        OrdersResponse response = ordersJsonCall.execute().body();
        Assert.assertNotNull(response);

        List<Order> orders = response.orders;
        Assert.assertNotNull(orders);
        Assert.assertTrue(orders.size() > 0);
    }


    @Test
    public void param_fields() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put(SimpleShopifyAPI.KEY_ACCESS_TOKEN, App.ACCESS_TOKEN);
        params.put(SimpleShopifyAPI.KEY_FIELD, "total_price,email,currency,customer,line_items");

        Call<OrdersResponse> ordersJsonCall = shopify.orders(params);
        OrdersResponse response = ordersJsonCall.execute().body();
        Assert.assertNotNull(response);

        List<Order> orders = response.orders;
        Assert.assertNotNull(orders);
        Assert.assertTrue(orders.size() > 0);

        double totalNapoleonBatz = 0;
        double totalNapoleonBatzFromTotalPrices = 0;
        long quanityAwesomeBronzeBags = 0;
        for (Order order : orders) {
            Order.Customer c = order.customer;
            if ((c != null && c.first_name.equals("Napoleon") && c.last_name.equals("Batz"))) {
                totalNapoleonBatz = order.customer.total_spent;
                // make sure we get prices in CAD or else we need to convert it
                Assert.assertTrue(order.currency.equals("CAD"));
            }

            if (order.email.equals("napoleon.batz@gmail.com") && order.financial_status.equals("paid")) {
                totalNapoleonBatzFromTotalPrices += order.total_price;
                Assert.assertTrue(order.currency.equals("CAD"));
            }
            for (Order.LineItem lineItem : order.line_items) {
                if (lineItem.name.contains("Awesome Bronze Bag")) {
                    quanityAwesomeBronzeBags += lineItem.quantity;
                }
            }
        }

        Assert.assertTrue(totalNapoleonBatz > 0);
        Assert.assertTrue(quanityAwesomeBronzeBags > 0);
    }
}
