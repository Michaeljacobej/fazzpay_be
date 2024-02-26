package io.github.fazzpay.fazzpay.exceptions.custom;

import java.io.Serializable;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String resourceName, String fieldName, Serializable fieldValue) {
    super(String.format("%s not found with %s %s", resourceName, fieldName, fieldValue));
  }

}
