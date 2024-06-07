package org.example.springbootsveltekitrestback.global.initData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springbootsveltekitrestback.domain.member.member.entity.Member;
import org.example.springbootsveltekitrestback.domain.member.member.service.MemberService;
import org.example.springbootsveltekitrestback.global.app.AppConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class All {
    private final MemberService memberService;

    @Value("${custom.prod.members.system.password}")
    private String prodMemberSystemPassword;

    @Value("${custom.prod.members.admin.password}")
    private String prodMemberAdminPassword;

    @Value("${custom.prod.members.garage.password}")
    private String prodMemberGaragePassword;

    @Bean
    @Order(2)
    public ApplicationRunner initAll() {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                if (memberService.findByUsername("system").isPresent()) return;

                String memberSystemPassword = AppConfig.isProd() ? prodMemberSystemPassword : "1234";
                String memberAdminPassword = AppConfig.isProd() ? prodMemberAdminPassword : "1234";
                String memberGaragePassword = AppConfig.isProd() ? prodMemberGaragePassword : "1234";

                Member memberSystem = memberService.join("system", memberSystemPassword).getData();
                memberSystem.setRefreshToken("system");

                Member memberAdmin = memberService.join("admin", memberAdminPassword).getData();
                memberAdmin.setRefreshToken("admin");

                Member memberGarage = memberService.join("garage", memberGaragePassword).getData();
                memberGarage.setRefreshToken("garage");
            }
        };
    }
}
