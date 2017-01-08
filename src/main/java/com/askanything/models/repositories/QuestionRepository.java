package com.askanything.models.repositories;

import com.askanything.models.entitys.Tables.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by VadimOz on 06.01.17.
 */
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long>{

}
