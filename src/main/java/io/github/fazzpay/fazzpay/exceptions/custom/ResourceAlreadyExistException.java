package io.github.fazzpay.fazzpay.exceptions.custom;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistException extends RuntimeException {

  public ResourceAlreadyExistException(String resourceName, String fieldName, Serializable fieldValue) {
    super(String.format("%s already found with %s %s", resourceName, fieldName, fieldValue));
  }

}
