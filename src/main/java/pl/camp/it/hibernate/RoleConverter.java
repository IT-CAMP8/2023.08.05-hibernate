package pl.camp.it.hibernate;

import jakarta.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<User.Role, String> {
    @Override
    public String convertToDatabaseColumn(User.Role role) {
        if(role == null) {
            return null;
        }

        switch(role) {
            case USER:
                return "uzytkownik";
            case ADMIN:
                return "ADMIN";
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public User.Role convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }

        switch(s) {
            case "ADMIN":
                return User.Role.ADMIN;
            case "uzytkownik":
                return User.Role.USER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
