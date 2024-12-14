package com.eventmanagement.EventManagementBackend.infrastructure.referralCode;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;

public class CodeGeneratorUseCase implements IdentifierGenerator {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object)
            throws HibernateException {
        try {
            String code = generateRandomString();
            boolean isUnique = isCodeUnique(code, session);
            if (!isUnique) {
                code = generateRandomString();
                isUnique = isCodeUnique(code, session);
            }

            do {
                code = generateRandomString();
                isUnique = isCodeUnique(code, session);
            } while (!isUnique);

            return code;
        } catch (Exception e) {
            throw new HibernateException("Error generating code", e);
        }
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for(int i = 0; i < LENGTH; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    private boolean isCodeUnique(String code, SharedSessionContractImplementor session) {
        try {
            Long count = (Long) session
                    .createNativeQuery("SELECT COUNT(*) FROM rmpdb.public.referral_codes r WHERE referral_code = :code AND deleted_at IS NULL")
                    .setParameter("code", code)
                    .uniqueResult();
            boolean isUnique = count == 0;
            return isUnique;
        } catch (Exception e) {
            return false;
        }
    }

//    private boolean isCodeUnique(String code, SharedSessionContractImplementor session) {
//        String query = "SELECT COUNT(r) FROM ReferralCode r WHERE r.referralCodeId = :code";
//        Long count = (Long) session.createQuery(query)
//                .setParameter("code", code)
//                .uniqueResult();
//        return count == 0;
//    }
}

