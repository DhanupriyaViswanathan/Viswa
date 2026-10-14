package com.vsr.factory;

import java.util.List;
import java.util.stream.Collectors;

public class UserFactory {

    public static List<com.vsr.composite.User> toCompositeList(
            List<com.vsr.entity.User> users) {

        return users.stream()
                .map(UserFactory::toComposite)
                .collect(Collectors.toList());
    }

    public static com.vsr.composite.User toComposite(
            com.vsr.entity.User user) {

        com.vsr.composite.User compositeUser =
                new com.vsr.composite.User();

        compositeUser.setUserId(user.getUserId());
        compositeUser.setFirstName(user.getFirstName());
        compositeUser.setLastName(user.getLastName());
        compositeUser.setRole(user.getRole());
        compositeUser.setUserAccess(user.getUserAccess());
        compositeUser.setPhone(user.getPhone());
        compositeUser.setEmail(user.getEmail());
        compositeUser.setAddress(user.getAddress());

        return compositeUser;
    }
    
    public static com.vsr.entity.User toEntity(
            com.vsr.composite.User user) {

        com.vsr.entity.User entityUser =
                new com.vsr.entity.User();

        entityUser.setUserId(user.getUserId());
        entityUser.setFirstName(user.getFirstName());
        entityUser.setLastName(user.getLastName());
        entityUser.setRole(user.getRole());
        entityUser.setUserAccess(user.getUserAccess());
        entityUser.setPhone(user.getPhone());
        entityUser.setEmail(user.getEmail());
        entityUser.setAddress(user.getAddress());

        return entityUser;
    }
}
