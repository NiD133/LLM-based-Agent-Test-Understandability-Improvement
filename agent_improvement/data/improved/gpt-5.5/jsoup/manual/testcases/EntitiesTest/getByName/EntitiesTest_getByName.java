package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_getByName {

    @Test
    public void getByName() {
        assertEquals("≫⃒", Entities.getByName("nGt"));
        assertEquals("fj", Entities.getByName("fjlig"));
        assertEquals("≫", Entities.getByName("gg"));
        assertEquals("©", Entities.getByName("copy"));
    }
}
