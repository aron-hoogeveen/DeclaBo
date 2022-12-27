package ch.bolkhuis.declabo.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public @NotNull EntityModel<User> toModel(@NotNull User entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(UserController.class).getOne(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel("users")
        );
    }

}
