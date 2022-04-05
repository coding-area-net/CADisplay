package net.codingarea.common.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Config {
  private String databaseHost;
  private int databasePort;
  private String databaseUser;
  private String databasePassword;
  private String databaseName;
}
