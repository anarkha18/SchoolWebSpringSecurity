package com.schoolweb.entitys;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, message = "name must be at least 3 characters")
    private String name;
    @NotBlank(message = "dob cannot be blank")
    @Size(min = 3, message = "dob must be at least 3 characters")
    private String dob;
    @NotBlank(message = "phone cannot be blank")
    @Pattern(regexp = "\\d{5}", message = "Phone must have 5 digits and contain only numbers")
    private String phone;

    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @Valid
    private LoginCredentials user;


}
