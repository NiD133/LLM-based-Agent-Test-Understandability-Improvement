package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CharSetTest_testContains_Char extends AbstractLangTest {

    @Test
    void testContains_Char() {
        final CharSet bThroughD = CharSet.getInstance("b-d");
        final CharSet dThroughB = CharSet.getInstance("d-b");
        final CharSet separateBcd = CharSet.getInstance("bcd");
        final CharSet separateBd = CharSet.getInstance("bd");
        final CharSet notBThroughD = CharSet.getInstance("^b-d");

        assertFalse(bThroughD.contains('a'));
        assertTrue(bThroughD.contains('b'));
        assertTrue(bThroughD.contains('c'));
        assertTrue(bThroughD.contains('d'));
        assertFalse(bThroughD.contains('e'));

        assertFalse(separateBcd.contains('a'));
        assertTrue(separateBcd.contains('b'));
        assertTrue(separateBcd.contains('c'));
        assertTrue(separateBcd.contains('d'));
        assertFalse(separateBcd.contains('e'));

        assertFalse(separateBd.contains('a'));
        assertTrue(separateBd.contains('b'));
        assertFalse(separateBd.contains('c'));
        assertTrue(separateBd.contains('d'));
        assertFalse(separateBd.contains('e'));

        assertTrue(notBThroughD.contains('a'));
        assertFalse(notBThroughD.contains('b'));
        assertFalse(notBThroughD.contains('c'));
        assertFalse(notBThroughD.contains('d'));
        assertTrue(notBThroughD.contains('e'));

        assertFalse(dThroughB.contains('a'));
        assertTrue(dThroughB.contains('b'));
        assertTrue(dThroughB.contains('c'));
        assertTrue(dThroughB.contains('d'));
        assertFalse(dThroughB.contains('e'));

        final Set<CharRange> ranges = dThroughB.getCharRanges();
        assertEquals("[b-d]", dThroughB.toString());
        assertEquals(1, ranges.size());
    }
}
