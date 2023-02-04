package ch.bolkhuis.declabo.fund;

import ch.bolkhuis.declabo.exceptions.ConstraintViolationException;
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
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("api/funds")
public class FundController {

    private transient FundRepository repository;

    private transient FundModelAssembler assembler;

    public static final Map<String, String> errorMessages = Map.of(
            "constraintName", "This name already exists"
    );

    @Autowired
    public FundController(FundRepository repository, FundModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping()
    ResponseEntity<?> getAll() {
        List<EntityModel<Fund>> funds = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(funds,
                linkTo(methodOn(FundController.class).getAll()).withSelfRel()
        ));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getOne(@PathVariable Long id) {
        Fund fund = repository.findById(id).orElseThrow(() -> new FundNotFoundException(id));
        return ResponseEntity.ok(assembler.toModel(fund));
    }

    @PostMapping("/debit")
    ResponseEntity<?> createDebit(@Valid @RequestBody DebitFund fund) {
        return createFund(fund);
    }

    @PostMapping("/credit")
    ResponseEntity<?> createCredit(@Valid @RequestBody CreditFund fund) {
        return createFund(fund);
    }

    /**
     * Creates a new Fund in the repository.
     *
     * @param fund the fund to create in the repository
     * @return a ResponseEntity
     * @throws IllegalArgumentException if field {@code id} is non-null
     * @throws ConstraintViolationException if any constraints are violated
     */
    private ResponseEntity<?> createFund(Fund fund) {
        if (fund.getId() != null) {
            throw new IllegalArgumentException("A new fund must not have an id");
        }

        if (repository.existsByFundName(fund.getFundName())) {
            throw new ConstraintViolationException(Map.of("fundName", errorMessages.get("constraintName")));
        }

        fund = repository.saveAndFlush(fund);

        return ResponseEntity
                .created(linkTo(methodOn(FundController.class).getOne(fund.getId())).toUri())
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                .body(assembler.toModel(fund));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok(linkTo(methodOn(FundController.class).getAll()).withRel("funds"));
    }

    @PutMapping("/debit")
    ResponseEntity<?> createOrUpdateDebit(@Valid @RequestBody DebitFund fund) {
        return createOrUpdateFund(fund);
    }

    @PutMapping("/credit")
    ResponseEntity<?> createOrUpdateCredit(@Valid @RequestBody CreditFund fund) {
        return createOrUpdateFund(fund);
    }

    /**
     * Creates or updates the given fund in the repository.
     *
     * @param fund the fund to create or update
     * @return a ResponseEntity
     */
    private ResponseEntity<?> createOrUpdateFund(Fund fund) {
        if (fund.getId() == null) {
            return createFund(fund);
        }

        // update fund
        if (repository.existsByFundNameAndIdNot(fund.getFundName(), fund.getId())) {
            throw new ConstraintViolationException(Map.of("fundName", errorMessages.get("constraintName")));
        }

        fund = repository.saveAndFlush(fund);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                .body(assembler.toModel(fund));
    }

}
