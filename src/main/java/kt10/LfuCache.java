package kt10;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LfuCache {
    private final int capacity;
    private final Map<Integer, Entry> entries = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Integer>> frequencies = new HashMap<>();
    private int minFrequency;

    public LfuCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Entry entry = entries.get(key);
        if (entry == null) {
            return -1;
        }
        touch(key, entry);
        return entry.value;
    }

    public void put(int key, int value) {
        Entry entry = entries.get(key);
        if (entry != null) {
            entry.value = value;
            touch(key, entry);
            return;
        }
        if (entries.size() == capacity) {
            // Порядок в группе частоты совпадает с порядком последних успешных обращений.
            LinkedHashSet<Integer> leastUsed = frequencies.get(minFrequency);
            int oldest = leastUsed.iterator().next();
            leastUsed.remove(oldest);
            entries.remove(oldest);
            if (leastUsed.isEmpty()) {
                frequencies.remove(minFrequency);
            }
        }
        entries.put(key, new Entry(value));
        frequencies.computeIfAbsent(1, ignored -> new LinkedHashSet<>()).add(key);
        minFrequency = 1;
    }

    private void touch(int key, Entry entry) {
        LinkedHashSet<Integer> group = frequencies.get(entry.frequency);
        group.remove(key);
        if (group.isEmpty()) {
            frequencies.remove(entry.frequency);
            if (minFrequency == entry.frequency) {
                minFrequency++;
            }
        }
        entry.frequency++;
        frequencies.computeIfAbsent(entry.frequency, ignored -> new LinkedHashSet<>()).add(key);
    }

    private static class Entry {
        int value;
        int frequency = 1;

        Entry(int value) {
            this.value = value;
        }
    }
}
