package com.kropotov.asrd.services;

import com.kropotov.asrd.dto.docs.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActInputControlService extends CrudService<ActInputControl, Long> {
    //ActInputControlDto saveDto(ActInputControlDto dto);
    ActInputControlDto getDtoById(Long id);
    Page<ActInputControl> getAll(Pageable pageable);
}
