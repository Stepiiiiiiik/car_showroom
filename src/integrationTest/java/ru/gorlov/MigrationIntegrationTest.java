package ru.gorlov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class MigrationIntegrationTest extends PostgresIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldCreateMainTables() {
        Integer count = jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*)
                        FROM information_schema.tables
                        WHERE table_schema = 'public'
                          AND table_name IN (
                            'users', 'roles', 'users_roles', 'cars', 'spare_parts',
                            'common_orders', 'custom_orders', 'custom_order_parts',
                            'test_drives', 'car_default_spare_parts', 'cars_allowed_spare_parts'
                          )
                        """,
                Integer.class);

        assertEquals(11, count);
    }

    @Test
    void shouldInsertSeedData() {
        Integer users = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        Integer cars = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cars", Integer.class);
        Integer commonOrders = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM common_orders", Integer.class);

        assertEquals(2, users);
        assertEquals(5, cars);
        assertEquals(2, commonOrders);
    }
}
