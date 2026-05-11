package br.com.hanrry.deliveries_service.database.model;

import br.com.hanrry.deliveries_service.enums.DeliveriesStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_deliveries")
public class DeliveriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientEmail;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    @Enumerated(EnumType.STRING)
    private DeliveriesStatus status;

    private String trackingCode;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deliveredAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = DeliveriesStatus.PENDING;

        if (this.trackingCode == null) {
            this.trackingCode = "BR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
