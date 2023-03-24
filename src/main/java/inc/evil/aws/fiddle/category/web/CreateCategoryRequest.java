package inc.evil.aws.fiddle.category.web;

import inc.evil.aws.fiddle.category.domain.Category;

public record CreateCategoryRequest(String name) {

    public Category toCategory() {
        return Category.builder()
                    .name(name)
                    .build();
    }
}
