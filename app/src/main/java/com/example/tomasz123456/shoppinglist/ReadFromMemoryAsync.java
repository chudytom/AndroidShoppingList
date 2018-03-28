package com.example.tomasz123456.shoppinglist;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadFromMemoryAsync extends AsyncTask<Void, Void, List<ShoppingItem>> {
    private final String filename;
    private Context context;
    List<ShoppingItem> shoppingItems = new ArrayList<>();

    public interface AsyncResponse {
        void processFinish(List<ShoppingItem> output);
    }

    public AsyncResponse delegate = null;

    public ReadFromMemoryAsync(String filename, Context context, AsyncResponse delegate){
        this.filename = filename;
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected List<ShoppingItem> doInBackground(Void... voids) {
        try{
            shoppingItems = new ArrayList<>();
            FileInputStream inputStream = context.openFileInput(filename);
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            shoppingItems = (List<ShoppingItem>) objectStream.readObject();
            objectStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  shoppingItems;
    }

    @Override
    protected void onPostExecute(List<ShoppingItem> result){
        delegate.processFinish(result);
    }
}
