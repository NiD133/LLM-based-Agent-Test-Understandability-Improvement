package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_noSpuriousDecodes {

    /**
     * A URL query string contains ampersand-separated parameters (e.g. {@code &num_rooms=}) that look like the
     * start of an HTML entity but are not valid ones. Unescaping must leave such text untouched rather than
     * spuriously decoding these "&name" fragments.
     */
    @Test
    public void noSpuriousDecodes() {
        String urlWithEntityLikeParams = "http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";

        String unescaped = Entities.unescape(urlWithEntityLikeParams);

        assertEquals(urlWithEntityLikeParams, unescaped);
    }
}
