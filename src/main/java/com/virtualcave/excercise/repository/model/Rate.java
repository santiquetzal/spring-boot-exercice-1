package com.virtualcave.excercise.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rate {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @Column(name = "brand_id")
    private Integer brandId;

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "start_date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate endDate;

    @NotNull
    private Integer price;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Rate rate = (Rate) o;
        return id != null && Objects.equals(id, rate.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
