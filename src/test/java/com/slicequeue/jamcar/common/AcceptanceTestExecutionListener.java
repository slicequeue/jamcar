package com.slicequeue.jamcar.common;

import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Nested;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class AcceptanceTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        Class<?> testClass = testContext.getTestClass();
        boolean nestedAnnotationPresent = testClass.isAnnotationPresent(Nested.class);
        if (!nestedAnnotationPresent) {
            final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
            final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
            truncateTables(jdbcTemplate, truncateQueries);
        }
    }

//    @Override
//    public void afterTestMethod(final TestContext testContext) throws SQLException {
//        final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
//        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
//        truncateTables(jdbcTemplate, truncateQueries);
//    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private String getDatabaseProductName(final JdbcTemplate jdbcTemplate) throws SQLException {
        Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
        String databaseProductName = connection.getMetaData().getDatabaseProductName();
        connection.close();
        return databaseProductName;
    }

    private String getDatabaseSchemaName(final JdbcTemplate jdbcTemplate) throws SQLException {
        return Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getSchema();
    }

    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) throws SQLException {
        String databaseProductName = getDatabaseProductName(jdbcTemplate);
        String query;
        switch (databaseProductName.toLowerCase()) {
            case "mysql":
            case "mariadb":
                query = "show tables";
                break;
            case "h2":
            default:
                query = "SELECT TABLE_NAME AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
//                query = "SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') AS q FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
                break;
        }
        List<String> results = jdbcTemplate.queryForList(query, String.class);
        return results.stream().map(elem -> String.format("TRUNCATE TABLE %s;", elem)).toList();
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) throws SQLException {
        String databaseProductName = getDatabaseProductName(jdbcTemplate);
        String offForeignKeyQuery, onForeignKeyQuery;
        switch (databaseProductName.toLowerCase()) {
            case "mysql":
            case "mariadb":
                offForeignKeyQuery = "SET @@foreign_key_checks = 0";
                onForeignKeyQuery = "SET @@foreign_key_checks = 1";
                break;
            case "h2":
            default:
                offForeignKeyQuery = "SET REFERENTIAL_INTEGRITY FALSE";
                onForeignKeyQuery = "SET REFERENTIAL_INTEGRITY TRUE";
                break;
        }
        execute(jdbcTemplate, offForeignKeyQuery);
        truncateQueries.forEach(v -> execute(jdbcTemplate, v));
        execute(jdbcTemplate, onForeignKeyQuery);
    }

    private void execute(final JdbcTemplate jdbcTemplate, final String query) {
        jdbcTemplate.execute(query);
    }

}
