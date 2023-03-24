package inc.evil.aws.fiddle.category.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.category.domain.Category;

public interface CategoryRepository {
    List<Category> findAll();

    Category create(Category categoryToCreate);

    Optional<Category> findById(String id);

    Optional<Category> deleteById(String id);
}
