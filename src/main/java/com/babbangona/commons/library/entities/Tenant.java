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
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity(name = "tenant")
public class Tenant extends BaseEntity
{

  @Column(nullable = false, columnDefinition="TINYINT")
  private Boolean active;
  
  @Column(nullable = false, length = 45)
  private String name;

  @JsonIgnore
  @Column(nullable = true, name="last_balance_alert")
  private Timestamp lastBalanceAlert;
  
  @Column(nullable = true, name="address", length=255)
  private String address;
  
  @Column(nullable = true, name="phone", length=45)
  private String phone;
  
  @Column(nullable = true, name="contact_email", length=255)
  private String contactEmail;

  @Column(name = "created_at", insertable = false, updatable = false)
  private Timestamp createdAt;

  public Tenant()
  {
  }

}