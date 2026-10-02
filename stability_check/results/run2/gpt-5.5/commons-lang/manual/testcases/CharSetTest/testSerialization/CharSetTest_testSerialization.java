package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetTest_testSerialization extends AbstractLangTest {

    @Test
    void testSerialization() {
        assertSerializationRoundTripPreservesCharSet("a");
        assertSerializationRoundTripPreservesCharSet("a-e");
        assertSerializationRoundTripPreservesCharSet("be-f^a-z");
    }

    private void assertSerializationRoundTripPreservesCharSet(final String charSetExpression) {
        final CharSet original = CharSet.getInstance(charSetExpression);
        final CharSet deserializedClone = SerializationUtils.clone(original);

        assertEquals(original, deserializedClone);
    }
}
