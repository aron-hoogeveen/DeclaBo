package ch.bolkhuis.declabo.fund;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FundModelAssembler implements RepresentationModelAssembler<Fund, EntityModel<Fund>> {

    @Override
    public @NotNull EntityModel<Fund> toModel(@NotNull Fund entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FundController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(FundController.class).getAll()).withRel("funds")
        );
    }

}
