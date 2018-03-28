package com.example.tomasz123456.shoppinglist;

import java.util.Comparator;

public class ShoppingItemNameComparator implements Comparator<ShoppingItem> {
    @Override
    public int compare(ShoppingItem item1, ShoppingItem item2) {
        return item1.getName().compareTo(item2.getName());
    }
}
