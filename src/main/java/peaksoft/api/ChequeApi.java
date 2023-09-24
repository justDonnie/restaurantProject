package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.ChequeRequest;
import peaksoft.dto.ChequeResponse;
import peaksoft.dto.SimpleResponse;
import peaksoft.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
@Tag(name = "Cheque Api")
public class ChequeApi {
    private final ChequeService chequeService;

    @PermitAll
    @GetMapping
    List<ChequeResponse> getAll() {
        return chequeService.getAll();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping()
    SimpleResponse save(@RequestBody ChequeRequest chequeRequest){
        return chequeService.save(chequeRequest);
    }
}
