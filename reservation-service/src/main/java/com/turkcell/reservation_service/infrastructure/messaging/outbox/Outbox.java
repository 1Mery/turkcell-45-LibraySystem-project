package com.turkcell.reservation_service.infrastructure.messaging.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "outbox")
public class Outbox {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;


}
