package com.restTry.restService.challenge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.restTry.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Provide your implementation of the SearchSvcImpl here.
 * Also annotate your methods with Rest end point wrappers as required in the problem statement
 *
 * You can create any auxiliary classes or interfaces in this package if you need them.
 *
 * Do NOT add annotations as a Bean or Component or Service.   This is being handled in the custom Config class
 * PriceAdjustConfiguration
 */

public class SearchSvcImpl implements SearchSvcInterface {

    private ObjectMapper mapper = new ObjectMapper();
    private  File itemsFromItemPriceFile ;

    @Override
    public void init(String itemPriceJsonFileName) {

    }

    @Override
    public void init(File itemPriceJsonFile) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        itemsFromItemPriceFile = itemPriceJsonFile;

    }

    @Override
    public List<Item> getItems() {
        List<Item> lst = new ArrayList<>();
        try{
        lst = mapper.readValue(itemsFromItemPriceFile, new TypeReference<List<Item>>() {
        });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lst;
    }

    @Override
    public List<Item> getItems(String category) {
        List<Item> lst = new ArrayList<>();
        List<Item> lstCat = new ArrayList<>();
        try{
            lst = mapper.readValue(itemsFromItemPriceFile, new TypeReference<List<Item>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Item i: lst) {
            if(i.getCategory_short_name().equals(category)){
                lstCat.add(i);
            }
        }

        return lstCat;
    }

    @Override
    public boolean addItem(Item item) {
        //List<Item> lst = new ArrayList<>();
        try{
            JsonNode jsonNode = mapper.readTree(itemsFromItemPriceFile);
            ArrayNode arrayNode = ((ArrayNode) jsonNode).addPOJO(item);
            mapper.writerWithDefaultPrettyPrinter().writeValue(itemsFromItemPriceFile, arrayNode);
            System.out.println("data added to an existing employee1.json file successfully");
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }



        return false;
    }

    @Override
    public boolean putItem(float id, Item item) {
        List<Item> lst = new ArrayList<>();
        try{
            final JsonNode json = mapper.readTree(itemsFromItemPriceFile);

            if (json.isArray()) {
                for ( Iterator<JsonNode> i = json.elements(); i.hasNext(); ) {
                     JsonNode jsonNode = i.next();

                    if (id == jsonNode.get("id").floatValue()) {
                        i.remove();
                        ArrayNode arrayNode = ((ArrayNode) json).addPOJO(item);
                        mapper.writerWithDefaultPrettyPrinter().writeValue(itemsFromItemPriceFile, arrayNode);
                        return true;
                    }
                }
            }
         } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteItem(float id) {
        try{
            final JsonNode json = mapper.readTree(itemsFromItemPriceFile);

            if (json.isArray()) {
                for ( Iterator<JsonNode> i = json.elements(); i.hasNext(); ) {
                     JsonNode jsonNode = i.next();

                    if (id == jsonNode.get("id").floatValue()) {
                        i.remove();
                        mapper.writerWithDefaultPrettyPrinter().writeValue(itemsFromItemPriceFile,json);
                       return true;
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Item> getItem(String itemShortName) {
        List<Item> lst = new ArrayList<>();
        List<Item> lstCat = new ArrayList<>();
        try{
            lst = mapper.readValue(itemsFromItemPriceFile, new TypeReference<List<Item>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Item i: lst) {
            if(i.getShort_name().equals(itemShortName)){
                lstCat.add(i);
            }
        }

        return lstCat;
    }
}
