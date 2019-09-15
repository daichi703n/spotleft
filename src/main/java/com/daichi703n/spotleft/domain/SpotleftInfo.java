package com.daichi703n.spotleft.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotleftInfo {
  private String name;
  private String deployment;
  private String instanceId;
  private String type;
  private String lifecycle;
  private String state;
  private String launchTime;
  private Boolean requireSpot = true;
  private Boolean isSaved = false;
}