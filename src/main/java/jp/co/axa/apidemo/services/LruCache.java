package jp.co.axa.apidemo.services;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

class Cache<K, V> {
  K key;
  V value;

  Cache(K key, V value) {
    this.key = key;
    this.value = value;
  }
}


public class LruCache<K, V> {
  private Deque<K> queue = new LinkedList<>();
  private Map<K, Cache<K, V>> map = new HashMap<>();
  int maxCapacity;

  LruCache(int capacity) {
    maxCapacity = capacity;
  }

  public Optional<V> get(K key) {
    if (map.containsKey(key)) {
      Cache<K, V> current = map.get(key);
      queue.remove(current.key);
      queue.addFirst(current.key);
      return Optional.of(current.value);
    }
    return Optional.empty();
  }

  public void put(K key, V value) {
    if (map.containsKey(key)) {
      Cache<K, V> curr = map.get(key);
      queue.remove(curr.key);
    } else {
      if (queue.size() == maxCapacity) {
        K temp = queue.removeLast();
        map.remove(temp);
      }
    }
    Cache<K, V> newItem = new Cache<>(key, value);
    queue.addFirst(newItem.key);
    map.put(key, newItem);
  }

  public void remove(K key) {
    map.remove(key);
    queue.remove(key);
  }
}
