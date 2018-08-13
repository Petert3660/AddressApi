package com.ptconsultancy.repositories;

import com.ptconsultancy.entities.AddressEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AddressEntityRepository extends CrudRepository<AddressEntity, Long> {

    List<AddressEntity> findByUserId(Long userId);
}
