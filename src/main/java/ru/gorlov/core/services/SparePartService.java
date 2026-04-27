package ru.gorlov.core.services;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorlov.core.entity.SparePartEntity;
import ru.gorlov.core.exceptions.EntityNotFoundException;
import ru.gorlov.data.postgres.SparePartRepository;
import ru.gorlov.presentation.dto.part.SparePartUpdateDto;
import ru.gorlov.presentation.mapper.SparePartMapper;

@RequiredArgsConstructor
@Service
public class SparePartService {

    private final SparePartMapper sparePartMapper;

    private final SparePartRepository sparePartRepository;

    @Transactional
    public UUID createSparePart(
        SparePartEntity sparePartEntity) {

        sparePartRepository.save(sparePartEntity);

        return sparePartEntity.getId();
    }

    @Transactional(readOnly = true)
    public SparePartEntity findSparePartById(UUID id) {
        return sparePartRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Spare part not found"));
    }

    @Transactional(readOnly = true)
    public List<SparePartEntity> findAll() {
        return sparePartRepository.findAll();
    }

    @Transactional
    public SparePartEntity updateSparePart(UUID id, SparePartUpdateDto sparePartUpdateDto) {
        SparePartEntity sparePartEntity = sparePartRepository.findById(id)
            .orElseThrow(() ->
                new EntityNotFoundException("Spare part with id=" + id
                    + " was not found"));

        sparePartMapper.toUpdatedEntity(sparePartEntity, sparePartUpdateDto);

        sparePartRepository.save(sparePartEntity);

        return sparePartEntity;
    }

    @Transactional
    public void deleteSparePart(UUID id) {
        if (!sparePartRepository.existsById(id)) {
            throw new EntityNotFoundException("Common order with id=" + id + " was not found");
        }
        sparePartRepository.deleteById(id);
    }
}
