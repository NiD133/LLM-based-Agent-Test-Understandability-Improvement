package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_noSpuriousDecodes {
    private static final String URL_WITH_QUERY_AMPERSANDS =
        "http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";

    @Test
    public void noSpuriousDecodes() {
        String string = URL_WITH_QUERY_AMPERSANDS;

        assertEquals(string, Entities.unescape(string));
    }
}
