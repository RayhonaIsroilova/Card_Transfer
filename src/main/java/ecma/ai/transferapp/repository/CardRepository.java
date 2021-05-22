package ecma.ai.transferapp.repository;

import ecma.ai.transferapp.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByCardNumber(String number);

     List<Card> findByUsername(String username);
}
