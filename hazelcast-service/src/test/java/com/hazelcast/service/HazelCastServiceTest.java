package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HazelCastServiceTest {

    @InjectMocks
    HazelcastService service;

    @Mock
    CacheService cacheService;

    @Mock
    CustomService customService;

    @Value("${cache.cache-name}")
    String cacheName;


    @Test
    public void testFindResponseByUUID(){
        Cache mockCache = mock(Cache.class);
        SaveRequestDto mockSaveRequestDto = mock(SaveRequestDto.class);

        Mockito.when(cacheService.getCache(cacheName)).thenReturn(mockCache);
        Mockito.when(mockCache.get("uuid", SaveRequestDto.class)).thenReturn(mockSaveRequestDto);

        SaveRequestDto response = service.findResponseByUUID("uuid");

        assertNotNull(response);
        assertEquals(mockSaveRequestDto, response);

        verify(cacheService).getCache(cacheName);
        verify(mockCache).get("uuid", SaveRequestDto.class);
    }

    @Test
    public void testFindResponseByUUIDWhenCacheAndDBReturnNull(){
        Cache mockCache = mock(Cache.class);

        Mockito.when(cacheService.getCache(cacheName)).thenReturn(mockCache);
        Mockito.when(mockCache.get("uuid", SaveRequestDto.class)).thenThrow(new HazelCastServiceException(ErrorType.NOT_FOUND));

        Mockito.when(customService.findByUUId("uuid")).thenReturn(null);


        HazelCastServiceException exception = assertThrows(HazelCastServiceException.class, () -> {
            service.findResponseByUUID("uuid");
        });

        assertNotNull(exception);
        Assertions.assertInstanceOf(HazelCastServiceException.class, exception);
    }
}
