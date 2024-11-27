package com.tourbooking.dto.response;

import com.tourbooking.model.Category;
import com.tourbooking.model.TourImage;
import com.tourbooking.model.TourTime;
import com.tourbooking.repository.TourImageRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindTourResponse {
    private int tourId;

    private String tourName;

    private String tourDetail;

    private String tourCode;

    private String dayStay;

    private List<TourTimeResponse> tourTimes;

    private List<TourImageResponse> tourImages;
    private Integer tourPrice;
    private CategoryResponse category;
}
