package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.StopListRequest;
import peaksoft.dto.StopListResponse;

import java.util.List;
import java.util.Optional;

public interface StopListService {

    SimpleResponse createList(Long menuId, StopListRequest request);

    List<StopListResponse>allStopLists();

   Optional<StopListResponse> findById(Long listId);

    SimpleResponse updateList(Long listId, StopListRequest request);

    SimpleResponse deleteList(Long listId);

}
