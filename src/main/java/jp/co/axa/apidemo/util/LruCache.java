package jp.co.axa.apidemo.util;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

/**
 * An item to be stored in the {@link LruCache}.
 * 
 * @param <K> The key of this item.
 * @param <V> The value of this item.
 */
class CacheItem<K, V> {
  K key;
  V value;

  /**
   * Construct a cache item for an {@link LruCache}.
   * 
   * @param <K> The key of this item.
   * @param <V> The value of this item.
   */
  CacheItem(K key, V value) {
    this.key = key;
    this.value = value;
  }
}


/**
 * A simple LRU (Least Recently Used) cache implementation
 * {@link <a href="https://www.interviewcake.com/concept/java/lru-cache">LRU Cache</a>}. It is used
 * to cache items that are frequently requested in-memory. It will only hold a number of items less
 * than or equal to the value of its capacity field. If the total number is higher, then the least
 * recently requested item will be removed from the cache.
 * 
 * @param <K> The key of this item.
 * @param <V> The value of this item.
 */
public class LruCache<K, V> {
  /**
   * Tracking the least recently used item is carried out using a {@link LinkedList}.
   */
  private Deque<K> queue = new LinkedList<>();

  /**
   * The items themselves are stored in a {@link HashMap}.
   */
  private Map<K, CacheItem<K, V>> map = new HashMap<>();

  /**
   * The capacity beyond which the cache will not grow..
   */
  private int maxCapacity;

  /**
   * Construct the {@link LruCache} with a given capacity;
   * 
   * @param capacity The maximum number of items this cache can hold.
   */
  public LruCache(int capacity) {
    maxCapacity = capacity;
  }

  /**
   * @return the maximum capacity of this cache
   */
  public int getMaxCapacity() {
    return maxCapacity;
  }

  /**
   * Attemps to retrieve an item from the cache. Returns an {@link Optional} value.
   * 
   * @param <V> the type of the value to be retrieved
   * @param <K> the type of the key to be retrieved
   * @param key the key of the item to retrieve
   * @return the maximum capacity of this cache
   */
  public Optional<V> get(K key) {
    if (map.containsKey(key)) {
      CacheItem<K, V> current = map.get(key);
      queue.remove(current.key);
      queue.addFirst(current.key);
      return Optional.of(current.value);
    }
    return Optional.empty();
  }

  /**
   * Adds an item to the queue. If adding the item would put the number of cached items over the
   * maxCapacity, the least recently used item will be removed.
   * 
   * @param <V> the type of the value to be stored
   * @param <K> the type of the key to be stored
   * @param key the key of the item to be stored
   * @param value the value to be stored
   */
  public void put(K key, V value) {
    if (map.containsKey(key)) {
      CacheItem<K, V> curr = map.get(key);
      queue.remove(curr.key);
    } else {
      if (queue.size() == maxCapacity) {
        K temp = queue.removeLast();
        map.remove(temp);
      }
    }
    CacheItem<K, V> newItem = new CacheItem<>(key, value);
    queue.addFirst(newItem.key);
    map.put(key, newItem);
  }

  /**
   * Remove an item from the cache. This is used in cases where you delete an item from your
   * datasource and need to make sure the item is no longer in the cache.
   * 
   * @param <K> the type of the key to representing the item to be removed
   * @param key the key of the item to be removed
   */
  public void remove(K key) {
    map.remove(key);
    queue.remove(key);
  }

  /**
   * @return the current size of the cache.
   */
  public int size() {
    return map.size();
  }
}
