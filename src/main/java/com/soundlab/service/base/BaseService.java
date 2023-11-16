package com.soundlab.service.base;

import java.util.List;

/**
 * Base Service used to define upfront the methods that all the services implementing it has to
 * support
 */
public interface BaseService<S, T, ID, K> {

  /**
   * Retrieve a single Object
   *
   * @param id identifier of the Object
   * @return Infos
   */
  T getSingle(ID id);

  /**
   * Retrieve multiple Object
   *
   * @return Objects Infos
   */
  List<T> getAll();

  /**
   * Insert a single Object
   *
   * @param s Object data
   * @return Response
   */
  K insert(S s);

  /**
   * Update a single Object
   *
   * @param s New object data
   * @return Response
   */
  K update(S s);

  /**
   * Delete a single Object
   *
   * @param id Identifier of the object
   * @return Response
   */
  K delete(ID id);
}