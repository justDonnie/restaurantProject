package peaksoft.service;

import peaksoft.dto.ChequeRequest;
import peaksoft.dto.ChequeResponse;
import peaksoft.dto.SimpleResponse;

import java.util.List;

public interface ChequeService {
    List<ChequeResponse> getAll();

    SimpleResponse save(ChequeRequest chequeRequest);
}
