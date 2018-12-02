package bgu.spl.mics;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {

    @Test
    public void get() {
        Future<Integer> futureint= new Future<>();
        try {
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
            futureint.resolve(5);
            try{
                int value= futureint.get();
                assertEquals(value, 5);
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
        try{
            assertFalse(futureint.isDone());
            futureint.resolve(10);
            assertTrue(futureint.isDone());
        }catch (Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void get1() {
        Future<Integer> futureint= new Future<>();
        long timetowait = System.currentTimeMillis();
        try{
            futureint.get(1000, TimeUnit.MILLISECONDS);
            assert (System.currentTimeMillis()-timetowait<=1000);
        }
        catch (Exception ex){
            Assert.fail();
        }
    }
}