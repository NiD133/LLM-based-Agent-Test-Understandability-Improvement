# Human Evaluation — 180 pairs over 15 surveys

Seed `20260924`. Drawn from 13829 eligible pairs (4836 distinct test logics).

## Summary

| model | manual | auto | total |
|---|--:|--:|--:|
| gpt-5.5 | 30 | 30 | 60 |
| opus-4.8 | 30 | 30 | 60 |
| sonnet-4.6 | 30 | 30 | 60 |
| **total** | **90** | **90** | **180** |

LOC: short 60 / medium 90 / long 30  —  Part 1 90 / Part 2 90  —  coverage floor 56 / random 124

## Question order — identical in every survey

### Part 1 — the six pairs as twelve Likert items

`pair` is the slot number used in the per-survey lists below; which test sits in a slot differs per survey. The attention check is a fixed question at screen 9 and is the same in all fifteen surveys.

| | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| **item** | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 |
| **screen** | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 10 | 11 | 12 | 13 |
| **pair** | 6 | 5 | 4 | 2 | 3 | 1 | 4 | 6 | 2 | 1 | 5 | 3 |
| **side** | I | O | O | I | I | O | I | O | O | I | I | O |

Screen gap between the two sides of a pair: pair 4 = 4, pair 1 = 5, pair 2 = 6, pair 6 = 7, pair 3 = 8, pair 5 = 10. Three pairs are seen improved-first and three original-first.

### Part 2 — which side is Test A

| pair | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|
| **Test A** | I | I | O | I | O | O |
| **Test B** | O | O | I | O | I | I |

(I = improved, O = original)

## Coverage — pairs carrying each type, per column

`n (p2)` = pairs in the column, of which in Part 2. Bold = the type is recurrent in that column (≥ 2%), so the floor applies: ≥ 3 overall and ≥ 2 in Part 2. A dash = not recurrent there.

| transformation | gpt-5.5/auto | gpt-5.5/manual | opus-4.8/auto | opus-4.8/manual | sonnet-4.6/auto | sonnet-4.6/manual |
|---|---|---|---|---|---|---|
| Add Attribute Modifier | – | 5 (4) | – | **4 (3)** | – | 2 (2) |
| Add Method Annotation | – | – | – | 2 (0) | – | **4 (2)** |
| Add Variable Modifier | **3 (2)** | **3 (2)** | 2 (0) | **4 (3)** | – | – |
| Attribute Rename | – | **9 (4)** | – | **6 (4)** | – | **4 (3)** |
| Blank-Line Separation Added | **29 (14)** | **24 (12)** | **30 (15)** | **19 (13)** | **28 (15)** | **22 (11)** |
| Change Attribute Access Modifier | – | **3 (2)** | – | **6 (4)** | – | – |
| Change Method Access Modifier | – | **3 (2)** | – | **5 (3)** | – | **4 (2)** |
| Change Variable Type | 1 (1) | **5 (2)** | **7 (4)** | **5 (4)** | – | **4 (3)** |
| Extract Attribute | **4 (2)** | **8 (4)** | – | **4 (3)** | **3 (2)** | **5 (4)** |
| Extract Method | – | **6 (5)** | – | **5 (3)** | – | – |
| Extract Variable | **6 (5)** | **7 (4)** | **8 (4)** | **8 (4)** | **5 (2)** | **5 (3)** |
| Inline Method | – | – | – | **4 (2)** | – | **3 (2)** |
| Inline Variable | – | 2 (1) | **8 (5)** | **3 (2)** | **7 (3)** | – |
| Javadoc Comment Change | – | **4 (2)** | **30 (15)** | **30 (15)** | **19 (10)** | **14 (9)** |
| Line Comment Change | **9 (5)** | **16 (10)** | **22 (12)** | **20 (11)** | **25 (14)** | **25 (14)** |
| Method Rename | **3 (2)** | **5 (2)** | **24 (11)** | **11 (7)** | **15 (8)** | **6 (3)** |
| Modify Class Annotation | – | **3 (2)** | – | – | – | – |
| Modify Method Annotation | – | – | – | **3 (2)** | – | – |
| Parameterize Variable | – | **3 (2)** | – | – | – | – |
| Remove Class Annotation | – | **3 (2)** | – | **4 (2)** | – | **3 (2)** |
| Remove Thrown Exception Type | – | **3 (2)** | – | **3 (2)** | – | **3 (2)** |
| Replace Variable With Attribute | 1 (0) | **4 (3)** | – | **3 (3)** | – | – |
| Variable Rename | **30 (15)** | **18 (7)** | **27 (13)** | **15 (9)** | **28 (14)** | **14 (6)** |

All floors met.

