package inc.evil.aws.fiddle.category.web;

import inc.evil.aws.fiddle.category.domain.Category;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponse {
    private String id;
    private String name;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                               .id(category.getId())
                               .name(category.getName())
                               .build();
    }
}
