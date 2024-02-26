package io.github.fazzpay.fazzpay.payloads.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

   private String name;
    
   private String password;
}
