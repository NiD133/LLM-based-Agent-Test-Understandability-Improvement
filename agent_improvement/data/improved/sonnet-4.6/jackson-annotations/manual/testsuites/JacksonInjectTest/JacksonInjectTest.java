package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JacksonInject} annotation and its nested {@link JacksonInject.Value} class.
 *
 * <p>Covers: construction of {@code Value} instances from annotations and factory methods,
 * equality/hashCode/toString contracts, mutant-factory (with*) methods, and JDK serialization.
 */
public class JacksonInjectTest
    extends AnnotationTestUtil
{
    // -----------------------------------------------------------------------
    // Annotated helper class used as the source of live JacksonInject instances
    // -----------------------------------------------------------------------

    private static final class BogusModel {

        /** All three annotation attributes set explicitly. */
        @JacksonInject(value = "inject", useInput = OptBoolean.FALSE, optional = OptBoolean.FALSE)
        public int field;

        /** No attributes — should produce all-null Value (equivalent to EMPTY). */
        @JacksonInject
        public int vanilla;

        /** Only `optional = TRUE` set, everything else default. */
        @JacksonInject(optional = OptBoolean.TRUE)
        public int optionalField;
    }

    // Shared canonical "all-defaults" instance
    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    // -----------------------------------------------------------------------
    // Tests
    // -----------------------------------------------------------------------

    /**
     * An EMPTY value has all three fields null, and {@code willUseInput} delegates to the
     * supplied default.  Constructing with null args — or an empty-string id — must return
     * the same cached singleton.
     */
    @Test
    public void testEmpty()
    {
        assertNull(EMPTY.getId());
        assertNull(EMPTY.getUseInput());

        // willUseInput falls through to the caller-supplied default when _useInput is null
        assertTrue(EMPTY.willUseInput(true));
        assertFalse(EMPTY.willUseInput(false));

        // null id/useInput/optional → singleton EMPTY is returned
        assertSame(EMPTY, JacksonInject.Value.construct(null, null, null));
        // empty-string id is coerced to null, so still produces the EMPTY singleton
        assertSame(EMPTY, JacksonInject.Value.construct("", null, null));
    }

    /**
     * Verifies that {@link JacksonInject.Value#from} correctly maps annotation attributes to
     * the corresponding {@code Value} fields, handles a {@code null} annotation gracefully,
     * and that the resulting object survives a JDK serialization round-trip unchanged.
     */
    @Test
    public void testFromAnnotation() throws Exception
    {
        // null annotation → EMPTY singleton
        assertSame(EMPTY, JacksonInject.Value.from(null));

        // Annotation with all attributes set explicitly
        JacksonInject fullyAnnotated = BogusModel.class.getField("field")
                .getAnnotation(JacksonInject.class);
        JacksonInject.Value fullyPopulated = JacksonInject.Value.from(fullyAnnotated);
        assertEquals("inject", fullyPopulated.getId());
        assertEquals(Boolean.FALSE, fullyPopulated.getUseInput());
        assertEquals("JacksonInject.Value(id=inject,useInput=false,optional=false)",
                fullyPopulated.toString());
        assertFalse(fullyPopulated.equals(EMPTY));
        assertFalse(EMPTY.equals(fullyPopulated));

        // JDK serialization round-trip must preserve equality
        byte[] serialized = jdkSerialize(fullyPopulated);
        JacksonInject.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fullyPopulated, deserialized);

        // Plain @JacksonInject with no attributes → equivalent to construct(null, null, null)
        JacksonInject vanillaAnnotation = BogusModel.class.getField("vanilla")
                .getAnnotation(JacksonInject.class);
        JacksonInject.Value vanillaValue = JacksonInject.Value.from(vanillaAnnotation);
        assertEquals(JacksonInject.Value.construct(null, null, null), vanillaValue,
                "optional should be `null` by default");

        // Annotation with only optional=TRUE set
        JacksonInject optionalAnnotation = BogusModel.class.getField("optionalField")
                .getAnnotation(JacksonInject.class);
        JacksonInject.Value optionalValue = JacksonInject.Value.from(optionalAnnotation);
        assertEquals(JacksonInject.Value.construct(null, null, true), optionalValue);
    }

    /**
     * Validates the equals/hashCode/toString contracts on {@code JacksonInject.Value}:
     * <ul>
     *   <li>toString produces the expected format for EMPTY and a known value</li>
     *   <li>hashCode is non-zero (it starts at 1 and adds field hashes)</li>
     *   <li>equals is reflexive, rejects null, rejects foreign types, and distinguishes
     *       values that differ in exactly one field</li>
     * </ul>
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testStdMethods()
    {
        // --- toString ---
        assertEquals("JacksonInject.Value(id=null,useInput=null,optional=null)",
                EMPTY.toString());

        // --- hashCode must not be zero ---
        int emptyHash = EMPTY.hashCode();
        if (emptyHash == 0) { // internal impl starts at 1, so 0 would indicate a bug
            fail();
        }

        // --- equals: identity, null, and cross-type ---
        assertEquals(EMPTY, EMPTY);
        assertFalse(EMPTY.equals(null));
        assertFalse(EMPTY.equals("xyz"));

        // Reference value: id="value", useInput=true, optional=true
        JacksonInject.Value reference      = JacksonInject.Value.construct("value", true, true);
        JacksonInject.Value sameAsRef      = JacksonInject.Value.construct("value", true, true);

        // Each variant differs in exactly one field compared to `reference`
        JacksonInject.Value nullId         = JacksonInject.Value.construct(null,      true,  true);
        JacksonInject.Value nullUseInput   = JacksonInject.Value.construct("value",   null,  true);
        JacksonInject.Value nullOptional   = JacksonInject.Value.construct("value",   true,  null);
        JacksonInject.Value differentId    = JacksonInject.Value.construct("not equal", true, true);
        JacksonInject.Value differentUseInput = JacksonInject.Value.construct("value", false, true);
        JacksonInject.Value differentOptional = JacksonInject.Value.construct("value", true,  false);
        String notAValue = "string";

        assertEquals(reference, sameAsRef);
        assertNotEquals(reference, nullId);
        assertNotEquals(reference, nullUseInput);
        assertNotEquals(reference, nullOptional);
        assertNotEquals(reference, differentId);
        assertNotEquals(reference, differentUseInput);
        assertNotEquals(reference, differentOptional);
        assertNotEquals(reference, notAValue);
    }

    /**
     * Verifies the mutant-factory methods ({@code withId}, {@code withUseInput},
     * {@code withOptional}) return new instances when the field changes but the
     * same instance when the value is unchanged (identity optimization).
     */
    @Test
    public void testFactories() throws Exception
    {
        // withId: new instance when id changes, same instance when id is unchanged
        JacksonInject.Value withName = EMPTY.withId("name");
        assertNotSame(EMPTY, withName);
        assertEquals("name", withName.getId());
        assertSame(withName, withName.withId("name")); // no-op when value is unchanged

        // withUseInput: produces distinct instance; setting same value is a no-op
        JacksonInject.Value withUseInput = withName.withUseInput(Boolean.TRUE);
        assertNotSame(withName, withUseInput);
        assertFalse(withName.equals(withUseInput));
        assertFalse(withUseInput.equals(withName));
        assertSame(withUseInput, withUseInput.withUseInput(Boolean.TRUE));

        // withOptional: produces distinct instance; setting same value is a no-op
        JacksonInject.Value withOptional = withName.withOptional(Boolean.TRUE);
        assertNotSame(withName, withOptional);
        assertFalse(withName.equals(withOptional));
        assertFalse(withOptional.equals(withName));
        assertSame(withOptional, withOptional.withOptional(Boolean.TRUE));
        assertTrue(withOptional.getOptional());

        // hashCode must be non-zero for a non-empty Value
        int hash = withUseInput.hashCode();
        if (hash == 0) {
            fail();
        }
    }
}
