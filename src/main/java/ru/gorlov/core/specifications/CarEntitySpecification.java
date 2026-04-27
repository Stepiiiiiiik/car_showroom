package ru.gorlov.core.specifications;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ru.gorlov.core.entity.BaseEntity;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.CarEntity.Fields;
import ru.gorlov.core.filters.CarFilter;

public record CarEntitySpecification(CarFilter filter) implements Specification<CarEntity> {

    @Override
    public Predicate toPredicate(@Nonnull Root<CarEntity> root, @Nullable CriteriaQuery<?> query,
        @Nonnull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getBrand() != null) {
            predicates.add(
                criteriaBuilder.equal(root.get(Fields.brand), filter.getBrand()));
        }

        if (filter.getSpareParts() != null && !filter.getSpareParts().isEmpty()) {
            predicates.add(
                root.get(Fields.allowedSpareParts).get(BaseEntity.Fields.id)
                    .in(filter.getSpareParts()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
