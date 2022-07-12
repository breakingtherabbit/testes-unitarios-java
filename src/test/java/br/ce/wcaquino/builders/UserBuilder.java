package br.ce.wcaquino.builders;

import br.ce.wcaquino.entities.User;
import lombok.NoArgsConstructor;

public class UserBuilder {

    private User user;

    private UserBuilder() {}

    public static UserBuilder oneUser() {
        UserBuilder builder = new UserBuilder();
        builder.user = new User();
        builder.user.setName("User 1");
        return builder;
    }

    public User now() {
        return user;
    }
}
