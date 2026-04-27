package ru.gorlov;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgresIT {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        POSTGRES.start();
        applyMigrationsAndSeed();
    }

    @DynamicPropertySource
    static void registerPostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }

    private static void applyMigrationsAndSeed() {
        List<String> scripts = List.of(
                "migrations/sql/V001__users_roles_schema.sql",
                "migrations/sql/V002__cars_spare_parts_schema.sql",
                "migrations/sql/V003__common_orders_schema.sql",
                "migrations/sql/V004__custom_orders_schema.sql",
                "migrations/sql/V005__test_drives_schema.sql",
                "migrations/sql/V006__seed_data.sql");

        try (Connection connection = DriverManager.getConnection(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword())) {
            for (String script : scripts) {
                ScriptUtils.executeSqlScript(
                        connection,
                        new EncodedResource(new FileSystemResource(Path.of(script).toAbsolutePath()), "UTF-8"));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to prepare PostgreSQL integration test schema", e);
        }
    }
}
