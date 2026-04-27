package ru.gorlov.data.postgres;

import jakarta.annotation.Nonnull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gorlov.core.entity.TestDriveEntity;

@Repository
public interface TestDriveRepository extends JpaRepository<TestDriveEntity, UUID> {

    @Nonnull
    @EntityGraph(attributePaths = {"car", "client"})
    @Query("SELECT DISTINCT t FROM TestDriveEntity t WHERE t.id = :id")
    Optional<TestDriveEntity> findById(@Nonnull @Param("id") UUID id);
}
