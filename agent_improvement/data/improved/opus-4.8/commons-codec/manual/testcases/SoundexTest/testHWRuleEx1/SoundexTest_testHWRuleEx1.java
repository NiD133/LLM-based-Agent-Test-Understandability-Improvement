package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the Soundex "H/W rule": when two consonants that share the same
 * Soundex code are separated only by an {@code H} or a {@code W}, they are
 * collapsed into a single code digit rather than being encoded twice.
 */
public class SoundexTest_testHWRuleEx1 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Consonants from the same code group separated by W or H are treated as one.
     *
     * <p>Example taken from the US National Archives Soundex guide
     * (http://www.archives.gov/research_room/genealogy/census/soundex.html):
     * "Ashcraft" is coded A-261, not A-226. The breakdown is:</p>
     * <ul>
     *   <li>A  &rarr; retained as the leading letter</li>
     *   <li>S  &rarr; 2</li>
     *   <li>C  &rarr; ignored (same code "2" as S, separated only by the silent H)</li>
     *   <li>R  &rarr; 6</li>
     *   <li>F  &rarr; 1</li>
     * </ul>
     */
    @Test
    void testHWRuleEx1() {
        final Soundex soundex = getStringEncoder();

        // "sh" and "c" share code 2; the H must not let C produce a second 2.
        assertEquals("A261", soundex.encode("Ashcraft"));
        assertEquals("A261", soundex.encode("Ashcroft"));

        // "yehudit": vowels/H are dropped, leaving Y + D(3) + T(3) collapsed.
        assertEquals("Y330", soundex.encode("yehudit"));
        // "yhwdyt": the H and W are silent and do not separate the consonants.
        assertEquals("Y330", soundex.encode("yhwdyt"));
    }
}
