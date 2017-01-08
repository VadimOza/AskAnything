package com.askanything.models.repositories;

import com.askanything.models.entitys.Tables.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by VadimOz on 06.01.17.
 */
@Repository
public interface AuthoritiRepository extends CrudRepository<Authorities,Long> {
}
