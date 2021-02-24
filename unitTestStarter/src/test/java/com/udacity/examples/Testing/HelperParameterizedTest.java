package com.udacity.examples.Testing;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotEquals;

public class HelperParameterizedTest {
    private String input;
    private String output;

    public HelperParameterizedTest(String input, String output) {
        this.input = input;
        this.output = output;
    }

    @Parameterized.Parameters
    public static Collection initData(){
        String[][] empNames = {{"sareeta", "sareeta"}, {"sareeta", "Jeff"}};
        return Arrays.asList(empNames);
    }

    @Test
    public void verify_input_name_is_not_the_same_as_the_output_name(){
        assertNotEquals(input, output);
    }
}
