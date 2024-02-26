package io.github.fazzpay.fazzpay.models;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @Id
  @Column(name = "user_id")
  @JsonIgnore
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  private String name;

  private String image;

  @UpdateTimestamp
  @JsonIgnore
  private LocalDateTime updatedAt;

  public String getImage() {
    String IMAGE_DIR = "/user-image/";
    if (Objects.isNull(this.image)) {
      return null;
    }
    return ServletUriComponentsBuilder.fromCurrentContextPath().path(IMAGE_DIR).path(this.image).build().toUriString();
  }

}
