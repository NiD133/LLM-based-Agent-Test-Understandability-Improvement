package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_noSpuriousDecodes {

    /**
     * Verifies that {@code Entities.unescape} leaves a URL with ampersand-delimited query parameters
     * unchanged. Without this guard, substrings like {@code &int} (a valid HTML entity for ∫) or
     * {@code &num_rooms} could be misread as entity references and replaced with their Unicode
     * equivalents, corrupting the URL.
     */
    @Test
    public void noSpuriousDecodes() {
        // URL whose query string contains segments that resemble HTML entity names
        // (e.g. "&int=" looks like the integral-sign entity, "&num_rooms=" starts with "&num")
        String urlWithEntityLikeParams = "http://www.foo.com?a=1&num_rooms=1&children=0&int=VA&b=2";

        assertEquals(urlWithEntityLikeParams, Entities.unescape(urlWithEntityLikeParams));
    }
}
