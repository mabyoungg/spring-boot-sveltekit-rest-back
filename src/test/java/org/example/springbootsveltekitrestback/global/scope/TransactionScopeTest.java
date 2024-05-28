package org.example.springbootsveltekitrestback.global.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class TransactionScopeTest {
    @Autowired
    private TransactionScopeTestService transactionScopeTestService;

    @Test
    @DisplayName("t1")
    public void t1() {
        String uuid1 = transactionScopeTestService.test1();
        String uuid2 = transactionScopeTestService.test2();

        assertThat(uuid1).isNotEqualTo(uuid2);
    }

    @Test
    @DisplayName("t2")
    @Transactional
    public void t2() {
        String uuid1 = transactionScopeTestService.test1();
        String uuid2 = transactionScopeTestService.test2();

        assertThat(uuid1).isEqualTo(uuid2);
    }
}