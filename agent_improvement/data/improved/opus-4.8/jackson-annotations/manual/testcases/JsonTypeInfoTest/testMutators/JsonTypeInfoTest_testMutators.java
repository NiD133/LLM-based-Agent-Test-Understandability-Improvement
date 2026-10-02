package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Value;
import com.fasterxml.jackson.annotation.JsonTypeInfoTest.Anno1;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the {@code withXxx(...)} "mutator" methods on {@link JsonTypeInfo.Value}.
 * <p>
 * {@code Value} is immutable, so every mutator returns a <em>copy</em> with the one
 * property changed. Two behaviours are checked for each mutator:
 * <ul>
 *   <li>when the new value equals the current one, the <b>same</b> instance is returned
 *       (no needless copy); and</li>
 *   <li>when the new value differs, a fresh instance carrying the updated property
 *       is returned.</li>
 * </ul>
 */
public class JsonTypeInfoTest_testMutators extends AnnotationTestUtil {

    @Test
    public void testMutators() throws Exception {
        // Base value derived from Anno1: idType=CLASS, inclusion=PROPERTY,
        // defaultImpl=null, idVisible=true.
        Value base = Value.from(Anno1.class.getAnnotation(JsonTypeInfo.class));

        // --- withIdType ------------------------------------------------------
        assertEquals(Id.CLASS, base.getIdType());
        // Setting the same id type is a no-op -> returns the same instance.
        assertSame(base, base.withIdType(Id.CLASS));
        // Different id types each produce a new value with the updated id type.
        assertEquals(Id.MINIMAL_CLASS, base.withIdType(Id.MINIMAL_CLASS).getIdType());
        assertEquals(Id.SIMPLE_NAME, base.withIdType(Id.SIMPLE_NAME).getIdType());

        // --- withInclusionType ----------------------------------------------
        assertEquals(As.PROPERTY, base.getInclusionType());
        // Setting the same inclusion type is a no-op -> returns the same instance.
        assertSame(base, base.withInclusionType(As.PROPERTY));
        // A different inclusion type produces a new value with the updated inclusion.
        assertEquals(As.EXTERNAL_PROPERTY,
                base.withInclusionType(As.EXTERNAL_PROPERTY).getInclusionType());

        // --- withDefaultImpl -------------------------------------------------
        // Base default impl is already null, so setting null is a no-op.
        assertSame(base, base.withDefaultImpl(null));
        assertEquals(String.class, base.withDefaultImpl(String.class).getDefaultImpl());

        // --- withIdVisible ---------------------------------------------------
        // Base is already visible, so setting visible=true is a no-op.
        assertSame(base, base.withIdVisible(true));
        assertFalse(base.withIdVisible(false).getIdVisible());

        // --- withPropertyName ------------------------------------------------
        assertEquals("foobar", base.withPropertyName("foobar").getPropertyName());
    }
}
