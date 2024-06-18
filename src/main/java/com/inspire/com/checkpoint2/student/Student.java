package com.inspire.com.checkpoint2.student;

import com.inspire.com.checkpoint2.user_app.UserApp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private String description;
    private String imgUrl;
    private String githubUrl;
    private String linkedinUrl;

    @OneToOne
    private UserApp user;
}