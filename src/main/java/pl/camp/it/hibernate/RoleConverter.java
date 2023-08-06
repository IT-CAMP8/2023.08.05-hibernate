package pl.camp.it.hibernate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
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
                return "administrator";
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
            case "administrator":
                return User.Role.ADMIN;
            case "uzytkownik":
                return User.Role.USER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
