package com.daichi703n.spotleft.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="instances")
public class SavedInstances{

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  
  // @Column(length = 255)
  @Column(name="name")
  private String name;

  // @Column(length = 255)
  @Column(name="deployment")
  private String deployment = "-";

  @Column(name="requireSpot")
  private Boolean requireSpot;

}
