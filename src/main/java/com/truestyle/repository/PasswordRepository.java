package com.truestyle.repository;

import com.truestyle.entity.Password;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "passwords")
public interface PasswordRepository extends CrudRepository<Password, Long> {
}
