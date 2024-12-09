package com.eventmanagement.EventManagementBackend.infrastructure.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {

    private Integer id;
    private Integer transactionId;
    private Integer eventId;
    private Integer customerId;
    private String customerName;
    private String eventName;
    private String eventImagesUrl;
    private OffsetDateTime eventStartDate;
    private OffsetDateTime eventEndDate;
    private BigDecimal price;
    private String status;
    private OffsetDateTime issuedate;
    private OffsetDateTime validUntil;

}
