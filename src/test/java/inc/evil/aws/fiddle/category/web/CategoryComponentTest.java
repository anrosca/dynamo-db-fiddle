package inc.evil.aws.fiddle.category.web;

import inc.evil.aws.fiddle.category.domain.Category;
import inc.evil.aws.fiddle.common.AbstractWebIntegrationTest;
import inc.evil.aws.fiddle.common.ComponentTest;
import inc.evil.aws.fiddle.common.DynamoCsv;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ComponentTest
public class CategoryComponentTest extends AbstractWebIntegrationTest {

    @Test
    @DynamoCsv(value = "/test-data/categories/two-categories.csv", entityType = Category.class)
    public void shouldBeAbleToFindAllCategories() {
        CategoryResponse[] expectedCategories = {
            CategoryResponse.builder()
                            .name("Anime")
                            .id("01735c3-6da7-4684-62d3-47af5d5dc44e")
                            .build(),
            CategoryResponse.builder()
                            .name("Software development")
                            .id("01735c3-5da7-4684-82d3-37af5d5dc44f")
                            .build()

        };
        RequestEntity<Void> request = makeRequestFor("/api/v1/categories", HttpMethod.GET);

        ResponseEntity<CategoryResponse[]> response = restTemplate.exchange(request, CategoryResponse[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedCategories);
    }
}
