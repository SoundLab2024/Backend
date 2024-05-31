package com.soundlab.service;

import com.soundlab.domain.Library;
import com.soundlab.dto.LibraryDTO;
import com.soundlab.repository.LibraryRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.exceptions.LibraryNotFoundException;
import com.soundlab.utils.mappers.LibraryMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService implements BaseService<Library, LibraryDTO, Long, Payload> {

    private final LibraryRepository repository;
    private final LibraryMapper mapper;

    @Override
    public LibraryDTO getSingle(Long id) {
        return this.mapper.toDTO(this.repository.findById(id).orElseThrow(()->new LibraryNotFoundException(String.valueOf(id))));
    }

    @Override
    public List<LibraryDTO> getAll() {
        return null;
    }

    @Override
    public Payload insert(Library library) {
        return null;
    }

    @Override
    public Payload update(Library library) {
        return null;
    }

    @Override
    public Payload delete(Long Long) {
        return null;
    }
}
