/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link JavaVersion}.
 */
class JavaVersionTest extends AbstractLangTest {

    @Test
    void testAtLeast() {
        // Lower version is not at least a higher version
        assertFalse(JavaVersion.JAVA_1_2.atLeast(JavaVersion.JAVA_1_5), "JAVA_1_2 should not be at least JAVA_1_5");
        assertFalse(JavaVersion.JAVA_1_6.atLeast(JavaVersion.JAVA_1_7), "JAVA_1_6 should not be at least JAVA_1_7");

        // Higher version is at least a lower version
        assertTrue(JavaVersion.JAVA_1_5.atLeast(JavaVersion.JAVA_1_2), "JAVA_1_5 should be at least JAVA_1_2");

        // JAVA_0_9 (Android) has an internal float value of 1.5, so it compares equal to JAVA_1_5 but not JAVA_1_6
        assertTrue(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_5), "JAVA_0_9 (float=1.5) should be at least JAVA_1_5");
        assertFalse(JavaVersion.JAVA_0_9.atLeast(JavaVersion.JAVA_1_6), "JAVA_0_9 (float=1.5) should not be at least JAVA_1_6");
    }

    @Test
    void testGetJavaVersion() {
        // Legacy 1.x version strings (Java 1.1 through 1.8)
        assertEquals(JavaVersion.JAVA_0_9, JavaVersion.get("0.9"), "version string '0.9' should map to JAVA_0_9");
        assertEquals(JavaVersion.JAVA_1_1, JavaVersion.get("1.1"), "version string '1.1' should map to JAVA_1_1");
        assertEquals(JavaVersion.JAVA_1_2, JavaVersion.get("1.2"), "version string '1.2' should map to JAVA_1_2");
        assertEquals(JavaVersion.JAVA_1_3, JavaVersion.get("1.3"), "version string '1.3' should map to JAVA_1_3");
        assertEquals(JavaVersion.JAVA_1_4, JavaVersion.get("1.4"), "version string '1.4' should map to JAVA_1_4");
        assertEquals(JavaVersion.JAVA_1_5, JavaVersion.get("1.5"), "version string '1.5' should map to JAVA_1_5");
        assertEquals(JavaVersion.JAVA_1_6, JavaVersion.get("1.6"), "version string '1.6' should map to JAVA_1_6");
        assertEquals(JavaVersion.JAVA_1_7, JavaVersion.get("1.7"), "version string '1.7' should map to JAVA_1_7");
        assertEquals(JavaVersion.JAVA_1_8, JavaVersion.get("1.8"), "version string '1.8' should map to JAVA_1_8");

        // Modern single-number version strings (Java 9 and later)
        assertEquals(JavaVersion.JAVA_9,  JavaVersion.get("9"),  "version string '9' should map to JAVA_9");
        assertEquals(JavaVersion.JAVA_10, JavaVersion.get("10"), "version string '10' should map to JAVA_10");
        assertEquals(JavaVersion.JAVA_11, JavaVersion.get("11"), "version string '11' should map to JAVA_11");
        assertEquals(JavaVersion.JAVA_12, JavaVersion.get("12"), "version string '12' should map to JAVA_12");
        assertEquals(JavaVersion.JAVA_13, JavaVersion.get("13"), "version string '13' should map to JAVA_13");
        assertEquals(JavaVersion.JAVA_14, JavaVersion.get("14"), "version string '14' should map to JAVA_14");
        assertEquals(JavaVersion.JAVA_15, JavaVersion.get("15"), "version string '15' should map to JAVA_15");
        assertEquals(JavaVersion.JAVA_16, JavaVersion.get("16"), "version string '16' should map to JAVA_16");
        assertEquals(JavaVersion.JAVA_17, JavaVersion.get("17"), "version string '17' should map to JAVA_17");
        assertEquals(JavaVersion.JAVA_18, JavaVersion.get("18"), "version string '18' should map to JAVA_18");
        assertEquals(JavaVersion.JAVA_19, JavaVersion.get("19"), "version string '19' should map to JAVA_19");
        assertEquals(JavaVersion.JAVA_20, JavaVersion.get("20"), "version string '20' should map to JAVA_20");
        assertEquals(JavaVersion.JAVA_21, JavaVersion.get("21"), "version string '21' should map to JAVA_21");
        assertEquals(JavaVersion.JAVA_22, JavaVersion.get("22"), "version string '22' should map to JAVA_22");
        assertEquals(JavaVersion.JAVA_23, JavaVersion.get("23"), "version string '23' should map to JAVA_23");
        assertEquals(JavaVersion.JAVA_24, JavaVersion.get("24"), "version string '24' should map to JAVA_24");
        assertEquals(JavaVersion.JAVA_25, JavaVersion.get("25"), "version string '25' should map to JAVA_25");
        assertEquals(JavaVersion.JAVA_26, JavaVersion.get("26"), "version string '26' should map to JAVA_26");
        assertEquals(JavaVersion.JAVA_27, JavaVersion.get("27"), "version string '27' should map to JAVA_27");

        // Unrecognized or out-of-range versions fall back to JAVA_RECENT
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("1.10"), "version string '1.10' (decimal part > .9) should fall back to JAVA_RECENT");
        assertEquals(JavaVersion.JAVA_RECENT, JavaVersion.get("99"), "unrecognized high version '99' should fall back to JAVA_RECENT");

        // getJavaVersion is a public alias that delegates to the package-private get method
        assertEquals(JavaVersion.get("1.5"), JavaVersion.getJavaVersion("1.5"), "getJavaVersion should return the same result as get");
    }

    @Test
    void testToString() {
        assertEquals("1.2", JavaVersion.JAVA_1_2.toString());
    }

}
