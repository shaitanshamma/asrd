package com.kropotov.asrd.services.springdatajpa.docs;

import com.kropotov.asrd.converters.docs.ActInputControlToDto;
import com.kropotov.asrd.dto.docs.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.repositories.ActInputControlRepository;
import com.kropotov.asrd.services.ActInputControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActInputControlSDService implements ActInputControlService {

    private final ActInputControlRepository actInputControlRepository;
    //private final DtoToActInputControl dtoToActInputControl;
    private final ActInputControlToDto actInputControlToDto;

    @Override
    public List<ActInputControl> getAll() {
        List<ActInputControl> actInputControlList = new ArrayList<>();
        actInputControlRepository.findAll().forEach(actInputControlList::add);
        return actInputControlList;
    }

    public Page<ActInputControl> getAll(Pageable pageable) {
        return actInputControlRepository.findAll(pageable);
    }

    @Override
    public Optional<ActInputControl> getById(Long id) {
        return id == null ? Optional.empty() : actInputControlRepository.findById(id);
    }

    @Override
    @Transactional
    public ActInputControl save(ActInputControl act) {
        return actInputControlRepository.save(act);
    }


/*    @Override
    @Transactional
    public ActInputControlDto saveDto(ActInputControlDto actDto) {
        ActInputControl detachedAct = dtoToActInputControl.convert(actDto);
        ActInputControl savedAct = actInputControlRepository.save(detachedAct);
        return actInputControlToDto.convert(savedAct);
    }*/

    @Override
    @Transactional
    public ActInputControlDto getDtoById(Long id) {
        return actInputControlToDto.convert(getById(id).orElse(new ActInputControl()));
    }

    @Override
    public void delete(Long id) {
        actInputControlRepository.findById(id);
    }
}
