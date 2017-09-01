package com.nwu.shopifywinter2018challenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nwu.shopifywinter2018challenge.shopifyapi.Order;
import com.nwu.shopifywinter2018challenge.shopifyapi.OrdersResponse;
import com.nwu.shopifywinter2018challenge.shopifyapi.SimpleShopifyAPI;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //----------------------------------------------------------------------------------------------
    private App mSession;
    private TextView napoleonBatzTotal, awesomeBronzeBagsSold;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSession = App.instance();

        napoleonBatzTotal = (TextView) findViewById(R.id.total_napoleon_batz);
        awesomeBronzeBagsSold = (TextView) findViewById(R.id.quantity_awesome_bronze_bag_sold);

        refresh(null);
    }

    //----------------------------------------------------------------------------------------------
    public void refresh(View view) {
        Map<String, String> params = new HashMap<>();
        params.put(SimpleShopifyAPI.KEY_ACCESS_TOKEN, App.ACCESS_TOKEN);

        mSession.getShopifyAPI().orders(params).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    toastNetworkError();
                    return;
                }

                // note: if this operation was more expensive, should do this on a separate thread
                double totalNapoleonBatz = 0;
                long quantityBag = 0;
                for (Order order : response.body().orders) {
                    Order.Customer c = order.customer;
                    if (((c != null && c.first_name.equals("Napoleon") && c.last_name.equals("Batz")))
                            && order.financial_status.equals("paid")) {
                        totalNapoleonBatz += order.total_price;
                    }
                    for (Order.LineItem lineItem : order.line_items) {
                        if (lineItem.name.contains("Awesome Bronze Bag")) {
                            quantityBag += lineItem.quantity;
                        }
                    }
                }

                String stringTotal = mSession.getCurrencyFormatter().format(totalNapoleonBatz);
                napoleonBatzTotal.setText(stringTotal);
                awesomeBronzeBagsSold.setText(String.valueOf(quantityBag));
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                toastNetworkError();
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    private void toastNetworkError() {
        Toast.makeText(getApplicationContext(),
                "Unable to fetch orders information",
                Toast.LENGTH_SHORT).show();
    }

    //----------------------------------------------------------------------------------------------
}
