package org.example.springbootsveltekitrestback.global.scope;


import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.domain.member.member.repository.MemberRepository;
import org.example.springbootsveltekitrestback.global.transactionCache.TransactionCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionScopeTestService {
    private final TransactionCache transactionCache;
    private final MemberRepository memberRepository;

    public String test1() {
        return (String) transactionCache.computeIfAbsent("test", k -> UUID.randomUUID().toString());
    }

    public String test2() {
        return (String) transactionCache.computeIfAbsent("test", k -> UUID.randomUUID().toString());
    }
}