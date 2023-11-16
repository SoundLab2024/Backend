package com.soundlab.utils.common;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.experimental.UtilityClass;

/**
 * Utility class for {@link Date} & {@link LocalDateTime} operation
 */
@UtilityClass
public class DateUtils {
  /** Default ZONE ID based on the system */
  public final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();


  /**
   * LocalDateTime converter
   *
   * @return {@link Date}
   */
  public Date toDate() {
    return Date.from(LocalDateTime.now().atZone(DEFAULT_ZONE_ID).toInstant());
  }

  /**
   * LocalDateTime converter
   *
   * @return {@link LocalDateTime}
   */
  public LocalDateTime toLocalDateTime() {
    return LocalDateTime.now(DEFAULT_ZONE_ID);
  }
}