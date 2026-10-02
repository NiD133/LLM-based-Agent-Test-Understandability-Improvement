package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumList extends AbstractLangTest {

    /**
     * {@link EnumUtils#getEnumList(Class)} should return every constant of the
     * enum, in declaration order, as a {@link List}.
     */
    @Test
    void testGetEnumList() {
        final List<Traffic> trafficLights = EnumUtils.getEnumList(Traffic.class);

        // The list mirrors the enum's declaration order: RED, AMBER, GREEN.
        assertEquals(3, trafficLights.size());
        assertEquals(Traffic.RED, trafficLights.get(0));
        assertEquals(Traffic.AMBER, trafficLights.get(1));
        assertEquals(Traffic.GREEN, trafficLights.get(2));
    }
}
