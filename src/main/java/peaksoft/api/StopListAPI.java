package peaksoft.api;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.StopListRequest;
import peaksoft.dto.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stopLists")
@Tag(name = "StopList API")
public class StopListAPI {
    private final StopListService stopListService;

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @GetMapping("/getAll")
    List<StopListResponse> getAllLists() {
        return stopListService.allStopLists();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping("/create/{menuId}")
    SimpleResponse createStopList(@PathVariable Long menuId,
                                  @RequestBody StopListRequest request) {
        return stopListService.createList(menuId, request);
    }

    @PermitAll
    @GetMapping("/byId/{listId}")
    Optional<StopListResponse> findById(@PathVariable Long listId) {
        return stopListService.findById(listId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/update/{listId}")
    SimpleResponse updateList(@PathVariable Long listId,
                              @RequestBody StopListRequest request) {
        return stopListService.updateList(listId, request);
    }

    @DeleteMapping("/delete/{listId}")
    SimpleResponse deleteList(@PathVariable Long listId) {
        return stopListService.deleteList(listId);
    }


}
