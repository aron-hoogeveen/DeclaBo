package ch.bolkhuis.declabo.fund;

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
@RequestMapping("api/funds")
public class FundController {

    private transient FundRepository repository;

    private transient FundModelAssembler assembler;

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
        if (fund.getId() != null) {
            throw new IllegalArgumentException("A new fund must not have an id");
        }

        fund = repository.saveAndFlush(fund);

        return ResponseEntity
                .created(linkTo(methodOn(FundController.class).getOne(fund.getId())).toUri())
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                .body(assembler.toModel(fund));
    }

    @PostMapping("/credit")
    ResponseEntity<?> createCredit(@Valid @RequestBody CreditFund fund) {
        if (fund.getId() != null) {
            throw new IllegalArgumentException("A new fund must not have an id");
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
        if (fund.getId() == null) {
            fund = repository.saveAndFlush(fund);
            return ResponseEntity
                    .created(linkTo(methodOn(FundController.class).getOne(fund.getId())).toUri())
                    .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                    .body(assembler.toModel(fund));
        }

        fund = repository.saveAndFlush(fund);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                .body(assembler.toModel(fund));
    }

    @PutMapping("/credit")
    ResponseEntity<?> createOrUpdateCredit(@Valid @RequestBody CreditFund fund) {
        if (fund.getId() == null) {
            fund = repository.saveAndFlush(fund);
            return ResponseEntity
                    .created(linkTo(methodOn(FundController.class).getOne(fund.getId())).toUri())
                    .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                    .body(assembler.toModel(fund));
        }

        fund = repository.saveAndFlush(fund);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LOCATION, linkTo(methodOn(FundController.class).getOne(fund.getId())).toString())
                .body(assembler.toModel(fund));
    }

}