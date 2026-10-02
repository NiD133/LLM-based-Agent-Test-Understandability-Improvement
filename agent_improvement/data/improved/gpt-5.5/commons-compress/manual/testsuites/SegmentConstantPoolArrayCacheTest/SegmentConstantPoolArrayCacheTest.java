/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class SegmentConstantPoolArrayCacheTest {

    private static final String SHARED_KEY = "Shared";

    @Test
    void testMultipleArrayMultipleHit() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] arrayOne = { "Zero", SHARED_KEY, "Two", SHARED_KEY, SHARED_KEY };
        final String[] arrayTwo = { SHARED_KEY, "One", SHARED_KEY, SHARED_KEY, SHARED_KEY };

        List<Integer> indexesInArrayOne = arrayCache.indexesForArrayKey(arrayOne, SHARED_KEY);
        List<Integer> indexesInArrayTwo = arrayCache.indexesForArrayKey(arrayTwo, SHARED_KEY);

        indexesInArrayOne = arrayCache.indexesForArrayKey(arrayOne, "Two");
        indexesInArrayTwo = arrayCache.indexesForArrayKey(arrayTwo, SHARED_KEY);

        assertIndexes(indexesInArrayOne, 2);

        indexesInArrayOne = arrayCache.indexesForArrayKey(arrayOne, SHARED_KEY);
        assertIndexes(indexesInArrayOne, 1, 3, 4);
        assertIndexes(indexesInArrayTwo, 0, 2, 3, 4);

        final List<Integer> missingIndexes = arrayCache.indexesForArrayKey(arrayOne, "Not found");
        assertIndexes(missingIndexes);
    }

    @Test
    void testSingleMultipleHitArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String repeatedKey = "OneThreeFour";
        final String[] array = { "Zero", repeatedKey, "Two", repeatedKey, repeatedKey };

        final List<Integer> indexes = arrayCache.indexesForArrayKey(array, repeatedKey);

        assertIndexes(indexes, 1, 3, 4);
    }

    @Test
    void testSingleSimpleArray() {
        final SegmentConstantPoolArrayCache arrayCache = new SegmentConstantPoolArrayCache();
        final String[] array = { "Zero", "One", "Two", "Three", "Four" };

        final List<Integer> indexes = arrayCache.indexesForArrayKey(array, "Three");

        assertIndexes(indexes, 3);
    }

    private void assertIndexes(final List<Integer> actualIndexes, final int... expectedIndexes) {
        assertEquals(expectedIndexes.length, actualIndexes.size());
        for (int index = 0; index < expectedIndexes.length; index++) {
            assertEquals(expectedIndexes[index], actualIndexes.get(index).intValue());
        }
    }

}
