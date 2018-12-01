package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.OrderResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest {

    private  Inventory test;
    private  BookInventoryInfo[] inventoryInfos=null;

    @Before // setup()
    public void before() {
        test = Inventory.getInstance();
        inventoryInfos=new BookInventoryInfo[3];
        for (int i = 0; i <inventoryInfos.length ; i++) {
            inventoryInfos[i]=new BookInventoryInfo("name"+(i+1),i+1,i+1);
        }
    }

    @Test
    public void getInstance() {
        assert(test!=null);
    }


    @Test
    public void load() {
        try {
            test.load(inventoryInfos);
            assert (test.checkAvailabiltyAndGetPrice("name1")==1);
            test.take("name1");
            assert (test.checkAvailabiltyAndGetPrice("name1")==-1);
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void take() {
       try {
          assertEquals(test.take("name1"), OrderResult.SUCCESSFULLY_TAKEN);
          assertEquals(test.take("name1"),OrderResult.NOT_IN_STOCK);
       }
       catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void checkAvailabiltyAndGetPrice() {
        try {
            assertEquals(test.checkAvailabiltyAndGetPrice("name2"), 2);
            assertEquals(test.take("name4"),-1);
        }
        catch (Exception ex) {
            Assert.fail();
        }
    }

}