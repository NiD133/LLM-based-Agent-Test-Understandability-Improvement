package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies how {@link JacksonInject.Value#from(JacksonInject)} translates a
 * physical {@code @JacksonInject} annotation (read off the fields of the shared
 * {@code Bogus} helper type) into its logical {@code Value} representation.
 */
public class JacksonInjectTest_testFromAnnotation extends AnnotationTestUtil {

    /** The canonical "no settings" value; every freshly-built Value is compared against it. */
    private final JacksonInject.Value EMPTY = JacksonInject.Value.empty();

    @Test
    public void testFromAnnotation() throws Exception {
        // A null annotation always maps to the shared EMPTY instance.
        assertSame(EMPTY, JacksonInject.Value.from(null));

        // Field "field" carries an annotation with an explicit id and useInput=false.
        JacksonInject annWithIdAndUseInput = annotationOn("field");
        JacksonInject.Value configured = JacksonInject.Value.from(annWithIdAndUseInput);
        assertEquals("inject", configured.getId());
        assertEquals(Boolean.FALSE, configured.getUseInput());
        assertEquals("JacksonInject.Value(id=inject,useInput=false,optional=false)", configured.toString());

        // A configured value is distinct from EMPTY, in both directions.
        assertFalse(configured.equals(EMPTY));
        assertFalse(EMPTY.equals(configured));

        // A round-trip through JDK serialization must yield an equal value.
        byte[] serialized = jdkSerialize(configured);
        JacksonInject.Value deserialized = jdkDeserialize(serialized);
        assertEquals(configured, deserialized);

        // Field "vanilla" uses all defaults, so "optional" stays null.
        JacksonInject annVanilla = annotationOn("vanilla");
        JacksonInject.Value vanilla = JacksonInject.Value.from(annVanilla);
        assertEquals(JacksonInject.Value.construct(null, null, null), vanilla,
                "optional should be `null` by default");

        // Field "optionalField" sets optional=true and nothing else.
        JacksonInject annOptional = annotationOn("optionalField");
        JacksonInject.Value optional = JacksonInject.Value.from(annOptional);
        assertEquals(JacksonInject.Value.construct(null, null, true), optional);
    }

    /** Reads the {@code @JacksonInject} annotation declared on the named {@code Bogus} field. */
    private JacksonInject annotationOn(String fieldName) throws Exception {
        return JacksonInjectTest.Bogus.class.getField(fieldName).getAnnotation(JacksonInject.class);
    }
}
