package edu.uoc.epcsd.user.infrastructure.repository.jpa;


import edu.uoc.epcsd.user.domain.User;
import edu.uoc.epcsd.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository jpaRepository;

    @Override
    public List<User> findAllUsers() {
        return jpaRepository.findAll().stream().map(UserEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return jpaRepository.findById(id).map(UserEntity::toDomain);
    }

    public Optional<User> findUserByEmail(String email) {
        return jpaRepository.findUserEntityByEmail(email).map(UserEntity::toDomain);
    }

//    @Override
//    public List<User> findAlertsByProductIdAndDate(Long productId, LocalDate availableOnDate) {
//        return null;
//    }

    @Override
    public Long createUser(User user) {
        return jpaRepository.save(UserEntity.fromDomain(user)).getId();
    }

//    @Query("select a from Alert a where a.productId = ?1 and a.from <= ?2 and a.to >= ?2")
//    List<Alert> findAlertsByProductIdAndInterval(Long productId, LocalDate availableOnDate);

}
