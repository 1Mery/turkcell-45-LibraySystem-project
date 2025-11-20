package com.turkcell.reservation_service.infrastructure.scheduler;

import com.turkcell.reservation_service.domain.event.ReservationExpiredEvent;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Otomatik olarak süresi dolan rezervasyonları iptal eden scheduler.
 * Her 5 dakikada bir çalışır ve expireAt tarihi geçmiş rezervasyonları bulup iptal eder.
 */
@Component
public class ReservationExpirationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ReservationExpirationScheduler.class);
    
    private final ReservationRepository reservationRepository;
    private final DomainEventsPublisher domainEventsPublisher;

    public ReservationExpirationScheduler(
            ReservationRepository reservationRepository,
            DomainEventsPublisher domainEventsPublisher) {
        this.reservationRepository = reservationRepository;
        this.domainEventsPublisher = domainEventsPublisher;
    }

    @Scheduled(fixedRate = 300000) // Her 5 dakikada bir (300000 ms)
    @Transactional
    public void processExpiredReservations() {
        logger.info("Starting expired reservations check...");
        
        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations();
        
        if (expiredReservations.isEmpty()) {
            logger.debug("No expired reservations found.");
            return;
        }
        
        logger.info("Found {} expired reservations to cancel.", expiredReservations.size());
        
        for (Reservation reservation : expiredReservations) {
            try {
                reservation.updateReservationStatusAsCancelled("Auto-cancelled: Reservation expired");
                reservationRepository.save(reservation);
                
                domainEventsPublisher.publish(new ReservationExpiredEvent(reservation.id()));
                
                logger.info("Cancelled expired reservation: {}", reservation.id().value());
            } catch (Exception e) {
                logger.error("Error cancelling expired reservation: {}", reservation.id().value(), e);
            }
        }
        
        logger.info("Completed expired reservations check. Processed {} reservations.", expiredReservations.size());
    }
}

