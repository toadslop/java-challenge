package jp.co.axa.apidemo;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import jp.co.axa.apidemo.services.LruCache;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LruCacheTests {
  
  @Test
	public void cacheEmptyOnCreate() {
    LruCache<Integer, String> cache = new LruCache<>(5);

    assertEquals(0, cache.size());
	}

  @Test
	public void cacheWontGrowLargerThanMaxSize() {
    LruCache<Integer, String> cache = new LruCache<>(2);
    cache.put(0, "First");
    cache.put(1, "Second");
    cache.put(2, "Third");
    assertEquals(2, cache.size());
	}

  @Test
	public void overCapacityItemsRemoved() {
    LruCache<Integer, String> cache = new LruCache<>(2);
    cache.put(0, "First");
    cache.put(1, "Second");
    cache.put(2, "Third");
    Optional<String> expected = Optional.empty();
    assertEquals(expected, cache.get(0));
	}

  @Test
	public void mostRecentItemsAvailable() {
    LruCache<Integer, String> cache = new LruCache<>(2);
    cache.put(0, "First");
    cache.put(1, "Second");
    cache.put(2, "Third");
    Optional<String> expected = Optional.of("Second");
    assertEquals(expected, cache.get(1));

    expected = Optional.of("Third");
    assertEquals(expected, cache.get(2));
	}
}
