package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Years fiveYears = Years.of(5);
        assertEquals(Years.of(0),   fiveYears.multipliedBy(0));   // multiply by zero always yields zero
        assertEquals(Years.of(5),   fiveYears.multipliedBy(1));   // multiply by one is identity
        assertEquals(Years.of(10),  fiveYears.multipliedBy(2));   // double
        assertEquals(Years.of(15),  fiveYears.multipliedBy(3));   // triple
        assertEquals(Years.of(-15), fiveYears.multipliedBy(-3));  // negative scalar negates and triples
    }
}
