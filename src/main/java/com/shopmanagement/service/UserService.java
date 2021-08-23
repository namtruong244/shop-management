package com.shopmanagement.service;

import com.shopmanagement.common.CmnConst;
import com.shopmanagement.common.TranslatorCode;
import com.shopmanagement.component.Translator;
import com.shopmanagement.entity.Role;
import com.shopmanagement.entity.User;
import com.shopmanagement.model.UserDetailsImpl;
import com.shopmanagement.model.UserModel;
import com.shopmanagement.repository.RoleRepository;
import com.shopmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService extends BaseService<UserRepository> implements UserDetailsService {
    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, RoleRepository roleRepository) {
        super(repository);
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByField(CmnConst.USERNAME_FIELD, username);
        if (userOptional.isEmpty()){
            log.error(Translator.getLogMessageWithParam(TranslatorCode.LOG_ERROR_USER_NOT_FOUND, username));
            throw new UsernameNotFoundException(Translator.getLogMessageWithParam(TranslatorCode.LOG_ERROR_USER_NOT_FOUND, username));
        }
        User user = userOptional.get();
        Optional<Role> roleOptional = roleRepository.findById(user.getRole());
        if (roleOptional.isEmpty()){
            log.error("role is not found");
            throw new UsernameNotFoundException(Translator.getLogMessageWithParam(TranslatorCode.LOG_ERROR_USER_NOT_FOUND, username));
        }
        user.setRole(roleOptional.get().getName());
        return UserDetailsImpl.build(new UserModel(user));
    }
}
