package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that {@link JsonIncludeProperties.Value#from} correctly reads the
 * {@code @JsonIncludeProperties({"foo", "bar"})} annotation declared on
 * {@code Bogus}, and that the resulting value is well-behaved (equality,
 * {@code toString} and JDK serialization).
 */
public class JsonIncludePropertiesTest_testFromAnnotation extends AnnotationTestUtil {

    /** Sample type carrying the annotation under test: includes "foo" and "bar". */
    @JsonIncludeProperties({"foo", "bar"})
    private static class Bogus {
    }

    /** Builds a set whose iteration order matches the given arguments. */
    private Set<String> setOf(String... names) {
        return new LinkedHashSet<String>(Arrays.asList(names));
    }

    @Test
    public void testFromAnnotation() {
        JsonIncludeProperties annotation = Bogus.class.getAnnotation(JsonIncludeProperties.class);

        // Read the annotation into a Value.
        JsonIncludeProperties.Value value = JsonIncludeProperties.Value.from(annotation);
        assertNotNull(value);

        // "included" should hold exactly the two annotated property names.
        Set<String> included = value.getIncluded();
        assertEquals(2, included.size());
        assertEquals(setOf("foo", "bar"), included);

        // "ordered" was not specified on the annotation, so it stays undefined.
        assertNull(value.getOrdered());

        // toString lists the included names; the underlying set is unordered,
        // so accept either order of "foo" and "bar".
        String description = value.toString();
        boolean fooThenBar = description.equals("JsonIncludeProperties.Value(included=[foo, bar],ordered=null)");
        boolean barThenFoo = description.equals("JsonIncludeProperties.Value(included=[bar, foo],ordered=null)");
        assertTrue(fooThenBar || barThenFoo);

        // Reading the same annotation again must yield an equal Value.
        JsonIncludeProperties.Value fromSameAnnotation = JsonIncludeProperties.Value.from(annotation);
        assertEquals(value, fromSameAnnotation);

        // The Value must survive a JDK serialize/deserialize round trip unchanged.
        byte[] serialized = jdkSerialize(value);
        JsonIncludeProperties.Value deserialized = jdkDeserialize(serialized);
        assertEquals(value, deserialized);
    }
}
