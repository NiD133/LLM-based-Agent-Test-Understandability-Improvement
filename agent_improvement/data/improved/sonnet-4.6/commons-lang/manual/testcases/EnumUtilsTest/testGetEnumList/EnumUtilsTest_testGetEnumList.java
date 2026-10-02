package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumList extends AbstractLangTest {

    @Test
    void testGetEnumList() {
        List<Traffic> enumList = EnumUtils.getEnumList(Traffic.class);

        List<Traffic> expectedOrder = Arrays.asList(Traffic.RED, Traffic.AMBER, Traffic.GREEN);
        assertEquals(expectedOrder, enumList);
    }
}
