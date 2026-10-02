package com.fasterxml.jackson.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonSetterTest
    extends AnnotationTestUtil
{
    private static final class Bogus {
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

        int hashCode = emptyValue.hashCode();
        if (hashCode == 0) {
            fail();
        }

        assertEquals(emptyValue, emptyValue);
        assertFalse(emptyValue.equals(null));
        assertFalse(emptyValue.equals("xyz"));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        assertSame(emptyValue, JsonSetter.Value.from(null));

        JsonSetter annotation = Bogus.class.getField("field").getAnnotation(JsonSetter.class);
        JsonSetter.Value fromAnnotation = JsonSetter.Value.from(annotation);
        assertEquals(Nulls.FAIL, fromAnnotation.getValueNulls());
        assertEquals(Nulls.SKIP, fromAnnotation.getContentNulls());

        byte[] serialized = jdkSerialize(fromAnnotation);
        JsonSetter.Value deserialized = jdkDeserialize(serialized);

        assertEquals(fromAnnotation, deserialized);
    }

    @Test
    public void testConstruct() throws Exception
    {
        JsonSetter.Value constructed = JsonSetter.Value.construct(null, null);
        assertSame(emptyValue, constructed);
    }

    @Test
    public void testFactories() throws Exception
    {
        JsonSetter.Value contentNulls = JsonSetter.Value.forContentNulls(Nulls.SET);
        assertEquals(Nulls.DEFAULT, contentNulls.getValueNulls());
        assertEquals(Nulls.SET, contentNulls.getContentNulls());
        assertEquals(Nulls.SET, contentNulls.nonDefaultContentNulls());

        JsonSetter.Value valueNulls = JsonSetter.Value.forValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueNulls.getValueNulls());
        assertEquals(Nulls.DEFAULT, valueNulls.getContentNulls());
        assertEquals(Nulls.SKIP, valueNulls.nonDefaultValueNulls());
    }

    @Test
    public void testSimpleMerge()
    {
        JsonSetter.Value value = emptyValue.withContentNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, value.getContentNulls());

        value = value.withValueNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, value.getValueNulls());
    }

    @Test
    public void testWithMethods()
    {
        JsonSetter.Value contentFail = emptyValue.withContentNulls(null);
        assertSame(emptyValue, contentFail);

        contentFail = contentFail.withContentNulls(Nulls.FAIL);
        assertEquals(Nulls.FAIL, contentFail.getContentNulls());
        assertSame(contentFail, contentFail.withContentNulls(Nulls.FAIL));

        JsonSetter.Value valueSkipContentFail = contentFail.withValueNulls(Nulls.SKIP);
        assertEquals(Nulls.SKIP, valueSkipContentFail.getValueNulls());
        assertFalse(contentFail.equals(valueSkipContentFail));
        assertFalse(valueSkipContentFail.equals(contentFail));

        JsonSetter.Value resetToDefaults = valueSkipContentFail.withValueNulls(null, null);
        assertEquals(Nulls.DEFAULT, resetToDefaults.getContentNulls());
        assertEquals(Nulls.DEFAULT, resetToDefaults.getValueNulls());
        assertSame(resetToDefaults, resetToDefaults.withValueNulls(null, null));

        JsonSetter.Value merged = resetToDefaults.withOverrides(valueSkipContentFail);
        assertNotSame(valueSkipContentFail, merged);
        assertEquals(merged, valueSkipContentFail);
        assertEquals(valueSkipContentFail, merged);
    }
}
