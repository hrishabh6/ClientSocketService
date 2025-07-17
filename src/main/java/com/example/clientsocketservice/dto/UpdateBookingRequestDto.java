package com.example.clientsocketservice.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookingRequestDto {

    private String status;
    private Optional<Long> driverId;

}
