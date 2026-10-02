package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testHWRuleEx1 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies the H/W rule: when H or W appears between two consonants that share the same Soundex
     * digit, those consonants are treated as one and only encoded once.
     *
     * <p>Reference: http://www.archives.gov/research_room/genealogy/census/soundex.html
     *
     * <p>Example breakdown for "Ashcraft":
     * <ul>
     *   <li>A – kept as the initial letter</li>
     *   <li>S – digit 2</li>
     *   <li>H – ignored (H/W rule)</li>
     *   <li>C – digit 2, same as S → suppressed (adjacent-duplicate rule)</li>
     *   <li>R – digit 6</li>
     *   <li>A – vowel (digit 0) → not encoded</li>
     *   <li>F – digit 1</li>
     *   <li>T – digit 3, but the code is already 4 characters → truncated</li>
     * </ul>
     * Result: A261 (not A226, which would be wrong if C were encoded independently).
     */
    @Test
    void testHWRuleEx1() {
        // "Ashcraft" and "Ashcroft" differ only in the vowel between r and f,
        // but vowels are not encoded after the first letter, so both yield A261.
        assertEquals("A261", getStringEncoder().encode("Ashcraft"));
        assertEquals("A261", getStringEncoder().encode("Ashcroft"));

        // "yehudit" and "yhwdyt" demonstrate the same rule with W:
        // Y is kept as initial; H and W are both ignored; the remaining
        // consonants D and T share a code, collapsing further – result is Y330.
        assertEquals("Y330", getStringEncoder().encode("yehudit"));
        assertEquals("Y330", getStringEncoder().encode("yhwdyt"));
    }
}
