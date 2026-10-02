package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies {@link JsonAutoDetect.Value#merge} semantics: each accessor's
 * visibility comes from the {@code overrides} value unless that override is
 * {@link Visibility#DEFAULT}, in which case the {@code base} value is kept.
 *
 * Note on argument order of {@code Value.construct(...)}:
 *   (fieldVisibility, getterVisibility, isGetterVisibility,
 *    setterVisibility, creatorVisibility, scalarConstructorVisibility)
 */
public class JsonAutoDetectTest_testSimpleMerge extends AnnotationTestUtil {

    @Test
    public void testSimpleMerge() {
        // Base value: a concrete visibility set for every accessor.
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
                /* field               */ Visibility.ANY,
                /* getter              */ Visibility.PUBLIC_ONLY,
                /* isGetter            */ Visibility.ANY,
                /* setter              */ Visibility.NONE,
                /* creator             */ Visibility.ANY,
                /* scalarConstructor   */ Visibility.PROTECTED_AND_PUBLIC);

        // Overrides: DEFAULT entries should fall back to the other value when merged.
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(
                /* field               */ Visibility.NON_PRIVATE,
                /* getter              */ Visibility.DEFAULT,
                /* isGetter            */ Visibility.PUBLIC_ONLY,
                /* setter              */ Visibility.DEFAULT,
                /* creator             */ Visibility.DEFAULT,
                /* scalarConstructor   */ Visibility.PUBLIC_ONLY);

        // --- merge(base, overrides): override wins unless it is DEFAULT ---
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);

        // The merged result differs from both inputs, but equals itself.
        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);

        assertEquals(Visibility.NON_PRIVATE, merged.getFieldVisibility());            // from overrides
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());           // override DEFAULT -> base
        assertEquals(Visibility.PUBLIC_ONLY, merged.getIsGetterVisibility());         // from overrides
        assertEquals(Visibility.NONE, merged.getSetterVisibility());                  // override DEFAULT -> base
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());                  // override DEFAULT -> base
        assertEquals(Visibility.PUBLIC_ONLY, merged.getScalarConstructorVisibility());// from overrides

        // --- merge(overrides, base): roles swapped, so 'base' now supplies the overrides ---
        merged = JsonAutoDetect.Value.merge(overrides, base);

        assertEquals(Visibility.ANY, merged.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());
        assertEquals(Visibility.ANY, merged.getIsGetterVisibility());
        assertEquals(Visibility.NONE, merged.getSetterVisibility());
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, merged.getScalarConstructorVisibility());

        // --- special cases: a null operand makes merge return the other value unchanged ---
        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }
}
