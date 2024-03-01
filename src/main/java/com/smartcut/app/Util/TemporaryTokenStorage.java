package com.smartcut.app.Util;

import java.util.HashMap;
import java.util.Map;

public class TemporaryTokenStorage {

  /**
   * Esta clase permite gestionar el almacenamiento de los tokens
   */
  private TemporaryTokenStorage() {}

  private static final Map<String, String> tokenWhiteListStorage = new HashMap<>();
  private static final Map<String, String> tokenBlackListStorage = new HashMap<>();

  /**
   * Permite registra el token generado al momento de iniciar sesión, de acuerdo al usuario que sé auténtica
   * @param subject
   * @param token
   */
  public static void addTokenToWhiteList(String subject, String token) {
    tokenWhiteListStorage.put(subject, token);
  }

  /**
   * Se obtiene el token almacenado a partir de una clave, la cual es el nombre de usuario
   * @param subject
   * @return el token almacenado que corresponde al usuario autenticado
   */
  public static String getTokenOfWhiteList(String subject) {
    return tokenWhiteListStorage.get(subject);
  }

  /**
   * Permite registrar el token a la lista negra,a partir del nombre de usuario
   * @param subject
   * @param token
   */
  public static void addTokenToBlackList(String subject, String token) {
    tokenBlackListStorage.put(subject, token);
  }

  /**
   * Válida si el token obtenido de la lista negra con el username, es igual al que se envia,
   * de ser así se considera un toquen inhabilitado para usarse.
   * @param subject
   * @param token
   * @return un boolean que confirmando si se encuentra cancelado o no.
   */

  public static Boolean isTokenCancellation(String subject, String token) {
    return token.equals(getTokenOfBlackList(subject));
  }

  /**
   * Permite obtener un token almacenado en la lista negra a partir del nombre de usuario
   * @param subject
   * @return
   */
  public static String getTokenOfBlackList(String subject) {
    return tokenBlackListStorage.get(subject);
  }
}
