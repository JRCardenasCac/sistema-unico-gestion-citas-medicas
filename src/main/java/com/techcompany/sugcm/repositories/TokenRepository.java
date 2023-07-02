package com.techcompany.sugcm.repositories;

import com.techcompany.sugcm.models.entity.Token;
import com.techcompany.sugcm.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = "select t.* from token t"
            + " inner join users u on t.fk_user = u.user_id"
            + " where u.user_id = :userId and (t.expired = false or t.revoked = false)", nativeQuery = true)
    List<Token> findAllValidTokenByUserId(@Param("userId") Long userId);

    Optional<Token> findByToken(String token);
}
