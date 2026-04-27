package ru.gorlov.data.postgres;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gorlov.core.entity.SparePartEntity;

@Repository
public interface SparePartRepository extends JpaRepository<SparePartEntity, UUID> {

}
