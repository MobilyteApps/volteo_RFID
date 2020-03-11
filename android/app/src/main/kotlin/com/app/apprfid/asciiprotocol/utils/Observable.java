// 
// Decompiled by Procyon v0.5.36
// 

package com.app.apprfid.asciiprotocol.utils;

/*

public class Observable<T>
{
    private boolean a;
    private final Collection<Observer<? super T>> b;
    
    public Observable() {
        this.a = false;
        this.b = new ArrayList<Observer<? super T>>();
    }
    
    public void addObserver(final Observer<? super T> observer) {
        synchronized (this.b) {
            if (!this.b.contains(observer)) {
                this.b.add(observer);
            }
        }
    }
    
    public void removeObserver(final Observer<? super T> observer) {
        synchronized (this.b) {
            this.b.remove(observer);
        }
    }
    
    public void clearObservers() {
        synchronized (this.b) {
            this.b.clear();
        }
    }
    
    public void setChanged() {
        synchronized (this.b) {
            this.a = true;
        }
    }
    
    public void clearChanged() {
        synchronized (this.b) {
            this.a = false;
        }
    }
    
    public boolean hasChanged() {
        synchronized (this.b) {
            return this.a;
        }
    }
    
    public int countObservers() {
        synchronized (this.b) {
            return this.b.size();
        }
    }
    
    public void notifyObservers() {
        this.notifyObservers(null);
    }
    
    public void notifyObservers(final T value) {
        ArrayList<Observer<T>> list = null;
        synchronized (this.b) {
            if (!this.a) {
                return;
            }
            list = new ArrayList<Observer<T>>((Collection<? extends Observer<T>>)b);
            this.a = false;
        }
        final Iterator<Observer<T>> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().update((Observable<? extends T>)this, value);
        }
    }
    
    public interface Observer<U>
    {
        void update(final Observable<? extends U> p0, final U p1);
    }
}
*/
