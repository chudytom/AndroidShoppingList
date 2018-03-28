package com.example.tomasz123456.shoppinglist;

import java.util.ArrayList;
import java.util.List;

public class ShoppingItemsProvider {
    private List<ShoppingItem> items = new ArrayList<>();

    public List<ShoppingItem> GetSampleItems(){
        items.clear();
        addSampleData();
        return items;
    }

    private void addSampleData() {
        items.add(new ShoppingItem("Mleko", 5));
        items.add(new ShoppingItem("Frytki", 10));
        items.add(new ShoppingItem("Pizza", 20));
        items.add(new ShoppingItem("Chleb", 3));
        items.add(new ShoppingItem("Bułki", 5));
        items.add(new ShoppingItem("Pomarańcze", 15));
        items.add(new ShoppingItem("Banany", 12));
        items.add(new ShoppingItem("Jogurt", 4));
    }

}
