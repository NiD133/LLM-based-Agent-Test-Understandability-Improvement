package com.fasterxml.jackson.annotation;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonIgnorePropertiesTest_testMutantFactories extends AnnotationTestUtil {

    private final JsonIgnoreProperties.Value EMPTY = JsonIgnoreProperties.Value.empty();

    @Test
    public void testWithIgnored_varargs_addsAllNames() {
        assertEquals(2, EMPTY.withIgnored("a", "b").getIgnored().size());
    }

    @Test
    public void testWithIgnored_singletonSet_addsName() {
        assertEquals(1, EMPTY.withIgnored(Collections.singleton("x")).getIgnored().size());
    }

    @Test
    public void testWithIgnored_nullSet_yieldsEmptyIgnored() {
        assertEquals(0, EMPTY.withIgnored((Set<String>) null).getIgnored().size());
    }

    @Test
    public void testWithIgnoreUnknown_enablesIgnoreUnknown() {
        assertTrue(EMPTY.withIgnoreUnknown().getIgnoreUnknown());
    }

    @Test
    public void testWithoutIgnoreUnknown_disablesIgnoreUnknown() {
        assertFalse(EMPTY.withoutIgnoreUnknown().getIgnoreUnknown());
    }

    @Test
    public void testWithAllowGetters_enablesAllowGetters() {
        assertTrue(EMPTY.withAllowGetters().getAllowGetters());
    }

    @Test
    public void testWithoutAllowGetters_disablesAllowGetters() {
        assertFalse(EMPTY.withoutAllowGetters().getAllowGetters());
    }

    @Test
    public void testWithAllowSetters_enablesAllowSetters() {
        assertTrue(EMPTY.withAllowSetters().getAllowSetters());
    }

    @Test
    public void testWithoutAllowSetters_disablesAllowSetters() {
        assertFalse(EMPTY.withoutAllowSetters().getAllowSetters());
    }

    @Test
    public void testWithMerge_enablesMerge() {
        assertTrue(EMPTY.withMerge().getMerge());
    }

    @Test
    public void testWithoutMerge_disablesMerge() {
        assertFalse(EMPTY.withoutMerge().getMerge());
    }
}
