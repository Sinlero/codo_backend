package Application.Data.Repositories.UserRepositories;

import Application.Entities.UserEntities.Administrator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends CrudRepository<Administrator, Long> {

    Optional<Administrator> findAdministratorByLogin(String login);
}
