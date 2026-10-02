package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonSetter.Value}, the helper class that captures the
 * {@code nulls} / {@code contentNulls} settings of a {@link JsonSetter}
 * annotation and supports merging of layered configuration.
 */
public class JsonSetterTest
    extends AnnotationTestUtil
{
    /**
     * Sample annotated field used to verify that a {@link JsonSetter.Value}
     * can be built from a real annotation instance via reflection.
     */
    private final static class Bogus {
        @JsonSetter(nulls = Nulls.FAIL, contentNulls = Nulls.SKIP)
        public int field;
    }

    /** The canonical "no overrides" value: both null-handling settings are {@link Nulls#DEFAULT}. */
    private final JsonSetter.Value EMPTY = JsonSetter.Value.empty();

    @Test
    public void testEmpty()
    {
        // An empty Value leaves both null-handling settings at their defaults...
        assertEquals(Nulls.DEFAULT, EMPTY.getValueNulls());
        assertEquals(Nulls.DEFAULT, EMPTY.getContentNulls());

        // ...is associated with the JsonSetter annotation type...
        assertEquals(JsonSetter.class, EMPTY.valueFor());

        // ...and reports no non-default settings.
        assertNull(EMPTY.nonDefaultValueNulls());
        assertNull(EMPTY.nonDefaultContentNulls());
    }

    @Test
    public void testStdMethods() {
        // toString() should spell out both null-handling settings.
        assertEquals("JsonSetter.Value(valueNulls=DEFAULT,contentNulls=DEFAULT)",
                EMPTY.toString());

        // hashCode() has no fixed contract here, but should not collapse to 0.
        int hash = EMPTY.hashCode();
        if (hash == 0) {
            fail("hashCode() should not evaluate to 0");
        }

        // equals(): reflexive, but never equal to null or to an unrelated type.
        assertEquals(EMPTY, EMPTY);
        assertFalse(EMPTY.equals(null));
        assertFalse(EMPTY.equals("xyz"));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        // A null annotation yields the shared EMPTY instance.
        assertSame(EMPTY, JsonSetter.Value.from(null));

        // Building from a real annotation preserves its nulls / contentNulls settings.
        JsonSetter annotation = Bogus.class.getField("field").getAnnotation(JsonSetter.class);
        JsonSetter.Value fromAnnotation = JsonSetter.Value.from(annotation);
        assertEquals(Nulls.FAIL, fromAnnotation.getValueNulls());
        assertEquals(Nulls.SKIP, fromAnnotation.getContentNulls());

        // The Value must survive a JDK serialize / deserialize round-trip unchanged.
        byte[] serialized = jdkSerialize(fromAnnotation);
        JsonSetter.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fromAnnotation, deserialized);
    }

    @Test
    public void testConstruct() throws Exception
    {
        // construct(null, null) normalizes to defaults, returning the shared EMPTY instance.
        JsonSetter.Value constructed = JsonSetter.Value.construct(null, null);
        assertSame(EMPTY, constructed);
    }

    @Test
    public void testFactories() throws Exception
    {
        // forContentNulls() sets only the content-nulls setting; value-nulls stays default.
        JsonSetter.Value contentOnly = JsonSetter.Value.forContentNulls(Nulls.SET);
        assertEquals(Nulls.DEFAULT, contentOnly.getValueNulls());
        assertEquals(Nulls.SET, contentOnly.getContentNulls());
        assertEquals(Nulls.SET, contentOnly.nonDefaultContentNulls());

        // forValueNulls() sets only the value-nulls setting; content-nulls stays default.
        JsonSetter.Value valueOnly = JsonSetter.Value.forValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueOnly.getValueNulls());
        assertEquals(Nulls.DEFAULT, valueOnly.getContentNulls());
        assertEquals(Nulls.SKIP, valueOnly.nonDefaultValueNulls());
    }

    @Test
    public void testSimpleMerge()
    {
        // withContentNulls / withValueNulls each layer a single setting onto the value.
        JsonSetter.Value withContent = EMPTY.withContentNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withContent.getContentNulls());

        JsonSetter.Value withBoth = withContent.withValueNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withBoth.getValueNulls());
    }

    @Test
    public void testWithMethods()
    {
        // Passing null is a no-op that returns the same (unchanged) instance.
        JsonSetter.Value unchanged = EMPTY.withContentNulls(null);
        assertSame(EMPTY, unchanged);

        // Setting a real content-nulls value produces a changed instance...
        JsonSetter.Value withContentFail = unchanged.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withContentFail.getContentNulls());
        // ...and re-setting the identical value returns the same instance.
        assertSame(withContentFail, withContentFail.withContentNulls(Nulls.FAIL));

        // Layering a value-nulls setting yields a distinct value, unequal to the original.
        JsonSetter.Value withValueSkip = withContentFail.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withValueSkip.getValueNulls());
        assertFalse(withContentFail.equals(withValueSkip));
        assertFalse(withValueSkip.equals(withContentFail));

        // withValueNulls(null, null) resets both settings back to default.
        JsonSetter.Value resetToDefault = withValueSkip.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToDefault.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToDefault.getValueNulls());
        // Resetting an already-default value again returns the same instance.
        assertSame(resetToDefault, resetToDefault.withValueNulls(null, null));

        // Overriding the default value with withValueSkip yields an equal-but-distinct instance.
        JsonSetter.Value merged = resetToDefault.withOverrides(withValueSkip);
        assertNotSame(withValueSkip, merged);
        assertEquals(merged, withValueSkip);
        assertEquals(withValueSkip, merged);
    }
}
