package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the {@code writeTypeIdForDefaultImpl} flag passed to
 * {@link JsonTypeInfo.Value#construct} is preserved on the resulting value
 * and is reflected by {@code shouldWriteTypeIdForDefaultImpl()}.
 *
 * <p>See <a href="https://github.com/FasterXML/jackson-annotations/issues/342">annotations#342</a>.
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplConstruct extends AnnotationTestUtil {

    // The value we explicitly disable type-id writing with, and expect to read back.
    private static final Boolean WRITE_TYPE_ID_FOR_DEFAULT_IMPL = Boolean.FALSE;

    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        // Build a Value with writeTypeIdForDefaultImpl explicitly set to FALSE.
        JsonTypeInfo.Value value = JsonTypeInfo.Value.construct(
                Id.CLASS,                          // use
                As.PROPERTY,                       // include
                null,                              // property (default)
                Void.class,                        // defaultImpl
                false,                             // idVisible
                null,                              // requireTypeIdForSubtypes
                WRITE_TYPE_ID_FOR_DEFAULT_IMPL);   // writeTypeIdForDefaultImpl

        // The flag is stored and returned verbatim by the getter...
        assertEquals(WRITE_TYPE_ID_FOR_DEFAULT_IMPL, value.getWriteTypeIdForDefaultImpl());
        // ...and, being FALSE, the type id should NOT be written for the default impl.
        assertFalse(value.shouldWriteTypeIdForDefaultImpl());
    }
}
