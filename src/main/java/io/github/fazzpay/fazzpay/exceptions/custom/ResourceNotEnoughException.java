package io.github.fazzpay.fazzpay.exceptions.custom;

import java.io.Serializable;

public class ResourceNotEnoughException extends RuntimeException {

  public ResourceNotEnoughException(String resourceName, String fieldName, Serializable fieldValue) {
    super(String.format("%s don't have enough %s for %s", resourceName, fieldName, fieldValue));
  }

}
