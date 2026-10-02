package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testCyclicReplacement {

    private static final String SENTENCE_TEMPLATE = "The ${animal} jumps over the ${target}.";

    @Test
    void testCyclicReplacement() {
        final Map<String, String> map = createCyclicReplacementMap();
        final StrSubstitutor sub = new StrSubstitutor(map);

        assertCyclicReplacementThrows(sub);

        map.put("critterType", "${animal:-fox}");
        assertCyclicReplacementThrows(new StrSubstitutor(map));
    }

    private Map<String, String> createCyclicReplacementMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("animal", "${critter}");
        map.put("target", "${pet}");
        map.put("pet", "${petCharacteristic} dog");
        map.put("petCharacteristic", "lazy");
        map.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        map.put("critterSpeed", "quick");
        map.put("critterColor", "brown");
        map.put("critterType", "${animal}");
        return map;
    }

    private void assertCyclicReplacementThrows(final StrSubstitutor substitutor) {
        assertThrows(IllegalStateException.class, () -> substitutor.replace(SENTENCE_TEMPLATE));
    }
}
