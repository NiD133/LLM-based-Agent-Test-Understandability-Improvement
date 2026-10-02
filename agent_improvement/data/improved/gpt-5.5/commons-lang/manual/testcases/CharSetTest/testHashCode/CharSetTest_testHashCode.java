package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetTest_testHashCode extends AbstractLangTest {

    @Test
    void testHashCode() {
        assertHashCodeIsStableAndConsistent("abc");
        assertHashCodeIsStableAndConsistent("a-c");
        assertHashCodeIsStableAndConsistent("^a-c");
    }

    private void assertHashCodeIsStableAndConsistent(final String setDefinition) {
        final CharSet charSet = CharSet.getInstance(setDefinition);
        final CharSet sameDefinition = CharSet.getInstance(setDefinition);

        assertEquals(charSet.hashCode(), charSet.hashCode());
        assertEquals(charSet.hashCode(), sameDefinition.hashCode());
    }
}
