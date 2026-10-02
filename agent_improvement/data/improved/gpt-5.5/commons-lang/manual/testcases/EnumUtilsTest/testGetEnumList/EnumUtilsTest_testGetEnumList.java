package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumList extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testGetEnumList() {
        final List<Traffic> trafficLights = EnumUtils.getEnumList(Traffic.class);

        assertEquals(3, trafficLights.size());
        assertEquals(Traffic.RED, trafficLights.get(0));
        assertEquals(Traffic.AMBER, trafficLights.get(1));
        assertEquals(Traffic.GREEN, trafficLights.get(2));
    }
}
