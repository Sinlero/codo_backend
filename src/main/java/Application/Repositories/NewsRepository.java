package Application.Repositories;

import Application.Entities.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {

    List<News> findAllByOrderByIdDesc();

    @Override
    List<News> findAll();
}
