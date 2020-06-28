package com.strike.strijkatelier.task;

import com.strike.strijkatelier.persistence.dao.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
import java.util.*;

@Service
@Transactional
public class TokensPurgeTask {

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        passwordTokenRepository.deleteAllExpiredSince(now);
        tokenRepository.deleteAllExpiredSince(now);
    }
}
