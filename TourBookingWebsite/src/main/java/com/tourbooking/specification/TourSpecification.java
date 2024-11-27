package com.tourbooking.specification;

import com.tourbooking.model.Category;
import com.tourbooking.model.Tour;
import com.tourbooking.model.TourTime;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TourSpecification {
    public static Specification<Tour> hasPriceBetween(Integer min, Integer max, String sort) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<TourTime> tourTimeRoot = subquery.from(TourTime.class);
            subquery.select(criteriaBuilder.min(tourTimeRoot.get("priceAdult")))
                    .where(criteriaBuilder.equal(tourTimeRoot.get("tour").get("id"), root.get("id")));
            if(sort.equalsIgnoreCase("asc") ){
                query.orderBy(criteriaBuilder.asc(subquery));
            }
            if(sort.equalsIgnoreCase("desc")) {
                query.orderBy(criteriaBuilder.desc(subquery));
            }
            if (min == null && max == null) return null;
            if (min != null && max != null) {
                return criteriaBuilder.between(subquery, min * 1000000, max * 1000000);
            }

            if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(subquery, min * 1000000);
            }

            return criteriaBuilder.lessThanOrEqualTo(subquery, max * 1000000);
        };
    }

    public static Specification<Tour> hasSearchKeyword(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) return null;
            String likePattern = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.like(root.get("tourName"), likePattern);
        };
    }

    public static Specification<Tour> hasDepartureDate(Date departureDate) {
        return (root, query, criteriaBuilder) -> {
            if (departureDate == null) return null;
            Join<Tour, TourTime> join = root.join("tourTimes");
            LocalDate localDate = departureDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Tạo khoảng thời gian bắt đầu và kết thúc trong ngày
            LocalDateTime startOfDay = localDate.atStartOfDay(); // 00:00:00
            LocalDateTime endOfDay = localDate.atTime(23, 59, 59); // 23:59:59
            System.out.println(join.get("departureTime"));
            return criteriaBuilder.between(join.get("departureTime"), startOfDay, endOfDay);
        };
    }

    public static Specification<Tour> hasCategoryId(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) return null;
            return criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
        };
    }

    public static Specification<Tour> hasStatus(Integer status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }


}
