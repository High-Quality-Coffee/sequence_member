package sequence.sequence_member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sequence.sequence_member.entity.CareerEntity;

public interface CareerRepository extends JpaRepository<CareerEntity, Long> {
}
