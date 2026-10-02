package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_unescape {

    @Test
    public void unescape() {
        String mixedNamedAndNumericEntities =
            "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";
        String expectedDecodedText = "Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©";

        assertEquals(expectedDecodedText, Entities.unescape(mixedNamedAndNumericEntities));
        assertEquals("&0987654321; &unknown", Entities.unescape("&0987654321; &unknown"));
    }
}
