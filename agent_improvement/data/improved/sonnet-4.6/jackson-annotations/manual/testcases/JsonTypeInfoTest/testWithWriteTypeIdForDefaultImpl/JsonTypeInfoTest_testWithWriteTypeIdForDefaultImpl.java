package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonTypeInfo.Value#withWriteTypeIdForDefaultImpl(Boolean)} and
 * related accessors {@code getWriteTypeIdForDefaultImpl()} / {@code shouldWriteTypeIdForDefaultImpl()}.
 *
 * Background: when {@code writeTypeIdForDefaultImpl} is {@code null} (the default), Jackson treats
 * it as "write the type id" (backwards-compatible behaviour), so {@code shouldWriteTypeIdForDefaultImpl()}
 * returns {@code true} whenever the stored flag is {@code null} or {@code Boolean.TRUE}.
 *
 * See: jackson-annotations#342
 */
public class JsonTypeInfoTest_testWithWriteTypeIdForDefaultImpl extends AnnotationTestUtil {

    // -----------------------------------------------------------------------
    // Default / empty state
    // -----------------------------------------------------------------------

    /**
     * The sentinel {@link JsonTypeInfo.Value#EMPTY} carries no explicit write-type-id flag,
     * so the raw accessor must return {@code null} and the convenience predicate must
     * return {@code true} (null is treated as "yes, write the type id").
     */
    @Test
    public void testDefaultEmpty_writeTypeIdFlagIsNull() {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        assertNull(emptyValue.getWriteTypeIdForDefaultImpl(),
                "EMPTY value should have no explicit writeTypeIdForDefaultImpl flag (null)");
        assertTrue(emptyValue.shouldWriteTypeIdForDefaultImpl(),
                "null flag should be treated as 'write type id' (shouldWriteTypeIdForDefaultImpl returns true)");
    }

    // -----------------------------------------------------------------------
    // Mutation to FALSE
    // -----------------------------------------------------------------------

    /**
     * Setting the flag to {@code Boolean.FALSE} must suppress type-id writing for the
     * default implementation: the raw accessor returns {@code FALSE} and the predicate
     * returns {@code false}.
     */
    @Test
    public void testMutateToFalse_suppressesTypeIdWrite() {
        JsonTypeInfo.Value base = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value withFalse = base.withWriteTypeIdForDefaultImpl(Boolean.FALSE);

        assertEquals(Boolean.FALSE, withFalse.getWriteTypeIdForDefaultImpl(),
                "After withWriteTypeIdForDefaultImpl(FALSE), getter should return Boolean.FALSE");
        assertFalse(withFalse.shouldWriteTypeIdForDefaultImpl(),
                "Boolean.FALSE flag should make shouldWriteTypeIdForDefaultImpl() return false");
    }

    // -----------------------------------------------------------------------
    // Mutation to TRUE
    // -----------------------------------------------------------------------

    /**
     * Setting the flag to {@code Boolean.TRUE} must enable type-id writing explicitly:
     * the raw accessor returns {@code TRUE} and the predicate returns {@code true}.
     */
    @Test
    public void testMutateToTrue_enablesTypeIdWrite() {
        JsonTypeInfo.Value base = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value withTrue = base.withWriteTypeIdForDefaultImpl(Boolean.TRUE);

        assertEquals(Boolean.TRUE, withTrue.getWriteTypeIdForDefaultImpl(),
                "After withWriteTypeIdForDefaultImpl(TRUE), getter should return Boolean.TRUE");
        assertTrue(withTrue.shouldWriteTypeIdForDefaultImpl(),
                "Boolean.TRUE flag should make shouldWriteTypeIdForDefaultImpl() return true");
    }

    // -----------------------------------------------------------------------
    // Mutation back to null (reset to default)
    // -----------------------------------------------------------------------

    /**
     * Passing {@code null} to the mutator resets the flag to the default "unset" state:
     * the raw accessor returns {@code null} and the predicate returns {@code true}
     * (same behaviour as the original EMPTY value).
     */
    @Test
    public void testMutateBackToNull_resetsToDefault() {
        JsonTypeInfo.Value withFalse = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value resetToNull = withFalse.withWriteTypeIdForDefaultImpl(null);

        assertNull(resetToNull.getWriteTypeIdForDefaultImpl(),
                "After withWriteTypeIdForDefaultImpl(null), getter should return null (flag cleared)");
        assertTrue(resetToNull.shouldWriteTypeIdForDefaultImpl(),
                "Cleared flag (null) should be treated as 'write type id' again");
    }

    // -----------------------------------------------------------------------
    // Identity optimisation: no-op mutation returns the same instance
    // -----------------------------------------------------------------------

    /**
     * When the new value equals the current stored flag, the mutator must return the
     * <em>same</em> {@link JsonTypeInfo.Value} instance rather than allocating a new one.
     * This verifies the identity-shortcut documented on the {@code with*} methods.
     */
    @Test
    public void testNoOpMutation_returnsSameInstance() {
        JsonTypeInfo.Value withFalse = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value withTrue  = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);

        assertSame(withFalse, withFalse.withWriteTypeIdForDefaultImpl(Boolean.FALSE),
                "Mutating FALSE value with Boolean.FALSE should return the identical instance");
        assertSame(withTrue,  withTrue.withWriteTypeIdForDefaultImpl(Boolean.TRUE),
                "Mutating TRUE value with Boolean.TRUE should return the identical instance");
    }
}
