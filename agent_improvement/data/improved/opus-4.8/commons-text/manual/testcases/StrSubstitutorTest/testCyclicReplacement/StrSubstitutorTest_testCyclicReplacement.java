package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testCyclicReplacement {

    /** Template whose variables form a replacement cycle through the maps below. */
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";

    /**
     * Builds a variable map in which resolving {@code animal} eventually requires
     * resolving {@code animal} again, forming a cycle:
     * animal -> critter -> ... -> critterType -> animal.
     *
     * @return a map of variable names to their (cyclic) replacement values.
     */
    private static Map<String, String> newCyclicValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "${animal}");
        return values;
    }

    /**
     * Tests a cyclic replace operation.
     * The cycle should be detected and cause an exception to be thrown.
     */
    @Test
    void testCyclicReplacement() {
        final Map<String, String> values = newCyclicValues();

        // A plain cycle must be detected and rejected.
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertThrows(IllegalStateException.class, () -> sub.replace(TEMPLATE));

        // The cycle must still be detected even when the closing variable
        // declares a default value (${animal:-fox}).
        values.put("critterType", "${animal:-fox}");
        final StrSubstitutor subWithDefault = new StrSubstitutor(values);
        assertThrows(IllegalStateException.class, () -> subWithDefault.replace(TEMPLATE));
    }
}
