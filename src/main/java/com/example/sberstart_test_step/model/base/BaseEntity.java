package com.example.sberstart_test_step.model.base;

import com.example.sberstart_test_step.utils.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {
    @Id
    UUID id;

    @CreatedDate
    @Column(name = "created")
    LocalDate created;

    @LastModifiedDate
    @Column(name = "updated")
    LocalDate updated;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;
}
