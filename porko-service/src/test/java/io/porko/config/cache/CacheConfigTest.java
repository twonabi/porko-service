package io.porko.config.cache;

import static io.porko.widget.controller.WidgetControllerTestHelper.widgets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

@DisplayName("Config:Cache")
@WebMvcTest(controllers = CacheManager.class)
class CacheConfigTest extends CacheConfigTestHelper {
    @SpyBean
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager.getCache(CacheType.WIDGETS.getName()).putIfAbsent(CacheType.WIDGETS.getName(), widgets);
    }

    @AfterEach
    void tearDown() {
        cacheManager.getCache(CacheType.WIDGETS.getName()).invalidate();
    }

    @Test
    @DisplayName("캐시 매니저에 등록된 캐시 목록 확인")
    void checkRegisteredCaches() {
        // When
        Collection<String> actual = cacheManager.getCacheNames();

        // Then
        assertThat(actual).containsExactly(CacheType.WIDGETS.getName());
    }

    @Test
    @DisplayName("각 캐시의 모든 key 목록 확인")
    public void getAllKeyAndValue() {
        // When
        Map<String, List<String>> cacheMapInfo = cacheManager.getCacheNames().stream()
            .collect(Collectors.toMap(
                cacheName -> cacheName,
                cacheName -> {
                    Cache<Object, Object> nativeCache = ((CaffeineCache) cacheManager.getCache(cacheName)).getNativeCache();
                    return nativeCache.asMap().keySet().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
                }
            ));

        // Then
        assertThat(cacheMapInfo).containsKeys(CacheType.WIDGETS.getName());
        assertThat(cacheMapInfo).containsValue(List.of(CacheType.WIDGETS.getName()));
    }

    @Test
    @DisplayName("캐시 적중 여부 확인")
    void checkHitCount() {
        // Given
        cacheManager.getCache(cacheName).putIfAbsent(testKey, testValue);

        // When
        String value = assertDoesNotThrow(() -> cacheManager.getCache(cacheName).get(testKey, String.class));
        CacheStats actual = ((CaffeineCache) cacheManager.getCache(cacheName)).getNativeCache().stats();

        // Then
        assertThat(actual.hitCount()).isEqualTo(1);
        assertThat(value).isEqualTo(testValue);
    }

    @Test
    @DisplayName("evict에 의한 캐시 삭제 여부 확인")
    void checkCacheEviction() {
        cacheManager.getCache(cacheName).putIfAbsent(testKey, testValue);
        ((CaffeineCache) cacheManager.getCache(cacheName)).evictIfPresent(testKey);

        // When
        String actual = assertDoesNotThrow(() -> cacheManager.getCache(cacheName).get(testKey, String.class));
        CacheStats stats = ((CaffeineCache) cacheManager.getCache(cacheName)).getNativeCache().stats();

        // Then
        assertThat(actual).isNull();
        assertThat(stats.hitCount()).isEqualTo(0);
    }
}
