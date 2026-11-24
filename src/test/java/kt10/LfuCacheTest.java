package kt10;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LfuCacheTest {
    @Test
    void evictsLeastFrequentlyUsedKey() {
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(10, cache.get(1));
        cache.put(3, 30);
        assertEquals(-1, cache.get(2));
        assertEquals(10, cache.get(1));
        assertEquals(30, cache.get(3));
    }

    @Test
    void breaksFrequencyTiesByLastUse() {
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(3, 30);
        assertEquals(-1, cache.get(1));
        assertEquals(20, cache.get(2));
        assertEquals(30, cache.get(3));
        cache.put(4, 40);
        assertEquals(-1, cache.get(2));
        assertEquals(30, cache.get(3));
        assertEquals(40, cache.get(4));
    }

    @Test
    void updatingExistingKeyRaisesFrequencyWithoutEvictingAnotherKey() {
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(1, 11);
        cache.put(3, 30);
        assertEquals(-1, cache.get(2));
        assertEquals(11, cache.get(1));
        assertEquals(30, cache.get(3));
    }

    @Test
    void putAlsoRefreshesLastUseWithinNewFrequency() {
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(20, cache.get(2));
        cache.put(1, 11);
        cache.put(3, 30);
        assertEquals(-1, cache.get(2));
        assertEquals(11, cache.get(1));
    }

    @Test
    void missingGetDoesNotChangeEvictionOrder() {
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        assertEquals(-1, cache.get(42));
        assertEquals(-1, cache.get(42));
        cache.put(3, 30);
        assertEquals(-1, cache.get(1));
        assertEquals(20, cache.get(2));
    }

    @Test
    void reinsertedKeyStartsWithFrequencyOne() {
        LfuCache cache = new LfuCache(2);
        cache.put(1, 10);
        cache.get(1);
        cache.put(2, 20);
        cache.get(2);
        cache.get(2);
        cache.put(3, 30);
        cache.put(1, 11);
        assertEquals(11, cache.get(1));
        cache.put(4, 40);
        assertEquals(-1, cache.get(1));
        assertEquals(20, cache.get(2));
        assertEquals(40, cache.get(4));
    }

    @Test
    void handlesCapacityOneAndBoundaryKeysAndValues() {
        LfuCache cache = new LfuCache(1);
        cache.put(0, 0);
        assertEquals(0, cache.get(0));
        cache.put(0, 1_000_000_000);
        assertEquals(1_000_000_000, cache.get(0));
        cache.put(1_000_000_000, 7);
        assertEquals(-1, cache.get(0));
        assertEquals(7, cache.get(1_000_000_000));
    }

    @Test
    void handlesMaximumCapacityAndCommandCount() {
        LfuCache cache = new LfuCache(200_000);
        for (int i = 0; i < 200_000; i++) {
            cache.put(i, i);
        }
        for (int i = 0; i < 100_000; i++) {
            assertEquals(i, cache.get(i));
        }
        for (int i = 200_000; i < 300_000; i++) {
            cache.put(i, i);
        }
        for (int i = 100_000; i < 200_000; i++) {
            assertEquals(-1, cache.get(i));
        }
    }

    @Test
    void matchesSimpleFrequencyAndTimestampModel() {
        Random random = new Random(42);
        for (int capacity = 1; capacity <= 5; capacity++) {
            LfuCache cache = new LfuCache(capacity);
            Map<Integer, ModelEntry> model = new HashMap<>();
            for (int time = 1; time <= 5_000; time++) {
                int key = random.nextInt(12);
                ModelEntry entry = model.get(key);
                if (random.nextBoolean()) {
                    int expected = -1;
                    if (entry != null) {
                        expected = entry.value;
                        entry.frequency++;
                        entry.time = time;
                    }
                    assertEquals(expected, cache.get(key));
                } else {
                    int value = random.nextInt(1_001);
                    if (entry != null) {
                        entry.value = value;
                        entry.frequency++;
                        entry.time = time;
                    } else {
                        if (model.size() == capacity) {
                            int victim = -1;
                            ModelEntry leastUsed = null;
                            for (Map.Entry<Integer, ModelEntry> candidate : model.entrySet()) {
                                ModelEntry current = candidate.getValue();
                                if (leastUsed == null || current.frequency < leastUsed.frequency
                                        || current.frequency == leastUsed.frequency && current.time < leastUsed.time) {
                                    victim = candidate.getKey();
                                    leastUsed = current;
                                }
                            }
                            model.remove(victim);
                        }
                        model.put(key, new ModelEntry(value, time));
                    }
                    cache.put(key, value);
                }
            }
            for (int key = 0; key < 12; key++) {
                assertEquals(model.containsKey(key) ? model.get(key).value : -1, cache.get(key));
            }
        }
    }

    private static class ModelEntry {
        int value;
        int frequency = 1;
        int time;

        ModelEntry(int value, int time) {
            this.value = value;
            this.time = time;
        }
    }
}
