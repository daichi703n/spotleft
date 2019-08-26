package com.daichi703n.spotleft.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotleftInfo {
  private String name;
  private String deployment;
  private String id;
  private String type;
  private String lifeCycle;
  private String state;
  private String launchTime;
  private Boolean requireSpot = false;
  private Boolean isSaved = false;
}