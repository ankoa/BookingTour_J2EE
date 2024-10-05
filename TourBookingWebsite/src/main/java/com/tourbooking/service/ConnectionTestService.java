package com.tourbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ConnectionTestService {

    @Autowired
    private DataSource dataSource;

    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                return "Kết nối đến MySQL thành công!";
            } else {
                return "Không thể kết nối đến MySQL.";
            }
        } catch (SQLException e) {
            return "Lỗi kết nối đến MySQL: " + e.getMessage();
        }
    }
}
