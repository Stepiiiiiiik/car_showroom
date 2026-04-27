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
import ru.gorlov.core.entity.CommonOrderEntity;

@Repository
public interface CommonOrderRepository extends JpaRepository<CommonOrderEntity, UUID>,
    JpaSpecificationExecutor<CommonOrderEntity> {

    @Nonnull
    @EntityGraph(attributePaths = {"client", "storeManager", "car"})
    @Query("SELECT DISTINCT o FROM CommonOrderEntity o WHERE o.id = :id")
    Optional<CommonOrderEntity> findById(@Nonnull @Param("id") UUID id);

    boolean existsById(UUID id);
}
