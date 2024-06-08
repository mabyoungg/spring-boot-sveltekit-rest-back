package org.example.springbootsveltekitrestback.global.transactionCache;

import org.example.springbootsveltekitrestback.global.transaction.TransactionScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@TransactionScope
@Component
public class TransactionCache {
    private final Map<String, Object> data = new HashMap<>();

    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public <T> T computeIfAbsent(
            String key,
            Function<String, T> mappingFunction
    ) {
        return (T) data.computeIfAbsent(key, mappingFunction);
    }
}
