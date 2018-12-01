package bgu.spl.mics;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FutureTest {

    @Test
    public void get() {
        Future<Integer> futureint= new Future<>();

        try {
            futureint.get();
            Assert.fail();

        } catch (IllegalStateException is) {
            futureint.resolve(10);
            int newResult = futureint.get();
            assertEquals(newResult, 10);
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void resolve() {

        Future<Integer> futureint= new Future<>();
        try{
            int [] array= new int [2];
            futureint.resolve(5);
            try{
                futureint.resolve(6);
                Assert.fail();
            }catch(IllegalStateException ex){
                int value= futureint.get();
                assertEquals(value, 5);
                assertEquals(1, array[0]);
                assertEquals(1, array[1]);
            }catch(Exception ex){
                Assert.fail();
            }
        }catch(Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void isDone() {
        Future<Integer> futureint= new Future<>();
        try {
            assertFalse(futureint.isDone());
            futureint.resolve(10);
            assertTrue(futureint.isDone());
        } catch (Exception ex) {
            Assert.fail();
        }

        //set a future object as resolved and check if it returns true

        //set a future object as not resolved and check if it returns false
    }

    @Test
    public void get1() {
        // we would like to check if future waits for the timeout time that is given
        //we would make a timeunit of our own and check that only after timeout the result is given

        //we would likt to check that future returns the result if available-
        //we will make a future object that has been resolved and check if it gets it

        //we would like to check that future returns the correct answer if t

        //we would like to check that future returns the correct result if after timeout there is still no available result

    }
}