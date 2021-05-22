package ecma.ai.transferapp.repository;

import ecma.ai.transferapp.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

//    @Query(nativeQuery = true,value = "SELECT Card.username FROM Card inner join Outcome on Card.id=Outcome.id")
//    List<Outcome> findByUsername(String username);

    List<Outcome> findByFromCard_Username(String fromCard_username);
}
