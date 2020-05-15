package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Audited(withModifiedFlag = true)
public abstract class ItemEntity extends InfoEntity {

    public ItemEntity(Long id, Status entityStatus, @NotNull(message = "Number cannot be null") String number,
                      Location location, String purpose, String purposePassport, LocalDate vintage, int vpNumber,
                      LocalDate otkDate, LocalDate vpDate) {
        super(id, entityStatus);
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
    @Audited(modifiedColumnName = "accept_otk_date_mod", withModifiedFlag = true)
    private LocalDate otkDate;

    @Column(name = "accept_vp_date")
    @Audited(modifiedColumnName = "accept_vp_date_mod", withModifiedFlag = true)
    private LocalDate vpDate;
}
