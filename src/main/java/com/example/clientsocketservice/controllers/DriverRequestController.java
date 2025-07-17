package com.example.clientsocketservice.controllers;

import com.example.clientsocketservice.dto.RideRequestDto;
import com.example.clientsocketservice.dto.RideResponseDto;


import com.example.clientsocketservice.dto.UpdateBookingRequestDto;
import com.example.clientsocketservice.dto.UpdateBookingResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/api/socket")
public class    DriverRequestController {

    private final SimpMessagingTemplate template;
    private final RestTemplate restTemplate;

    public DriverRequestController(SimpMessagingTemplate template) {
        this.template = template;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/newride")
    public ResponseEntity<Boolean> raiseSideRequest(@RequestBody RideRequestDto request){
        sendToDriver(request);
        return ResponseEntity.ok(true);
    }


   public void sendToDriver(RideRequestDto request) {
        //Todo : the request should go to only nearby drivers
        template.convertAndSend("/topic/rideRequest", request);

   }
    @MessageMapping("/rideResponse/{userId}")
   public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto request){
       System.out.println("Ride response received " + request.getResponse());
        UpdateBookingRequestDto updateBookingRequestDto =
                UpdateBookingRequestDto.builder()
                        .driverId(Optional.of(Long.parseLong(userId)))
                        .status("SCHEDULED")
                        .build();
        ResponseEntity<UpdateBookingResponseDto> result = restTemplate.postForEntity("http://localhost:7479/api/v1/booking/"+ request.getBookingId(),updateBookingRequestDto, UpdateBookingResponseDto.class);
        System.out.println( "The staatus code " + result.getStatusCode());
   }


}
