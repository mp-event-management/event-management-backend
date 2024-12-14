package com.eventmanagement.EventManagementBackend.usecase.auth.impl;

import com.eventmanagement.EventManagementBackend.entity.ReferralCode;
import com.eventmanagement.EventManagementBackend.entity.ReferralPoint;
import com.eventmanagement.EventManagementBackend.entity.Role;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.UserRegistrationDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.repository.RoleRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.referralCode.ReferralCodeRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.referralPoints.repository.ReferralPointsRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.UserDetailResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.auth.CreateUserUsecase;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecaseImpl implements CreateUserUsecase {
    private final UsersAccountRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ReferralCodeRepository referralCodeRepository;
    private final ReferralPointsRepository referralPointsRepository;

    public CreateUserUsecaseImpl(UsersAccountRepository usersRepository, PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository, ReferralCodeRepository referralCodeRepository,
                                 ReferralPointsRepository referralPointsRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.referralCodeRepository = referralCodeRepository;
        this.referralPointsRepository = referralPointsRepository;
    }

    @Override
    @CachePut(value = "userDetailResponseDTO", key = "#result.id")
    @Transactional
    public UserDetailResponseDTO createUser(UserRegistrationDTO req) {

        Role role = roleRepository.findByName(req.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

            if (referralCodeRepository.existsByReferralCodeId(req.getReferral_code())) {
                referralCodeRepository.incrementUsage(req.getReferral_code()); // working
                UsersAccount referrer = usersRepository.findByReferralCode(req.getReferral_code())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                ReferralPoint newPoint = new ReferralPoint();
                newPoint.setUser(referrer);
                referralPointsRepository.save(newPoint);
            }


        UsersAccount newUser = new UsersAccount();
        newUser.setName(req.getName());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(req.getPassword());
        newUser.setRole(role);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (referralCodeRepository.existsByReferralCodeId(req.getReferral_code())) {
            newUser.setUsedReferralCode(req.getReferral_code());
            newUser.setIsFirstTimeDiscount(true);
        }
        ReferralCode code = new ReferralCode(); //create new java object of code for new user
        //1. handle user first to avoid transient entity error
        var savedUser = usersRepository.save(newUser);
        code.setUser(newUser);
        code = referralCodeRepository.save(code); //2. safe to save java object of code for new user to DB
        newUser.setReferralCode(code.getReferralCodeId());
        savedUser = usersRepository.save(savedUser);
        return new UserDetailResponseDTO(savedUser.getUserId(), savedUser.getEmail());


    }

    @Override
    public UsersAccount createUserWithEntity(UsersAccount req) {
        return null;
    }
}
