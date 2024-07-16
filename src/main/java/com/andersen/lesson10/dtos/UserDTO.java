package com.andersen.lesson10.dtos;


import com.andersen.lesson9.Models.Ticket;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {
    @JsonProperty
    private Integer id;

    @JsonProperty
    private String name;

    @JsonProperty
    private Timestamp creationDate;

    @JsonProperty
    private Set<Ticket> ticketList;
}
