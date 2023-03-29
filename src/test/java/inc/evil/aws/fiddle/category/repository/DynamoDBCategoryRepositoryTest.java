package inc.evil.aws.fiddle.category.repository;

import java.util.List;
import java.util.Optional;

import inc.evil.aws.fiddle.category.domain.Category;
import inc.evil.aws.fiddle.common.AbstractDatabaseTest;
import inc.evil.aws.fiddle.common.DynamoCsv;
import inc.evil.aws.fiddle.common.DynamoDbTest;
import inc.evil.aws.fiddle.topic.domain.Topic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DynamoDbTest(repositories = DynamoDBCategoryRepository.class)
public class DynamoDBCategoryRepositoryTest extends AbstractDatabaseTest {

    @Autowired
    private DynamoDBCategoryRepository categoryRepository;

    @Test
    @DynamoCsv(value = "/test-data/categories/two-categories.csv", entityType = Category.class)
    @DynamoCsv(value = "/test-data/topics/two-topics.csv", entityType = Topic.class)
    public void shouldBeAbleToFindAllCategories() {
        List<Category> expectedCategories = List.of(
            Category.builder()
                    .name("Anime")
                    .partitionKey("Category#601735c3-6da7-4684-62d3-47af5d5dc44e")
                    .sortKey("Category#601735c3-6da7-4684-62d3-47af5d5dc44e")
                    .build(),
            Category.builder()
                    .name("Software development")
                    .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                    .sortKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                    .build()
        );

        List<Category> actualCategories = categoryRepository.findAll();

        assertThat(actualCategories).isEqualTo(expectedCategories);
    }

    @Test
    @DynamoCsv(entityType = Category.class)
    public void shouldBeAbleToCreateCategories() {
        Category categoryToCreate = Category.builder()
                                               .name("Software development")
                                               .partitionKey("Category")
                                               .sortKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                                               .build();
        List<Category> expectedCategories = List.of(categoryToCreate);

        Category actualCategory = categoryRepository.create(categoryToCreate);

        assertThat(actualCategory).isEqualTo(categoryToCreate);
        assertThat(categoryRepository.findAll()).isEqualTo(expectedCategories);
    }

    @Test
    @DynamoCsv(value = "/test-data/categories/one-category.csv", entityType = Category.class)
    public void shouldBeAbleToFindCategoriesById() {
        Category expectedCategory = Category.builder()
                                            .name("Software development")
                                            .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                                            .sortKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                                            .build();

        Optional<Category> actualCategory = categoryRepository.findById("501735c3-5da7-4684-82d3-37af5d5dc44f");

        assertThat(actualCategory).isEqualTo(Optional.of(expectedCategory));
    }

    @Test
    @DynamoCsv(value = "/test-data/categories/one-category.csv", entityType = Category.class)
    public void shouldBeAbleToDeleteCategoriesById() {
        Category expectedCategory = Category.builder()
                                            .name("Software development")
                                            .partitionKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                                            .sortKey("Category#501735c3-5da7-4684-82d3-37af5d5dc44f")
                                            .build();

        Optional<Category> actualCategory = categoryRepository.deleteById("501735c3-5da7-4684-82d3-37af5d5dc44f");

        assertThat(actualCategory).isEqualTo(Optional.of(expectedCategory));
        assertThat(categoryRepository.findAll()).isEmpty();
    }
}
