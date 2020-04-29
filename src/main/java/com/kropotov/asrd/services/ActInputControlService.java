package com.kropotov.asrd.services;

import com.kropotov.asrd.dto.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;

public interface ActInputControlService extends CrudService<ActInputControl, Long> {
    ActInputControlDto saveDto(ActInputControlDto dto);
    ActInputControlDto getDtoById(Long id);
}
