package org.example.springbootsveltekitrestback.global.transactionCache;

import lombok.experimental.Delegate;
import org.example.springbootsveltekitrestback.global.transaction.TransactionScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@TransactionScope
@Component
public class TransactionCache {
    @Delegate
    private final Map<String, Object> data = new HashMap<>();

    public <T> T get(String key) {
        return (T) data.get(key);
    }
}
