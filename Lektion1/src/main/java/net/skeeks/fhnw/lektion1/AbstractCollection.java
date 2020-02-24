package net.skeeks.fhnw.lektion1;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractCollection<E> implements Collection<E> {

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        int i = 0;
        for (E elem : this) {
            i++;
        }
        return i;
    }

    @Override
    public boolean contains(Object o) {
        for (E elem : this) {
            if (elem.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean containsAll = true;
        for (Object elem : c) {
            containsAll = containsAll && this.contains(elem);
        }
        return containsAll;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        for (E elem : c) {
            changed = changed || add(elem);
        }
        return changed;
    }

    @Override
    public boolean remove(Object o) {
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            E elem = iter.next();
            if (elem.equals(o)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object elem : c) {
            changed = changed || remove(elem);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        Iterator<E> iter = iterator();
        while (iter.hasNext()) {
            E elem = iter.next();
            if (!elem.equals(elem)) {
                iter.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        removeAll(this);
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int i = 0;
        for (E elem : this) {
            arr[i++] = elem;
        }
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] arr) {
        int i = 0;
        for (E elem : this) {
            arr[i++] = (T) elem;
        }
        return arr;
    }
}
