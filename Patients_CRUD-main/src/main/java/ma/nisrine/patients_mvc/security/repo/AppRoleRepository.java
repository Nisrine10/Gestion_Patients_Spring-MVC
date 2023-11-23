package ma.nisrine.patients_mvc.security.repo;


import ma.nisrine.patients_mvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {

}