package com.andrea.pyobjects;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * https://docs.python.org/3/library/stdtypes.html#ranges
 * The range type represents an immutable sequence of numbers and is commonly used for looping a specific number of times in for loops.
 */
public class PyRange implements Iterable<Long> {

    private long start;
    private long stop;
    private long step;

    public PyRange(long start, long stop, long step) {
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    @Override
    public Iterator<Long> iterator() {
        return new RangeIterator(start, stop, step);
    }

    private static final class RangeIterator implements Iterator<Long> {

        private long cursor;
        private long stop;
        private long step;

        private RangeIterator(long start, long stop, long step) {
            this.cursor = start;
            this.stop = stop;
            this.step = step;
        }

        @Override
        public boolean hasNext() {
            return cursor < stop;
        }

        @Override
        public Long next() {
            if (this.hasNext()) {
                long current = cursor;
                cursor += step;
                return current;
            }
            throw new NoSuchElementException();
        }
    }
}
