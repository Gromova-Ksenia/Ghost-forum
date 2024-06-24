package org.project.ghost_forum.service;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.TagDto;
import org.project.ghost_forum.mapper.TagMapper;
import org.project.ghost_forum.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.project.ghost_forum.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository repository;

    public Set<Tag> getTags(List<UUID> tagIds){
        return tagIds.stream()
                .map(repository::findById)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

}
