package com.restTry.restService.challenge;

import com.restTry.model.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
 * This controller needs to expose the following rest endpoints.  You need to fill in the implementation here
 *
 * Required REST Endpoints
 *
 *      /item                       Get all items
 *      /item?category=C            Get all items in category specified by Category shortName
 *      /item/{itemShortName}       Get item that matches the specified Item shortName
 */

@Profile("default")
@RestController

public class SearchRestControllerImpl {

    @Autowired
    SearchSvcInterface searchInt;
    private static final String FAILURE_RESULT="failed!!";

    @RequestMapping(method = RequestMethod.GET, path = "/item", produces = "application/json")
    public List<Item> getItems(@RequestParam(name = "category", required = false) String category) {
        List<Item> lst = new ArrayList<>();
        if (category==null){
            lst = searchInt.getItems();
        }else {
            lst = searchInt.getItems(category);
        }
        return lst;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/item/{itemShortName}", produces = "application/json")
    public List<Item> getItem(@PathVariable String itemShortName) {
        List<Item> lst = new ArrayList<>();
        System.out.println("itemShortName : " + itemShortName);
            lst = searchInt.getItem(itemShortName);

        return lst;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/item", consumes = "application/json")
    public String addItem(@RequestBody Item item ) {
        boolean result= false;
        System.out.println("item : " + item.toString());
        result = searchInt.addItem(item);

        return result?item.getId()+" added successfully!!" : FAILURE_RESULT;
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/item/{id}", consumes = "application/json")
    public String putItem(@PathVariable float id,@RequestBody Item item ) {
        boolean result= false;
        System.out.println("item : " + item.toString());
        result = searchInt.putItem(id,item);
        return result?item.getId()+" added successfully!!" : FAILURE_RESULT;
    }
    @RequestMapping(method = RequestMethod.DELETE, path = "/item/{id}")
    public String deleteItem(@PathVariable float id) {
        boolean result= false;
        System.out.println("itemId : " + id);
        result = searchInt.deleteItem(id);
        return result?id+" was deleted successfully!!" : FAILURE_RESULT;
    }
}
