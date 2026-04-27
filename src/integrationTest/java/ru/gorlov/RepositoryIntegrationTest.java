package ru.gorlov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gorlov.core.entity.CarEntity;
import ru.gorlov.core.entity.CustomOrderEntity;
import ru.gorlov.data.postgres.CarRepository;
import ru.gorlov.data.postgres.CustomOrderRepository;
import ru.gorlov.data.postgres.UserRepository;

@SpringBootTest
class RepositoryIntegrationTest extends PostgresIT {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomOrderRepository customOrderRepository;

    @Test
    void shouldLoadCarWithSparePartCollections() {
        UUID carId = UUID.fromString("00000000-0000-0000-0000-000000000201");

        CarEntity car = carRepository.findById(carId).orElseThrow();

        assertEquals("Toyota", car.getBrand());
        assertEquals(4, car.getDefaultSpareParts().size());
        assertEquals(8, car.getAllowedSpareParts().size());
    }

    @Test
    void shouldLoadCustomOrderWithRelations() {
        UUID orderId = UUID.fromString("00000000-0000-0000-0000-000000000401");

        CustomOrderEntity order = customOrderRepository.findById(orderId).orElseThrow();

        assertNotNull(order.getClient());
        assertNotNull(order.getStoreManager());
        assertNotNull(order.getCar());
        assertEquals(4, order.getConfiguration().size());
    }
}
