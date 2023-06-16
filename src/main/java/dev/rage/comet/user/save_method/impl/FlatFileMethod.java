package dev.rage.comet.user.save_method.impl;

import dev.rage.comet.user.User;
import dev.rage.comet.user.save_method.FlatType;

import java.util.UUID;

public class FlatFileMethod extends FlatType {
    @Override
    public void load(User user) {
        
    }

    @Override
    public void save(User user) {

    }

    @Override
    public User getUser(String name) {
        return null;
    }

    @Override
    public User getUser(UUID uuid) {
        return null;
    }
}
