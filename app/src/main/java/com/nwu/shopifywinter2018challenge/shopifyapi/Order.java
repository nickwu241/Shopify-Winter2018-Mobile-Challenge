package com.nwu.shopifywinter2018challenge.shopifyapi;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    //----------------------------------------------------------------------------------------------
    public final double total_price;
    public final String email;
    public final String currency;
    public final String financial_status;
    public final Customer customer;
    public final List<LineItem> line_items;

    //----------------------------------------------------------------------------------------------
    public Order(double total_price, String email, String currency, String financial_status,
                 Customer customer, List<LineItem> line_items) {
        this.total_price = total_price;
        this.email = email;
        this.currency = currency;
        this.financial_status = financial_status;
        this.customer = customer;
        this.line_items = line_items;
    }

    //----------------------------------------------------------------------------------------------
    public class LineItem implements Serializable {
        public final String price;
        public final long product_id;
        public final long quantity;
        public final String title;
        public final String name;

        public LineItem(String price, long product_id, long quantity, String title, String name) {
            this.price = price;
            this.product_id = product_id;
            this.quantity = quantity;
            this.title = title;
            this.name = name;
        }
    }

    //----------------------------------------------------------------------------------------------
    public class Customer implements Serializable {
        public final String first_name;
        public final String last_name;
        public final String name;
        public double total_spent;

        public Customer(String first_name, String last_name, String name, double total_spent) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.name = name;
            this.total_spent = total_spent;
        }
    }

    //----------------------------------------------------------------------------------------------
}
