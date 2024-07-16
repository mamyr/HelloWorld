package com.andersen.lesson10.dtos;

import com.andersen.lesson9.Models.TicketType;
import com.andersen.lesson9.Models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TicketDTO {
    @JsonProperty
    private Integer id;

    @JsonProperty
    private User user;

    @JsonProperty
    private Timestamp creationDate;

    @JsonProperty
    private TicketType ticketType;
}
