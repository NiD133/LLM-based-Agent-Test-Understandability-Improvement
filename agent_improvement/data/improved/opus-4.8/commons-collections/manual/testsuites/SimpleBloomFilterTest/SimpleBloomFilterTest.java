/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.bloomfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link SimpleBloomFilter}.
 */
class SimpleBloomFilterTest extends AbstractBloomFilterTest<SimpleBloomFilter> {

    @Override
    protected SimpleBloomFilter createEmptyFilter(final Shape shape) {
        return new SimpleBloomFilter(shape);
    }

    /**
     * Merging a {@link BitMapExtractor} that emits fewer bit maps than the shape
     * requires should still succeed, leaving the remaining bit maps untouched.
     * The test shape spans two longs, but the extractor supplies only a single
     * long (with one bit set), so the resulting filter has a cardinality of one.
     */
    @Test
    void testMergeShortBitMapExtractor() {
        final SimpleBloomFilter filter = createEmptyFilter(getTestShape());

        // Emit a single bit map (one long) with exactly one bit set, even though
        // the shape expects two longs.
        final BitMapExtractor shortExtractor = consumer -> consumer.test(2L);

        assertTrue(filter.merge(shortExtractor), "merge should report success");
        assertEquals(1, filter.cardinality(), "exactly one bit should be set");
    }
}
