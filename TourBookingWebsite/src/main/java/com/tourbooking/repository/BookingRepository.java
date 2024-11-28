package com.tourbooking.repository;

import com.tourbooking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.tourbooking.model.TourTime;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    // Lấy tất cả các TourTime liên quan đến Booking
    @Query("SELECT b.tourTime FROM Booking b")
    List<TourTime> findAllTourTimes();

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = 0 WHERE b.bookingId = :bookingId")
    int deactivateBooking(@Param("bookingId") Integer id);


    Page<Booking> findAll(Pageable pageable);
    
    
    
    
    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m') AS month, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE YEAR(b.time) = :year " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m')", nativeQuery = true)
   List<Map<String, Object>> findRevenueByYear(@Param("year") int year);

    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE YEAR(b.time) = :year AND MONTH(b.time) = :month " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
   List<Map<String, Object>> findDailyRevenue(@Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT YEAR(b.time) AS year, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE YEAR(b.time) BETWEEN :startYear AND :endYear " +
            "GROUP BY YEAR(b.time) " +
            "ORDER BY YEAR(b.time) DESC", nativeQuery = true)
   List<Map<String, Object>> findRevenueOfLastFourYears(@Param("startYear") int startYear, @Param("endYear") int endYear);
    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE b.time BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> findRevenueFor30Days(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE DATE(b.time) = :specificDate " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> findRevenueForSpecificDay(@Param("specificDate") String specificDate);

    
    
    
    
    @Query(value = "SELECT t.tour_name, COUNT(b.booking_id) AS booking_count " +
            "FROM booking b " +
            "JOIN tour_time tt ON b.tour_time_id = tt.tour_time_id " +
            "JOIN tour t ON tt.tour_id = t.tour_id " +
            "WHERE t.status = 1 " +
            "GROUP BY t.tour_name " +
            "ORDER BY COUNT(b.booking_id) DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<Map<String, Object>> findTopBookedTours(@Param("limit") int limit);

    
    @Query(value = "SELECT YEAR(b.time) AS year, COUNT(DISTINCT b.tour_time_id) AS tour_count " +
            "FROM booking b " +
            "WHERE YEAR(b.time) = :year " +
            "GROUP BY YEAR(b.time) " +
            "ORDER BY YEAR(b.time) DESC", nativeQuery = true)
List<Map<String, Object>> findTourCountByYear(@Param("year") int year);

    
    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, COUNT(DISTINCT b.tour_time_id) AS tour_count " +
            "FROM booking b " +
            "WHERE YEAR(b.time) = :year AND MONTH(b.time) = :month " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
List<Map<String, Object>> findTourCountByDayInMonth(@Param("year") int year, @Param("month") int month);

    
    @Query(value = "SELECT YEAR(b.time) AS year, MONTH(b.time) AS month, COUNT(DISTINCT b.tour_time_id) AS tour_count " +
            "FROM booking b " +
            "WHERE b.time BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(b.time), MONTH(b.time) " +
            "ORDER BY YEAR(b.time) DESC, MONTH(b.time) DESC", nativeQuery = true)
List<Map<String, Object>> findTourCountInRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    
    
    /*@Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m') AS month, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m')", nativeQuery = true)
    List<Map<String, Object>> findMonthlyRevenue();

    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE DATE_FORMAT(b.time, '%Y-%m') = :month " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> findDailyRevenue(@Param("month") String month);

    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE b.time BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> findRevenueInRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m') AS month, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE YEAR(b.time) = :year AND MONTH(b.time) = :month " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m')", nativeQuery = true)
	List<Map<String, Object>> findMonthlyRevenue(@Param("year") int year, @Param("month") int month);
    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m') AS month, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE YEAR(b.time) = :year " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m')", nativeQuery = true)
    List<Map<String, Object>> findAnnualRevenue(@Param("year") int year);
    @Query(value = "SELECT DATE_FORMAT(b.time, '%Y-%m-%d') AS day, SUM(d.price) AS revenue " +
            "FROM booking_detail d " +
            "JOIN booking b ON d.booking_id = b.booking_id " +
            "WHERE YEAR(b.time) = :year AND MONTH(b.time) = :month " +
            "GROUP BY DATE_FORMAT(b.time, '%Y-%m-%d') " +
            "ORDER BY DATE_FORMAT(b.time, '%Y-%m-%d')", nativeQuery = true)
    List<Map<String, Object>> findDailyRevenue(@Param("year") int year, @Param("month") int month);*/

}
