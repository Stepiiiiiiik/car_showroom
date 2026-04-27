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
import ru.gorlov.core.entity.CustomOrderEntity;

@Repository
public interface CustomOrderRepository extends JpaRepository<CustomOrderEntity, UUID>,
    JpaSpecificationExecutor<CustomOrderEntity> {

    @Nonnull
    @EntityGraph(attributePaths = {"client", "storeManager", "car", "configuration"})
    @Query("SELECT DISTINCT o FROM CustomOrderEntity o WHERE o.id = :id")
    Optional<CustomOrderEntity> findById(@Nonnull @Param("id") UUID id);
}
