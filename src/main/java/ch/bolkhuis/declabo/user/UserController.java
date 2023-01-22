package ch.bolkhuis.declabo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("api/users")
public class UserController {

    private transient UserRepository repository;

    private transient UserModelAssembler assembler;

    @Autowired
    public UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping()
    ResponseEntity<?> getAll() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable Long id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return ResponseEntity.ok(assembler.toModel(user));
    }

    /*
     * TODO check all parameters of the new user before processing. This is where the validation should happen
     */
    @PostMapping()
    ResponseEntity<?> create(@Valid @RequestBody User user) {
//        if (user == null)
//            throw new UserBadRequestException("User could not be made. Probably not all fields were presented");
        user = repository.saveAndFlush(user);

        return ResponseEntity
                .created(linkTo(methodOn(UserController.class).getOne(user.getId())).toUri())
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(UserController.class).getOne(user.getId())).toString())
                .body(assembler.toModel(user));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok(linkTo(methodOn(UserController.class).getAll()).withRel("users"));
    }

    /**
     * Creates or updates a User.
     *
     * @param user the user to update or create
     * @return the created or updated user
     */
    @PutMapping()
    ResponseEntity<?> createOrUpdate(@Valid @RequestBody User user) {
        if (user.getId() == null) {
            user = repository.saveAndFlush(user);
            return ResponseEntity
                    .created(linkTo(methodOn(UserController.class).getOne(user.getId())).toUri())
                    .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(UserController.class).getOne(user.getId())).toString())
                    .body(assembler.toModel(user));
        }

        user = repository.saveAndFlush(user);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(UserController.class).getOne(user.getId())).toString())
                .body(assembler.toModel(user));
    }

}