## Survey 1

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** threeten-extra / TestInternationalFixedChronology / test_chronology  (manual, long, 76→12 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Line Comment Change, Modify Class Annotation
2. **[opus-4.8]** commons-codec / MurmurHash2Test / testHash32ByteArrayIntInt  (manual, medium, 17→36 LOC)
   - features: Attribute Rename, Change Attribute Access Modifier, Extract Attribute, Javadoc Comment Change, Variable Rename
3. **[gpt-5.5]** commons-csv / CSVRecord_ESTest / test16  (auto, medium, 22→23 LOC)
   - features: Blank-Line Separation Added, Variable Rename
4. **[sonnet-4.6]** commons-collections / FilterListIterator_ESTest / test08  (auto, medium, 18→19 LOC)
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Line Comment Change, Variable Rename
5. **[sonnet-4.6]** commons-lang / EnumUtilsTest / testGenerateBitVectors_nullArrayElement  (manual, short, 9→6 LOC)
   - features: Line Comment Change
6. **[opus-4.8]** commons-compress / LZMAUtils_ESTest / test09  (auto, short, 9→9 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Method Rename, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** jackson-annotations / JsonAutoDetect_ESTest / test36  (auto, medium, 19→18 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Change Variable Type, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
2. **[sonnet-4.6]** commons-collections / IndexedCollectionTest / testUnsupportedAdd  (manual, long, 224→48 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Change Attribute Type, Change Method Access Modifier, Inline Method, Javadoc Comment Change, Line Comment Change, Method Rename, Remove Class Annotation, Remove Parameter
3. **[sonnet-4.6]** commons-cli / TextStyle_ESTest / test02  (auto, short, 14→14 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
4. **[opus-4.8]** commons-compress / LZMAUtilsTest / testCanTurnOffCaching  (manual, short, 12→13 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change
5. **[gpt-5.5]** itextpdf / GroupedRandomAccessSource_ESTest / test5  (auto, medium, 24→27 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Variable, Variable Rename
6. **[gpt-5.5]** commons-text / RandomStringGeneratorTest / testWithinMultipleRanges  (manual, medium, 25→38 LOC)  — Test A = original
   - features: Add Variable Modifier, Blank-Line Separation Added, Extract Method, Line Comment Change, Parameterize Variable, Replace Variable With Attribute, Variable Rename

## Survey 2

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[opus-4.8]** commons-codec / BinaryCodecTest / testEncodeByteArray  (manual, long, 151→48 LOC)
   - features: Add Variable Modifier, Blank-Line Separation Added, Block Comment Change, Change Attribute Access Modifier, Extract Method, Inline Variable, Javadoc Comment Change, Line Comment Change, Variable Rename
2. **[gpt-5.5]** threeten-extra / PaxChronology_ESTest / test00  (auto, medium, 16→16 LOC)
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
3. **[sonnet-4.6]** commons-codec / SoundexTest / testSoundexUtilsNullBehaviour  (manual, short, 13→22 LOC)
   - features: Blank-Line Separation Added, Method Rename
4. **[opus-4.8]** jackson-annotations / JsonAutoDetect_ESTest / test07  (auto, medium, 18→17 LOC)
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[sonnet-4.6]** commons-io / ByteOrderMark_ESTest / test13  (auto, short, 10→9 LOC)
   - features: Inline Variable, Javadoc Comment Change, Method Rename, Variable Rename
6. **[gpt-5.5]** commons-codec / URLCodecTest / testDecodeStringWithNull  (manual, medium, 22→8 LOC)
   - features: Blank-Line Separation Added, Inline Variable, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** commons-text / AlphabetConverter_ESTest / test04  (auto, medium, 16→13 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change
2. **[opus-4.8]** commons-collections / IndexedCollectionTest / testCollectionAdd  (manual, long, 235→46 LOC)  — Test A = improved
   - features: Add Method Modifier, Blank-Line Separation Added, Change Method Access Modifier, Change Variable Type, Extract Variable, Inline Method, Javadoc Comment Change, Line Comment Change, Method Rename, Remove Class Annotation, Replace Variable With Attribute, Variable Rename
3. **[gpt-5.5]** jackson-annotations / JsonIncludePropertiesTest / testFromAnnotation  (manual, medium, 23→30 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Variable, Line Comment Change, Variable Rename
4. **[sonnet-4.6]** commons-lang / RandomUtilsTest / testConstructor  (manual, short, 10→6 LOC)  — Test A = improved
   - features: Javadoc Comment Change, Line Comment Change
5. **[gpt-5.5]** commons-io / NullInputStream_ESTest / test07  (auto, short, 14→17 LOC)  — Test A = original
   - features: Add Variable Modifier, Blank-Line Separation Added, Extract Variable, Line Comment Change, Variable Rename
6. **[sonnet-4.6]** commons-io / FileAlterationObserver_ESTest / test04  (auto, medium, 28→27 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Line Comment Change, Variable Rename

## Survey 3

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** jackson-annotations / JsonIgnoreProperties_ESTest / test03  (auto, medium, 16→16 LOC)
   - features: Blank-Line Separation Added, Variable Rename
2. **[opus-4.8]** commons-codec / PercentCodec_ESTest / test02  (auto, medium, 16→14 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
3. **[opus-4.8]** commons-lang / EnumUtilsTest / testGenerateBitVector_nullClassWithArray  (manual, short, 9→6 LOC)
   - features: Javadoc Comment Change
4. **[sonnet-4.6]** spatial4j / TestGeohashUtils / testLookupHashLenForWidthHeight  (manual, medium, 16→20 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Javadoc Comment Change, Line Comment Change
5. **[sonnet-4.6]** commons-io / FileTimes_ESTest / test16  (auto, short, 10→11 LOC)
   - features: Blank-Line Separation Added, Line Comment Change, Method Rename, Variable Rename
6. **[gpt-5.5]** commons-collections / SparseBloomFilterTest / testContains  (manual, long, 101→54 LOC)
   - features: Add Variable Modifier, Blank-Line Separation Added, Change Method Access Modifier, Change Return Type, Change Variable Type, Extract Method, Extract Variable, Javadoc Comment Change, Line Comment Change, Parameterize Variable, Remove Method Modifier, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[sonnet-4.6]** commons-cli / Options_ESTest / test07  (auto, short, 11→11 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
2. **[sonnet-4.6]** threeten-extra / TestMutableClock / test_set_adjuster_null  (manual, short, 6→8 LOC)  — Test A = improved
   - features: Add Method Annotation, Extract Variable, Line Comment Change
3. **[gpt-5.5]** commons-lang / TimedSemaphore_ESTest / test12  (auto, medium, 21→23 LOC)  — Test A = original
   - features: Add Variable Modifier, Blank-Line Separation Added, Extract Variable, Line Comment Change, Variable Rename
4. **[gpt-5.5]** commons-math / CovarianceTest / testLongley  (manual, medium, 20→48 LOC)  — Test A = improved
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Access Modifier, Change Method Access Modifier, Extract Variable
5. **[opus-4.8]** commons-math / CalinskiHarabasz_ESTest / test3  (auto, medium, 19→19 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
6. **[opus-4.8]** commons-collections / IndexedCollectionTest / testCollectionRemove  (manual, long, 236→56 LOC)  — Test A = original
   - features: Add Method Modifier, Blank-Line Separation Added, Change Attribute Type, Change Method Access Modifier, Change Variable Type, Inline Method, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Remove Class Annotation, Replace Variable With Attribute, Variable Rename

## Survey 4

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** threeten-extra / TestDayOfYear / test_atYear_int_invalidDay  (manual, medium, 21→21 LOC)
   - features: Attribute Rename, Blank-Line Separation Added, Extract Variable, Line Comment Change, Variable Rename
2. **[sonnet-4.6]** jackson-annotations / JsonIgnorePropertiesTest / testEquality  (manual, short, 15→16 LOC)
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
3. **[sonnet-4.6]** commons-lang / TimedSemaphore_ESTest / test01  (auto, long, 38→37 LOC)
   - features: Blank-Line Separation Added, Inline Variable, Line Comment Change, Variable Rename
4. **[opus-4.8]** commons-lang / EnumUtilsTest / testGenerateBitVector_nullArray  (manual, short, 9→7 LOC)
   - features: Javadoc Comment Change
5. **[gpt-5.5]** jackson-annotations / JsonIncludeProperties_ESTest / test11  (auto, medium, 21→22 LOC)
   - features: Blank-Line Separation Added, Variable Rename
6. **[opus-4.8]** commons-text / LevenshteinDetailedDistance_ESTest / test11  (auto, medium, 16→17 LOC)
   - features: Blank-Line Separation Added, Change Variable Type, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** commons-text / RandomStringGeneratorTest / testZeroLength  (manual, short, 12→8 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change
2. **[sonnet-4.6]** commons-collections / SimpleBloomFilterTest / testContains  (manual, long, 101→41 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Change Method Access Modifier, Change Return Type, Change Variable Type, Extract Variable, Inline Method, Javadoc Comment Change, Line Comment Change, Remove Method Modifier
3. **[sonnet-4.6]** commons-math / TriDiagonalTransformer_ESTest / test1  (auto, medium, 22→23 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Attribute, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
4. **[gpt-5.5]** threeten-extra / Seconds_ESTest / test21  (auto, short, 12→12 LOC)  — Test A = improved
   - features: Line Comment Change, Variable Rename
5. **[gpt-5.5]** jsoup / ValidateTest / testNotEmpty  (manual, medium, 24→15 LOC)  — Test A = original
   - features: Assert Throws, Blank-Line Separation Added, Extract Method, Inline Variable, Line Comment Change, Remove Class Annotation
6. **[opus-4.8]** threeten-extra / AccountingChronology_ESTest / test21  (auto, medium, 16→25 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename

## Survey 5

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[sonnet-4.6]** jackson-annotations / JsonAutoDetectTest / testSimpleChanges  (manual, medium, 21→21 LOC)
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
2. **[sonnet-4.6]** jsoup / Printer_ESTest / test09  (auto, medium, 19→19 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
3. **[gpt-5.5]** jackson-annotations / JsonIncludePropertiesTest / testHashCodeIncludesContents  (manual, short, 13→13 LOC)
   - features: Blank-Line Separation Added, Variable Rename
4. **[gpt-5.5]** commons-lang / TimedSemaphore_ESTest / test08  (auto, long, 32→32 LOC)
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
5. **[opus-4.8]** commons-compress / X5455_ExtendedTimestamp_ESTest / test17  (auto, short, 12→11 LOC)
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Method Rename, Variable Rename
6. **[opus-4.8]** commons-compress / LZMAUtilsTest / testGetUncompressedFilename  (manual, medium, 20→19 LOC)
   - features: Add Method Annotation, Add Parameter, Javadoc Comment Change, Method Rename, Remove Method Annotation

### Part 2 — 6 side-by-side comparisons

1. **[sonnet-4.6]** commons-compress / ExtraFieldUtilsTest / testMerge  (manual, long, 45→53 LOC)  — Test A = improved
   - features: Attribute Rename, Blank-Line Separation Added, Extract Attribute, Javadoc Comment Change, Line Comment Change, Variable Rename
2. **[gpt-5.5]** spatial4j / SpatialContextFactory_ESTest / test10  (auto, medium, 16→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
3. **[sonnet-4.6]** threeten-extra / InternationalFixedChronology_ESTest / test07  (auto, medium, 16→15 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Variable, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
4. **[opus-4.8]** spatial4j / SpatialContextFactoryTest / testSystemPropertyLookup  (manual, medium, 21→24 LOC)  — Test A = improved
   - features: Attribute Rename, Blank-Line Separation Added, Change Attribute Access Modifier, Javadoc Comment Change, Line Comment Change, Method Rename
5. **[gpt-5.5]** jackson-annotations / JsonAutoDetectTest / testAnnotationProperties  (manual, short, 15→25 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Attribute, Extract Method, Line Comment Change, Variable Rename
6. **[opus-4.8]** jsoup / StringUtil_ESTest / test14  (auto, short, 9→11 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename

## Survey 6

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[sonnet-4.6]** itextpdf / GroupedRandomAccessSource_ESTest / test4  (auto, medium, 24→24 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
2. **[gpt-5.5]** commons-collections / SimpleBloomFilter_ESTest / test04  (auto, medium, 18→22 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Line Comment Change, Variable Rename
3. **[sonnet-4.6]** commons-codec / Base58Test / testEncodeDecodeSmall  (manual, medium, 19→14 LOC)
   - features: Extract Variable, Inline Method, Variable Rename
4. **[opus-4.8]** commons-compress / X5455_ExtendedTimestamp_ESTest / test16  (auto, short, 11→11 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Method Rename, Variable Rename
5. **[opus-4.8]** commons-collections / IndexedCollectionTest / testEnsureDuplicateObjectsCauseException  (manual, long, 214→17 LOC)
   - features: Blank-Line Separation Added, Change Method Access Modifier, Change Return Type, Inline Method, Javadoc Comment Change, Line Comment Change, Method Rename, Remove Class Annotation, Remove Thrown Exception Type
6. **[gpt-5.5]** commons-text / LevenshteinDetailedDistanceTest / testGetDefaultInstanceOne  (manual, short, 10→14 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[gpt-5.5]** threeten-extra / TestInternationalFixedChronology / test_era_yearDay_loop  (manual, long, 80→24 LOC)  — Test A = improved
   - features: Extract Attribute, Extract Method, Line Comment Change, Modify Class Annotation, Replace Variable With Attribute
2. **[opus-4.8]** commons-csv / CSVRecord_ESTest / test28  (auto, medium, 17→19 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Change Variable Type, Extract Variable, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
3. **[opus-4.8]** threeten-extra / TestYears / test_parse_CharSequence_invalid  (manual, short, 10→20 LOC)  — Test A = original
   - features: Javadoc Comment Change, Line Comment Change, Method Rename, Modify Method Annotation, Variable Rename
4. **[gpt-5.5]** commons-compress / SegmentUtils_ESTest / test00  (auto, medium, 16→17 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Variable, Variable Rename
5. **[sonnet-4.6]** jackson-annotations / JsonTypeInfoTest / testWriteTypeIdForDefaultImplFromAnnotation  (manual, short, 14→28 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
6. **[sonnet-4.6]** spatial4j / SpatialContextFactory_ESTest / test11  (auto, medium, 16→16 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename

## Survey 7

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** commons-text / RandomStringGeneratorTest / testRemoveFilters  (manual, medium, 19→21 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Variable Rename
2. **[opus-4.8]** threeten-extra / TestDayOfMonth / test_adjustInto_null  (manual, medium, 18→18 LOC)
   - features: Extract Variable, Javadoc Comment Change, Line Comment Change, Variable Rename
3. **[gpt-5.5]** commons-io / NullReader_ESTest / test06  (auto, medium, 16→16 LOC)
   - features: Blank-Line Separation Added, Variable Rename
4. **[opus-4.8]** commons-text / AlphabetConverter_ESTest / test17  (auto, long, 34→16 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change
5. **[sonnet-4.6]** threeten-extra / Minutes_ESTest / test13  (auto, short, 11→12 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Inline Variable, Line Comment Change, Method Rename, Variable Rename
6. **[sonnet-4.6]** commons-text / LongestCommonSubsequenceTest / testGettingLongestCommonSubsequenceApplyStringNull  (manual, short, 11→14 LOC)
   - features: Add Method Annotation, Attribute Rename, Blank-Line Separation Added, Extract Variable, Line Comment Change, Method Rename

### Part 2 — 6 side-by-side comparisons

1. **[sonnet-4.6]** threeten-extra / TestDayOfMonth / test_adjustInto_nonIso  (manual, medium, 18→16 LOC)  — Test A = improved
   - features: Javadoc Comment Change, Line Comment Change
2. **[gpt-5.5]** commons-cli / TextStyle_ESTest / test09  (auto, short, 12→13 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Variable, Variable Rename
3. **[gpt-5.5]** jackson-annotations / JsonTypeInfoTest / testWriteTypeIdForDefaultImplEqualsAndHashCode  (manual, short, 14→20 LOC)  — Test A = original
   - features: Add Variable Modifier, Blank-Line Separation Added, Variable Rename
4. **[opus-4.8]** commons-codec / PercentCodec_ESTest / test08  (auto, medium, 16→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[sonnet-4.6]** commons-text / RandomStringGenerator_ESTest / test00  (auto, medium, 18→17 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
6. **[opus-4.8]** commons-codec / BinaryCodecTest / testToByteArrayFromString  (manual, long, 120→37 LOC)  — Test A = original
   - features: Add Variable Modifier, Blank-Line Separation Added, Change Attribute Access Modifier, Extract Method, Javadoc Comment Change, Line Comment Change, Remove Thrown Exception Type, Variable Rename

## Survey 8

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** threeten-extra / TestMinutes / test_plus_int  (manual, medium, 17→13 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Line Comment Change, Variable Rename
2. **[gpt-5.5]** commons-codec / Base16_ESTest / test02  (auto, medium, 20→20 LOC)
   - features: Blank-Line Separation Added, Variable Rename
3. **[opus-4.8]** threeten-extra / TestDayOfYear / test_of_int_tooLow  (manual, medium, 21→18 LOC)
   - features: Extract Variable, Javadoc Comment Change, Line Comment Change, Variable Rename
4. **[sonnet-4.6]** commons-lang / JavaVersion_ESTest / test21  (auto, short, 9→9 LOC)
   - features: Method Rename, Variable Rename
5. **[opus-4.8]** commons-compress / LZMAUtils_ESTest / test10  (auto, short, 9→9 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Method Rename, Variable Rename
6. **[sonnet-4.6]** commons-collections / IndexedCollectionTest / testAddedObjectsCanBeRetrievedByKey  (manual, long, 224→28 LOC)
   - features: Blank-Line Separation Added, Change Method Access Modifier, Javadoc Comment Change, Method Rename, Remove Class Annotation, Remove Parameter, Remove Thrown Exception Type

### Part 2 — 6 side-by-side comparisons

1. **[sonnet-4.6]** spatial4j / SpatialContextFactory_ESTest / test02  (auto, medium, 16→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
2. **[opus-4.8]** commons-compress / LZMAUtilsTest / testGetCompressedFilename  (manual, medium, 16→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Method, Javadoc Comment Change, Remove Method Annotation
3. **[gpt-5.5]** spatial4j / GeohashUtils_ESTest / test1  (auto, short, 9→12 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Attribute, Method Rename, Variable Rename
4. **[opus-4.8]** commons-io / SequenceReader_ESTest / test3  (auto, medium, 19→19 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[gpt-5.5]** jsoup / ValidateTest / testNotNull  (manual, short, 14→7 LOC)  — Test A = original
   - features: Line Comment Change, Remove Class Annotation
6. **[sonnet-4.6]** commons-collections / BoundedIteratorTest / testRemoveUnsupported  (manual, long, 42→23 LOC)  — Test A = original
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Type, Change Variable Type, Javadoc Comment Change, Line Comment Change, Remove Method Annotation, Remove Thrown Exception Type, Variable Rename

## Survey 9

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** threeten-extra / Years_ESTest / test07  (auto, short, 14→14 LOC)
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
2. **[opus-4.8]** commons-codec / Base58_ESTest / test03  (auto, medium, 16→18 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
3. **[gpt-5.5]** commons-collections / FilterListIteratorTest / testPreviousChangesNext  (manual, long, 180→54 LOC)
   - features: Attribute Rename, Change Attribute Type, Line Comment Change, Method Rename, Remove Class Annotation, Variable Rename
4. **[sonnet-4.6]** threeten-extra / TestDayOfYear / test_atYear_fromStartOfYear_notLeapYear_day366  (manual, medium, 22→18 LOC)
   - features: Line Comment Change, Variable Rename
5. **[opus-4.8]** commons-io / FileTimesTest / testNullDateToNullFileTime  (manual, short, 6→7 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change
6. **[sonnet-4.6]** jackson-annotations / JsonAutoDetect_ESTest / test22  (auto, medium, 16→16 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** commons-lang / EnumUtilsTest / testGenerateBitVectorsFromArray  (manual, medium, 23→23 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename
2. **[sonnet-4.6]** commons-lang / CharRange_ESTest / test10  (auto, short, 15→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Line Comment Change, Method Rename, Variable Rename
3. **[gpt-5.5]** jackson-annotations / JsonIgnoreProperties_ESTest / test37  (auto, medium, 20→20 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Variable Rename
4. **[opus-4.8]** spatial4j / SpatialContextFactory_ESTest / test00  (auto, medium, 16→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Change Variable Type, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[gpt-5.5]** commons-collections / BoundedIteratorTest / testRemoveWithoutCallingNext  (manual, long, 35→16 LOC)  — Test A = original
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Type, Change Variable Type, Extract Attribute, Javadoc Comment Change, Remove Method Annotation, Remove Thrown Exception Type
6. **[sonnet-4.6]** commons-io / SequenceReaderTest / testReadClosedReader  (manual, short, 15→17 LOC)  — Test A = original
   - features: Add Method Annotation, Blank-Line Separation Added, Extract Attribute, Javadoc Comment Change, Line Comment Change, Method Rename

## Survey 10

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[opus-4.8]** commons-collections / IndexedCollectionTest / testCollectionRemoveIf  (manual, long, 237→49 LOC)
   - features: Add Method Modifier, Blank-Line Separation Added, Change Class Access Modifier, Change Method Access Modifier, Change Return Type, Change Variable Type, Extract Variable, Inline Method, Javadoc Comment Change, Line Comment Change, Method Rename, Remove Class Annotation, Variable Rename
2. **[gpt-5.5]** commons-codec / URLCodecTest / testDecodeInvalidContent  (manual, medium, 27→28 LOC)
   - features: Attribute Rename, Blank-Line Separation Added, Change Attribute Access Modifier, Method Rename, Variable Rename
3. **[gpt-5.5]** commons-codec / MurmurHash2_ESTest / test07  (auto, short, 9→11 LOC)
   - features: Blank-Line Separation Added, Variable Rename
4. **[sonnet-4.6]** commons-csv / CSVRecord_ESTest / test01  (auto, medium, 20→21 LOC)
   - features: Blank-Line Separation Added, Block Comment Change, Extract Variable, Line Comment Change, Variable Rename
5. **[sonnet-4.6]** commons-text / LevenshteinDetailedDistanceTest / testHashCode  (manual, medium, 16→16 LOC)
   - features: Add Method Annotation, Blank-Line Separation Added, Line Comment Change, Variable Rename
6. **[opus-4.8]** commons-lang / CharSet_ESTest / test8  (auto, short, 10→10 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Method Rename, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[sonnet-4.6]** threeten-extra / TestDayOfMonth / test_atMonth_tooLow  (manual, medium, 18→17 LOC)  — Test A = improved
   - features: Extract Attribute, Line Comment Change
2. **[sonnet-4.6]** threeten-extra / Weeks_ESTest / test18  (auto, short, 11→11 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Method Rename, Variable Rename
3. **[opus-4.8]** jackson-annotations / JsonTypeInfo_ESTest / test38  (auto, short, 15→21 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Change Variable Type, Extract Variable, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
4. **[gpt-5.5]** commons-lang / NumericEntityUnescaper_ESTest / test0  (auto, medium, 17→17 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Variable Rename
5. **[gpt-5.5]** commons-codec / URLCodecTest / testSafeCharEncodeDecode  (manual, medium, 24→15 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Attribute, Line Comment Change, Replace Variable With Attribute
6. **[opus-4.8]** commons-collections / BoundedIteratorTest / testForEachRemaining  (manual, long, 36→20 LOC)  — Test A = original
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Type, Change Method Access Modifier, Change Return Type, Change Variable Type, Extract Attribute, Extract Variable, Javadoc Comment Change, Method Rename, Remove Method Annotation, Remove Thrown Exception Type, Variable Rename

## Survey 11

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[opus-4.8]** commons-codec / SoundexTest / testEncodeBatch4  (manual, medium, 17→21 LOC)
   - features: Extract Method, Javadoc Comment Change
2. **[gpt-5.5]** commons-io / FileTimes_ESTest / test01  (auto, short, 9→11 LOC)
   - features: Add Variable Modifier, Blank-Line Separation Added, Extract Variable, Method Rename, Variable Rename
3. **[sonnet-4.6]** commons-text / AlphabetConverterTest / testEquals  (manual, long, 51→12 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
4. **[opus-4.8]** commons-csv / CSVRecord_ESTest / test15  (auto, medium, 21→20 LOC)
   - features: Add Variable Modifier, Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[gpt-5.5]** commons-io / FileTimesTest / testPlusNanos  (manual, short, 8→9 LOC)
   - features: Change Variable Type, Replace Variable With Attribute
6. **[sonnet-4.6]** commons-csv / CSVRecord_ESTest / test09  (auto, medium, 21→19 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Line Comment Change, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[sonnet-4.6]** commons-lang / TimedSemaphoreTest / testAcquireLimit  (manual, medium, 30→59 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change, Variable Rename
2. **[sonnet-4.6]** commons-lang / TimedSemaphore_ESTest / test17  (auto, medium, 16→14 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename
3. **[gpt-5.5]** jsoup / NodeIteratorTest / canIterateNodes  (manual, long, 45→49 LOC)  — Test A = original
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Access Modifier, Change Method Access Modifier, Extract Method, Method Rename, Parameterize Variable, Variable Rename
4. **[opus-4.8]** commons-compress / SegmentUtilsTest / testCountArgs  (manual, short, 15→18 LOC)  — Test A = improved
   - features: Javadoc Comment Change, Line Comment Change, Method Rename, Modify Method Annotation
5. **[gpt-5.5]** jackson-annotations / JsonIgnoreProperties_ESTest / test08  (auto, medium, 18→19 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Variable Rename
6. **[opus-4.8]** commons-compress / X5455_ExtendedTimestamp_ESTest / test19  (auto, short, 12→11 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Inline Variable, Javadoc Comment Change, Method Rename, Variable Rename

## Survey 12

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[sonnet-4.6]** commons-collections / FilterListIterator_ESTest / test01  (auto, medium, 20→20 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
2. **[sonnet-4.6]** commons-codec / Base58Test / testBase58  (manual, medium, 19→12 LOC)
   - features: Blank-Line Separation Added, Variable Rename
3. **[opus-4.8]** commons-compress / LZMAUtilsTest / testCachingIsEnabledByDefaultAndLZMAIsPresent  (manual, short, 7→8 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename
4. **[opus-4.8]** jackson-annotations / JsonAutoDetect_ESTest / test04  (auto, medium, 16→14 LOC)
   - features: Blank-Line Separation Added, Change Variable Type, Inline Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[gpt-5.5]** commons-collections / BoundedIteratorTest / testOffsetGreaterThanSize  (manual, long, 34→19 LOC)
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Type, Change Variable Type, Javadoc Comment Change, Remove Method Annotation, Remove Thrown Exception Type
6. **[gpt-5.5]** commons-lang / AppendableJoiner_ESTest / test00  (auto, short, 11→11 LOC)
   - features: Blank-Line Separation Added, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** commons-compress / LZMAUtils_ESTest / test02  (auto, short, 10→11 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Method Rename, Variable Rename
2. **[gpt-5.5]** commons-text / LevenshteinDistance_ESTest / test06  (auto, medium, 17→17 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename
3. **[sonnet-4.6]** commons-collections / BoundedIteratorTest / testRemoveFirst  (manual, long, 47→27 LOC)  — Test A = original
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Type, Change Variable Type, Javadoc Comment Change, Line Comment Change, Remove Method Annotation, Remove Thrown Exception Type, Variable Rename
4. **[opus-4.8]** spatial4j / TestGeohashUtils / testDecodeEncode  (manual, medium, 18→24 LOC)  — Test A = improved
   - features: Add Attribute Modifier, Add Variable Modifier, Attribute Rename, Blank-Line Separation Added, Block Comment Change, Change Attribute Access Modifier, Extract Attribute, Extract Variable, Javadoc Comment Change, Line Comment Change, Remove Variable Modifier, Variable Rename
5. **[sonnet-4.6]** commons-collections / SparseBloomFilter_ESTest / test05  (auto, medium, 18→17 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
6. **[gpt-5.5]** commons-text / LongestCommonSubsequenceTest / testGettingLogestCommonSubsequenceNullNull  (manual, short, 12→12 LOC)  — Test A = original
   - features: Method Rename

## Survey 13

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[gpt-5.5]** jackson-annotations / JsonIncludePropertiesTest / testWithOverridesMerge  (manual, short, 14→19 LOC)
   - features: Attribute Rename, Blank-Line Separation Added, Method Rename, Variable Rename
2. **[opus-4.8]** commons-text / LongestCommonSubsequenceTest / testLongestCommonSubsequence  (manual, medium, 25→32 LOC)
   - features: Add Attribute Modifier, Add Method Annotation, Add Parameter, Javadoc Comment Change, Remove Attribute Modifier, Remove Method Annotation
3. **[gpt-5.5]** commons-text / AlphabetConverter_ESTest / test06  (auto, medium, 17→21 LOC)
   - features: Blank-Line Separation Added, Extract Attribute, Replace Variable With Attribute, Variable Rename
4. **[opus-4.8]** commons-compress / SeekableInMemoryByteChannel_ESTest / test02  (auto, medium, 16→17 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[sonnet-4.6]** commons-codec / MurmurHash2_ESTest / test02  (auto, short, 9→11 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Method Rename, Variable Rename
6. **[sonnet-4.6]** commons-collections / SparseBloomFilterTest / testIndexExtractorMerge  (manual, long, 84→36 LOC)
   - features: Blank-Line Separation Added, Change Method Access Modifier, Change Return Type, Change Variable Type, Javadoc Comment Change, Line Comment Change, Remove Method Modifier, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[gpt-5.5]** threeten-extra / TestPaxChronology / test_adjust_toLocalDate  (manual, long, 45→10 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Variable, Line Comment Change, Modify Class Annotation, Variable Rename
2. **[opus-4.8]** commons-math / MullerSolverTest / testSinFunction  (manual, medium, 24→18 LOC)  — Test A = improved
   - features: Add Variable Modifier, Blank-Line Separation Added, Change Variable Type, Extract Attribute, Extract Method, Inline Variable, Javadoc Comment Change, Line Comment Change, Replace Variable With Attribute, Variable Rename
3. **[opus-4.8]** commons-lang / CharRange_ESTest / test14  (auto, medium, 16→16 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Variable Rename
4. **[gpt-5.5]** commons-cli / OptionFormatter_ESTest / test00  (auto, short, 10→13 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Extract Attribute, Method Rename, Variable Rename
5. **[sonnet-4.6]** commons-io / HexDumpTest / testDumpAppendable  (manual, medium, 24→53 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Line Comment Change
6. **[sonnet-4.6]** commons-lang / AppendableJoiner_ESTest / test06  (auto, short, 12→12 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Line Comment Change, Variable Rename

## Survey 14

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[opus-4.8]** commons-collections / IndexedCollection_ESTest / test10  (auto, medium, 18→20 LOC)
   - features: Add Variable Modifier, Blank-Line Separation Added, Change Variable Type, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
2. **[gpt-5.5]** commons-collections / LoopingListIterator_ESTest / test05  (auto, medium, 16→16 LOC)
   - features: Blank-Line Separation Added, Variable Rename
3. **[gpt-5.5]** threeten-extra / TestMinutes / test_multipliedBy_overflowTooSmall  (manual, short, 12→10 LOC)
   - features: Extract Attribute, Line Comment Change
4. **[sonnet-4.6]** jsoup / Validate_ESTest / test09  (auto, medium, 16→15 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
5. **[sonnet-4.6]** commons-text / LevenshteinDistanceTest / testGetLevenshteinDistance_StringStringInt  (manual, long, 46→52 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change
6. **[opus-4.8]** commons-compress / LZMAUtilsTest / testTurningOnCachingReEvaluatesAvailability  (manual, short, 13→15 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** commons-collections / ObjectGraphIteratorTest / testIteration_IteratorOfIteratorsWithEmptyIterators  (manual, long, 61→30 LOC)  — Test A = improved
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Access Modifier, Javadoc Comment Change, Line Comment Change, Variable Rename
2. **[gpt-5.5]** commons-lang / NumericEntityUnescaper_ESTest / test3  (auto, medium, 16→16 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Variable Rename
3. **[sonnet-4.6]** commons-codec / Base16_ESTest / test03  (auto, medium, 16→17 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Attribute, Extract Variable, Line Comment Change, Method Rename, Variable Rename
4. **[opus-4.8]** itextpdf / GroupedRandomAccessSource_ESTest / test3  (auto, medium, 24→25 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change
5. **[gpt-5.5]** threeten-extra / TestYears / test_get  (manual, short, 9→7 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Extract Variable, Line Comment Change
6. **[sonnet-4.6]** commons-text / LevenshteinDetailedDistanceTest / testGetThreshold  (manual, short, 8→17 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Method Rename, Variable Rename

## Survey 15

### Part 1 — 6 pairs shown as 12 Likert items
(slot order and sides: see the table above)

1. **[sonnet-4.6]** commons-io / CircularByteBuffer_ESTest / test27  (auto, medium, 16→16 LOC)
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename
2. **[sonnet-4.6]** commons-text / RandomStringGeneratorTest / testGenerateMinMaxLengthInvalidLength  (manual, short, 14→7 LOC)
   - features: Line Comment Change
3. **[gpt-5.5]** threeten-extra / Years_ESTest / test05  (auto, short, 13→13 LOC)
   - features: Blank-Line Separation Added, Variable Rename
4. **[opus-4.8]** commons-cli / PosixParser_ESTest / test02  (auto, medium, 16→17 LOC)
   - features: Blank-Line Separation Added, Extract Variable, Javadoc Comment Change, Line Comment Change, Variable Rename
5. **[gpt-5.5]** threeten-extra / TestDayOfYear / test_adjustInto_fromEndOfYear_notLeapYear  (manual, medium, 27→22 LOC)
   - features: Blank-Line Separation Added, Variable Rename
6. **[opus-4.8]** threeten-extra / TestAccountingChronology / test_LocalDate_from_AccountingDate  (manual, long, 36→44 LOC)
   - features: Attribute Rename, Javadoc Comment Change, Line Comment Change, Modify Method Annotation, Variable Rename

### Part 2 — 6 side-by-side comparisons

1. **[opus-4.8]** commons-lang / RandomUtilsTest / testNextLongRandomResult  (manual, short, 13→12 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Javadoc Comment Change, Variable Rename
2. **[gpt-5.5]** jackson-annotations / JsonIgnoreProperties_ESTest / test09  (auto, medium, 16→17 LOC)  — Test A = improved
   - features: Blank-Line Separation Added, Change Variable Type, Variable Rename
3. **[sonnet-4.6]** itextpdf / GroupedRandomAccessSource_ESTest / test8  (auto, medium, 19→19 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Line Comment Change, Method Rename, Variable Rename
4. **[sonnet-4.6]** jsoup / ValidateTest / testNotEmptyWithMessage  (manual, medium, 23→13 LOC)  — Test A = improved
   - features: Assert Throws, Blank-Line Separation Added, Extract Attribute, Line Comment Change, Remove Class Annotation
5. **[opus-4.8]** commons-io / FileAlterationObserver_ESTest / test19  (auto, short, 11→12 LOC)  — Test A = original
   - features: Blank-Line Separation Added, Javadoc Comment Change, Method Rename, Variable Rename
6. **[gpt-5.5]** commons-collections / BoundedIteratorTest / testFullIterator  (manual, long, 43→35 LOC)  — Test A = original
   - features: Add Attribute Modifier, Attribute Rename, Blank-Line Separation Added, Change Attribute Type, Change Return Type, Change Variable Type, Javadoc Comment Change, Line Comment Change, Remove Method Annotation, Remove Thrown Exception Type, Variable Rename
