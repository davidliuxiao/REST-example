package com.roche.product.server.dataprovider;

import com.roche.product.server.domains.BasicEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeleteCrudRepository<T extends BasicEntity, I> extends CrudRepository<T, I> {
    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.active = true")
    List<T> findAll();


    @Override
    @Transactional(readOnly = true)
    @Query("select e from #{#entityName} e where e.id = ?1 and e.active = true")
    Optional<T> findById(I id);


    @Override
    @Query("update #{#entityName} e set e.active=false where e.id = ?1")
    @Transactional
    @Modifying
    void deleteById(I id);

}
