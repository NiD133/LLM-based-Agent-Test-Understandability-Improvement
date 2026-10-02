package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetTest_testSerialization extends AbstractLangTest {

    @Test
    void testSerialization() {
        assertSerializationPreservesCharSet("a");
        assertSerializationPreservesCharSet("a-e");
        assertSerializationPreservesCharSet("be-f^a-z");
    }

    private static void assertSerializationPreservesCharSet(final String charSetPattern) {
        final CharSet set = CharSet.getInstance(charSetPattern);

        assertEquals(set, SerializationUtils.clone(set));
    }
}
