package com.example.clientsocketservice.dto;

import com.example.clientsocketservice.models.ExactLocation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long passengerId;

//    private ExactLocation startLocation;
//
//    private ExactLocation endLocation;

    private List<Long> driverIds;

    private Long bookingId;

}
