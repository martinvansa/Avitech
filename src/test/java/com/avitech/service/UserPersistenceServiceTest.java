package com.avitech.service;

import com.avitech.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPersistenceServiceTest {

    private UserPersistenceService ups;

    @BeforeEach
    void setUp() {
        ups = new UserPersistenceService();
    }

    @Test
    void testGetCountRecords() throws SQLException {
        // Mock the Connection, PreparedStatement, and ResultSet
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement("SELECT COUNT(*) FROM users")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(2);

        int count = ups.getCountRecords(mockConnection);
        assertEquals(2, count);
    }

    @Test
    void testCreateUser() {
        User user1 = ups.createUser(1);
        assertNotNull(user1);
        assertEquals("a1", user1.getUserGuid());
        assertEquals("Robert", user1.getUserName());

        User user2 = ups.createUser(2);
        assertNotNull(user2);
        assertEquals("a2", user2.getUserGuid());
        assertEquals("Martin", user2.getUserName());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            ups.createUser(0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            ups.createUser(3);
        });
    }
}