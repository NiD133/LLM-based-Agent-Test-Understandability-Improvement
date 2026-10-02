package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplEqualsAndHashCode
    extends AnnotationTestUtil
{
    // [annotations#342]
    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        final JsonTypeInfo.Value writesTypeIdForDefaultImpl =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        final JsonTypeInfo.Value alsoWritesTypeIdForDefaultImpl =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        final JsonTypeInfo.Value doesNotWriteTypeIdForDefaultImpl =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        final JsonTypeInfo.Value defaultWriteTypeIdSetting =
                JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);

        assertEquals(writesTypeIdForDefaultImpl, alsoWritesTypeIdForDefaultImpl);
        assertEquals(writesTypeIdForDefaultImpl.hashCode(), alsoWritesTypeIdForDefaultImpl.hashCode());
        assertNotEquals(writesTypeIdForDefaultImpl, doesNotWriteTypeIdForDefaultImpl);
        assertNotEquals(writesTypeIdForDefaultImpl, defaultWriteTypeIdSetting);
        assertNotEquals(doesNotWriteTypeIdForDefaultImpl, defaultWriteTypeIdSetting);
    }
}
