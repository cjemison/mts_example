package com.cjemison.mts.controller.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by cjemison on 7/22/16.
 */
public class StudentRequestVO {
  @NotBlank
  private String id;

  @JsonCreator
  public StudentRequestVO() {
  }

  public StudentRequestVO(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "StudentRequestVO{" +
          "id='" + id + '\'' +
          '}';
  }
}
