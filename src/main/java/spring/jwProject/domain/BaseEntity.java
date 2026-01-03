package spring.jwProject.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity {
    private String createdBy;
    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)   // YYYY-MM-DD HH:mm:SS
    private LocalDateTime createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;
}
