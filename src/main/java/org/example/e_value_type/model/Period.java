package org.example.e_value_type.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Period {
    private LocalDate startDate;
    private LocalDate endDate;
}
