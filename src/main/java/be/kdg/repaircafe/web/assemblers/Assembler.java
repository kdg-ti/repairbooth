package be.kdg.repaircafe.web.assemblers;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public abstract class Assembler<S, T> {

    private final Class<S> sourceClass;
    private final Class<T> resourceClass;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired(required = false)
    public Assembler(Class<S> sourceClass, Class<T> resourceClass) {
        this.sourceClass = sourceClass;
        this.resourceClass = resourceClass;
    }

    public T toResource(S source) {
        return mapperFacade.map(source, resourceClass);
    }

    public List<T> toResources(Iterable<? extends S> sources) {
        return StreamSupport
                .stream(sources.spliterator(), false)
                .map(s -> toResource(s))
                .collect(Collectors.toList());
    }

    public S fromResource(T resource) {
        return mapperFacade.map(resource, sourceClass);
    }

    public List<S> fromResources(Iterable<? extends T> resources) {
        return StreamSupport
                .stream(resources.spliterator(), false)
                .map(r -> fromResource(r))
                .collect(Collectors.toList());
    }
}