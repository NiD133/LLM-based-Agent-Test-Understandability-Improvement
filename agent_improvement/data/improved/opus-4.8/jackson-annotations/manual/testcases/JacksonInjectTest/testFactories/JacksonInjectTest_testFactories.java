package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the mutant-factory ("withXxx") methods of {@link JacksonInject.Value}.
 *
 * <p>Each {@code withXxx} method is expected to behave like an immutable "wither":
 * <ul>
 *   <li>changing a property returns a brand-new, non-equal instance, and</li>
 *   <li>re-applying the value already held returns the very same instance (no needless copy).</li>
 * </ul>
 */
public class JacksonInjectTest_testFactories extends AnnotationTestUtil {

    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testFactories() throws Exception {
        // --- withId: assigning an id produces a new, distinct Value ---
        JacksonInject.Value withId = EMPTY.withId("name");
        assertNotSame(EMPTY, withId, "withId should create a new instance");
        assertEquals("name", withId.getId());
        // Re-setting the same id is a no-op and returns the same instance.
        assertSame(withId, withId.withId("name"), "withId with unchanged id should return same instance");

        // --- withUseInput: enabling useInput produces a new Value that is not equal to the original ---
        JacksonInject.Value withUseInput = withId.withUseInput(Boolean.TRUE);
        assertNotSame(withId, withUseInput, "withUseInput should create a new instance");
        assertNotEquals(withId, withUseInput, "Values differing in useInput must not be equal");
        assertNotEquals(withUseInput, withId, "equals must be symmetric for differing values");
        // Re-setting the same useInput is a no-op and returns the same instance.
        assertSame(withUseInput, withUseInput.withUseInput(Boolean.TRUE),
                "withUseInput with unchanged value should return same instance");

        // --- withOptional: enabling optional produces a new Value that is not equal to the original ---
        JacksonInject.Value withOptional = withId.withOptional(Boolean.TRUE);
        assertNotSame(withId, withOptional, "withOptional should create a new instance");
        assertNotEquals(withId, withOptional, "Values differing in optional must not be equal");
        assertNotEquals(withOptional, withId, "equals must be symmetric for differing values");
        // Re-setting the same optional is a no-op and returns the same instance.
        assertSame(withOptional, withOptional.withOptional(Boolean.TRUE),
                "withOptional with unchanged value should return same instance");
        assertTrue(withOptional.getOptional(), "optional flag should be readable as true");

        // --- hashCode: a non-empty Value should not hash to 0 (no fixed value, but must be non-zero here) ---
        assertNotEquals(0, withUseInput.hashCode(), "hashCode of a populated Value should not be 0");
    }
}
