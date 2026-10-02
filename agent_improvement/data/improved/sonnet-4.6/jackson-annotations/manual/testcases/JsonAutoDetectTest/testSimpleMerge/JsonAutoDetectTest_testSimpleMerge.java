package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonAutoDetectTest_testSimpleMerge extends AnnotationTestUtil {

    private final static JsonAutoDetect.Value NO_OVERRIDES = JsonAutoDetect.Value.noOverrides();

    private final static JsonAutoDetect.Value DEFAULTS = JsonAutoDetect.Value.defaultVisibility();

    @Test
    public void testSimpleMerge() {
        // construct(fields, getters, isGetters, setters, creators, scalarConstructors)
        JsonAutoDetect.Value base = JsonAutoDetect.Value.construct(
                Visibility.ANY,                  // fields
                Visibility.PUBLIC_ONLY,          // getters
                Visibility.ANY,                  // isGetters
                Visibility.NONE,                 // setters
                Visibility.ANY,                  // creators
                Visibility.PROTECTED_AND_PUBLIC  // scalarConstructors
        );

        // Visibility.DEFAULT means "no override — keep base value during merge"
        JsonAutoDetect.Value overrides = JsonAutoDetect.Value.construct(
                Visibility.NON_PRIVATE,  // fields       — overrides base (ANY → NON_PRIVATE)
                Visibility.DEFAULT,      // getters      — keep base (PUBLIC_ONLY)
                Visibility.PUBLIC_ONLY,  // isGetters    — overrides base (ANY → PUBLIC_ONLY)
                Visibility.DEFAULT,      // setters      — keep base (NONE)
                Visibility.DEFAULT,      // creators     — keep base (ANY)
                Visibility.PUBLIC_ONLY   // scalarConstructors — overrides base (PROTECTED_AND_PUBLIC → PUBLIC_ONLY)
        );

        // merge(base, overrides): non-DEFAULT override values win; DEFAULT keeps base value
        JsonAutoDetect.Value merged = JsonAutoDetect.Value.merge(base, overrides);

        assertFalse(merged.equals(base));
        assertFalse(merged.equals(overrides));
        assertEquals(merged, merged);

        assertEquals(Visibility.NON_PRIVATE, merged.getFieldVisibility());              // override won
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());             // base kept (override was DEFAULT)
        assertEquals(Visibility.PUBLIC_ONLY, merged.getIsGetterVisibility());           // override won
        assertEquals(Visibility.NONE, merged.getSetterVisibility());                    // base kept (override was DEFAULT)
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());                    // base kept (override was DEFAULT)
        assertEquals(Visibility.PUBLIC_ONLY, merged.getScalarConstructorVisibility());  // override won

        // merge(overrides, base): roles are swapped — previous overrides become base, previous base becomes overrides
        // Only non-DEFAULT values in the new overrides (original base) take effect
        merged = JsonAutoDetect.Value.merge(overrides, base);

        assertEquals(Visibility.ANY, merged.getFieldVisibility());                          // new override (ANY) wins over NON_PRIVATE
        assertEquals(Visibility.PUBLIC_ONLY, merged.getGetterVisibility());                 // new override (PUBLIC_ONLY) wins over DEFAULT
        assertEquals(Visibility.ANY, merged.getIsGetterVisibility());                       // new override (ANY) wins over PUBLIC_ONLY
        assertEquals(Visibility.NONE, merged.getSetterVisibility());                        // new override (NONE) wins over DEFAULT
        assertEquals(Visibility.ANY, merged.getCreatorVisibility());                        // new override (ANY) wins over DEFAULT
        assertEquals(Visibility.PROTECTED_AND_PUBLIC, merged.getScalarConstructorVisibility()); // new override wins

        // null handling: merge returns the non-null argument unchanged
        assertSame(overrides, JsonAutoDetect.Value.merge(null, overrides));
        assertSame(overrides, JsonAutoDetect.Value.merge(overrides, null));
    }
}
