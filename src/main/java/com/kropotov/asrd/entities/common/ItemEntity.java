package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class ItemEntity extends InfoEntity {

    public ItemEntity(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                      @NotNull(message = "Number cannot be null") String number, Location location, String purpose,
                      String purposePassport, LocalDate vintage, int vpNumber, LocalDate otkDate, LocalDate vpDate) {
        super(id, entityStatus, createdAt, updatedAt);
        this.number = number;
        if (location == null) {
            this.location = Location.ANYWHERE;
        } else {
            this.location = location;
        }
        this.purpose = purpose;
        this.purposePassport = purposePassport;
        this.vintage = vintage;
        this.vpNumber = vpNumber;
        this.otkDate = otkDate;
        this.vpDate = vpDate;
    }

    @NotNull(message = "Number cannot be null")
    @Column(name = "number")
    private String number;

    @Enumerated(value = EnumType.ORDINAL)
    @Value("0")
    private Location location;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "purpose_passport")
    private String purposePassport;

    @Column(name = "vintage")
    private LocalDate vintage;

    @Column(name = "vp_number")
    private int vpNumber;

    @Column(name = "accept_otk_date")
    private LocalDate otkDate;

    @Column(name = "accept_vp_date")
    private LocalDate vpDate;
}
