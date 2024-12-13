package com.eventmanagement.EventManagementBackend.usecase.auth.impl;

import com.eventmanagement.EventManagementBackend.entity.ReferralCode;
import com.eventmanagement.EventManagementBackend.entity.Role;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.UserRegistrationDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.repository.RoleRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.referralCode.ReferralCodeRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.UserDetailResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import com.eventmanagement.EventManagementBackend.usecase.auth.CreateUserUsecase;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecaseImpl implements CreateUserUsecase {
    private final UsersAccountRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ReferralCodeRepository referralCodeRepository;

    public CreateUserUsecaseImpl(UsersAccountRepository usersRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ReferralCodeRepository referralCodeRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.referralCodeRepository = referralCodeRepository;
    }

    @Override
    @CachePut(value = "userDetailResponseDTO", key = "#result.id")
    public UserDetailResponseDTO createUser(UserRegistrationDTO req) {
        ReferralCode code;
        Role role = roleRepository.findByName(req.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (req.getReferral_code() != null){
            code = referralCodeRepository.
        }

        UsersAccount newUser = new UsersAccount();
        newUser.setName(req.getName());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(req.getPassword());
        newUser.setRole(role);
//            newUser.setReferralCode("asdfasdf"); //hardcoded, logic not yet implemented !
        if (req.getReferral_code() != null) { //to be implemented to find existing user's code from existing user in db
            newUser.setIsFirstTimeDiscount(true); //hardcoded, logic not yet implemented
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        var savedUser = usersRepository.save(newUser);
        return new UserDetailResponseDTO(savedUser.getUserId(), savedUser.getEmail());


    }

    @Override
    public UsersAccount createUserWithEntity(UsersAccount req) {
        return null;
    }
}
