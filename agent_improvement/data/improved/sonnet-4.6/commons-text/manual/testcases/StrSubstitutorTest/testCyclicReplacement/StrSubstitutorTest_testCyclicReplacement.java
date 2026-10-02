package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that StrSubstitutor detects and rejects cyclic variable references.
 *
 * The cycle in these tests is: animal -> critter -> critterType -> animal
 */
public class StrSubstitutorTest_testCyclicReplacement {

    /**
     * Builds a variable map that contains a cycle:
     *   animal -> ${critter}
     *   critter -> ${critterSpeed} ${critterColor} ${critterType}
     *   critterType -> ${animal}   <-- closes the cycle
     *
     * The non-cyclic variables (target, pet, petCharacteristic, critterSpeed,
     * critterColor) resolve normally and are present to make the map realistic.
     */
    private Map<String, String> buildCyclicMap() {
        final Map<String, String> map = new HashMap<>();
        // cyclic chain: animal -> critter -> critterType -> animal
        map.put("animal",             "${critter}");
        map.put("critter",            "${critterSpeed} ${critterColor} ${critterType}");
        map.put("critterType",        "${animal}");
        // non-cyclic supporting entries
        map.put("critterSpeed",       "quick");
        map.put("critterColor",       "brown");
        map.put("target",             "${pet}");
        map.put("pet",                "${petCharacteristic} dog");
        map.put("petCharacteristic",  "lazy");
        return map;
    }

    @Test
    void testCyclicReplacement() {
        final Map<String, String> map = buildCyclicMap();
        final StrSubstitutor sub = new StrSubstitutor(map);

        assertThrows(IllegalStateException.class,
                () -> sub.replace("The ${animal} jumps over the ${target}."));
    }

    /**
     * Verifies that adding a default value to a cyclic variable (${animal:-fox})
     * does not break cycle detection — an IllegalStateException is still thrown.
     */
    @Test
    void testCyclicReplacementWithDefaultValue() {
        final Map<String, String> map = buildCyclicMap();
        // Replacing the plain cycle endpoint with one that carries a default value
        // must not suppress cycle detection.
        map.put("critterType", "${animal:-fox}");

        assertThrows(IllegalStateException.class,
                () -> new StrSubstitutor(map).replace("The ${animal} jumps over the ${target}."));
    }
}
