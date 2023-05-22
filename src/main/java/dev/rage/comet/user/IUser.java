package dev.rage.comet.user;

public interface IUser {

    void saveUser(User user);
    void loadUser(User user, boolean byName);
}
