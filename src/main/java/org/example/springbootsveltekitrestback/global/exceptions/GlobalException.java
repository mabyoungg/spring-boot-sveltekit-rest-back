package org.example.springbootsveltekitrestback.global.exceptions;

import lombok.Getter;
import org.example.springbootsveltekitrestback.global.rsData.RsData;
import org.example.springbootsveltekitrestback.standard.base.Empty;

@Getter
public class GlobalException extends RuntimeException {
    private final RsData<Empty> rsData;

    public GlobalException(String resultCode, String msg) {
        super("resultCode=" + resultCode + ",msg=" + msg);
        this.rsData = RsData.of(resultCode, msg);
    }
}
