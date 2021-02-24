package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.*;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class HelperTest {
    @Test
    public void test(){
        assertEquals(3, 3);
    }

    @Test
    public void verify_getCount(){
        List<String> empNames = Arrays.asList("sareeta", "udacity");
        long actual = Helper.getCount(empNames);
        assertEquals(2, actual);
    }

    @Test
    public void verify_getStats(){
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        List<Integer> expectedList = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        IntSummaryStatistics stats = Helper.getStats(yrsOfExperience);
        assertEquals(19, stats.getMax());
        assertEquals(expectedList, yrsOfExperience);
    }

    @Test
    public void compare_arrays(){
        int[] yrs = {10, 14, 2};
        int[] expectedYrs = {10, 14, 2};
        assertArrayEquals(expectedYrs, yrs);
    }

    @Test
    public void verify_getMerged(){
        List<String> empNames = Arrays.asList("sareeta", "udacity");
        String actual = Helper.getMergedList(empNames);
        String expected = "sareeta" + ", " + "udacity";
        assertEquals(expected, actual);
    }

    @Before
    public void init(){
        System.out.println("Runs before each method");
    }

    @BeforeClass
    public static void setup(){
        System.out.println("Runs before each class");
    }

    @After
    public void initEnd(){
        System.out.println("Runs after each method");
    }

    @AfterClass
    public static void teardown(){
        System.out.println("Runs after each class");
    }
	
}
