package com.example.tomasz123456.shoppinglist;

import java.util.Comparator;

public class ShoppingItemCountComparator implements Comparator<ShoppingItem>{
    @Override
    public int compare(ShoppingItem o1, ShoppingItem o2) {
        return o1.getCount() - o2.getCount();
    }
}
