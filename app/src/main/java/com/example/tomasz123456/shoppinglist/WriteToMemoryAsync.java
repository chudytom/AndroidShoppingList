package com.example.tomasz123456.shoppinglist;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class WriteToMemoryAsync extends AsyncTask<Void, Void, Boolean> {

    private final String filename;
    private final Context context;
    private List<ShoppingItem> shoppingList;

    public WriteToMemoryAsync(String filename, Context context, List<ShoppingItem> shoppingList){
        this.filename = filename;
        this.context = context;
        this.shoppingList = shoppingList;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
            objectStream.writeObject(shoppingList);
            objectStream.close();
            return true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return  false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
