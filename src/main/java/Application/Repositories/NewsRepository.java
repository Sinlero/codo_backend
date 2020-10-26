package Application.Repositories;

import Application.Entities.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    Optional<News> findById(Long id);

    Optional<News> findByImg(String img);
}
