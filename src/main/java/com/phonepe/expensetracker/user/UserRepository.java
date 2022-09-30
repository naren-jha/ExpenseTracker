package com.phonepe.expensetracker.user;

import com.phonepe.expensetracker.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private Database db;

    public void addUser(User user) {
        db.addUser(user);
    }

    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(db.getUsers().get(id));
    }

    public List<User> getUsers() {
        return new ArrayList<>(db.getUsers().values());
    }

    public Optional<User> getUserByEmail(String email) {
        User match = null;
        for (User user : db.getUsers().values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                match = user;
            }
        }
        return Optional.ofNullable(match);
    }

    public Optional<User> getUserByMobile(String mobile) {
        User match = null;
        for (User user : db.getUsers().values()) {
            if (user.getMobile().equals(mobile)) {
                match = user;
            }
        }
        return Optional.ofNullable(match);
    }
}
