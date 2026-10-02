package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link JsonTypeInfo.Value}: the immutable value object that captures
 * the configuration of a {@link JsonTypeInfo} annotation.
 *
 * The tests cover four areas:
 * <ul>
 *   <li>reading a {@code Value} from an annotation ({@code from(...)}),</li>
 *   <li>the copy-on-write mutators ({@code withXxx(...)}),</li>
 *   <li>the {@code requireTypeIdForSubtypes} property, and</li>
 *   <li>the {@code writeTypeIdForDefaultImpl} property (see annotations#342).</li>
 * </ul>
 */
public class JsonTypeInfoTest
    extends AnnotationTestUtil
{
    /*
    /**********************************************************************
    /* Sample annotated classes, each exercising a distinct configuration
    /**********************************************************************
     */

    // Id by CLASS, type id visible, default impl is an annotation type
    // (treated as "no default impl" -> null), and type id required for subtypes.
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible=true,
            defaultImpl = JsonTypeInfo.class, requireTypeIdForSubtypes = OptBoolean.TRUE)
    private final static class Anno1 { }

    // Id by NAME, included as an external property named "ext",
    // default impl Void, and type id NOT required for subtypes.
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class, requireTypeIdForSubtypes = OptBoolean.FALSE)
    private final static class Anno2 { }

    // Like Anno2 but leaves requireTypeIdForSubtypes at its default (DEFAULT -> null).
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.EXTERNAL_PROPERTY,
            property = "ext",
            defaultImpl = Void.class)
    private final static class Anno3 { }

    // writeTypeIdForDefaultImpl explicitly disabled (FALSE).
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, visible = true,
            defaultImpl = Void.class,
            writeTypeIdForDefaultImpl = OptBoolean.FALSE)
    private final static class Anno4 { }

    // writeTypeIdForDefaultImpl explicitly enabled (TRUE).
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
            writeTypeIdForDefaultImpl = OptBoolean.TRUE)
    private final static class Anno5 { }

    /**
     * Builds a {@link JsonTypeInfo.Value} from the {@link JsonTypeInfo}
     * annotation declared on the given sample class.
     */
    private static JsonTypeInfo.Value valueOf(Class<?> annotatedClass) {
        return JsonTypeInfo.Value.from(annotatedClass.getAnnotation(JsonTypeInfo.class));
    }

    /*
    /**********************************************************************
    /* Reading Value from an annotation
    /**********************************************************************
     */

    @Test
    public void testEmpty() {
        // 07-Mar-2017, tatu: Important to distinguish "none" from 'empty' value:
        // a null annotation yields a null Value (not the EMPTY instance).
        assertNull(JsonTypeInfo.Value.from(null));
    }

    @Test
    public void testFromAnnotation() throws Exception
    {
        JsonTypeInfo.Value fromAnno1 = valueOf(Anno1.class);
        assertEquals(JsonTypeInfo.Id.CLASS, fromAnno1.getIdType());
        // include not specified on Anno1 -> annotation default of PROPERTY
        assertEquals(JsonTypeInfo.As.PROPERTY, fromAnno1.getInclusionType());
        // property not specified on Anno1 -> Id.CLASS default property name "@class"
        assertEquals("@class", fromAnno1.getPropertyName());
        assertTrue(fromAnno1.getIdVisible());
        // defaultImpl was the annotation type itself -> normalized to null
        assertNull(fromAnno1.getDefaultImpl());
        assertTrue(fromAnno1.getRequireTypeIdForSubtypes());

        JsonTypeInfo.Value fromAnno2 = valueOf(Anno2.class);
        assertEquals(JsonTypeInfo.Id.NAME, fromAnno2.getIdType());
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY, fromAnno2.getInclusionType());
        assertEquals("ext", fromAnno2.getPropertyName());
        assertFalse(fromAnno2.getIdVisible());
        assertEquals(Void.class, fromAnno2.getDefaultImpl());
        assertFalse(fromAnno2.getRequireTypeIdForSubtypes());

        // A value equals itself...
        assertTrue(fromAnno1.equals(fromAnno1));
        assertTrue(fromAnno2.equals(fromAnno2));
        // ...but two values with different configuration are not equal (both directions).
        assertFalse(fromAnno1.equals(fromAnno2));
        assertFalse(fromAnno2.equals(fromAnno1));

        assertEquals("JsonTypeInfo.Value(idType=CLASS,includeAs=PROPERTY,propertyName=@class,defaultImpl=NULL,idVisible=true,requireTypeIdForSubtypes=true,writeTypeIdForDefaultImpl=null)",
                fromAnno1.toString());
        assertEquals("JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=false,writeTypeIdForDefaultImpl=null)",
                fromAnno2.toString());

        // A Value should survive a JDK serialize/deserialize round-trip unchanged.
        byte[] serialized = jdkSerialize(fromAnno1);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);
        assertEquals(fromAnno1, deserialized);
    }

    /*
    /**********************************************************************
    /* Copy-on-write mutators
    /**********************************************************************
     */

    @Test
    public void testMutators() throws Exception
    {
        JsonTypeInfo.Value base = valueOf(Anno1.class);
        assertEquals(JsonTypeInfo.Id.CLASS, base.getIdType());

        // withIdType: unchanged value returns same instance; changes produce new values.
        assertSame(base, base.withIdType(JsonTypeInfo.Id.CLASS));
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS,
                base.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS).getIdType());
        assertEquals(JsonTypeInfo.Id.SIMPLE_NAME,
                base.withIdType(JsonTypeInfo.Id.SIMPLE_NAME).getIdType());

        // withInclusionType
        assertEquals(JsonTypeInfo.As.PROPERTY, base.getInclusionType());
        assertSame(base, base.withInclusionType(JsonTypeInfo.As.PROPERTY));
        assertEquals(JsonTypeInfo.As.EXTERNAL_PROPERTY,
                base.withInclusionType(JsonTypeInfo.As.EXTERNAL_PROPERTY).getInclusionType());

        // withDefaultImpl: base's defaultImpl is already null, so null is a no-op.
        assertSame(base, base.withDefaultImpl(null));
        assertEquals(String.class, base.withDefaultImpl(String.class).getDefaultImpl());

        // withIdVisible: base is already visible, so true is a no-op.
        assertSame(base, base.withIdVisible(true));
        assertFalse(base.withIdVisible(false).getIdVisible());

        // withPropertyName
        assertEquals("foobar", base.withPropertyName("foobar").getPropertyName());
    }

    /*
    /**********************************************************************
    /* requireTypeIdForSubtypes property
    /**********************************************************************
     */

    @Test
    public void testWithRequireTypeIdForSubtypes() {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getRequireTypeIdForSubtypes());

        assertEquals(Boolean.TRUE,
                empty.withRequireTypeIdForSubtypes(Boolean.TRUE).getRequireTypeIdForSubtypes());
        assertEquals(Boolean.FALSE,
                empty.withRequireTypeIdForSubtypes(Boolean.FALSE).getRequireTypeIdForSubtypes());
        assertNull(empty.withRequireTypeIdForSubtypes(null).getRequireTypeIdForSubtypes());
    }

    @Test
    public void testDefaultValueForRequireTypeIdForSubtypes() {
        // Anno3 leaves requireTypeIdForSubtypes unset -> null.
        JsonTypeInfo.Value fromAnno3 = valueOf(Anno3.class);
        assertNull(fromAnno3.getRequireTypeIdForSubtypes());

        assertEquals("JsonTypeInfo.Value(idType=NAME,includeAs=EXTERNAL_PROPERTY,propertyName=ext,"
                + "defaultImpl=java.lang.Void,idVisible=false,requireTypeIdForSubtypes=null,writeTypeIdForDefaultImpl=null)",
                fromAnno3.toString());
    }

    /*
    /**********************************************************************
    /* writeTypeIdForDefaultImpl property [annotations#342]
    /**********************************************************************
     */

    @Test
    public void testWriteTypeIdForDefaultImplFromAnnotation() {
        // Anno4: explicitly FALSE -> should NOT write type id for default impl.
        JsonTypeInfo.Value fromAnno4 = valueOf(Anno4.class);
        assertEquals(Boolean.FALSE, fromAnno4.getWriteTypeIdForDefaultImpl());
        assertFalse(fromAnno4.shouldWriteTypeIdForDefaultImpl());

        // Anno5: explicitly TRUE -> should write type id for default impl.
        JsonTypeInfo.Value fromAnno5 = valueOf(Anno5.class);
        assertEquals(Boolean.TRUE, fromAnno5.getWriteTypeIdForDefaultImpl());
        assertTrue(fromAnno5.shouldWriteTypeIdForDefaultImpl());

        // Anno3: unset (null), but the default behavior is to write (true).
        JsonTypeInfo.Value fromAnno3 = valueOf(Anno3.class);
        assertNull(fromAnno3.getWriteTypeIdForDefaultImpl());
        assertTrue(fromAnno3.shouldWriteTypeIdForDefaultImpl());
    }

    @Test
    public void testWithWriteTypeIdForDefaultImpl() {
        // Default (null) on EMPTY still means "should write" (true).
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        assertNull(empty.getWriteTypeIdForDefaultImpl());
        assertTrue(empty.shouldWriteTypeIdForDefaultImpl());

        JsonTypeInfo.Value withFalse = empty.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        assertEquals(Boolean.FALSE, withFalse.getWriteTypeIdForDefaultImpl());
        assertFalse(withFalse.shouldWriteTypeIdForDefaultImpl());

        JsonTypeInfo.Value withTrue = empty.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        assertEquals(Boolean.TRUE, withTrue.getWriteTypeIdForDefaultImpl());
        assertTrue(withTrue.shouldWriteTypeIdForDefaultImpl());

        // Mutating back to null restores the default ("should write" = true).
        JsonTypeInfo.Value backToNull = withFalse.withWriteTypeIdForDefaultImpl(null);
        assertNull(backToNull.getWriteTypeIdForDefaultImpl());
        assertTrue(backToNull.shouldWriteTypeIdForDefaultImpl());

        // Mutating to the value already held returns the same instance.
        assertSame(withFalse, withFalse.withWriteTypeIdForDefaultImpl(Boolean.FALSE));
        assertSame(withTrue, withTrue.withWriteTypeIdForDefaultImpl(Boolean.TRUE));
    }

    @Test
    public void testWriteTypeIdForDefaultImplEqualsAndHashCode() {
        JsonTypeInfo.Value trueA = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value trueB = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE);
        JsonTypeInfo.Value falseValue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);
        JsonTypeInfo.Value nullValue = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(null);

        // Same writeTypeIdForDefaultImpl -> equal and consistent hashCode.
        assertEquals(trueA, trueB);
        assertEquals(trueA.hashCode(), trueB.hashCode());

        // Differing writeTypeIdForDefaultImpl (true vs false vs null) -> not equal.
        assertNotEquals(trueA, falseValue);
        assertNotEquals(trueA, nullValue);
        assertNotEquals(falseValue, nullValue);
    }

    @Test
    public void testWriteTypeIdForDefaultImplToString() {
        assertTrue(JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE)
                .toString().contains("writeTypeIdForDefaultImpl=false"));
        assertTrue(JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.TRUE)
                .toString().contains("writeTypeIdForDefaultImpl=true"));
    }

    @Test
    public void testWriteTypeIdForDefaultImplConstruct() {
        // construct(...) with writeTypeIdForDefaultImpl = FALSE is honored.
        JsonTypeInfo.Value constructed = JsonTypeInfo.Value.construct(
                JsonTypeInfo.Id.CLASS, JsonTypeInfo.As.PROPERTY,
                null, Void.class, false, null, Boolean.FALSE);
        assertEquals(Boolean.FALSE, constructed.getWriteTypeIdForDefaultImpl());
        assertFalse(constructed.shouldWriteTypeIdForDefaultImpl());
    }

    @Test
    public void testWriteTypeIdForDefaultImplSerialization() throws Exception {
        JsonTypeInfo.Value original = JsonTypeInfo.Value.EMPTY.withWriteTypeIdForDefaultImpl(Boolean.FALSE);

        byte[] serialized = jdkSerialize(original);
        JsonTypeInfo.Value deserialized = jdkDeserialize(serialized);

        // The property must survive the JDK serialize/deserialize round-trip.
        assertEquals(original, deserialized);
        assertEquals(Boolean.FALSE, deserialized.getWriteTypeIdForDefaultImpl());
    }
}
