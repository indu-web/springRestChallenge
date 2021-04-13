package com.restTry.restService.challenge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restTry.model.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchSvcImplTest {
    @InjectMocks
    SearchSvcImpl searchSvc;

    @Mock
    ObjectMapper mapper;

    @Mock
    File itemsFromItemPriceFile;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetItems_happyPath() throws IOException {
        Item item = new Item();
        item.setDescription("Test");
        item.setId(1);
        Mockito.when(mapper.readValue(Mockito.any(File.class), Mockito.any(TypeReference.class))).thenReturn(Arrays.asList(item));
        List<Item> items = searchSvc.getItems();
        Assert.assertNotNull(items);
        Assert.assertEquals(1, items.size());
    }
}
