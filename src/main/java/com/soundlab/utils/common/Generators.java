package com.soundlab.utils.common;
import java.security.SecureRandom;

/**
 * Random String Generator utility
 */
public class Generators {
  /** Charset used to generate a random string */
  private static final String charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  /** Actual generator */
  private static final SecureRandom generator = new SecureRandom();

  /**
   * Random string generation
   *
   * @param length Length of the string to generate
   * @return Random String
   */
  public static String generate(int length) {
    return generator.ints(length, 0, charset.length()).mapToObj(charset::charAt)
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
  }
}