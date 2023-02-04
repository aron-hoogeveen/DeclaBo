package ch.bolkhuis.declabo.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FundUserModelAssembler implements RepresentationModelAssembler<FundUser, EntityModel<FundUser>> {

    @Override
    public @NotNull EntityModel<FundUser> toModel(@NotNull FundUser entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(FundUserController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(FundUserController.class).getAll()).withRel("fundusers")
        );
    }

}
