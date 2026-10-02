package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonIgnoreProperties.Value#withOverrides} merge behaviour:
 * verifies that settings are unioned when the override has merge=true,
 * and that the base is fully replaced when the override has merge=false.
 */
public class JsonIgnorePropertiesTest_testSimpleMerge extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    /**
     * When the override value has merge=true, {@code withOverrides} should
     * produce a union: ignored-property names are combined, and each boolean
     * flag is the logical OR of the base and override values.
     */
    @Test
    public void testWithOverrides_mergeMode_unionsPropertiesAndOrsBooleanFlags() {
        JsonIgnoreProperties.Value base = EMPTY.withIgnoreUnknown().withAllowGetters();
        // override carries merge=true and adds one ignored property name
        JsonIgnoreProperties.Value overrideWithMerge = EMPTY.withMerge().withIgnored("a");

        JsonIgnoreProperties.Value merged = base.withOverrides(overrideWithMerge);

        assertEquals(Collections.singleton("a"), merged.getIgnored(),
                "ignored set should be union of base (empty) and override ({\"a\"})");
        assertTrue(merged.getIgnoreUnknown(),
                "ignoreUnknown should be true because base had it true");
        assertTrue(merged.getAllowGetters(),
                "allowGetters should be true because base had it true");
        assertFalse(merged.getAllowSetters(),
                "allowSetters should be false because neither base nor override set it true");
    }

    /**
     * When the override value has merge=false, {@code withOverrides} (and the
     * static {@code merge} helper) should simply return the override as-is,
     * discarding all base settings entirely.
     */
    @Test
    public void testWithOverrides_nonMergeMode_replacesBaseWithOverride() {
        JsonIgnoreProperties.Value base = EMPTY.withIgnoreUnknown().withAllowGetters();
        // override carries merge=false — no ignored properties, all flags default (false)
        JsonIgnoreProperties.Value overrideWithoutMerge = EMPTY.withoutMerge();

        JsonIgnoreProperties.Value result = JsonIgnoreProperties.Value.merge(base, overrideWithoutMerge);

        assertEquals(Collections.emptySet(), result.getIgnored(),
                "ignored set should be empty because override has no ignored properties");
        assertFalse(result.getIgnoreUnknown(),
                "ignoreUnknown should be false because the override (which wins) has it false");
        assertFalse(result.getAllowGetters(),
                "allowGetters should be false because the override (which wins) has it false");
        assertFalse(result.getAllowSetters(),
                "allowSetters should be false because the override (which wins) has it false");
        // confirm the result is effectively identical to the override object itself
        assertEquals(overrideWithoutMerge, result,
                "non-merge result should equal the override value");
    }

    /**
     * {@code withOverrides(null)} and {@code withOverrides(EMPTY)} are
     * no-ops: the same instance should be returned without creating a new object.
     */
    @Test
    public void testWithOverrides_nullOrEmptyOverride_returnsSameInstance() {
        JsonIgnoreProperties.Value base = EMPTY.withoutMerge();

        assertSame(base, base.withOverrides(null),
                "withOverrides(null) should return the same Value instance");
        assertSame(base, base.withOverrides(EMPTY),
                "withOverrides(EMPTY) should return the same Value instance");
    }
}
