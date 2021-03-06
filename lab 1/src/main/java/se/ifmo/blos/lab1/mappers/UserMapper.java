package se.ifmo.blos.lab1.mappers;

import se.ifmo.blos.lab1.exceptions.IllegalMappingOperationException;
import se.ifmo.blos.lab1.domains.User;
import se.ifmo.blos.lab1.dtos.UserDto;

public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public User mapToPersistable(final UserDto dto) throws IllegalMappingOperationException {
        throw new IllegalMappingOperationException("Unsupported mapping of UserDto to User.");
    }

    @Override
    public UserDto mapToDto(final User persistable) throws IllegalMappingOperationException {
        return new UserDto(persistable.getPassword());
    }

    @Override
    public void updateFromDto(final UserDto dto, final User persistable) throws IllegalMappingOperationException {
        throw new IllegalMappingOperationException("Unsupported mapping of UserDto to User.");

    }
}
