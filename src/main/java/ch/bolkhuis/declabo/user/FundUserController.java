package ch.bolkhuis.declabo.user;

import ch.bolkhuis.declabo.exceptions.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The Controller for the FundUser class.
 *
 * This controller has only methods for:
 *   - getting all fundusers
 *   - getting a single funduser
 *   - creating a new funduser
 *
 * for other actions (such as updating certain fields) the other controllers can be used
 * (FuncController, UserController).
 */
@Controller
@RequestMapping("api/fundusers")
public class FundUserController {

    public static final Map<String, String> errorMessages = Map.of(
            "constraintFullname", "The combination of name and surname already exists",
            "constraintRoom", "There already exists a user with that room number",
            "constraintEmail", "This email is already in use"
    );

    private transient FundUserRepository repository;

    private transient FundUserModelAssembler assembler;

    @Autowired
    public FundUserController(FundUserRepository repository, FundUserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping()
    ResponseEntity<?> getAll() {
        List<EntityModel<FundUser>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(users,
                linkTo(methodOn(FundUserController.class).getAll()).withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable Long id) {
        FundUser user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @PostMapping()
    ResponseEntity<?> create(@Valid @RequestBody FundUser user) {
        Map<String, String> errors = new HashMap<>();

        if (repository.existsByEmail(user.getEmail())) {
            errors.put("email", errorMessages.get("constraintEmail"));
        }
        if (repository.existsByNameAndSurname(user.getName(), user.getSurname())) {
            errors.put("full-name", errorMessages.get("constraintFullname"));
        }
        if (repository.existsByRoom(user.getRoom())) {
            errors.put("room", errorMessages.get("constraintRoom"));
        }

        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }

        user = repository.saveAndFlush(user);

        return ResponseEntity
                .created(linkTo(methodOn(FundUserController.class).getOne(user.getId())).toUri())
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(UserController.class).getOne(user.getId())).toString())
                .body(assembler.toModel(user));
    }

}
