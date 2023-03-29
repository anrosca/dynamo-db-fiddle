package inc.evil.aws.fiddle.common;

import java.io.IOException;
import java.util.Set;

import org.springframework.boot.test.autoconfigure.filter.AnnotationCustomizableTypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

public class DynamoDbTypeExcludeFilter extends AnnotationCustomizableTypeExcludeFilter {
    private static final Set<Class<?>> DEFAULT_INCLUDES = Set.of(Repository.class);

    private final DynamoDbTest annotation;

    public DynamoDbTypeExcludeFilter(Class<?> testClass) {
        this.annotation = AnnotatedElementUtils.getMergedAnnotation(testClass, DynamoDbTest.class);
    }

    @Override
    protected boolean hasAnnotation() {
        return this.annotation != null;
    }

//    @Override
//    protected boolean defaultInclude(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
//        if (super.defaultInclude(metadataReader, metadataReaderFactory)) {
//            return true;
//        }
//        for (Class<?> repository : this.annotation.repositories()) {
//            if (isTypeOrAnnotated(metadataReader, metadataReaderFactory, repository)) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    protected ComponentScan.Filter[] getFilters(FilterType type) {
        return new ComponentScan.Filter[0];
    }

    @Override
    protected boolean isUseDefaultFilters() {
        return true;
    }

    @Override
    protected Set<Class<?>> getDefaultIncludes() {
//        return ObjectUtils.isEmpty(this.annotation.repositories()) ? DEFAULT_INCLUDES : Set.of();
        return DEFAULT_INCLUDES;
    }

    @Override
    protected Set<Class<?>> getComponentIncludes() {
        return Set.of();
    }
}
