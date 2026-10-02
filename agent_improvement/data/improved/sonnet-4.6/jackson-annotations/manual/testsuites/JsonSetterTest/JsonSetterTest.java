package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest
    extends AnnotationTestUtil
{
    // A dummy class whose field carries both nulls= and contentNulls= settings,
    // used by testFromAnnotation() to exercise Value.from(JsonSetter).
    private final static class Bogus {
        @JsonSetter(nulls=Nulls.FAIL, contentNulls=Nulls.SKIP)
        public int field;
    }

    private final JsonSetter.Value emptyValue = JsonSetter.Value.empty();

    @Test
    public void testEmpty()
    {
        assertEquals(Nulls.DEFAULT, emptyValue.getValueNulls());
        assertEquals(Nulls.DEFAULT, emptyValue.getContentNulls());

        assertEquals(JsonSetter.class, emptyValue.valueFor());

        assertNull(emptyValue.nonDefaultValueNulls());
        assertNull(emptyValue.nonDefaultContentNulls());
    }

    @Test
    public void testStdMethods() {
        assertEquals("JsonSetter.Value(valueNulls=DEFAULT,contentNulls=DEFAULT)",
                emptyValue.toString());
        assertNotEquals(0, emptyValue.hashCode());
        assertEquals(emptyValue, emptyValue);
        assertFalse(emptyValue.equals(null));
        assertFalse(emptyValue.equals("xyz"));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        // null input must return the shared empty instance
        assertSame(emptyValue, JsonSetter.Value.from(null));

        JsonSetter ann = Bogus.class.getField("field").getAnnotation(JsonSetter.class);
        JsonSetter.Value fromAnnotation = JsonSetter.Value.from(ann);
        assertEquals(Nulls.FAIL, fromAnnotation.getValueNulls());
        assertEquals(Nulls.SKIP, fromAnnotation.getContentNulls());

        // Verify JDK serialization round-trip produces an equal value
        byte[] serialized = jdkSerialize(fromAnnotation);
        JsonSetter.Value deserialized = jdkDeserialize(serialized);

        assertEquals(fromAnnotation, deserialized);
    }

    @Test
    public void testConstruct() throws Exception
    {
        // Both-null construct must return the shared empty instance
        JsonSetter.Value v = JsonSetter.Value.construct(null, null);
        assertSame(emptyValue, v);
    }

    @Test
    public void testFactories() throws Exception
    {
        JsonSetter.Value contentOnly = JsonSetter.Value.forContentNulls(Nulls.SET);
        assertEquals(Nulls.DEFAULT, contentOnly.getValueNulls());
        assertEquals(Nulls.SET, contentOnly.getContentNulls());
        assertEquals(Nulls.SET, contentOnly.nonDefaultContentNulls());

        JsonSetter.Value valueOnly = JsonSetter.Value.forValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueOnly.getValueNulls());
        assertEquals(Nulls.DEFAULT, valueOnly.getContentNulls());
        assertEquals(Nulls.SKIP, valueOnly.nonDefaultValueNulls());
    }

    @Test
    public void testSimpleMerge()
    {
        JsonSetter.Value withContentSkip = emptyValue.withContentNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withContentSkip.getContentNulls());
        JsonSetter.Value withBothSet = withContentSkip.withValueNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withBothSet.getValueNulls());
    }

    @Test
    public void testWithMethods()
    {
        // withContentNulls(null) is a no-op: must return the same empty instance
        JsonSetter.Value afterNullContentNulls = emptyValue.withContentNulls(null);
        assertSame(emptyValue, afterNullContentNulls);

        // withContentNulls(FAIL) creates a new instance with FAIL content-nulls
        JsonSetter.Value withFailContentNulls = afterNullContentNulls.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, withFailContentNulls.getContentNulls());
        // Calling with the same value must return the identical instance (optimization)
        assertSame(withFailContentNulls, withFailContentNulls.withContentNulls(Nulls.FAIL));

        // withValueNulls(SKIP) produces a new instance that differs from the source
        JsonSetter.Value withSkipValueNulls = withFailContentNulls.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, withSkipValueNulls.getValueNulls());
        assertFalse(withFailContentNulls.equals(withSkipValueNulls));
        assertFalse(withSkipValueNulls.equals(withFailContentNulls));

        // withValueNulls(null, null) resets both fields to DEFAULT, returning the empty instance
        JsonSetter.Value resetToBothDefault = withSkipValueNulls.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToBothDefault.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToBothDefault.getValueNulls());
        assertSame(resetToBothDefault, resetToBothDefault.withValueNulls(null, null));

        // withOverrides applies non-DEFAULT values from the override; result equals the override
        JsonSetter.Value merged = resetToBothDefault.withOverrides(withSkipValueNulls);
        assertNotSame(withSkipValueNulls, merged);
        assertEquals(merged, withSkipValueNulls);
        assertEquals(withSkipValueNulls, merged);
    }
}
