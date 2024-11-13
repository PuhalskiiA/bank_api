package com.example.sberstart_test_step.mapper;

public interface BaseMapper<M, DTO> {
    M toModel(DTO dto);

    DTO toDto(M model);
}