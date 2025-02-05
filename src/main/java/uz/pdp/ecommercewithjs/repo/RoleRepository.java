package uz.pdp.ecommercewithjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ecommercewithjs.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}