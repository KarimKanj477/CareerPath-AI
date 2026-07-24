package com.careerpath.careerpathai.repository;
import com.careerpath.careerpathai.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    boolean existsByName(String name);
    List<Role> findByNameContainingIgnoreCase(String name);
    Optional<Role> findByName(String name);


}
