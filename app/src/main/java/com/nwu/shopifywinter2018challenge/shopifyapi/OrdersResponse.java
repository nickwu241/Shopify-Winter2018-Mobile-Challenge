package com.nwu.shopifywinter2018challenge.shopifyapi;

import java.io.Serializable;
import java.util.List;

public class OrdersResponse implements Serializable {
    public final List<Order> orders;

    public OrdersResponse(List<Order> orders) {
        this.orders = orders;
    }
}
