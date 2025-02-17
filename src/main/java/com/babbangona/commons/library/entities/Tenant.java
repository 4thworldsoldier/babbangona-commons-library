package com.babbangona.commons.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * The persistent class for the tenant database table.
 * 
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity(name = "tenant")
public class Tenant extends BaseEntity
{

  @Column(name = "is_active", columnDefinition="TINYINT")
  private Boolean isActive;
  
  @Column(nullable = false, length = 45)
  private String name;

  public Tenant()
  {
  }

}