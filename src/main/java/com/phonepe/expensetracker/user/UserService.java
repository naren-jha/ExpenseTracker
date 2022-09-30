package com.phonepe.expensetracker.user;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.phonepe.expensetracker.common.Constants.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        validateEmailAndPhone(user);

        user.setGroups(new HashSet<>());
        userRepository.addUser(user);
    }

    public User getUser(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(USER_ID_NULL_ERR_MSG);
        }

        return userRepository.getUser(id)
                .orElseThrow(() -> new IllegalStateException(USER_DOES_NOT_EXIST_ERR_MSG + " user id = " + id));
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserByEmail(String email) {
        if (Strings.isBlank(email)) {
            throw new IllegalArgumentException(EMAIL_NULL_OR_EMPTY_ERR_MSG);
        }

        Optional<User> userOptional = userRepository.getUserByEmail(email);

        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public User getUserByMobile(String mobile) {
        if (Strings.isBlank(mobile)) {
            throw new IllegalArgumentException(MOBILE_NULL_OR_EMPTY_ERR_MSG);
        }

        Optional<User> userOptional = userRepository.getUserByMobile(mobile);

        return userOptional.isPresent() ? userOptional.get() : null;
    }

    private void validateEmailAndPhone(User user) {
        if (Strings.isBlank(user.getEmail())) {
            throw new IllegalArgumentException(EMAIL_NULL_OR_EMPTY_ERR_MSG);
        }

        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new IllegalArgumentException(INVALID_EMAIL_ERR_MSG);
        }

        Optional<User> userOptional = userRepository.getUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException(DUPLICATE_EMAIL_ERR_MSG);
        }

        if (Strings.isBlank(user.getMobile())) {
            throw new IllegalArgumentException(MOBILE_NULL_OR_EMPTY_ERR_MSG);
        }

        userOptional = userRepository.getUserByMobile(user.getMobile());
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException(DUPLICATE_MOBILE_ERR_MSG);
        }
    }
}
