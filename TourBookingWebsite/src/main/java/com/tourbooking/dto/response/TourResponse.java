package com.tourbooking.dto.response;

import com.tourbooking.model.Tour;
import com.tourbooking.model.TourImage;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TourResponse {
    String tourName;
    String tourCode;
    String urlImage;
    TourTimeResponse tourTimeResponse;
    public TourResponse(Tour tour, TourTimeResponse tourTimeResponse) {
        this.tourCode=tour.getTourCode();
        this.tourName=tour.getTourName();
        this.tourTimeResponse=tourTimeResponse;
        Set<TourImage>tourImages=tour.getTourImages();
        this.urlImage="";
        for(TourImage tourImage:tourImages){
            if(tourImage.getStatus()==1)
            this.urlImage=tourImage.getImageUrl();
        }

    }
}

