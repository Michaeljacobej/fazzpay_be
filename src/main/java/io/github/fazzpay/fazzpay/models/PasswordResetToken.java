package io.github.fazzpay.fazzpay.models;

import java.time.LocalDateTime;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @Column(unique = true)
  @JsonIgnore
  private String token;

  @OneToOne(fetch = FetchType.EAGER)
  private User user;

  private LocalDateTime expiryDate;

  public void generateResetToken() {
    Random random = new Random();
    String sixDigitNumber = "";
    String digits = "0123456789";
    int[] digitCount = new int[10];
    int sameDigitCount = 0;

    while (sixDigitNumber.length() < 6) {
      int index = random.nextInt(digits.length());
      char nextDigit = digits.charAt(index);
      if (digitCount[nextDigit - '0'] < 3) {
        sixDigitNumber += nextDigit;
        digitCount[nextDigit - '0']++;
        if (digitCount[nextDigit - '0'] == 3) {
          sameDigitCount++;
        }
      }
      if (sameDigitCount == 9) {
        break;
      }
    }
    this.token = sixDigitNumber;
  }

}