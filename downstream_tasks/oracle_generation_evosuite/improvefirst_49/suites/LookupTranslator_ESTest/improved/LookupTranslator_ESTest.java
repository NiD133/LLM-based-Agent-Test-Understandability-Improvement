/*
 * Improved for understandability from the EvoSuite-generated test.
 */

package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LookupTranslator_ESTest extends LookupTranslator_ESTest_scaffolding {

    /**
     * When the input shares a prefix character with keys in the lookup table but
     * does not fully match any key, the translator should pass the input through
     * unchanged.
     *
     * Lookup table: "FFFFFFFF" -> "FFFFFFFF" (two entries, both identical)
     * Input: "FFFFFCCE" — starts with 'F' (a known prefix) but is not a key.
     * Expected: "FFFFFCCE" returned verbatim.
     */
    @Test(timeout = 4000)
    public void testTranslatePassesThroughInputWhenNoKeyMatches() throws Throwable {
        // Entry 1: key="FFFFFFFF", value="FFFFFFFF"
        CharSequence[] entry1 = new CharSequence[4];
        entry1[0] = "FFFFFFFF";
        entry1[1] = "FFFFFFFF";

        // Entry 2: key="FFFFFFFF", value="FFFFFFFF"; index 3 holds the translate argument
        CharSequence[] entry2 = new CharSequence[5];
        entry2[0] = "FFFFFFFF";
        entry2[1] = "FFFFFFFF";
        entry2[3] = "FFFFFCCE";

        CharSequence[][] lookupTable = new CharSequence[2][6];
        lookupTable[0] = entry1;
        lookupTable[1] = entry2;

        LookupTranslator translator = new LookupTranslator(lookupTable);
        String result = translator.translate(entry2[3]); // translates "FFFFFCCE"

        assertEquals("FFFFFCCE", result);
    }

    /**
     * When the input exactly matches a key in the lookup table, the translator
     * should return the mapped value.
     *
     * Lookup table: "FFFFFCCE" -> "FFFFFCCE"
     * Input: "FFFFFCCE" — an exact key match.
     * Expected: "FFFFFCCE" (the mapped value).
     */
    @Test(timeout = 4000)
    public void testTranslateReturnsValueForMatchingKey() throws Throwable {
        // Single entry: key="FFFFFCCE", value="FFFFFCCE"
        CharSequence[] entry = new CharSequence[3];
        entry[0] = "FFFFFCCE";
        entry[1] = "FFFFFCCE";

        CharSequence[][] lookupTable = new CharSequence[1][7];
        lookupTable[0] = entry;

        LookupTranslator translator = new LookupTranslator(lookupTable);
        String result = translator.translate((CharSequence) "FFFFFCCE");

        assertEquals("FFFFFCCE", result);
    }

    /**
     * Passing null as the lookup table should be handled gracefully without
     * throwing an exception; the resulting translator simply has no mappings.
     */
    @Test(timeout = 4000)
    public void testConstructorHandlesNullLookupTableWithoutException() throws Throwable {
        LookupTranslator translator = new LookupTranslator((CharSequence[][]) null);
    }
}
