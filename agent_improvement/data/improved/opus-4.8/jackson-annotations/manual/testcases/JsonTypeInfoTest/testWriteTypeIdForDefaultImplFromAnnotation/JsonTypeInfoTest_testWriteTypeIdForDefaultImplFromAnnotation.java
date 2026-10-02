package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies how {@link JsonTypeInfo.Value} exposes the annotation property
 * {@code writeTypeIdForDefaultImpl} after being read from a {@link JsonTypeInfo}
 * annotation (see [annotations#342]).
 *
 * <p>Two accessors are checked for each case:
 * <ul>
 *   <li>{@link JsonTypeInfo.Value#getWriteTypeIdForDefaultImpl()} returns the raw,
 *       three-state value: {@code TRUE}, {@code FALSE}, or {@code null} (unset).</li>
 *   <li>{@link JsonTypeInfo.Value#shouldWriteTypeIdForDefaultImpl()} resolves the
 *       raw value into the effective boolean behavior, where "unset" defaults to
 *       writing the type id (i.e. {@code true}).</li>
 * </ul>
 *
 * <p>The {@code Anno3}/{@code Anno4}/{@code Anno5} sample annotations are inherited
 * from {@link AnnotationTestUtil}.
 */
public class JsonTypeInfoTest_testWriteTypeIdForDefaultImplFromAnnotation extends AnnotationTestUtil {

    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        // Case 1: annotation explicitly sets writeTypeIdForDefaultImpl = FALSE
        JsonTypeInfo.Value explicitlyDisabled = valueFromAnnotationOn(Anno4.class);
        assertEquals(Boolean.FALSE, explicitlyDisabled.getWriteTypeIdForDefaultImpl());
        assertFalse(explicitlyDisabled.shouldWriteTypeIdForDefaultImpl());

        // Case 2: annotation explicitly sets writeTypeIdForDefaultImpl = TRUE
        JsonTypeInfo.Value explicitlyEnabled = valueFromAnnotationOn(Anno5.class);
        assertEquals(Boolean.TRUE, explicitlyEnabled.getWriteTypeIdForDefaultImpl());
        assertTrue(explicitlyEnabled.shouldWriteTypeIdForDefaultImpl());

        // Case 3: annotation leaves writeTypeIdForDefaultImpl unset (DEFAULT -> null),
        // which must resolve to the backwards-compatible behavior of writing the type id.
        JsonTypeInfo.Value unset = valueFromAnnotationOn(Anno3.class);
        assertNull(unset.getWriteTypeIdForDefaultImpl());
        assertTrue(unset.shouldWriteTypeIdForDefaultImpl());
    }

    /** Reads the {@link JsonTypeInfo} annotation off the given class and wraps it as a Value. */
    private static JsonTypeInfo.Value valueFromAnnotationOn(Class<?> annotatedClass) {
        return JsonTypeInfo.Value.from(annotatedClass.getAnnotation(JsonTypeInfo.class));
    }
}
