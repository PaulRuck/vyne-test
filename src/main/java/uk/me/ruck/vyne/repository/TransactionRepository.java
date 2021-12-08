package uk.me.ruck.vyne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uk.me.ruck.vyne.dto.TransactionDto;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionDto, Long>, JpaSpecificationExecutor {

}
