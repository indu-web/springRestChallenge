package com.restTry.restService.challenge;


/*
 * Do not edit this interface.   Your SearchSvcInterface solution must implement this interface
 */

import java.io.File;
import java.util.List;

import com.restTry.model.Item;

public interface SearchSvcInterface {


    public void init(String itemPriceJsonFileName);

    //  Variant that takes a file instead of a fully qualified path name.
    public void init(File itemPriceJsonFile);


    public List<Item> getItems();

    public List<Item> getItems(String category);

    public List<Item> getItem(String itemShortName);

    public boolean addItem(Item item);

    public boolean putItem(float id, Item item);

    public boolean deleteItem(float id);
}

