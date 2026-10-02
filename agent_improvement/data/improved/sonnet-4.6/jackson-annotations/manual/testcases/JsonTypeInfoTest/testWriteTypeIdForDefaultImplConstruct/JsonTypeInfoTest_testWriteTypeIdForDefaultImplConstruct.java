package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that JsonTypeInfo.Value correctly stores and reports the writeTypeIdForDefaultImpl flag
 * when explicitly set to FALSE via construct(), meaning type id should NOT be written for
 * values whose runtime type matches the defaultImpl class.
 *
 * See: [annotations#342]
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplConstruct extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        // Construct a Value with writeTypeIdForDefaultImpl=FALSE to disable type id
        // emission when the runtime type matches defaultImpl (Void.class here).
        JsonTypeInfo.Id idType = JsonTypeInfo.Id.CLASS;
        JsonTypeInfo.As inclusionType = JsonTypeInfo.As.PROPERTY;
        String propertyName = null;          // use Id-specific default ("@class")
        Class<?> defaultImpl = Void.class;
        boolean idVisible = false;
        Boolean requireTypeIdForSubtypes = null;
        Boolean writeTypeIdForDefaultImpl = Boolean.FALSE;

        JsonTypeInfo.Value v = JsonTypeInfo.Value.construct(
                idType, inclusionType, propertyName, defaultImpl,
                idVisible, requireTypeIdForSubtypes, writeTypeIdForDefaultImpl);

        // getWriteTypeIdForDefaultImpl() must return the exact Boolean.FALSE that was passed in
        assertEquals(Boolean.FALSE, v.getWriteTypeIdForDefaultImpl());

        // shouldWriteTypeIdForDefaultImpl() returns true only when flag is null or true,
        // so FALSE should yield false
        assertFalse(v.shouldWriteTypeIdForDefaultImpl());
    }
}
