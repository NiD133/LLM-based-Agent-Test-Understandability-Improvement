package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the {@code writeTypeIdForDefaultImpl} property of
 * {@link JsonTypeInfo.Value}: see issue [annotations#342].
 *
 * The property is a nullable {@link Boolean} where:
 * <ul>
 *   <li>{@code null} (unset) means "write the type id" by default, and</li>
 *   <li>an explicit value of {@code TRUE}/{@code FALSE} controls the behaviour directly.</li>
 * </ul>
 * The {@code with...} mutator is expected to return a new instance only when the
 * value actually changes, and the same instance otherwise.
 */
public class JsonTypeInfoTest_testWithWriteTypeIdForDefaultImpl extends AnnotationTestUtil {

    @Test
    public void testWithWriteTypeIdForDefaultImpl() {
        // The EMPTY value leaves the property unset (null), which is treated as "should write".
        JsonTypeInfo.Value unset = JsonTypeInfo.Value.EMPTY;
        assertNull(unset.getWriteTypeIdForDefaultImpl());
        assertTrue(unset.shouldWriteTypeIdForDefaultImpl());

        // Setting the property to FALSE disables writing the type id.
        JsonTypeInfo.Value disabled = unset.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertEquals(Boolean.FALSE, disabled.getWriteTypeIdForDefaultImpl());
        assertFalse(disabled.shouldWriteTypeIdForDefaultImpl());

        // Setting the property to TRUE explicitly enables writing the type id.
        JsonTypeInfo.Value enabled = unset.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertEquals(Boolean.TRUE, enabled.getWriteTypeIdForDefaultImpl());
        assertTrue(enabled.shouldWriteTypeIdForDefaultImpl());

        // Clearing the property back to null restores the default "should write" behaviour.
        JsonTypeInfo.Value cleared = disabled.withWriteTypeIdForDefaultImpl(null);
        assertNull(cleared.getWriteTypeIdForDefaultImpl());
        assertTrue(cleared.shouldWriteTypeIdForDefaultImpl());

        // Mutating with the value already held returns the very same instance (no copy).
        assertSame(disabled, disabled.withWriteTypeIdForDefaultImpl(Boolean.FALSE));
        assertSame(enabled, enabled.withWriteTypeIdForDefaultImpl(Boolean.TRUE));
    }
}
