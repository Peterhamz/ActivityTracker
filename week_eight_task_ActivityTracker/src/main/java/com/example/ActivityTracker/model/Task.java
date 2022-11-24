package com.example.ActivityTracker.model;

import com.example.ActivityTracker.Enum.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;


    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate completedAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "user_fk"
            )
    )
    private  User user;


}
