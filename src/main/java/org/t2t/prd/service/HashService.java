package org.t2t.prd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.t2t.prd.dto.PrdHashDTO;
import org.t2t.prd.repository.HashMapper;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HashService {
    private final HashMapper hashMapper;
}
