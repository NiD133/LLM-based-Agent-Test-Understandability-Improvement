package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_unescape {

    /**
     * Verifies {@link Entities#unescape(String)} across the main HTML entity forms:
     * named entities (with and without trailing ';'), numeric and hex references,
     * and tokens that are not valid entities and must therefore be left untouched.
     */
    @Test
    public void unescape() {
        // A single input that mixes every supported entity form. Each token below is
        // separated by spaces; the comment shows how unescape() should resolve it:
        //   &AElig;   -> Æ   (named, with trailing ';')
        //   &amp;     -> &   (named, with trailing ';')
        //   &LT       -> <   (named, ';' optional for some legacy entities)
        //   &gt;      -> >   (named, with trailing ';')
        //   &reg      -> ®   (named, no trailing ';')
        //   &angst;   -> Å   (named, with trailing ';')
        //   &angst    -> &angst  (no trailing ';' -> not a legacy entity, left as-is)
        //   &#960;    -> π   (decimal numeric reference, with trailing ';')
        //   &#960     -> π   (decimal numeric reference, ';' optional)
        //   &#x65B0;  -> 新  (hexadecimal numeric reference)
        //   &!        -> &!  (not an entity, left as-is)
        //   &frac34;  -> ¾   (named, with trailing ';')
        //   &copy;    -> ©   (named, with trailing ';')
        //   &COPY;    -> ©   (uppercase variant of an entity)
        String mixedEntities =
            "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";
        String expectedMixed = "Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©";
        assertEquals(expectedMixed, Entities.unescape(mixedEntities));

        // A purely numeric token followed by ';' is not a valid entity name, and an
        // unknown named token is not an entity either; both are returned unchanged.
        String nonEntities = "&0987654321; &unknown";
        assertEquals(nonEntities, Entities.unescape(nonEntities));
    }
}
