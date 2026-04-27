package ru.gorlov.data.postgres;

import jakarta.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gorlov.core.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID>,
    JpaSpecificationExecutor<CarEntity> {

    @Nonnull
    @EntityGraph(attributePaths = {"defaultSpareParts", "allowedSpareParts"})
    @Query("SELECT DISTINCT c FROM CarEntity c WHERE c.id = :id")
    Optional<CarEntity> findById(@Nonnull @Param("id") UUID id);
}
