package com.fasterxml.jackson.annotation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that {@link JsonIgnoreProperties.Value#from} correctly translates a
 * {@link JsonIgnoreProperties} annotation into a {@code Value} instance, and that
 * the resulting {@code Value} survives JDK serialization unchanged.
 */
public class JsonIgnorePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    /**
     * Fixture carrying a {@link JsonIgnoreProperties} annotation whose values are
     * the input under test: two ignored properties ("foo", "bar") and
     * {@code ignoreUnknown=true}. All other annotation attributes keep their defaults.
     */
    @JsonIgnoreProperties(value = { "foo", "bar" }, ignoreUnknown = true)
    private static final class AnnotatedFixture {
    }

    @Test
    public void testFromAnnotation() throws Exception {
        JsonIgnoreProperties annotation =
                AnnotatedFixture.class.getAnnotation(JsonIgnoreProperties.class);

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(annotation);

        // The annotation should produce a non-null Value whose only set behaviour
        // is the ignored-property list; merge and getter/setter allowances stay off.
        assertNotNull(value);
        assertFalse(value.getMerge());
        assertFalse(value.getAllowGetters());
        assertFalse(value.getAllowSetters());

        // The ignored set must contain exactly the two names from the annotation.
        Set<String> expectedIgnored = new LinkedHashSet<String>(Arrays.asList("foo", "bar"));
        Set<String> actualIgnored = value.getIgnored();
        assertEquals(2, actualIgnored.size());
        assertEquals(expectedIgnored, actualIgnored);

        // The Value must be JDK-serializable and round-trip back to an equal instance.
        byte[] serialized = jdkSerialize(value);
        JsonIgnoreProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }
}
