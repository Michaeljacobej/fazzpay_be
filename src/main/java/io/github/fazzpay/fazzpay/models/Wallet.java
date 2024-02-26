package io.github.fazzpay.fazzpay.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

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
public class Wallet {

  @Id
  @Column(name = "user_id")
  @JsonIgnore
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @JsonIgnore
  private String pin;

  private Long balance = 0L;

  @UpdateTimestamp
  @JsonIgnore
  private LocalDateTime updatedAt;

}
